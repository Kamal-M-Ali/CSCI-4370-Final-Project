import './ErrorPage.css';
import Navigation from './Navigation';

export default function ErrorPage() {
    return (
        <div>
            <Navigation/>
            <p className='error-text'>Oops! The page you're looking for doesn't exist.</p>
        </div>
    );
}