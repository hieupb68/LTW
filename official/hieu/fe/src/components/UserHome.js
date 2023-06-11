import React, { useEffect, useState } from 'react'

export const UserHome = (props) => {

    const [books, setBooks] = useState([]);

    useEffect(() => {
        const fetchBooks = async () => {
            try {
                const response = await fetch('http://localhost:8080/books');
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
    }, []);

    return (
        <div className='container'>
            <div className="row hidden-lg-up">
                {books.map((book, index) => (
                    <div className="col-lg-4 mb-3" key={index}>
                        <div className="card" style={{ width: '18rem'}}>
                            <img className='img-fluid' src={book.img} alt="Preview"/>
                            <div className="card-body">
                                <h5 className="card-title">{book.title}</h5>
                                <p className="card-text">{book.author}</p>
                                <a href={(props.login === true) ? `home/book/${book.id}` : '/login'} className="btn btn-primary">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};