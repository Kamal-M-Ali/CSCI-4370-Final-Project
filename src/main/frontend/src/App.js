import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from "./Home";
import Signup from "./Account/Signup";
import Login from "./Account/Login";
import User from "./Account/User";
import Admin from "./Admin/Admin";
import AddGame from "./Admin/AddGame";
import AddMovie from "./Admin/AddMovie";
import AddShow from "./Admin/AddShow";
import AddBook from "./Admin/AddBook";
import Browse from "./Media/Browse";
import View from "./Media/View";
import Profile from "./Account/Profile";
import Reviews from "./Media/Reviews";
import ForumHome from "./Forum/ForumHome";
import Category from "./Forum/Category";
import Create from "./Forum/Create";
import Post from "./Forum/Post";
import ErrorPage from './ErrorPage';

export default function App() {
    return (
        <Router>
            <Routes>
                <Route exact path='/' element={<Home/>}/>
                <Route path='signup' element={<Signup/>}/>
                <Route path='login' element={<Login/>}/>
                <Route path='account' element={<User/>}/>
                <Route path='admin' element={<Admin/>}/>
                <Route path='admin/game' element={<AddGame/>}/>
                <Route path='admin/movie' element={<AddMovie/>}/>
                <Route path='admin/show' element={<AddShow/>}/>
                <Route path='admin/book' element={<AddBook/>}/>
                <Route path='games' element={<Browse/>}/>
                <Route path='books' element={<Browse/>}/>
                <Route path='movies' element={<Browse/>}/>
                <Route path='shows' element={<Browse/>}/>
                <Route path='view/:mediaType/:mediaId' element={<View/>}/>
                <Route path='view/profile/:userId' element={<Profile/>}/>
                <Route path='reviews/:mediaType/:mediaId' element={<Reviews/>}/>
                <Route path='forum' element={<ForumHome/>}/>
                <Route path='forum/:category' element={<Category/>}/>
                <Route path='forum/:category/create' element={<Create/>}/>
                <Route path='forum/post/:thread_id' element={<Post/>}/>
                <Route path='*' element={<ErrorPage/>}/>
            </Routes>
        </Router>
    )
}
