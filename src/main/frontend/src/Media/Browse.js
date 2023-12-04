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
    const [sortedMedia, setSortedMedia] = useState(null);
    const [sortState, setSortState] = useState('a-z');

    function fetch() {
        setMedia([]);
        axios
            .get('http://localhost:8080/api/media/:' + mediaType)
            .then((res) => {
                setMedia(res.data.sort((a, b) => a.title.localeCompare(b.title)));
            })
            .catch((err) => {
                console.log('Error getting items');
            });
    }
    useEffect(fetch, [mediaType]);

    const handleSearch = (e) => {
        const searchTerm = e.target.value.toLowerCase();
        let filtered = media.filter((el) => el.title.toLowerCase().includes(searchTerm));

        // Sort by
        if (sortState === 'a-z') {
            filtered = filtered.sort((a, b) => a.title.localeCompare(b.title));
        } else if (sortState === 'ratingh') {
            filtered = filtered.sort((a, b) => b.score - a.score);
        } else if (sortState === 'ratingl') {
            filtered = filtered.sort((a, b) => a.score - b.score);
        }

        setSortedMedia(filtered);
    }

    const sortResult = (e) => {
        setSortState(e);

        if (e === 'a-z') {
            setSortedMedia(media.sort((a, b) => a.title.localeCompare(b.title)));
        } else if (e === 'ratingh') {
            setSortedMedia(media.sort((a, b) => b.score - a.score));
        } else if (e === 'ratingl') {
            setSortedMedia(media.sort((a, b) => a.score - b.score));
        }
    }

    return (<>
        <Navigation />
        <div className='search-container'>
            <input className='searchbar' type='text' placeholder='Search' onChange={handleSearch}></input>

            <div className='search-criteria'>
                <h3>Sort by:</h3>
                <input type='radio' id='a-z' name='sort' checked={sortState === 'a-z'} onChange={(e) => sortResult(e.target.id)} />
                <label htmlFor='a-z'>Title (a-z)</label>
                <input type='radio' id='ratingh' name='sort' checked={sortState === 'ratingh'} onChange={(e) => sortResult(e.target.id)} />
                <label htmlFor='priceh'>Score (high-low)</label>
                <input type='radio' id='ratingl' name="sort" checked={sortState === 'ratingl'} onChange={(e) => sortResult(e.target.id)} />
                <label htmlFor="pricel">Score (low-high)</label>
            </div>
        </div>

        <div className='browse'>
            {(media.length === 0 ?
                    <h1>Fetching data...</h1>
                    :
                    <List
                        innerElementType='ul'
                        itemData={sortedMedia === null ? media : sortedMedia}
                        itemCount={sortedMedia === null ? media.length : sortedMedia.length}
                        itemSize={100}
                        height={650}
                    >
                        {sortedMedia === null ?
                            ({index, style}) => {
                                return <li style={style}>
                                    <Media details={media[index]} mediaType={mediaType} />
                                </li>
                            }
                            :
                            ({index, style}) => {
                                return <li style={style}>
                                    <Media details={sortedMedia[index]} mediaType={mediaType} />
                                </li>
                            }}
                    </List>
            )}
        </div>
    </>)
}