import React,{useState,useEffect,useRef, useInterval } from 'react';
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
    


    const getGame = async () => {
        try {
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const response = await fetch(`/api/v1/games/${id}`, {
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
        } catch (error) {
            console.error("Error al obtener la partida", error);
        }
    };

    const getPlayerNames = async () => {
        try {
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const playersPromises = players.map(async (x) => {
                const response = await fetch(`/api/v1/players/${x.id}`, {
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
        } catch (error) {
            console.error("Error al obtener el nombre del jugador", error);
        }
    };

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
                    setFriendsNotPlaying(friendsNotPlayingList);
                }
            }
        } catch (error) {
            console.error('Error fetching friends not playing:', error);
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
                alert("ENVIADA")
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


    useEffect(() => {
        const fetchData = async () => {
            try {
                await getGame();
                await getPlayerNames();
                await getFriendsNotPlaying();
               // setTimeout(fetchData, 10000);
            } catch (error) {
                console.error('Error in fetchData:', error);
            }
        };

        fetchData();

        return () => clearTimeout();
    }, [id, players]);

    const FriendsInviteFloatingBox = ({ friendNotPlaying, sendInvitationRequest }) => {
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
    const InvitationsFloatingBox = ({ invitations }) => {
        return (
            <div className="invitation-box floating-box" style={{ maxWidth: '800px' }}>
                <h3>INVITATIONS</h3>
                <ul style={{ listStyleType: 'none', padding: 0 }}>
                    {invitations.map((invitation) => (
                        <li key={invitation.id} style={{ marginBottom: '10px' }}>
                            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                                <span style={{ marginRight: '5px' }}>
                                    <a>{invitation.source_user}</a>
                                </span>
                                <span style={{ marginRight: '5px' }}>
                                    <Link to={`/game/quickPlay/${invitation.game.id}`} className='aceptar-boton' onClick={() => handleButton(invitation.game, invitation.id, 'ACEPTAR')}>ACEPTAR</Link>
                                </span>
                                <span>
                                    <div className='rechazar-boton' onClick={() => handleButton(invitation.game, invitation.id, 'RECHAZAR')}>RECHAZAR</div>
                                </span>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        );
    };
    async function handleButton(game, invitationId, action) {
        if (action == "ACEPTAR"){
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const invitationResponse = await fetch(`/api/v1/invitations/acceptRequest/${invitationId}`,
            {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json", 
                    Authorization: `Bearer ${jwt}`,
                },
            });
            if(invitationResponse.ok){
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));

                const gameResponse = await fetch(`/api/v1/games/quick/joinInvitation/${game.id}`,
                {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json", 
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                if(gameResponse.ok){

                }
            }
        }else{
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const invitationResponse = await fetch(`/api/v1/invitations/refusededRequest/${invitationId}`,
            {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json", 
                    Authorization: `Bearer ${jwt}`,
                },
            });
            alert("REFUSED")
            
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
                        <Link className='button'>The Pit </Link>
                    </div>
                    <div className="social">
                        {FriendsNotPlaying.length > 0 && <FriendsInviteFloatingBox friendNotPlaying={FriendsNotPlaying} sendInvitationRequest={sendInvitationRequest} />}

                    </div>
                </div>

                

            </div>
        </div>
    );
}