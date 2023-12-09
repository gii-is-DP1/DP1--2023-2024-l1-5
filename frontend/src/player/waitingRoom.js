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
    const [creator, setCreator] = useState('');
    const [playerId, setPlayerId] = useState(null);
    const user = tokenService.getUser();
    const [roundId, setRoundId] = useState(null);


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
        getPlayer();
    });

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
                }
                else{
                    console.error("Error XD", response.statusText);
                }
            }catch(error){
                console.error("Error al realizar el reparto", error);
            }
        }

    return(
        <div className="wallpaper">
            <div className="horizontal">
                <div className='distr'>
                    <span className="title">{game.gameMode} WAITING ROOM {roundId}  </span>
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
                        <Link className='button' onClick={shuffle}>The Pit </Link>
                    </div>  
                </div>

            </div>
        </div>
    );
}