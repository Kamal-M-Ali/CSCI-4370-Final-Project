import './Comment.css';
import { Link } from 'react-router-dom';
import Card from "../Card";

export default function Comment(props) {
    return (
        <Card className='comment'>
            <Link to={`/view/profile/:${props.details.user_id}`} state={{ userId: props.details.user_id }}>
                {props.details.extra}
            </Link>
            <p><b>{props.details.body}</b></p>
            <p className="timestamp">{props.details.created}</p>
        </Card>
    );
}