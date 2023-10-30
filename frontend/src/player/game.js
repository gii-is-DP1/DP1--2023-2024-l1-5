import React from 'react';
import '../App.css';
import "../static/css/player/game.css"

export default function Game(){
    return(
        <div className="fondo">
            <button className="boton">Partida Rápida</button>
            <button className="boton">Partida Competitiva</button>      
        </div>
    );
}