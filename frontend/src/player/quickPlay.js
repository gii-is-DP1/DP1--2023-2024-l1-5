import React from 'react';
import '../App.css';
import "../static/css/player/quickPlay.css"

export default function QuickPlay(){
    return(
        <div className="fondo">
            <button className="boton">Partida Rápida</button>
            <button className="boton">Partida Competitiva</button>      
        </div>
    );
}