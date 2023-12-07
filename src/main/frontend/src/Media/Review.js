import './Review.css';
import { Link } from 'react-router-dom';
import Card from "../Card";

export default function Review(props) {
    return (
        <Card className='review'>
            <Link to={`/view/profile/:${props.details.user_id}`} state={{ userId: props.details.user_id }}>
                {props.details.profile_name} gave {props.details.score}/5⭐,
            </Link>
            <p><b>{props.details.body}</b></p>
            <p className="timestamp">{props.details.created}</p>
        </Card>
    );
}