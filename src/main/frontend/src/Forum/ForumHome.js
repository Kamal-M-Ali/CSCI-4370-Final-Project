import './ForumHome.css';
import Navigation from "../Navigation";
import {Link} from "react-router-dom";

export default function ForumHome() {
    return (<>
        <Navigation/>
        <div className='forum-page'>
            <h1>Forums</h1>
            <hr/>

            <div className='categories'>
                <Link to={'news'} state={{ category: 'news' }}>
                    <button className='category'>News</button>
                </Link>
                <Link to={'general'} state={{ category: 'general' }}>
                    <button className='category'>General</button>
                </Link>
                <Link to={'off-topic'} state={{ category: 'offTopic' }}>
                    <button className='category'>Off Topic</button>
                </Link>
                <Link to={'qa'} state={{ category: 'qa' }}>
                    <button className='category'>Q&A</button>
                </Link>
            </div>
        </div>

    </>);
}