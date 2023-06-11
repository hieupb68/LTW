import './App.css';
import { Route, Routes } from 'react-router-dom';
import { UserHome } from './components/UserHome';
import { AdminHome } from './components/AdminHome';
import { Login } from './components/Login';
import { useState, useEffect } from 'react';
import { SignUp } from './components/SignUp';
import { ErrorPage } from './components/ErrorPage';
import { BookView } from './components/BookView';
import { BookDetail } from './components/BookDetail';
import { MyBook } from './components/MyBook';

function App() {
  const [id, setId] = useState(-1);
  const [login, setLogin] = useState(false);
  const [fullname, setFullname] = useState('');
  const [position, setPosition] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(false);

  const handleLogout = () => {
    fetch("http://localhost:8080/logout")
      .then(response => response.json())
      .then(data => {
        // Process the response data
        console.log(data);
        setId(-1);
        setFullname(data.fullname);
        setPosition('');
        if (data.fullname === '') {
          setLogin(false);
          setError(false);
        } else setLogin(true);
      })
      .catch(error => {
        // Handle any errors
        console.error(error);
      });
  }

  const checkCurrentUser = () => {
    setIsLoading(true);
    fetch("http://localhost:8080/user-login")
      .then(response => response.json())
      .then(data => {
        setIsLoading(false);
        // Process the response data
        console.log(data);
        setId(parseInt(data.id));
        setFullname(data.fullname);
        setPosition(data.position);

        if (data.fullname === '') {
          setLogin(false);
        } else {
          setLogin(true);
        }
      })
      .catch(error => {
        // Handle any errors
        console.error(error);
        setIsLoading(false);
        alert('Không thể kết nối tới server!');
      });
  };

  useEffect(() => {
    checkCurrentUser();
  }, []);

  if (isLoading)
    return (
      <div>
        Loading...
      </div>
    )

  else if (!isLoading)
    return (
      <div className="App">
        <div className='bg-success p-3 d-flex justify-content-between mb-3'>
          {!login ?
            <>
              <h1 className='ms-4'>Hãy đăng nhập để tiếp tục</h1>
              <div className='btn-group'>
                <button className='btn btn-primary' onClick={() => { window.location.href = '/login' }}> Login </button>
                <button className='btn btn-warning' onClick={() => { window.location.href = '/sign' }}> Sign Up </button>
              </div>
            </>
            :
            <>
              <h1 className='ms-4'>Xin chào {fullname}</h1>
              <div className='justify-content-end'>
                {position === 'user' && (
                  <button className='btn btn-warning me-3' onClick={() => { window.location.href = '/my-book' }}>My Book</button>
                )}
                <button className='btn btn-primary ' onClick={handleLogout}> Logout </button>
              </div>
            </>
          }

        </div>
        {error && <ErrorPage position={position} />}
        <Routes>

          {position !== 'admin' &&
            (<>
              <Route exact path='/' element={<UserHome login={login} />} />
              <Route path='/home' element={<UserHome login={login} />} />
              {login && (<>
                <Route path='/home/book/:id' element={<BookDetail id={id} />} />
                <Route path='/my-book' element={<MyBook id={id} />} />
              </>)}
            </>)
          }
          {position !== 'user' && (
            <>
              <Route path='/admin' element={<AdminHome login={login} setIsLoading={setIsLoading} />} />
              {login && <Route path='/admin/book/:id' element={<BookView />} />}
            </>
          )}
          {!login && <>
            <Route path='/login' element={<Login />} />
            <Route path='/sign' element={<SignUp />} />
          </>}
          <Route path='*' element={<ErrorPage position={position} />} />
        </Routes>

      </div>
    );
}

export default App;
