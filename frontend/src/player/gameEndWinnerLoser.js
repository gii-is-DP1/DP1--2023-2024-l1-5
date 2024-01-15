import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import "../static/css/player/gameEnd.css";
import tokenService from "../services/token.service";

export default function GameEnd() {
    const navigate = useNavigate();
    const user = tokenService.getUser();
    const { id, winnerId } = useParams();

    const goToMainMenu = () => {
        navigate('/');
    };

    const startNewGame = () => {
        navigate('/game');
    };

    const WinnerEnd = () => (
        <div className="container">
            <h1>👑Congratulations, you have won!👑</h1>
            <h4>What would you like to do next?</h4>
            <div className="botones-container">
                <button className="boton_left_winner" onClick={goToMainMenu}>Back to Main Menu</button>
                <button className="boton_rigth_winner" onClick={startNewGame}>Start New Game</button>
            </div>
        </div>
    );

    const LosserEnd = () => (
        <div className="container">
            <h1>❌Sorry, you lost!❌</h1>
            <h4>What would you like to do next?</h4>
            <div className="botones-container">
                <button className="boton_left_losser" onClick={goToMainMenu}>Back to Main Menu</button>
                <button className="boton_rigth_losser" onClick={startNewGame}>Start New Game</button>
            </div>
        </div>
    );

    return (

        <div className="game-end-container">
            {user.id == winnerId ? <WinnerEnd /> : <LosserEnd />}
        </div>
    );
}
