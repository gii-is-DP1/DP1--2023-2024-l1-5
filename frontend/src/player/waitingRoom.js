import React, { useState, useEffect, useRef} from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';

import '../App.css';
import "../static/css/player/newGame.css";
import "../static/css/player/quickWaitingRoom.css";
import tokenService from '../services/token.service';

export default function WaitingRoom(){
    const {id} = useParams();    
    const [game,setGame]=useState({});   
    const [players,setPlayers]=useState([]);
    const [playerNames,setPlayerNames]=useState([]);
    const [playerId, setPlayerId] = useState(null);
    const user = tokenService.getUser();
    const [roundId, setRoundId] = useState(null);
    const [friendsNotPlaying, setFriendsNotPlaying] = useState([]);
    const [friendUsername, setFriendUsername] = useState('');
    const tableRef = useRef(null);
    // const jwt = JSON.parse(window.localStorage.getItem("jwt"));

    ; // Reemplaza con la URL de tu servidor

    // useEffect(() => {
    //     const socket = io('ws://localhost:8080',{
    //     transports: ['websocket'],
    //     extraHeaders:{
    //         Authorization: `Bearer ${jwt}`
    //     },  
    // });
    //     socket.on('/topic/shuffle-completed', (redirectUrl) => {
    //         console.log("Reparto realizado");
    //         window.location.href = `/player/game/${id}/${redirectUrl}`;
    //     });

    //     return () => {
    //         // Desconectar el socket cuando el componente se desmonta
    //         if (socket.connected) {
    //             socket.disconnect();
    //         }
    //     };
    // }, []);

    async function setUp() {
        const jwt = JSON.parse(window.localStorage.getItem("jwt"));
        const myplayer = await fetch(`/api/v1/players/user/${user.id}`,
            {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            })
        if (myplayer.ok) {
            const data = await myplayer.json();
            setPlayerId(data.id);


        }


    }

    useEffect(() => {
        const getGame = async () => {
            try {
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));
                const response = await fetch(`/api/v1/games/${id}`,
                    {
                        method: 'GET',
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${jwt}`,
                        },
                    });
                if (response.ok) {
                    const data = await response.json();
                    setGame(data);
                    setPlayers(data.playerList);
                    setRoundId(data.roundList[0]);
                } else {
                    console.error("Error al obtener la partida", response.statusText);
                }
            }
            catch (error) {
                console.error("Error al obtener la partida", error);
            }
        } catch (error) {
            console.error('Error sending Invitation:', error);
            setFriendUsername('');
        }
    };
              
    const deletePlayerFromGame = async (currentUserId) => {
        try {
        const gameId = id; 
        const jwt = JSON.parse(window.localStorage.getItem("jwt"));
        const response = await fetch(`/api/v1/games/${gameId}/players/${currentUserId}`, {
            method: "DELETE",
            headers: {
            Authorization: `Bearer ${jwt}`,
            "Content-Type": "application/json",
            },
        });

        if (response.ok) {
            window.location.href = `/`;
        } else {
            const errorMessage = await response.text();
            console.error('Error Leaving The Game:', errorMessage);
        }
        } catch (error) {
        console.error('Error Leaving The Game:', error);
        }
    };
    
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
    useEffect(() => {
        const getPlayer = async () => {
            try {
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));
                const playersPromises = players.map(async (x) => {
                    try {
                        const response = await fetch(`/api/v1/players/${x.id}`, {
                            method: 'GET',
                            headers: {
                                "Content-Type": "application/json",
                                Authorization: `Bearer ${jwt}`,
                            },
                        });
                        const data = await response.json();
                        return data.playerUsername;
                    } catch (error) {
                        console.error("Error en la promesa individual:", error);
                        // Puedes decidir cómo manejar el error en cada promesa individual
                        // Por ejemplo, retornar un valor predeterminado o lanzar una nueva promesa resuelta con un valor predeterminado.
                        return "Nombre no disponible";
                    }
                });
                const updatedPlayerNames = await Promise.all(playersPromises);
                setPlayerNames(updatedPlayerNames);
                console.log(updatedPlayerNames);
            } catch (error) {
                console.error("Error al obtener nombres de jugadores", error);
            }
        };
        getPlayer();
    }, [players]);

    useEffect(()=>{
        const getPlayerName = async() =>{
            try{
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));
                const playersPromises = players.map(async(x)=>{
                    const response = await fetch(`/api/v1/players/${x.id}`,
                    {
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

    console.log("Componente renderizado");
    useEffect(() => {
        fetchData();
        console.log("hola")

        const intervalId = setInterval(() => {
            fetchData();
        }, 10000);

    const shuffle = async() =>{
            try{
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));
                const response = await fetch(`/api/v1/rounds/shuffle`,
                {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${jwt}`,
                    },
                    body: JSON.stringify(roundId),
                });
                if(response.ok){
                console.log("Reparto realizado");
                 window.location.href = `/game/quickPlay/${id}/${roundId}`;

            }else{
                console.error("Error al realizar el ", response.statusText);
            }

        }catch(error){
            console.error("Error al realizar el reparto", error);
        }
    }
    useEffect(() => {
        setUp();
    }, []);

    

    return(
        return () => clearInterval(intervalId); // Limpiar el intervalo cuando el componente desmonte
    }, [id]);

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
                    <span className="title">{game.gameMode} WAITING ROOM  </span>
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
                        <Link className='button' onClick={game.creator!==playerId?null:shuffle} disabled={playerId!==game.creator}>
                            {game.creator!==playerId?'Waiting':'Start'}
                        </Link>
                    </div>  
                    <div className="social">
                        {friendsNotPlaying.length > 0 && <FriendsInviteFloatingBox friendNotPlaying={friendsNotPlaying} />}
                    </div>
                    <Link onClick={() => deletePlayerFromGame(user.id)} className='button-leave'>Leave Game</Link>
                </div>
            </div>
        </div>
    );
}

