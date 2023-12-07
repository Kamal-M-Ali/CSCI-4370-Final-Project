import './Reviews.css';
import { useLocation } from 'react-router-dom';
import Card from "../Card";
import Navigation from "../Navigation";
import Review from "./Review";

export default function Reviews(props) {
    const { media, reviews } = useLocation().state;

    return (<>
        <Navigation/>
        <div className='reviews-page'>
            <Card className='reviews-main'>
                <h2>Reviews for <i>{media}</i></h2>
                <hr/>

                {reviews.length > 0 ?
                    <div className='reviews-container'>
                        {reviews.map((review, k) =>
                            <Review key={k} details={review}/>
                        )}
                    </div>
                    : <p>No reviews yet.</p>}


            </Card>
        </div>

    </>);
}