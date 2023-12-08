import './Add.css';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';
import Card from '../Card';
import Navigation from "../Navigation";

export default function AddBook() {
    const navigate = useNavigate();
    const [confirmOpen, setConfirmOpen] = useState(false);

    function addGame(e) {
        e.preventDefault();
        const userId = sessionStorage.getItem('userId');

        if (userId) {
            axios.put("http://localhost:8080/api/admin/add-game/:" + userId, {
                title: e.target.title.value,
                score: (e.target.score.value ? e.target.score.value : -1.0),
                summary: e.target.summary.value,
                genres: e.target.genres.value,
                review_count: e.target.reviewCount.value,
                release_date: e.target.releaseDate.value,
                developers: e.target.developers.value,
                platforms: e.target.platforms.value,
                plays: e.target.plays.value
            }).then((res) => {
                if (res.status === 200) {
                    setConfirmOpen(true);
                }
            }).catch((err) => {
                console.log(err.response);
                alert("Error: Couldn't add game")
            })
        }
    }

    return (<>
        <Navigation/>
        {(confirmOpen) ?
            <Card className='confirm-wnd'>
                <h1>Game Added</h1>
                <h2>The game was added to the system.</h2>
                <div className='confirm-btn'>
                    <button onClick={() => { setConfirmOpen(false); navigate('/admin/game') }}>OK</button>
                </div>
            </Card>
            :
            <Card className='add-form'>
                <form onSubmit={addGame}>
                    <h2>Enter Game Details</h2>
                    <input type='text' name='title' placeholder='Title' required/>
                    <input type='number' step='0.1' max='5' min='0' name='score' placeholder='Score'/>
                    <input type='text' name='summary' placeholder='Summary' required/>
                    <input type='text' name='genres' placeholder='Genres' required/>
                    <input type='number' step='1' min='0' name='reviewCount' placeholder='Review count' required/>
                    <input type='text' name='releaseDate' placeholder='Release date (yyyy-mm-dd)' required/>
                    <input type='text' name='developers' placeholder='Developers' required/>
                    <input type='text' name='platforms' placeholder='Platforms' required/>
                    <input type='number' step='1' min='0' name='plays' placeholder='Plays' required/>

                    <div className='confirm-btn'>
                        <button type='submit'>Add Game</button>
                    </div>
                </form>
            </Card>
        }
    </>);
}