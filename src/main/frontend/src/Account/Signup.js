import './Account.css';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Card from '../Card';
import Navigation from '../Navigation';

export default function Signup() {
    const navigate = useNavigate();

    function handleSubmit(e) {
        e.preventDefault();
        if (e.target.password1.value !== e.target.password2.value) {
            alert('Passwords must match')
            return;
        }

        axios.post("http://localhost:8080/api/register", {
            email: e.target.email.value,
            password: e.target.password1.value,
            profile_name: e.target.name.value
        }).then((res) => {
            if (res.status === 200) {
                sessionStorage.setItem("userId", res.data[0]);
                sessionStorage.setItem("profileName", res.data[1]);
                navigate('/');
            }
        }).catch((err) => {
            console.log(err.response.data);
            alert(typeof err.response.data === 'string' ? err.response.data : "Missing fields")
        })
    }

    return (<>
        <Navigation />
        <Card className='account-page'>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email:</label>
                    <input type='email' name='email' required/>
                </div>

                <div>
                    <label>Profile name:</label>
                    <input type='text' name='name' required/>
                </div>

                <div>
                    <label>Password:</label>
                    <input type='password' name='password1' required/>
                    <input type='password' name='password2' placeholder='confirm password' required/>
                </div>

                <div className='account-btn'>
                    <button type='submit'>Create Account</button>
                </div>
            </form>

            <Link className='account-link' to='/login'>
                Already have an account?
            </Link>
        </Card>
    </>);
}