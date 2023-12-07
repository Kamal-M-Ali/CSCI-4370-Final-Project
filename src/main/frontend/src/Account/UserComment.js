import './UserMade.css';
import Card from "../Card";

export default function UserComment(props) {
    return (
        <Card className='user-made-container'>
            <p>commented on <i>{props.details.extra}</i></p>
            <p><b>{props.details.body}</b></p>
            <p className='timestamp'>{props.details.created}</p>
        </Card>
    );
}