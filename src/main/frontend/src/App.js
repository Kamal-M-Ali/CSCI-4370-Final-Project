import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Home';
import Signup from "./Account/Signup";
import Login from "./Account/Login";
import ErrorPage from './ErrorPage';
import User from "./Account/User";

export default function App() {
    return (
        <Router>
            <Routes>
                <Route exact path='/' element={<Home/>}/>
                <Route path='signup' element={<Signup/>}/>
                <Route path='login' element={<Login/>}/>
                <Route path='account' element={<User/>}/>
                <Route path='*' element={<ErrorPage/>}/>
            </Routes>
        </Router>
    )
}
