import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';

export const AdminHome = (props) => {

  const [books, setBooks] = useState([])
  const navigate = useNavigate();
  const fetchBooks = () => {
    fetch('http://localhost:8080/books')
      .then((response) => response.json())
      .then((data) => {
        setBooks(data);
      })
      .catch((error) => {
        console.error(error);
      });
  }
  const handleBook = (id) => {
    navigate(`book/${id}`);
  };

  const handleDelete = (id) => {
    let text = "Bạn có đồng ý xóa sách vị trí " + id + " không?";
    if (window.confirm(text) === true) {
      fetch('http://localhost:8080/delete/' + id,
        {
          method: 'DELETE'
        })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          fetchBooks();
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }



  useEffect(() => {
    fetchBooks();
  }, []);

  return (
    <div className='container'>
      {props.login && (
        <button className='btn btn-success' onClick={() => handleBook(-1)}>ADD BOOK</button>
      )}
      <table className="table">
        <thead className="thead-dark">
          <tr>
            <th scope="col">#</th>
            <th scope="col">Tiêu đề</th>
            <th scope="col">Tác giả</th>
            <th scope="col">Thể loại</th>
            <th scope="col">Ngày phát hành</th>
            <th scope="col">Số trang</th>
            <th scope="col">Đã bán</th>
            {props.login && (
              <th scope="col">
                Action
              </th>
            )}
          </tr>
        </thead>
        <tbody>
          {books.map((book, index) =>
          (
            <tr key={index}>
              <th scope="row">{index}</th>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>{book.category}</td>
              <td>{book.release}</td>
              <td>{book.pages}</td>
              <td>{book.sold}</td>
              {props.login && (
                <th scope="col">
                  <button className='btn btn-primary' onClick={() => handleBook(book.id)}>VIEW</button>
                  <button className='btn btn-danger' onClick={() => handleDelete(book.id)}>DELETE</button>
                </th>
              )}
            </tr>
          )
          )}


        </tbody>
      </table>
    </div>
  )
}
