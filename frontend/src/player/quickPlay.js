import React, { useState, useEffect, useRef } from 'react';
import '../App.css';
import "../static/css/player/newGame.css"
import { Link } from "react-router-dom";
import tokenService from '../services/token.service';

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function QuickPlay() {
    const [error, setError] = useState(null);
    const [error2, setError2] = useState(null);
    const [error3,setError3] = useState(null);
    const [playerId, setPlayerId] = useState(null);
    const tableRef = useRef(null);
    const [otherGamesFriends, setOtherGamesFriends] = useState([]);

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
        if (myplayer.ok) {
            const data = await myplayer.json();
            setPlayerId(data.id);


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
        } else{
            console.error("Error al crear la partida", response3.statusText);
            setError3("Error al crear la partida. Ya perteneces a una partida");

        }}
        catch(error) {
        console.error("Error:Ya perteneces a una partida", error);
        }
    };

    const getFriendsPlaying = async () => {
        try {
            const userIdResponse = await fetch(`/api/v1/players/user/${user.id}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            if (userIdResponse.ok) {
                const responseBody = await userIdResponse.json();
                const playerId = responseBody.id;

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
            }
        } catch (error) {
            console.error('Error fetching friends playing:', error);
        }
    };

    useEffect(() => {
        setUp();
        getFriendsPlaying();
    }, []);

    return (
        <div className="wallpaper   ">
            <div className='buttonQP'>
                <div className="inButton">
                    <Link className='button' onClick={CreateThePit}>The Pit</Link>
                    <div className="blockText">
                        <span className="text">
                            On go, the players flip their draw pile face-up.
                            Players must be faster than the others to discard the cards from
                            their draw pile by placing them on the card in the middle.
                            To do that, they have to name the identical symbol between
                            the top card of their draw pile and the card in the middle.
                            As the middle card changes as soon as a player places one
                            of his or her cards on top of it, players must be quick

                        </span>
                    </div>
                    <p className='error'>{error}</p>
                </div>
                <div className="inButton">
                    <Link className="button" onClick={createInfernalTower}>Infernal Tower</Link>
                    <div className="blockText">
                        <span className="text">
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
                    </div>
                    <p className='error'>{error2}</p>
                </div>

                <div className="inButton">
                    <Link to="" className="button" onClick={joinGame}>Join Game</Link>
                    <div className="blockText">
                        <span className="text">
                            Join a random game
                        </span>
                    </div>
                    <p className='error'>{error3}</p>
                </div>

                <div className="social">
                    <br/>
                    <br/>
                    <br/>
                    <div className="friendsToInvite">
                        <h5>Invites</h5>
                        <table ref={tableRef} className="friendsTable">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <br/>
                    <br/>
                    <br/>
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