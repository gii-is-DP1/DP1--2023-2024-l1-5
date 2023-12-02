import React from 'react';
import '../App.css';
import "../static/css/player/newGame.css"
import { Link } from "react-router-dom";


export default function game(){

    return(
        <div className="wallpaper">
            <div className="gameButton">
            <Link to="/game/quickPlay" className="button">Quick Play</Link>
            <Link to="/game/competitiveGame" className="button">Competitive</Link>
            </div>
    </div>
    );
}