import { useNavigate } from 'react-router-dom';
import "../static/css/player/construction.css";

export default function Construction() {
    const navigate = useNavigate();

    const goToMainMenu = () => {
        navigate('/');
    };

    const redirectToGmail = () => {
        const emailTo = 'alvbercau@alum.us.es';
        
        const subject = 'Avísame del lanzamiento';
        
        const body = 'Hola, avísame cuando el modo de juego esté disponible.';

        const mailtoLink = `mailto:${emailTo}?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;

        // Redirige a la URL de Gmail
        window.location.href = mailtoLink;
    };

    const Construction = () => (
        <div className="container">
            <h1>⛏️⚠️🚧 Under construction...⛏️⚠️🚧</h1>
            <h4>This part of the application has not yet been developed, sorry for the inconvenience.</h4>
            <div className="botones-container">
                <button className="boton_left_winner" onClick={goToMainMenu}>Back to Main Menu</button>
                <button className="boton_rigth_winner" onClick={redirectToGmail}>Let me know when the launch is announced</button>
            </div>
        </div>
    );

    return (
        <div className="game-end-container">
            <Construction />
        </div>
    );
}
