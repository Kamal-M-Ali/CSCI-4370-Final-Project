import './Add.css';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';
import Card from '../Card';

export default function AddBook() {
    const navigate = useNavigate();
    const [confirmOpen, setConfirmOpen] = useState(false);

    function addMovie(e) {
        e.preventDefault();
        const userId = sessionStorage.getItem('userId');

        if (userId) {
            axios.put("http://localhost:8080/api/admin/add-movie/:" + userId, {
                title: e.target.title.value,
                score: (e.target.score.value ? e.target.score.value : -1.0),
                summary: e.target.summary.value,
                genres: e.target.genres.value,
                review_count: e.target.reviewCount.value,
                homepage: e.target.homepage.value,
                budget: e.target.budget.value,
                production: e.target.production.value,
                runtime: e.target.runtime.value,
                release_date: e.target.releaseDate.value
            }).then((res) => {
                if (res.status === 200) {
                    setConfirmOpen(true);
                }
            }).catch((err) => {
                console.log(err.response);
                alert("Error: Couldn't add movie")
            })
        }
    }

    return (<>
        {(confirmOpen) ?
            <Card className='confirm-wnd'>
                <h1>Movie Added</h1>
                <h2>The movie was added to the system.</h2>
                <div className='confirm-btn'>
                    <button onClick={() => { setConfirmOpen(false); navigate('/admin/movie') }}>OK</button>
                </div>
            </Card>
            :
            <Card className='add-form'>
                <form onSubmit={addMovie}>
                    <h2>Enter Movie Details</h2>
                    <input type='text' name='title' placeholder='Title' required/>
                    <input type='number' step='0.1' max='5' min='0' name='score' placeholder='Score'/>
                    <input type='text' name='summary' placeholder='Summary' required/>
                    <input type='text' name='genres' placeholder='Genres' required/>
                    <input type='number' step='1' min='0' name='reviewCount' placeholder='Review count' required/>
                    <input type='text' name='homepage' placeholder='Homepage'/>
                    <input type='number' step='1' min='0' name='budget' placeholder='Budget' required/>
                    <input type='text' name='production' placeholder='Production' required/>
                    <input type='number' step='1' min='1' name='runtime' placeholder='Runtime' required/>
                    <input type='text' name='releaseDate' placeholder='Release Date (yyyy-mm-dd)' required/>

                    <div className='confirm-btn'>
                        <button type='submit'>Add Movie</button>
                    </div>
                </form>
            </Card>
        }
    </>);
}