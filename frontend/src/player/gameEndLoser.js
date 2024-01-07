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

    return(
        <div className="game-end-container">
            <LosserEnd />
        </div>
    );
}