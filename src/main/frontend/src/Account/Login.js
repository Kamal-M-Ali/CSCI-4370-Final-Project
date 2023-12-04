import './Account.css';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';
import Card from '../Card';
import Navigation from '../Navigation';

export default function Login() {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const endpoint = 'http://localhost:8080/api/login';

    function handleSubmit(e) {
        e.preventDefault();

        axios.post(endpoint, {
            email: email,
            password: password
        }).then((res) => {
            if (res.status === 200) {
                sessionStorage.setItem('userId', res.data);
                navigate('/');
            }
        }).catch((err) => {
            // failed to log in
            alert(err.response.data);
        })
    }

    function handleLogout() {
        sessionStorage.clear();
        navigate('/');
    }

    return (<>
        <Navigation />
        {
            !(sessionStorage.getItem('email')) ?
                (<Card className='account-page'>
                    <form onSubmit={handleSubmit}>
                        <div>
                            <label>Email:</label>
                            <input type='email' required onChange={(e) => setEmail(e.target.value)} />
                        </div>
                        <div>
                            <label>Password:</label>
                            <input type='password' required onChange={(e) => setPassword(e.target.value)} />
                        </div>

                        <div className='account-btn'>
                            <button type='submit'>Login</button>
                        </div>

                    </form>
                    <Link className='account-link' to='/signup'>
                        Don't have an account?
                    </Link>
                </Card>)
                :
                (<div className='account-error'>
                    <h1>You're already logged in.</h1>
                    <div className='account-btn logout'>
                        <button onClick={handleLogout}>Logout</button>
                    </div>
                </div>)
        }

    </>);
}