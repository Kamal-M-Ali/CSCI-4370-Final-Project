import './Admin.css';
import { Link } from 'react-router-dom';
import Card from '../Card';
import Navigation from "../Navigation";

export default function Admin() {

    return (<>
        <div className='banner'>
            <h1>Admin Panel</h1>
        </div>
        <hr className='banner-underline'/>
        <Card className='admin-btns'>
            <Link to='/admin/game' className='admin-btn'>Add Game</Link>
            <Link to='/admin/movie' className='admin-btn'>Add Movie</Link>
            <Link to='/admin/show' className='admin-btn'>Add Show</Link>
            <Link to='/admin/book' className='admin-btn'>Add Book</Link>
        </Card>
    </>)
}