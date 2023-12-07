import './UserMade.css';
import Card from "../Card";

export default function UserPost(props) {
    return (
        <Card className='user-made-container'>
            <p>created a thread <i>{props.details.title}</i> in <b>{props.details.category}</b></p>
            <p className="timestamp">{props.details.created}</p>
        </Card>
    );
}