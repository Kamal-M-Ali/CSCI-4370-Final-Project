import './Add.css';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';
import Card from '../Card';

export default function AddBook() {
    const navigate = useNavigate();
    const [confirmOpen, setConfirmOpen] = useState(false);

    function addShow(e) {
        e.preventDefault();
        const userId = sessionStorage.getItem('userId');

        if (userId) {
            axios.put("http://localhost:8080/api/admin/add-show/:" + userId, {
                title: e.target.title.value,
                score: e.target.score.value,
                summary: e.target.summary.value,
                genres: e.target.genres.value,
                review_count: e.target.reviewCount.value,
                num_seasons: e.target.numSeasons.value,
                num_episodes: e.target.numEpisodes.value,
                is_adult: (e.target.isAdult.value === 'true'),
                first_air_date: e.target.firstAirDate.value,
                last_air_date: e.target.lastAirDate.value,
                homepage: e.target.homepage.value,
                created_by: e.target.createdBy.value,
                networks: e.target.networks.value,
            }).then((res) => {
                if (res.status === 200) {
                    setConfirmOpen(true);
                }
            }).catch((err) => {
                console.log(err.response);
                alert("Error: Couldn't add show")
            })
        }
    }

    return (<>
        {(confirmOpen) ?
            <Card className='confirm-wnd'>
                <h1>Show Added</h1>
                <h2>The show was added to the system.</h2>
                <div className='confirm-btn'>
                    <button onClick={() => { setConfirmOpen(false); navigate('/admin/show') }}>OK</button>
                </div>
            </Card>
            :
            <Card className='add-form'>
                <form onSubmit={addShow}>
                    <h2>Enter Show Details</h2>
                    <input type='text' name='title' placeholder='Title' required/>
                    <input type='number' step='0.1' max='5' min='0' name='score' placeholder='Score'/>
                    <input type='text' name='summary' placeholder='Summary' required/>
                    <input type='text' name='genres' placeholder='Genres' required/>
                    <input type='number' step='1' min='0' name='reviewCount' placeholder='Review count'/>
                    <input type='number' step='1' min='1' name='numSeasons' placeholder='Number of seasons' required/>
                    <input type='number' step='1' min='1' name='numEpisodes' placeholder='Number of episodes' required/>
                    <input type='text' name='isAdult' placeholder='Is adult ("true" or "false")'/>
                    <input type='text' name='firstAirDate' placeholder='First air date (yyyy-mm-dd)' required/>
                    <input type='text' name='lastAirDate' placeholder='Last air date (yyyy-mm-dd)'/>
                    <input type='text' name='homepage' placeholder='Homepage' required/>
                    <input type='text' name='createdBy' placeholder='Created by' required/>
                    <input type='text' name='networks' placeholder='Networks' required/>

                    <div className='confirm-btn'>
                        <button type='submit'>Add Show</button>
                    </div>
                </form>
            </Card>
        }
    </>);
}