import './Category.css';
import {Link, useLocation} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Navigation from "../Navigation";

export default function Category() {
    const API = "http://localhost:8080/api/forum/:";
    const [posts, setPosts] = useState([]);

    const { category } = useLocation().state;

    function fetchData() {
        axios.get(API + category)
            .then((res) => {
                setPosts(res.data);
            })
            .catch((err) => {
                console.log(err.response);
            })
    }
    useEffect(fetchData, [category]);

    return (<>
        <Navigation />
        <div className='forum-page'>
            <div className='category-header'>
                <h1>{category}</h1>
                {sessionStorage.getItem('userId') ?
                    <Link to={`/forum/${category}/create`} state={{ category: category }}>
                        <button className='create-btn'>Create Post</button>
                    </Link>
                    : <p>Log in to post.</p>}
            </div>
            <hr/>
            {posts.length > 0 ?
                <div className='forum-posts'>
                    {posts.map((post, k) =>
                        <Link key={k} to={`/forum/post/${post.thread_id}`} state={{ post: post }}>
                            <button className='forum-post'>{post.title}</button>
                        </Link>
                    )}
                </div>
                : <p>No posts yet in this category.</p>}
        </div>

    </>);
}