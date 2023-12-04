import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Home';
import Signup from "./Account/Signup";
import Login from "./Account/Login";
import User from "./Account/User";
import Admin from "./Admin/Admin";
import AddGame from "./Admin/AddGame";
import AddMovie from "./Admin/AddMovie";
import AddShow from "./Admin/AddShow";
import AddBook from "./Admin/AddBook";
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
                <Route path='*' element={<ErrorPage/>}/>
            </Routes>
        </Router>
    )
}
