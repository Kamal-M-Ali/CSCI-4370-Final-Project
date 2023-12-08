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
            <ul>
                <li key='home'>
                    <Link className='nav-link' to='/'>Home</Link>
                </li>
                {loggedIn ?
                    <li key='admin'>
                        <Link className='nav-link' to='/admin'>Admin</Link>
                    </li>
                    : <li></li>}
            </ul>

            <ul>
                <li key='games'>
                    <Link className='nav-link' to='/games' state={{mediaType: 'game'}}>Games</Link>
                </li>
                <li key='books'>
                    <Link className='nav-link' to='/books' state={{mediaType: 'book'}}>Books</Link>
                </li>
                <li key='movies'>
                    <Link className='nav-link' to='/movies' state={{mediaType: 'movie'}}>Movies</Link>
                </li>
                <li key='shows'>
                    <Link className='nav-link' to='/shows' state={{mediaType: 'tv_show'}}>Shows</Link>
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