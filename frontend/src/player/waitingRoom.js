import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
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
    const [buttonClicked, setButtonClicked] = useState(false);
    const [friendsNotPlaying, setFriendsNotPlaying] = useState([]);
    const [friendUsername, setFriendUsername] = useState('');

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
            } catch (error) {
                console.error("Error al obtener la partida", error);
            }
        }
        getGame();
    }, [id]);

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
                    const data = await response.json();
                    return data.playerUsername;
                });
                const playerNames = await Promise.all(playersPromises);
                setPlayerNames(playerNames);
            }catch(error){
                console.error("Error al obtener el nombre del jugador", error);
            }
        }
        getPlayerName();
    },[players]);

    
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

    useEffect(() => {
        fetchData();
        const intervalId = setInterval(() => {
            fetchData();
        }, 10000);

        return () => clearInterval(intervalId); // Limpiar el intervalo cuando el componente desmonte

    }, [id]);

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

            }else{
                console.error("Error al realizar el ", response.statusText);
            }

        }catch(error){
            console.error("Error al realizar el reparto", error);
        }
    }
    const ready = async() => {
        try {
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            if (!buttonClicked) { // Verifica si el botón ya se ha presionado
                const response = await fetch(`/api/v1/gameInfo/ready/${game.id}`, {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                if (response.ok) {
                    setButtonClicked(true); // Actualiza el estado para indicar que se ha presionado el botón
                    checkAllPlayersReady(game.id);
                }
            }
        } catch (error) {
            console.error("Error al realizar el reparto", error);
        }
    }

    const checkAllPlayersReady = async (gameId) => {
        try {
            let noPlayers = false;
            while (!noPlayers) {
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));
                const response = await fetch(`/api/v1/gameInfo/${gameId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });
                if (response.ok) {
                    const gameInfo = await response.json();
                    if (gameInfo.numPlayers === 0) {
                        if(gameInfo.creator === playerId){
                            shuffle();
                        }
                        noPlayers = true; // Establecer noPlayers a true para salir del ciclo
                        setTimeout(() => {
                            window.location.href = `/game/quickPlay/${id}/${roundId}`;
                        }, 3000);
                    } else {
                        console.log("El número de jugadores no es 0 en gameInfo. Esperando...");
                        await new Promise((resolve) => setTimeout(resolve, 1000));
                    }
                }
            }
        } catch (error) {
            console.error("Error al obtener gameInfo", error);
        }
    };

    useEffect(() => {
        setUp();
    }, []);

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
                        <Link className={`button ${buttonClicked ? 'disabled' : ''}`} onClick={ready} disabled={buttonClicked}>
                                    {'Ready'}
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
