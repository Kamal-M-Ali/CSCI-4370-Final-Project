import './Add.css';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';
import Card from '../Card';

export default function AddBook() {
    const navigate = useNavigate();
    const [confirmOpen, setConfirmOpen] = useState(false);

    function addBook(e) {
        e.preventDefault();
        const userId = sessionStorage.getItem('userId');

        if (userId) {
            axios.put("http://localhost:8080/api/admin/add-book/:" + userId, {
                title: e.target.title.value,
                score: e.target.score.value,
                summary: e.target.summary.value,
                genres: e.target.genres.value,
                review_count: e.target.reviewCount.value,
                author: e.target.author.value,
                series: e.target.series.value,
                num_pages: e.target.pages.value,
                publication_date: e.target.publication.value,
                publishers: e.target.publishers.value
            }).then((res) => {
                if (res.status === 200) {
                    setConfirmOpen(true);
                }
            }).catch((err) => {
                console.log(err.response);
                alert("Error: Couldn't add book")
            })
        }
    }

    return (<>
        {(confirmOpen) ?
            <Card className='confirm-wnd'>
                <h1>Book Added</h1>
                <h2>The book was added to the system.</h2>
                <div className='confirm-btn'>
                    <button onClick={() => { setConfirmOpen(false); navigate('/admin/book') }}>OK</button>
                </div>
            </Card>
            :
            <Card className='add-form'>
                <form onSubmit={addBook}>
                    <h2>Enter Book Details</h2>
                    <input type='text' name='title' placeholder='Title' required/>
                    <input type='number' step='0.1' max='5' min='0' name='score' placeholder='Score'/>
                    <input type='text' name='summary' placeholder='Summary' required/>
                    <input type='text' name='genres' placeholder='Genres' required/>
                    <input type='number' step='1' min='0' name='reviewCount' placeholder='Review count' required/>
                    <input type='text' name='author' placeholder='Author' required/>
                    <input type='text' name='series' placeholder='Series' required/>
                    <input type='number' step='1' min='1' name='pages' placeholder='Number of pages' required/>
                    <input type='text' name='publication' placeholder='Publication date (yyyy-mm-dd)' required/>
                    <input type='text' name='publishers' placeholder='Publishers' required/>

                    <div className='confirm-btn'>
                        <button type='submit'>Add Book</button>
                    </div>
                </form>
            </Card>
        }
    </>);
}