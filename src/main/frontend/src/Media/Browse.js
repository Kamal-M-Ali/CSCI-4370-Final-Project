import './Browse.css';
import { useLocation } from "react-router-dom";
import { useState, useEffect } from 'react';
import { FixedSizeList as List } from "react-window";
import axios from 'axios';
import Navigation from "../Navigation";
import Media from './Media';

export default function Browse() {
    const location = useLocation()
    const { mediaType } = location.state
    const [media, setMedia] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [sortState, setSortState] = useState('a-z');
    function fetch() {
        let ignore = false;

        setMedia([]);
        async function fetchData() {
            const res = await axios('http://localhost:8080/api/media/:' + mediaType, {
                params: {
                    sort: sortState,
                    searchTerm: searchTerm
                }
            });
            if (!ignore) setMedia(res.data.length === 0 ? null : res.data);
        }
        fetchData().catch((e) => { console.log(e); });
        return () => { ignore = true; }
    }
    useEffect(fetch, [mediaType, searchTerm, sortState]);


    return (<>
        <Navigation />
        <div className='search-container'>
            <input className='searchbar' type='text' placeholder='Search' onInput={(e) => setSearchTerm(e.target.value.toLowerCase())}></input>

            <div className='search-criteria'>
                <h3>Sort by:</h3>
                <input type='radio' name='sort' checked={sortState === 'a-z'} onChange={() => setSortState('a-z')} />
                <label htmlFor='a-z'>Title (a-z)</label>
                <input type='radio' name='sort' checked={sortState === 'ratingh'} onChange={() => setSortState('ratingh')} />
                <label htmlFor='priceh'>Score (high-low)</label>
                <input type='radio' name="sort" checked={sortState === 'ratingl'} onChange={() => setSortState('ratingl')} />
                <label htmlFor="pricel">Score (low-high)</label>
            </div>
        </div>

        <div className='browse'>
            {(media === null ?
                    <h1>No results.</h1>
                    :
                    media.length === 0 ?
                    <h1>Fetching data from database...</h1>
                    :
                    <List
                        innerElementType='ul'
                        itemData={media}
                        itemCount={media.length}
                        itemSize={100}
                        height={650}
                    >
                        {({index, style}) => {
                                return <li style={style}>
                                    <Media details={media[index]} mediaType={mediaType} />
                                </li>
                        }}
                    </List>
            )}
        </div>
    </>)
}