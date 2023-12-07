import './Media.css';
import { Link } from 'react-router-dom';
import Card from "../Card";


export default function Media(props) {
    return (
        <Card className='media'>
            <h2>{props.details.title}, [{Math.round(props.details.score * 100) / 100}/5⭐]</h2>

            <Link to={`/view/${props.mediaType}/${props.details.media_id}`} state={{ mediaId: props.details.media_id, mediaType: props.mediaType }}>
                <button className='view-details-btn'>View Details</button>
            </Link>
        </Card>
    );
}
