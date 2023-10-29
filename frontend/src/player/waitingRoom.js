import React,{useState,useEffect} from 'react';
import '../App.css';
import "../static/css/player/quickWaitingRoom.css";
import "../static/css/player/newGame.css"
import { useParams,Link } from 'react-router-dom';


export default function WaitingRoom(){
    const {id} = useParams();    
    const[game,setGame]=useState({});   

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

    return(
        <div className="wallpaper">
            <div className="horizontal">
                <div className='distr'>
                    <span className="title">{game.gameMode} WAITING ROOM </span>
                    <div className='distrPlay'>
                        <span className='text2'>  Players  {game.numPlayers} / 8 </span>
                        {/* <ul>
                    {game.players.map((player,index) => {
                        return(<li key={index}> {player.playerUsername} </li>)
                    } )}
                  </ul> */}
                    </div>
                </div>  
                <div className='vertical'>
                    <div className="inButton">
                        <Link className='button'>The Pit </Link>
                    </div>
                </div>



            </div>
        </div>
    );
}