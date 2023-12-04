import './Navigation.css';
import { Link, useNavigate } from 'react-router-dom';

export default function Navigation(props)
{
    const key = 'userId';
    let loggedIn = sessionStorage.getItem(key) || false;
    const navigate = useNavigate();

    function handleLogout() {
        sessionStorage.clear();
        navigate('/');
    }

    return (
        <div className='nav-bar'>
            <p className='home-button'>
                <Link className='nav-link' to='/'>Home</Link>
            </p>
            <ul>
                <li key='games'>
                    <Link className='nav-link' to='/games'>Games</Link>
                </li>
                <li key='books'>
                    <Link className='nav-link' to='/books'>Books</Link>
                </li>
                <li key='movies'>
                    <Link className='nav-link' to='/movies'>Movies</Link>
                </li>
                <li key='shows'>
                    <Link className='nav-link' to='/shows'>Shows</Link>
                </li>
                <li key='forum'>
                    <Link className='nav-link' to='/forum'>Forum</Link>
                </li>
                {(loggedIn) ?
                    <>
                        <li key='account'>
                            <Link className='nav-link' to='/account'>Account</Link>
                        </li>
                        <li key='logout'>
                            <button className='nav-button' onClick={handleLogout}>Logout</button>
                        </li>
                    </>
                    :
                    <>
                        <li key='login'>
                            <Link className='nav-link' to='/login'>Login</Link>
                        </li>
                        <li key='signup'>
                            <Link className='nav-link' to='/signup'>Signup</Link>
                        </li>
                    </>
                }
            </ul>
        </div>);
}