import './View.css';
import {Link, useLocation} from 'react-router-dom';
import {useEffect, useState} from "react";
import axios from 'axios';
import Card from "../Card";
import Navigation from "../Navigation";
import Comment from "./Comment";

export default function View(props) {
    const API = "http://localhost:8080/api/media/:";
    const [game, setGame] = useState(null);
    const [movie, setMovie] = useState(null);
    const [tv_show, setTv_show] = useState(null);
    const [book, setBook] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [comments, setCommments] = useState([]);
    const [media, setMedia] = useState(null);
    const [rated, setRated] = useState(false);
    const [commented, setCommented] = useState(0);

    const { mediaId, mediaType } = useLocation().state;

    function fetchData() {
        axios.get(API + mediaType + '/:' + mediaId)
            .then((res) => {
                // data given from server will be an array of form: [mediaObject, reviews, comments]
                if (mediaType === 'game') {
                    setGame(res.data[0]);
                } else if (mediaType === 'movie') {
                    setMovie(res.data[0]);
                } else if (mediaType === 'tv_show') {
                    setTv_show(res.data[0]);
                } else if (mediaType === 'book') {
                    setBook(res.data[0]);
                }
                setMedia({
                    media_id: res.data[0].media_id,
                    title: res.data[0].title,
                    score: res.data[0].score,
                    summary: res.data[0].summary,
                    genres: res.data[0].genres,
                    review_count: res.data[0].review_count
                });
                setReviews(res.data[1]);
                setCommments(res.data[2]);
            })
            .catch((err) => {
                console.log(err.response);
                if (mediaType === 'game') {
                    setGame(null);
                } else if (mediaType === 'movie') {
                    setMovie(null);
                } else if (mediaType === 'tv_show') {
                    setTv_show(null);
                } else if (mediaType === 'book') {
                    setBook(null);
                }
            })
    }
    useEffect(fetchData, [mediaId, mediaType]);


    function rate(e) {
        e.preventDefault();
        if (rated) {
            alert('Slow down.')
            return;
        }
        const scored = e.target.score.value;

        setRated(true);
        e.target.score.value = '';

        axios.put('http://localhost:8080/api/rate-media/:' + media.media_id, null, {
            params: {
                score: scored
            }})
            .then((res) => {
                if (res.status === 200) {
                    setMedia({
                        media_id: media.media_id,
                        title: media.title,
                        score: res.data,
                        summary: media.summary,
                        genres: media.genres,
                        review_count: media.review_count + 1
                    });
                }
            }).catch((err) => {
                alert(err.response.data);
            });
    }

    function postComment(e) {
        e.preventDefault();
        if (e.target.body.value === '') return;

        const date = new Date();
        let comment = {
            body: e.target.body.value,
            profile_name: sessionStorage.getItem('profileName'),
            media_id: media.media_id,
            user_id: sessionStorage.getItem('userId'),
            created: 'yyyy-mm-dd'
                .replace('yyyy', date.getFullYear())
                .replace('mm', date.getMonth() + 1)
                .replace('dd', date.getDate())
        };
        e.target.body.value = '';

        comments.push(comment);
        setCommments(comments);

        console.log(comment);

        axios.put('http://localhost:8080/api/add-comment', {
            body: comment.body,
            media_id: comment.media_id,
            user_id: comment.user_id
        }).then((res) => {
            if (res.status === 200) {
                setCommented(commented + 1);
            }
        }).catch((err) => {
            alert(err.response.data);
        });
    }

    return (<>
        <Navigation />
        <div className='view-page'>
            <Card className='view-section'>
                {media ?
                    <div>
                        <h2>{media.title}</h2>
                        <hr/>

                        <p><b>Rating: </b>{(media.score >= 0) ? Math.round(media.score * 100) / 100 + '/5⭐' : 'Unrated'}</p>
                        <p>{media.summary}</p>
                        <p><b>Genres: </b>{media.genres}</p>
                        <p><b>Votes: </b>{media.review_count.toLocaleString()}</p>
                    </div>
                    : <></>}

                {game ?
                    <div>
                        <p><b>Release date: </b>{game.release_date ? game.release_date : 'TBA'}</p>
                        <p><b>Developer(s): </b>{game.developers}</p>
                        <p><b>Platform(s): </b>{game.platforms}</p>
                        <p><b>Plays: </b>{game.plays}</p>
                    </div>
                    : <></>}

                {movie ?
                    <div>
                        {movie.homepage ? <p><b>Homepage: </b><a href={movie.homepage}>link</a></p> : ''}
                        <p><b>Budget: </b>{movie.budget.toLocaleString()}</p>
                        <p><b>Production company(s): </b>{movie.production}</p>
                        <p><b>Runtime: </b>{movie.runtime}</p>
                        <p><b>Release date: </b>{movie.release_date}</p>
                    </div>
                    : <></>}

                {tv_show ?
                    <div>
                        <p><b>Seasons: </b>{tv_show.num_seasons.toLocaleString()}</p>
                        <p><b>Episodes: </b>{tv_show.num_episodes.toLocaleString()}</p>
                        <p><b>Is adult: </b>{tv_show.is_adult ? 'Yes' : 'No'}</p>
                        <p><b>First air date: </b>{tv_show.first_air_date}</p>
                        <p><b>Last air date: </b>{tv_show.last_air_date ? tv_show.last_air_date : 'N/A'}</p>
                        {tv_show.homepage ? <p><b>Homepage: </b><a href={tv_show.homepage}>link</a></p> : ''}
                        <p><b>Created by: </b>{tv_show.created_by}</p>
                        <p><b>Network(s): </b>{tv_show.networks}</p>
                    </div>
                    : <></>}

                {book ?
                    <div>
                        <p><b>Author: </b>{book.author}</p>
                        <p><b>Series: </b>{book.series ? book.series : 'No series'}</p>
                        <p><b>Pages: </b>{book.num_pages.toLocaleString()}</p>
                        <p><b>Publication date: </b>{book.publication_date}</p>
                        <p><b>Publishers: </b>{book.publishers}</p>
                    </div>
                    : <></>}
                {media ?
                    <Link to={`/reviews/${mediaType}/${media.media_id}`} state={{ media: media.title, reviews: reviews }}>
                        <button className='view-btn'>View Critic Reviews</button>
                    </Link>
                    : <></>}

            </Card>

            <Card className='view-section'>
                <h2>Leave a Rating</h2>
                <hr/>
                {sessionStorage.getItem('userId') ?
                    <form className='box' onSubmit={rate}>
                        <input className='input-box' type='number' placeholder='score [1-5]' name='score' step='0.1' min='1' max='5' />
                        <input className='view-btn' type='submit' value='Rate' />
                    </form>
                    : <p>Must be logged in to rate.</p>}
            </Card>

            <Card className='view-section'>
                <h2>Comment Section</h2>
                <hr/>
                {comments.length > 0 ?
                    <div className='view-comments'>
                        {comments.map((comment, k) =>
                            <Comment key={k} details={comment}/>
                        )}</div>
                : <p>Nothing much to see here...</p>}
            </Card>

            <Card className='view-section'>
                <h2>Leave a comment</h2>
                <hr/>
                {sessionStorage.getItem('userId') ?
                    <form className='box' onSubmit={postComment}>
                        <textarea className='input-box' name='body' rows='4' cols='50' placeholder='comment'/>
                        <input className='view-btn' type='submit' value='Send' />
                    </form>
                    : <p>Must be logged in to comment</p>}
            </Card>
        </div>
    </>);
}