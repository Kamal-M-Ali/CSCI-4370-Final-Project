import './Home.css';
import Navigation from './Navigation';
import axios from "axios";
import {useEffect, useState} from "react";

export default function Home()
{
    const [total, setTotal] = useState(0);
    function fetch() {
        axios
            .get('http://localhost:8080/api/total')
            .then((res) => {
                setTotal(res.data);
            })
            .catch((err) => {
                console.log('Error getting total');
            });
    }
    useEffect(fetch, []);

    return (<>
        <Navigation />
        <div className='banner'>
            <h1>✰ Review Site ✰</h1>
        </div>
        <hr className='banner-underline'/>
        <div className='about-section'>
            <div>
                <h2>About</h2>
                <hr></hr>
                <p>
                    Welcome to our multimedia review site! There are total of {total.toLocaleString()} (and counting) individual pieces of media on the site!
                    Read user opinions on video games, movies, tv shows, and books. Discussion can be found in the forum section of the site.
                </p>
                <p>
                    *To post, review, or comment you will need to be logged into an account.
                </p>
            </div>
        </div>
    </>);
}