import './Media.css';
import { Link } from 'react-router-dom';
import Card from "../Card";


export default function Media(props) {
    return (
        <Card className='book'>
            <h2>{props.details.title}, [{props.details.score} / 5⭐]</h2>

            <Link to={`/view/${props.mediaType}/${props.details.media_id}`} state={{ media: props.details }}>
                <button className='view-details-btn'>View Details</button>
            </Link>
        </Card>
    );
}