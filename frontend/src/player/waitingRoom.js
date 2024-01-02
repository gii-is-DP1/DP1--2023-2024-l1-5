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
        }
        getGame();
    }, [id]);

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
                    const data = await response.json();
                    return data.playerUsername;
                });
                const playerNames = await Promise.all(playersPromises);
                setPlayerNames(playerNames);
                console.log(playerNames)
            }catch(error){
                console.error("Error al obtener el nombre del jugador", error);
            }
        }
        getPlayerName();
    },[players])


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
        <div className="wallpaper">
            <div className="horizontal">
                <div className='distr'>
                    <span className="title">{game.gameMode} WAITING ROOM  </span>
                    <div className='distrPlay'>
                        <span className='text2'>  Players  {game.numPlayers} / 8 </span>
                        <ul>
                    {playerNames.map((player,index) => {
                        return(<li key={index}> {player} </li>)
                    } )}
                  </ul>
                    </div>
                </div>  
                <div className='vertical'>
                        <div className="inButton">
                            <Link className='button' onClick={game.creator!==playerId?null:shuffle} disabled={playerId!==game.creator}>
                                {game.creator!==playerId?'Waiting':'Start'}
                                </Link>
                        </div>  
                </div>

            </div>
        </div>
    );
}