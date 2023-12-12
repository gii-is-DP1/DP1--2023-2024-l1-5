import React, { useState, useEffect, useRef } from 'react';
import '../App.css';
import "../static/css/player/quickWaitingRoom.css";
import { useParams, Link, } from 'react-router-dom';
import tokenService from '../services/token.service';
import ChatComponent from './chatComponent';
import io from 'socket.io-client';



const user = tokenService.getUser();

export default function WaitingRoom() {
    const { id } = useParams();
    const [game, setGame] = useState({});
    const [players, setPlayers] = useState([]);
    const [playerNames, setPlayerNames] = useState([]);
    const [friendsNotPlaying, setFriendsNotPlaying] = useState([]);
    const [socket, setSocket] = useState(null);
    const [friendUsername, setFriendUsername] = useState('');
    const socketRef = useRef(null);

    const sendInvitationRequest = async (friendUsername) => {
        try {
            const gameId = id;
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const response = await fetch(`/api/v1/games/${gameId}/invitations/${friendUsername}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            if (response.ok) {
                setFriendUsername(friendUsername);
                alert("ENVIADA");
            } else {
                const errorMessage = await response.text();
                console.error('Error sending Invitation:', errorMessage);
                setFriendUsername('');
            }
        } catch (error) {
            console.error('Error sending Invitation:', error);
            setFriendUsername('');
        }
    };

    const fetchData = async () => {
        try {
            console.log("fetchData llamado");
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));

            // Obtener información del juego
            const gameResponse = await fetch(`/api/v1/games/${id}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${jwt}`,
                },
            });

            if (gameResponse.ok) {
                const gameData = await gameResponse.json();
                setGame(gameData);
                setPlayers(gameData.playerList);

                // Obtener nombres de los jugadores
                const playersPromises = gameData.playerList.map(async (x) => {
                    const playerResponse = await fetch(`/api/v1/players/${x.id}`, {
                        method: 'GET',
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${jwt}`,
                        },
                    });
                    const playerData = await playerResponse.json();
                    return playerData.playerUsername;
                });
                const playerNames = await Promise.all(playersPromises);
                setPlayerNames(playerNames);

                // Obtener amigos que no están jugando
                const userIdResponse = await fetch(`/api/v1/players/user/${user.id}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });

                if (userIdResponse.ok) {
                    const responseBody = await userIdResponse.json();
                    const playerId = responseBody.id;

                    const friendsPlayingResponse = await fetch(`/api/v1/friendship/friends/notplaying/${playerId}`, {
                        headers: {
                            Authorization: `Bearer ${jwt}`,
                            "Content-Type": "application/json",
                        },
                    });

                    if (friendsPlayingResponse.ok) {
                        const friendsNotPlayingList = await friendsPlayingResponse.json();
                        setFriendsNotPlaying(friendsNotPlayingList);
                    }
                }
            } else {
                console.error("Error al obtener la partida", gameResponse.statusText);
            }
        } catch (error) {
            console.error('Error in fetchData:', error);
        }
    };
    const onError = (error) => {
        console.error('Error en la conexión WebSocket:', error);
        // Lógica para manejar el error de conexión
    };

    console.log("Componente renderizado");
    useEffect(() => {
        fetchData();
        const intervalId = setInterval(() => {
            fetchData();
        }, 10000);

        socketRef.current = io('http://localhost:8080'); // Cambia la URL por tu servidor WebSocket

        socketRef.current.on('connect', () => {
            console.log('Conectado al servidor WebSocket');
            players.forEach(player => {
                console.log(`Conectando como ${player.id}`);
                // Puedes enviar datos o realizar acciones específicas para cada jugador aquí
            });
        });

        socketRef.current.on('disconnect', () => {
            console.log('Desconectado del servidor WebSocket');
        });

        return () => {
            clearInterval(intervalId);
            socketRef.current.disconnect();
        };

    }, [id, players]);

    const FriendsInviteFloatingBox = ({ friendNotPlaying }) => {
        return (
            <div className="floating-box" style={{ maxWidth: '800px', position: 'fixed', bottom: '20px', right: '20px', backgroundColor: 'white', padding: '10px', borderRadius: '8px', boxShadow: '0px 0px 10px 0px rgba(0,0,0,0.2)' }}>
                <h3>Friends to Invite</h3>
                <table style={{ width: '100%' }}>
                    <thead>
                        <tr>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {friendNotPlaying.map(friend => (
                            <tr key={friend.id}>
                                <td style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                    <a>{friend.user.username}</a>
                                    <div className='invite-boton' onClick={() => sendInvitationRequest(friend.user.username)}>INVITE</div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    };

    return (
        <div className="wallpaper">
            <div className="horizontal">
                <div className='distr'>
                    <span className="title">{game.gameMode} WAITING ROOM </span>
                    <div className='distrPlay'>
                        <span className='text2'>  Players  {game.numPlayers} / 8 </span>
                        <ul>
                            {playerNames.map((player, index) => {
                                return (<li key={index}> {player} </li>)
                            })}
                        </ul>
                    </div>
                </div>
                <div className='vertical'>
                    <div className="inButton">
                        <Link className='button'>The Pit </Link>
                    </div>
                    <ChatComponent />
                </div>
            </div>
            <div className="social">
                {friendsNotPlaying.length > 0 && <FriendsInviteFloatingBox friendNotPlaying={friendsNotPlaying} />}
            </div>
        </div>
    );
}
