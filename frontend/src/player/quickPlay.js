import React, { useState, useEffect, useRef } from 'react';
import '../App.css';
import "../static/css/player/newGame.css"
import '../static/css/main.css'
import { Link } from "react-router-dom";
import tokenService from '../services/token.service';
import eyeLogo from '..//static/images/eye.png';

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function QuickPlay() {
    const [error, setError] = useState(null);
    const [error2, setError2] = useState(null);
    const [error3,setError3] = useState(null);
    const [playerId, setPlayerId] = useState(null);
    const tableRef = useRef(null);
    const [otherGamesFriends, setOtherGamesFriends] = useState([]);
    const [gameId, setGameId] = useState(null);
    const [roundId, setRoundId] = useState(null);

    const requestBody1 = {
        gameMode: "QUICK_PLAY"
    }

    async function setUp() {
        const jwt = JSON.parse(window.localStorage.getItem("jwt"));
        const myplayer = await fetch(`/api/v1/players/user/${user.id}`,
            {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            })
        const getFriendsPlaying = async (playerId) => {
            try {
                const friendsPlayingResponse = await fetch(`/api/v1/friendship/friends/playing/${playerId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });
                if (friendsPlayingResponse.ok) {
                    const friendsPlayingList = await friendsPlayingResponse.json();
                    console.log(friendsPlayingList);
                    setOtherGamesFriends(friendsPlayingList);
                }
            } catch (error) {
                console.error('Error fetching friends playing:', error);
            }
        };

        const getFriendGameInfo = async (playerId) => {
            try {
                const friendGameInfoResponse = await fetch(`/api/v1/games/inProgress/${playerId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });
                if (friendGameInfoResponse.ok) {
                    const friendGameInfo = await friendGameInfoResponse.json()
                    setGameId(friendGameInfo.id); 
                    setRoundId(friendGameInfo.rounds[0].id);
                }
                console.log(gameId)
                console.log(roundId)
            } catch (error) {
                console.error('Error fetching friends games:', error);
            }
        };

        if (myplayer.ok) {
            const data = await myplayer.json();
            setPlayerId(data.id);
            getFriendsPlaying(data.id);
            getFriendGameInfo(data.id);
        }

        
    }

    const CreateThePit = async () => {

        const requestBody2 = {
            roundMode: "PIT",
        }

        try {
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));

            const response1 = await fetch('/api/v1/games',
                {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${jwt}`,
                    },
                    body: JSON.stringify(requestBody1),
                });
            if (response1.ok) {
                const data = await response1.json();
                window.location.href = `/game/quickPlay/${data.id}`;
                const response2 = await fetch('/api/v1/rounds',
                    {
                        method: 'POST',
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${jwt}`,
                        },
                        body: JSON.stringify(requestBody2),
                    });
                if (!(response2.ok)) {
                    console.error("Error al crear la ronda", response2.statusText);
                }
            } else {
                console.error("Error: Ya perteneces a una partida", response1.statusText);
                setError("Error al crear la partida: Ya perteneces a una partida");
            }

        }
        catch (error) {
            console.error("Error:Ya perteneces a una partida", error);
        }

    }
    const createInfernalTower = async () => {
        const requestBody2 = {
            roundMode: "INFERNAL_TOWER",
        }
        try {
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));

            const response1 = await fetch('/api/v1/games',
                {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${jwt}`,
                    },
                    body: JSON.stringify(requestBody1),
                });
            if (response1.ok) {
                const data = await response1.json();
                window.location.href = `/game/quickPlay/${data.id}`;
                const response2 = await fetch('/api/v1/rounds',
                    {
                        method: 'POST',
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${jwt}`,
                        },
                        body: JSON.stringify(requestBody2),
                    });
                if (!(response2.ok)) {
                    console.error("Error al crear la ronda", response2.statusText);
                }

            } else {
                console.error("Error al crear la partida", response1.statusText);
                setError2("Error al crear la partida. Ya perteneces a una partida");

            }
        }
        catch (error) {
            console.error("Error:Ya perteneces a una partida", error);
        }

    }



    const joinGame = async () => {
        try {
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            
            const response3 = await fetch('/api/v1/games/quick/joinRandom',
            {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${jwt}`,
                },
                body: JSON.stringify(playerId),
            });
            if(response3.ok){
                const data = await response3.json();
                window.location.href = `/game/quickPlay/${data.id}`;
        } else if(response3.status === 409){
            console.error("Error al unirse a una partida.", response3.statusText);
            setError3("Error al unirse a una partida. No existen partidas disponibles");
        }
        else {
            console.error("Error al unirse a una partida.", response3.statusText);
            setError3("Error al unirse a una partida. Ya perteneces a una partida");

        }}
        catch(error) {
        console.error("Error:Ya perteneces a una partida", error);
        }
    };

    useEffect(() => {
        setUp();
    }, []);

    return (
        <div className="wallpaper">
            <div className='page'>
                <div className='small section' style={{ position: 'relative' }}>
                    <h1 className='text-center'>The Pit</h1>
                    <span className="text-center mt-2">
                        On go, the players flip their draw pile face-up.
                        Players must be faster than the others to discard the cards from
                        their draw pile by placing them on the card in the middle.
                        To do that, they have to name the identical symbol between
                        the top card of their draw pile and the card in the middle.
                        As the middle card changes as soon as a player places one
                        of his or her cards on top of it, players must be quick.
                    </span>
                    <br></br>
                    <Link 
                        className='purple-button' 
                        onClick={CreateThePit}
                        style={{ textDecoration: 'none' }}
                    >
                    Create Game
                    </Link>
                    <p className='error'>{error}</p>
                </div>
                <div className='small section'>
                    <h1 className='text-center'>Infernal Tower</h1>
                    <span className="text-center mt-2">
                        On go, the players flip their
                        card face-up.
                        Each player must be the fastest at
                        spotting the identical symbol between his
                        or her card and the first card of the draw
                        pile. The first player to find the symbol
                        names it, takes the card from the draw
                        pile and places it in front of him or
                        her, on top of his or her card. By
                        taking this card, a new card is
                        revealed. The game continues
                        until all the cards from the
                        draw pile have been drawn.
                    </span>
                    <br></br>
                    <Link 
                        className="purple-button" 
                        onClick={createInfernalTower}
                        style={{ textDecoration: 'none' }}
                        >
                        Create Game
                    </Link>
                    <p className='error'>{error2}</p>
                </div>
                <div className='small section'>
                    <h1 className='text-center'>Random Mode</h1>
                    <span className="text-center mt-2">
                        Join a random game
                    </span>
                    <br></br>
                    <Link 
                        className="purple-button" 
                        onClick={joinGame}
                        style={{ textDecoration: 'none' }}
                        >
                        Join Game
                    </Link>
                    <p className='error'>{error3}</p>
                </div>
                <div className="social">
                    <div className="friendsPlaying">
                        <h5>Friends Playing</h5>
                        <table ref={tableRef}>
                            <thead>
                                <tr>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {otherGamesFriends.map(friend => (
                                    <tr key={friend.id}>
                                        <td>{friend.user.username} <a>- IN GAME </a></td>

                                        {/* /game/quickPlay/${id}/${roundId} */}
                                        <td>
                                            <Link to={`/game/quickPlay/${gameId}/${roundId}/viewer`} className="purple-button" style={{ textDecoration: 'none' }}>
                                                <img alt="Eye Logo" src={eyeLogo} style={{ height: 25, width: 25}} />
                                            </Link>
                                        </td>
                                    </tr>

                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    );
}