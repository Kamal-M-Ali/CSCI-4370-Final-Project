import './Post.css';
import {useLocation, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Card from "../Card";
import Navigation from "../Navigation";
import Comment from "../Media/Comment";

export default function Post() {
    const API = "http://localhost:8080/api/forum/comment";
    const [ comments, setComments ] = useState([]);
    const [ commented, setCommented ] = useState(0);
    const navigate = useNavigate();
    const { post } = useLocation().state;

    function fetchData() {
        axios.get("http://localhost:8080/api/forum/post/comments/:" + post.thread_id)
            .then((res) => {
                setComments(res.data);
            })
            .catch((err) => {
                console.log(err.response);
            })
    }
    useEffect(fetchData, [post.thread_id]);

    function postComment(e) {
        e.preventDefault();
        if (e.target.body.value === '') return;

        const date = new Date();
        let comment = {
            body: e.target.body.value,
            extra: sessionStorage.getItem('profileName'),
            thread_id: post.thread_id,
            user_id: sessionStorage.getItem('userId'),
            created: 'yyyy-mm-dd'
                .replace('yyyy', date.getFullYear())
                .replace('mm', date.getMonth() + 1)
                .replace('dd', date.getDate())
        };
        e.target.body.value = '';

        comments.push(comment);
        setComments(comments);

        console.log(comment);

        axios.put(API, {
            body: comment.body,
            thread_id: comment.thread_id,
            user_id: comment.user_id
        }).then((res) => {
            if (res.status === 200) {
                setCommented(commented + 1);
            }
        }).catch((err) => {
            alert(err.response.data);
        });
    }

    function deleteThread(e) {
        e.preventDefault();
        axios.delete('http://localhost:8080/api/forum/:' + post.thread_id, { params: {
                userId: sessionStorage.getItem('userId')
            }}).then((res) => {
            if (res.status === 200) {
                alert('Deleted post.')
                navigate('/forum/' + post.category, {
                    state: {
                        category: post.category
                    }
                });
            }
            }).catch((err) => {
                alert(err.response.data);
            });
    }

    return (<>
        <Navigation/>
        <div className='post-page'>
            <Card className='post-section'>
                <h2>'{post.title}' posted by {post.profile_name}</h2>
                <hr/>
                <p>{post.body}</p>
                <p className="timestamp">{post.created}</p>
                {sessionStorage.getItem('userId') == post.user_id ?
                    <div>
                        <br/>
                        <button className='post-btn' onClick={deleteThread}>
                            Delete post
                        </button>
                    </div>
                    : ''}
            </Card>

            <Card className='post-section'>
                <h2>Comment Section</h2>
                <hr/>
                {comments.length > 0 ?
                    <div className='post-comments'>
                        {comments.map((comment, k) =>
                            <Comment key={k} details={comment}/>
                        )}</div>
                    : <p>Nothing much to see here...</p>}
            </Card>

            <Card className='post-section'>
                <h2>Leave a comment</h2>
                <hr/>
                {sessionStorage.getItem('userId') ?
                    <form className='box' onSubmit={postComment}>
                        <textarea className='input-box' name='body' rows='4' cols='50' placeholder='comment'/>
                        <input className='post-btn' type='submit' value='Send' />
                    </form>
                    : <p>Must be logged in to comment</p>}
            </Card>
        </div>
    </>);
}