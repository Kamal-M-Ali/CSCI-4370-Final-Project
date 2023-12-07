import './Profile.css';
import { useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import Navigation from '../Navigation';
import Card from '../Card'
import UserComment from "./UserComment";
import UserPost from "./UserPost";

export default function Profile() {
    const API = 'http://localhost:8080/api/view/profile/:';
    const [profile, setProfile] = useState(null);
    const [comments, setComments] = useState([]);
    const [posts, setPosts] = useState([]);

    const { userId } = useLocation().state;

    function fetchData() {
        axios.get(API + userId)
            .then((res) => {
                if (res.status === 200) {
                    setProfile(res.data[0]);
                    setComments(res.data[1]);
                    setPosts(res.data[2]);
                }
            })
            .catch((err) => {
                console.log(err.response);
                setProfile(null);
                setComments([]);
                setPosts([]);
            })
    }
    useEffect(fetchData, [userId])

    return (<>
        <Navigation />
        <div className='profile-header-container'>
            <h1>{profile ? profile : 'User'}'s profile</h1>
            <hr/>
            <p>Welcome to my profile!</p>
        </div>
        <div className='profile-page'>
            <Card className='profile-section'>
                <h2>Forum Posts</h2>
                <hr/>

                {posts.length > 0 ?
                    <div className='profile-stuff'>
                        {posts.map((post, k) =>
                            <UserPost key={k} details={post}/>
                        )}</div>
                    : <p>User hasn't created any forum posts.</p>}
            </Card>

            <Card className='profile-section'>
                <h2>Comments</h2>
                <hr/>

                {comments.length > 0 ?
                    <div className='profile-stuff'>
                        {comments.map((comment, k) =>
                            <UserComment key={k} details={comment}/>
                        )}</div>
                    : <p>User hasn't left any comments.</p>}
            </Card>
        </div>
    </>);
}