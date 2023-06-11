import React, { useState } from 'react'

export const Login = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        fetch("http://localhost:8080/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 'username': username.trim(), 'password': password.trim() })
        })
            .then(response => response.json())
            .then(data => {
                if (data.fullname !== '') {
                    const position = data.position;
                    if (position === 'admin') {
                        window.location.href = '/admin';
                    } else if (position === 'user') {
                        window.location.href = '/home';
                    }
                } else {
                    setPassword('');
                    alert("Đăng nhập không thành công! Hãy thử lại tên đăng nhập và mật khẩu.");
                }
            })
            .catch(error => {
                // Handle any errors
                console.error(error);
            });
    };

    return (
        <div className='d-flex justify-content-center'>
            <form className='w-25' onSubmit={handleSubmit}>
                <h1>Login</h1>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">Email address</label>
                    <input type="email" className="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" value={username} onChange={(e) => setUsername(e.target.value)} required />
                    <div id="emailHelp" className="form-text">We'll never share your email with anyone else.</div>
                </div>
                <div className="mb-3">
                    <label htmlFor="exampleInputPassword1" className="form-label">Password</label>
                    <input type="password" className="form-control" id="exampleInputPassword1" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <button type="submit" className="btn btn-primary">Submit</button>
            </form>
        </div>
    )
}
