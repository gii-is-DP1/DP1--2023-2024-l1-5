import React from 'react';
import '../App.css';
import '../static/css/home/home.css';
import logo from '../static/images/dobble_logo.png' 

export default function Home(){
    return(
        <div className="home-page-container">
            <div className="hero-div">
                <h1>Dobble</h1>
                <h3>---</h3>
                <h3>The most funny game</h3>               
            </div>
        </div>
    );
}