import React, { useEffect, useState, useRef } from 'react'
import { useParams } from 'react-router-dom';

const CATEGORY = ['Truyện tranh', 'Lập trình', 'Tiếng anh', 'Tiểu thuyết'];

const getCurrentYear = () => {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = String(currentDate.getMonth() + 1).padStart(2, '0');
    const day = String(currentDate.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
}

export const BookView = () => {
    const { id } = useParams();
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [des, setDes] = useState('');
    const [release, setRelease] = useState(() => getCurrentYear());
    const [pages, setPages] = useState(0);
    const [category, setCategory] = useState(CATEGORY[0]);
    const [price, setPrice] = useState(0);
    const [selectedImage, setSelectedImage] = useState({});
    const [previewImage, setPreviewImage] = useState('');

    const [enableEdit, setEnableEdit] = useState(true);
    const [enableSubmit, setEnableSubmit] = useState(true);
    const inputFileRef = useRef(null);

    const handleImageChange = (event) => {
        const file = event.target.files[0];
        setSelectedImage(file);

        if (file?.name !== undefined) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setPreviewImage(reader.result);
            };
            reader.readAsDataURL(file);
        } else {
            setPreviewImage('');
        }
    };

    const handleImageRemove = () => {
        setSelectedImage({});
        setPreviewImage('');
    }

    const handleUSubmit = (e) => {
        e.preventDefault();
        if (enableSubmit) {
            let text = "Bạn có đồng ý thêm sách không?";
            if (window.confirm(text) !== true)
                return;
            const formData = new FormData();
            formData.append('id', parseInt(id));
            formData.append('title', title.trim());
            formData.append('author', author.trim());
            formData.append('description', des.trim());
            formData.append('release', release);
            formData.append('pages', pages);
            formData.append('category', category);
            formData.append('price', price);
            if (selectedImage?.name !== undefined) formData.append('file', selectedImage, selectedImage?.name);
            // console.log(selectedImage?.name);
            fetch((selectedImage?.name !== undefined) ? 'http://localhost:8080/upload-image' : 'http://localhost:8080/upload', {
                method: 'POST',
                headers: {
                    // "Content-Type": "application/json",
                    "Access-Control-Allow-Origin": "*",
                },
                body: formData
            })
                .then((response) => {
                    const data = response.json();
                    console.log(data);
                    alert("Thêm sách thành công!");
                    window.location.href = '/admin';
                })
                .catch((error) => {
                    if (error.includes("SyntaxError: Expected ',' or '}' after property value in JSON")) {
                        alert("Thêm sách thành công!");
                        window.location.href = '/admin';
                    }
                });
        } else setEnableSubmit(true);
    };

    useEffect(() => {
        const setBlobAsFile = (blob, name) => {
            const file = new File([blob], name, { type: 'image/png' });
            let list = new DataTransfer();
            list.items.add(file);
            if (inputFileRef.current) {
                setSelectedImage(list.files[0]);
                inputFileRef.current.files = list.files;
            }
        };

        if (parseInt(id) > 0) {
            setEnableEdit(false);
            setEnableSubmit(false);
            fetch('http://localhost:8080/book/' + id)
                .then(response => response.json())
                .then(book => {
                    console.log(book);
                    setTitle(book.title);
                    setAuthor(book.author);
                    setCategory(book.category);
                    setDes(book.description);
                    setPages(book.pages);
                    setRelease(book.release);
                    setPrice(book.price);

                    fetch('http://localhost:8080/image/' + book.title + '-' + book.image)
                        .then(response => response.blob())
                        .then(data => {
                            setBlobAsFile(data, book.image);

                            const imageUrl = URL.createObjectURL(data);
                            setPreviewImage(imageUrl);
                        }).catch(err => console.log(err))

                })
                .catch(err => console.log(err));
        }

    }, [inputFileRef, id]);

    return (
        <div className='d-flex justify-content-center'>
            <div className='justify-content-center'>
                <h1>
                    BookView
                </h1>
                <form className='mb-3 bg-secondary pe-5 ps-5 pt-3 pb-3 rounded' onSubmit={handleUSubmit} encType="multipart/form-data">
                    <div className='d-flex me-lg-5'>
                        <div className='justify-content-center me-lg-5'>
                            <div className='d-flex'>
                                <div className="mb-3 w-50">
                                    <label htmlFor="exampleFormControlInput1" className="form-label">Tiêu đề</label>
                                    <input type="text" className="form-control" id="exampleFormControlInput1" placeholder="Title..." value={title} onChange={e => setTitle(e.target.value)} required disabled={!enableEdit} />
                                </div>
                                <div className="mb-3 ms-lg-4 w-50">
                                    <label htmlFor="exampleFormControlInput2" className="form-label">Tác giả</label>
                                    <input type="text" className="form-control" id="exampleFormControlInput2" placeholder="Author..." value={author} onChange={e => setAuthor(e.target.value)} required disabled={!enableEdit} />
                                </div>
                            </div>

                            <div className="mb-3">
                                <label htmlFor="exampleFormControlTextarea1" className="form-label">Mô tả</label>
                                <textarea
                                    style={{ resize: 'none' }}
                                    rows={4}
                                    cols={50}
                                    className="form-control" id="exampleFormControlTextarea1"
                                    value={des}
                                    onChange={e => setDes(e.target.value)}
                                    disabled={!enableEdit}
                                ></textarea>
                            </div>

                            <div className='d-flex'>
                                <div className="mb-3 w-50">
                                    <label htmlFor="exampleFormControlInput3" className="form-label">Ngày phát hành</label>
                                    <input type="date" min="2000-01-01" max={getCurrentYear()}
                                        className="form-control" id="exampleFormControlInput3" placeholder="Release..." value={release} onChange={e => setRelease(e.target.value)} required disabled={!enableEdit} />
                                </div>
                                <div className="mb-3 ms-lg-4 w-50">
                                    <label htmlFor="exampleFormControlInput4" className="form-label">Số trang</label>
                                    <input type="number" className="form-control" id="exampleFormControlInput4" placeholder="Pages..." value={pages} onChange={e => setPages(e.target.value)} required disabled={!enableEdit} />
                                </div>
                            </div>

                            <div className='d-flex'>
                                <div className="mb-3 w-50">
                                    <label className="form-label" htmlFor="inputGroupSelect01">Thể loại</label>
                                    <select className="form-select" aria-label="Default select example" id='inputGroupSelect01' value={category} onChange={e => setCategory(e.target.value)} disabled={!enableEdit}>
                                        <option disabled>Open this select menu</option>
                                        {CATEGORY.map((cat, index) => (<option key={index}>{cat}</option>))}
                                    </select>
                                </div>
                                <div className="mb-3 ms-lg-4 w-50">
                                    <label htmlFor="exampleFormControlInput5" className="form-label">Giá sản phẩm</label>
                                    <input type="number" className="form-control" id="exampleFormControlInput5" placeholder="Price..." value={price} onChange={e => setPrice(e.target.value)} required disabled={!enableEdit} />
                                </div>
                            </div>
                        </div>
                        <div className="mb-3 ms-lg-5">
                            <div className='mb-3'>
                                <label htmlFor="formFileMultiple" className="form-label">Ảnh bìa</label>
                                <input className="form-control" id="formFileMultiple" type="file" onChange={handleImageChange} ref={inputFileRef} disabled={!enableEdit} />
                            </div>
                            {previewImage ? (
                                <>
                                    <img className='mb-3' src={previewImage} alt="Preview" style={{ height: '300px' }} />
                                    <div>
                                        <button className='btn btn-warning' onClick={handleImageRemove} disabled={!enableEdit}
                                        >Remove</button>
                                    </div>
                                </>
                            ) : (<div> <p>Chưa chọn ảnh bìa</p> </div>)}
                        </div>
                    </div>
                    {id === '-1' ? (
                        <button className='btn btn-primary' type='submit' >Add</button>
                    ) : (
                        enableEdit === true ?
                            (<button className='btn btn-primary' type='submit' >Save</button>)
                            :
                            (<button className='btn btn-primary' onClick={() => setEnableEdit(true)} >Edit</button>)
                    )}
                </form>
                <button className='btn btn-outline-primary' onClick={() => window.history.back()}>Quay lại</button>

            </div>

        </div>
    )
}
