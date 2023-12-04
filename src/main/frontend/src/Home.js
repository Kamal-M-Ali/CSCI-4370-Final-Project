import './Home.css';
import Navigation from './Navigation';

export default function Home()
{
    return (<>
        <Navigation />
        <div className='banner'>
            <h1>Review Site</h1>
        </div>
        <hr className='banner-underline'/>
        <div className='about-section'>
            <div>
                <h2>About</h2>
                <hr></hr>
                <p>
                    Welcome to our multimedia review site! Browse user opinions on video games, movies, tv shows, and books.
                    Discussion can be found in the forum section of the site. To post you will need to be logged into an account.
                </p>
            </div>
        </div>
    </>);
}