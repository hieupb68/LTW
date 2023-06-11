import React, { useEffect, useState } from 'react'

export const MyBook = (props) => {
    const [books, setBooks] = useState([]);

    const handleCancel = (id) => {

        const fetchBooks = async (id) => {
            try {
                const updatedItems = [...books];
                const itemToUpdate = updatedItems.find(item => item.id === id);
                if (itemToUpdate) {
                    const formData = new FormData();
                    formData.append('id', itemToUpdate.id);
                    const response = await fetch('http://localhost:8080/cancel', { method: 'POST', body: formData });
                    if (response.ok) {
                        itemToUpdate.state = -1;
                        setBooks(updatedItems);
                    }
                } else alert('Hủy đơn hàng thất bại!')
            } catch (error) {
                alert('Hủy đơn hàng thất bại!')
                console.error(error);
            }
        };

        const ans = window.confirm('Bạn có muốn hủy đơn hàng này không?');
        if (ans) {
            fetchBooks(id);
        }
    }

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const formData = new FormData();
                formData.append('id', props.id);
                const response = await fetch('http://localhost:8080/buyings', { method: 'POST', body: formData });
                const data = await response.json();
                const fetchImagePromises = data.map(async (book) => {
                    const imageUrl = `http://localhost:8080/image/${book?.title}-${book?.image}`;
                    const imageResponse = await fetch(imageUrl);
                    const blob = await imageResponse.blob();
                    const img = URL.createObjectURL(blob);

                    return { ...book, img };
                });

                const booksWithImages = await Promise.all(fetchImagePromises);
                setBooks(booksWithImages);
            } catch (error) {
                console.error(error);
            }
        };

        fetchBooks();
    }, [props.id]);

    return (
        <div className='container'>
            <div className='mb-3 d-flex'>
                <a href='/home' className='btn btn-outline-primary'>Mua sách tiếp</a>
            </div>
            <div className=" row hidden-lg-up gap-5">
                {books.map((book, index) => (
                    <div key={index} className="card mb-3" style={{ "width": "500px" }}>
                        <div className="row g-0">
                            <div className="col-md-4 mt-3 mb-3">
                                <img src={book.img} className="img-fluid rounded-start" alt="Cover" />
                            </div>
                            <div className="col-md-8">
                                <div className="card-body">
                                    <h5 className="card-title">{book.title}</h5>
                                    <p className='text-start'>Ngày đặt: {book.buying}</p>
                                    <p className='text-start'>Dự kiến nhận hàng: {book.receiving}</p>
                                    <p className='text-start'>Tổng tiền: {book.quantity * book.price} VND</p>
                                    <p className='text-start'>Trạng thái: {book.state === -1 ? 'Đã hủy' : ((book.state === 1) ? 'Đã giao' : 'Đang giao')}</p>
                                    {book.state === 0 && <button className='btn btn-danger' onClick={() => handleCancel(book.id)}>Hủy Đơn</button>}
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}
