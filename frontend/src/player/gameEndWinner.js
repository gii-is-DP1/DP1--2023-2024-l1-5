import { useNavigate } from 'react-router-dom';
import "../static/css/player/gameEnd.css";

export default function GameEnd() {
    const navigate = useNavigate();

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

    return(
        <div className="game-end-container">
            <WinnerEnd />
        </div>
    );
}