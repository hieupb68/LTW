import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Comment } from './Comment';
import { Rating } from './Rating';

export const BookDetail = (props) => {

    const { id } = useParams();
    const [rating, setRating] = useState(0);
    const [book, setBook] = useState({});
    const [quantity, setQuantity] = useState(1);
    const [previewImage, setPreviewImage] = useState('');
    const [fbs, setFbs] = useState([]);
    const [comment, setComment] = useState('');
    const [userRate, setUserRate] = useState(0);

    const handleComment = () => {
        const ans = window.confirm('Bạn có muốn comment hay không?');
        if (ans === true) {
            if (comment.trim().length > 0 && userRate > 0) {
                const formData = new FormData();
                formData.append('id_user', props.id);
                formData.append('id_book', id);
                formData.append('rate', userRate);
                formData.append('message', comment);

                fetch('http://localhost:8080/upload-fb', {
                    method: 'POST',
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        setComment('');
                        setUserRate(0);
                        updateComment();
                        alert('Đánh giá thành công!');
                    })
                    .catch(err => console.log(err));
            } else {
                alert('Bạn hãy nhập suy nghĩ và đánh giá!!!');
            }
        }
    };

    const updateComment = () => {
        fetch('http://localhost:8080/book-detail/' + id)
            .then(response => response.json())
            .then(data => {
                const fb = data.fb;
                const r = parseFloat(data.avg_rate.toFixed(2));
                setFbs(fb);
                setRating(r);
            })
            .catch(err => console.log(err));
    }

    const handleBuy = (id) => {
        const ans = window.confirm('Bạn có xác nhận mua ' + quantity + ' quyển hay không?');
        if (ans === true) {
            const formData = new FormData();
            formData.append('id_user', props.id);
            formData.append('id_book', id);
            formData.append('quantity', quantity);

            fetch('http://localhost:8080/buy', {
                method: 'POST',
                body: formData
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    alert('Mua hàng thành công!');
                })
                .catch(err => console.log(err));
        }
    }

    useEffect(() => {
        fetch('http://localhost:8080/book-detail/' + id)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                const book = data.book;
                const fb = data.fb;
                const r = data.avg_rate;
                setBook(book);
                setFbs(fb);
                setRating(r);
                fetch('http://localhost:8080/image/' + book.title + '-' + book.image)
                    .then(response => response.blob())
                    .then(data => {
                        const imageUrl = URL.createObjectURL(data);
                        setPreviewImage(imageUrl);
                    }).catch(err => console.log(err))

            })
            .catch(err => console.log(err));
    }, [id]);

    return (
        <div className="container">
            <div className="card">
                <div className="card-body">
                    <h3 className="card-title">{book.title}</h3>
                    <div className="row">
                        <div className="col-lg-5 col-md-5 col-sm-6 border border-gray">
                            <div className="white-box text-center"><img src={previewImage} className="img-fluid" alt='Preview' /></div>
                        </div>
                        <div className="col-lg-7 col-md-7 col-sm-6">
                            <div className="w-100">
                                <h3 className="box-title mt-3 mb-3">Thông tin chung</h3>
                                <div className="table-responsive col-md-8 mx-auto">
                                    <table className="table table-striped table-product w-auto">
                                        <tbody>
                                            <tr>
                                                <td width="390">Tác giả</td>
                                                <td width="390">{book.author}</td>
                                            </tr>
                                            <tr>
                                                <td width="390">Thể loại</td>
                                                <td width="390">{book.category}</td>
                                            </tr>
                                            <tr>
                                                <td width="390">Số trang</td>
                                                <td width="390">{book.pages}</td>
                                            </tr>
                                            <tr>
                                                <td width="390">Ngày phát hành</td>
                                                <td width="390">{book.release}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div className='w-75 col-md-8 mx-auto mt-3'>
                                <h4 className="box-title">Thông tin sách</h4>
                                <p className='text-left'>{book.description}</p>
                                <h2 className="mt-3">
                                    {book?.price} VND
                                    {/* <small class="text-success">(36%off)</small> */}
                                </h2>
                            </div>
                            <div className="form-outline col-md-8 mx-auto mb-3" style={{ width: 5 + "rem" }}>
                                <label className="form-label" htmlFor="typeNumber">Số lượng</label>
                                <input type="number" id="typeNumber" className="form-control" value={quantity} onChange={(e) => { if (e.target.value > 0) setQuantity(e.target.value) }} />
                            </div>
                            <button className="btn btn-primary btn-rounded" onClick={() => handleBuy(book.id)}>Mua ngay</button>
                        </div>
                        <div>
                            <h3 className="mt-5">Feedback</h3>
                            <h4>Rating: {isNaN(rating) ? 0 : rating} / 5</h4>
                            <Rating rating={Math.floor(rating)} readOnly={true} />
                        </div>
                        <div>
                            <div className="card-footer py-3 border-0" style={{ 'backgroundColor': '#f8f9fa' }}>
                                <div className="d-flex flex-start w-100">
                                    <img className="rounded-circle shadow-1-strong me-3"
                                        src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/img%20(19).webp" alt="avatar" width="40"
                                        height="40" />
                                    <div className="form-outline w-75">
                                        <textarea className="form-control" id="textAreaExample" rows="3" col='4'
                                            style={{ background: '#fff', resize: 'none' }} value={comment} onChange={e => setComment(e.target.value)}></textarea>
                                        <label className="form-label" htmlFor="textAreaExample">Message</label>
                                    </div>
                                    <div className='mt-1 w-25'>
                                        <h3>Rating</h3>
                                        <Rating setUserRate={setUserRate} readOnly={false} />
                                    </div>

                                </div>
                                <div className="float-end">
                                    <button type="button" className="btn btn-primary btn-sm me-3" onClick={handleComment}>Post comment</button>
                                </div>
                            </div>
                        </div>
                        <div className='mt-3'>
                            {fbs.map((fb, index) => (
                                <Comment key={index} fb={fb} />
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div >
    )
}
