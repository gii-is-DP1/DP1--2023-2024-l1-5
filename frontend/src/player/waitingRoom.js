import React,{useState,useEffect} from 'react';
import '../App.css';
import "../static/css/player/quickWaitingRoom.css";
import "../static/css/player/newGame.css"
import { useParams,Link } from 'react-router-dom';
import tokenService from '../services/token.service';


export default function WaitingRoom(){
    const {id} = useParams();    
    const [game,setGame]=useState({});   
    const [players,setPlayers]=useState([]);
    const [playerNames,setPlayerNames]=useState([]);
    const [creator, setCreator] = useState('');
    const [playerId, setPlayerId] = useState(null);


    
    const user = tokenService.getUser();
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
                    setCreator(data.creator);
                    setPlayers(data.playerList);
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
    // console.log(game);
    // console.log(creator);
    // console.log(playerId);


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
                //console.log(playerNames)
            }catch(error){
                console.error("Error al obtener el nombre del jugador", error);
            }
        }
        getPlayerName();
    },[players])


    const handleStart = async () => {
        try {
            //const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const jwt = tokenService.getLocalAccessToken();
            
            const response3 = await fetch('/api/v1/games/quick/joinRandom',
            {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${jwt}`,
                },
            });
            if(response3.ok){
                const data = await response3.json();
                const gameId = data.id;
                console.log("DATA",data);
                //const roundId = data;
                window.location.href = `/game/quickPlay/${gameId}`;
        } else{
            console.error("Error al crear la partida", response3.statusText);
        }
    }catch(error) {
        console.error("Error:Ya perteneces a una partida", error);


    }
}

    return(
        <div className="wallpaper">
            <div className="horizontal">
                <div className='distr'>
                    <span className="title">{game.gameMode} WAITING ROOM </span>
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
                        <Link to="/game/quickPlay/1/1" className='button' onClick={playerId !== creator ? null : handleStart} disabled={playerId !== creator}>
                            {playerId !== creator ? 'Waiting' : 'START'}
                        </Link>
                    </div>
                </div>

            </div>
        </div>
    );
}