import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import "../static/css/player/gameEnd.css";
import tokenService from "../services/token.service";

export default function GameEnd() {
    const navigate = useNavigate();
    const jwt = JSON.parse(window.localStorage.getItem("jwt"));
    const user = tokenService.getUser();
    const { id } = useParams();
    const [winnerId, setWinnerId] = useState(null);

    useEffect(() => {
        const fetchWinnerId = async () => {
            const responseWinner = await fetch(`/api/v1/games/winner/${id}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${jwt}`,
                },
            });

            if (responseWinner.ok) {
                const responseText = await responseWinner.text();
                if (responseText.trim() !== "") {
                    const win = JSON.parse(responseText);
                    setWinnerId(win);
                }
            } else {
                console.log("Error en la respuesta del servidor:", responseWinner.status);
            }
        };

        fetchWinnerId();
    }, [id, jwt]);

    const goToMainMenu = () => {
        navigate('/');
    };

    const startNewGame = () => {
        navigate('/game');
    };

    const WinnerEnd = () => (
        <div className="container">
            <h1>👑¡Felicidades, has ganado!👑</h1>
            <h4>¿Qué te gustaría hacer a continuación?</h4>
            <div className="botones-container">
                <button className="boton_left_winner" onClick={goToMainMenu}>Volver al Menú Principal</button>
                <button className="boton_rigth_winner" onClick={startNewGame}>Iniciar Nueva Partida</button>
            </div>
        </div>
    );

    const LosserEnd = () => (
        <div className="container">
            <h1>❌¡Lo siento, has perdido!❌</h1>
            <h4>¿Qué te gustaría hacer a continuación?</h4>
            <div className="botones-container">
                <button className="boton_left_losser" onClick={goToMainMenu}>Volver al Menú Principal</button>
                <button className="boton_rigth_losser" onClick={startNewGame}>Iniciar Nueva Partida</button>
            </div>
        </div>
    );

    return (
        <div className="game-end-container">
            {user.id === winnerId ? <WinnerEnd /> : <LosserEnd />}
        </div>
    );
}
