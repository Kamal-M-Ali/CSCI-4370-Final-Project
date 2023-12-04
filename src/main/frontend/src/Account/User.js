import './Account.css';
import { Link, useNavigate} from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import Navigation from '../Navigation';
import Card from '../Card'

export default function User() {
    // fetch user profile
    const API = 'http://localhost:8080/api/profile/:';
    const navigate = useNavigate();
    const [accountDetails, setAccountDetails] = useState(null);

    function fetchData() {
        const userId = sessionStorage.getItem('userId');

        if (userId) {
            axios.get(API + userId)
                .then((res) => {
                    setAccountDetails(res.data);
                })
                .catch((err) => {
                    console.log(err.response);
                    setAccountDetails(null);
                })
        } else {
            setAccountDetails(null);
        }
    }
    useEffect(fetchData, [navigate])

    const [editInfo, setEditInfo] = useState(false);
    const [editPswd, setEditPswd] = useState(false);

    function changeInfo(e) {
        e.preventDefault();
        const userId = sessionStorage.getItem('userId');

        if (e.target.name.value === '' && e.target.email.value === '') {
            setEditInfo(false);
            return;
        }

        if (userId) {
            axios.post("http://localhost:8080/api/update-info/:" + userId, null, {
                params: {
                    profileName: (e.target.name.value !== '' ? e.target.name.value : accountDetails.name),
                    email: (e.target.email.value !== '' ? e.target.email.value : accountDetails.email)
                }
            }).then((res) => {
                if (res.status === 200) {
                    alert("Personal info changed");
                    fetchData();
                    setEditInfo(false);
                }
            }).catch((err) => {
                console.log(err.response);
                alert(typeof err.response.data === 'string' ? err.response.data : "Error: Couldn't change info")
                setEditInfo(false);
            })
        }
    }

    function changePassword(e) {
        e.preventDefault();
        if (e.target.password1.value !== e.target.password2.value) {
            alert('Passwords must match')
            return;
        }

        const userId = sessionStorage.getItem('userId');

        if (userId) {
            axios.post("http://localhost:8080/api/change-password/:" + userId, null, {
                params: {
                    password: e.target.password1.value
                }
            }).then((res) => {
                if (res.status === 200) {
                    alert("Password changed");
                    setEditPswd(false);
                }
            }).catch((err) => {
                console.log(err.response);
                alert(typeof err.response.data === 'string' ? err.response.data : "Error: Couldn't change password")
                setEditPswd(false);
            })
        }
    }

    return (<>
        <Navigation />
        {(accountDetails !== null) ?
            (<Card className='account-page'>
                <h1>Account</h1>
                {(!editInfo) ?
                    <div>
                        <p>Email: {accountDetails.email}</p>
                        <p>Profile name: {accountDetails.profile_name}</p>
                        <button className='account-edit-btn' onClick={() => setEditInfo(true)}>Edit account info</button>
                    </div>
                    :
                    <form onSubmit={changeInfo}>
                        <input type='text' name='email' placeholder='Email' />
                        <input type='text' name='name' placeholder='Profile name' />

                        <button className='account-btn' type='submit'>Finish</button>
                    </form>
                }
                <hr/>

                <h1>Password</h1>
                {(!editPswd) ?
                    <div>
                        <button className='account-edit-btn' onClick={() => setEditPswd(true)}>Change Password</button>
                    </div>
                    :
                    <form onSubmit={changePassword}>
                        <input type='password' name='password1' placeholder='New Password' required />
                        <input type='password' name='password2' placeholder='Confirm Password' required />
                        <button className='account-btn' type='submit'>Change</button>
                    </form>
                }
                <hr/>
                <Link className='account-btn' to={'/profile/:' + sessionStorage.getItem('userId')}>View profile</Link>
            </Card>)
            :
            (<div className='account-error'>
                <br/>
                <h1>Login to view/edit account details.</h1>
            </div>)
        }

    </>);
}