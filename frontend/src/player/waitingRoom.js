import React,{useState,useEffect,useRef} from 'react';
import '../App.css';
import "../static/css/player/quickWaitingRoom.css";
import "../static/css/player/newGame.css"
import { useParams,Link, } from 'react-router-dom';
import tokenService from '../services/token.service';

const user = tokenService.getUser();

export default function WaitingRoom(){
    const {id} = useParams();    
    const[game,setGame]=useState({});   
    const [players,setPlayers]=useState([]);
    const [playerNames,setPlayerNames]=useState([]);
    const [FriendsNotPlaying, setFriendsNotPlaying] = useState([]);
    const [friendUsername, setFriendUsername] = useState('');
    const tableRef = useRef(null);
    


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
    },[players]);

    useEffect(()=>{
        const getFriendsNotPlaying = async () => {
            try {
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));
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
                        console.log(friendsNotPlayingList);
                        setFriendsNotPlaying(friendsNotPlayingList);
                    }
                }
            } catch (error) {
                console.error('Error fetching friends playing:', error);
            }
        }
        getFriendsNotPlaying();
    })

    const sendInvitationRequest = async (event) => {
        try {
            const friendUsername = event.target.textContent;
            alert(friendUsername)
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
                alert("bien")
                setFriendUsername(friendUsername);// Limpia el input
            }
            else {
                const errorMessage = await response.text();
                setFriendUsername(''); // Limpia el input
            }
        } catch (error) {
            if (error.response && error.response.status === 409) {
                const errorMessage = await error.response.text();
                setFriendUsername(''); // Limpia el input
            } else {
                console.error('Error sending Invitation :', error);
            }
        }
    };
    

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
                        <Link className='button'>The Pit </Link>
                    </div>
                    <div className="social">
                        <div className="friendsNotPlaying">
                            <h5>Friends to Invite</h5>
                            <table ref={tableRef}>
                                <thead>
                                    <tr>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {FriendsNotPlaying.map(friend => (
                                        <tr key={friend.id}>
                                            <td><a>INVITE </a></td>
                                            <div className='button' onClick={sendInvitationRequest}>{friend.user.username}</div>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                

            </div>
        </div>
    );
}