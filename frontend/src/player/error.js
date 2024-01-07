import { useNavigate } from 'react-router-dom';
import "../static/css/player/error.css";

export default function Error() {
    const navigate = useNavigate();

    const goToMainMenu = () => {
        navigate('/');
    };

    const redirectToGmail = () => {
        const emailTo = 'alvbercau@alum.us.es';
        
        const subject = 'Problemas técnicos en el juego';
        
        const body = 'Hola, he tenido problemas técnicos en el juego.';

        const mailtoLink = `mailto:${emailTo}?subject=${encodeURIComponent(subject)}&body=${encodeURIComponent(body)}`;

        // Redirige a la URL de Gmail
        window.location.href = mailtoLink;
    };

    const ErrorMessage = () => (
        <div className="container">
            <h1>😞 ¡Ups, ha habido un problema! 😞</h1>
            <p>Lo sentimos, hemos experimentado dificultades técnicas. Por favor, contacta con nuestro soporte enviándonos un correo.</p>
            <div className="botones-container">
                <button className="boton_left_winner" onClick={goToMainMenu}>Volver al Menú Principal</button>
                <button className="boton_rigth_winner" onClick={redirectToGmail}>Contactar por Correo</button>
            </div>
        </div>
    );

    return (
        <div className="game-end-container">
            <ErrorMessage />
        </div>
    );
}
