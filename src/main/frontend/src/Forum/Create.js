import './Create.css';
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import Card from "../Card";
import Navigation from "../Navigation";

export default function Create() {
    const API = "http://localhost:8080/api/forum/create/:";
    const navigate = useNavigate();

    const { category } = useLocation().state;

    function handleSubmit(e) {
        e.preventDefault();

        axios.post(API + category, null, {
            params: {
                userId: sessionStorage.getItem('userId'),
                title: e.target.title.value,
                body: e.target.body.value
            }
        }).then((res) => {
            if (res.status === 200) {
                alert('Created post!');
                navigate('/forum/' + category, {
                    state: {
                        category: category
                    }
                });
            }
        })
            .catch((err) => {
                console.log(err.response);
            })
    }

    return (<>
        <Navigation />
        <div className='create-page'>
            <Card className='create-card'>
                <h2>Creating a Forum Post in {category}</h2>
                <hr/>
                <form className='thread-form' onSubmit={handleSubmit}>
                    <input className='thread-title' name='title' type='text' placeholder='title' required/>
                    <textarea className='thread-desc' name='body' rows='10' cols='50' placeholder='description' required/>
                    <input className='submit-btn' type='submit' value='Create' />
                </form>
            </Card>
        </div>
    </>);
}