import React from 'react';
import '../App.css';
import "../static/css/player/newGame.css"

export default function newGame(){
    return(
        <div className="fondo">
            <button className="boton">Partida Rápida</button>
            <button className="boton">Partida Competitiva</button>      
    </div>
    );
}