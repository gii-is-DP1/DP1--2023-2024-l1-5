import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import '../App.css';
import '../static/css/main.css'
import tokenService from "../services/token.service";
import "../static/css/player/gameView.css";
import friendLogo from '..//static/images/Sample_User_Icon.png';

export default function GameViewerView() {
    const [deckImg, setDeckImg] = useState(null);
    const [deckSize, setDeckSize] = useState(null);
    const { id: gameId, roundId , playerId: friendId} = useParams();
    const [handList, setHandList] = useState([]);
    const [players, setPlayers] = useState([]);

    async function getDeckByRoundId(roundId, jwt) {
        try {
            const response = await fetch(`/api/v1/decks/round/${roundId}`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                const deck = await response.json(); // Await the JSON parsing
                return deck; // Return the parsed JSON data
            } else {
                console.error('Error al obtener la baraja por ID de ronda');
                return null;
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
            return null;
        }
    }
    async function playersInfo(gameId){
        try{
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const response = await fetch(`/api/v1/games/${gameId}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                const game = await response.json(); // Await the JSON parsing
                setPlayers(game.playerList)// Return the parsed JSON data
            } else {
                console.error('Error al obtener la información de los jugadores');
                return null;
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
            return null;
        }
    };

    function getPlayerName(playerId) {
        const player = players.find((player) => player.id === playerId);
        return player ? player.playerUsername : null;
    }

    async function playersHandList(roundId, jwt){
        try {
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const response = await fetch(`/api/v1/hands/round/${roundId}`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                const hands = await response.json(); // Await the JSON parsing
                setHandList(hands); // Return the parsed JSON data
            } else {
                console.error('Error al obtener las manos');
                return null;
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
            return null;
        }
    };
    
    async function viewer() {
        const jwt = JSON.parse(window.localStorage.getItem("jwt"));
    
        const fetchDeck = async () => {
            const deckData = await getDeckByRoundId(roundId, jwt);
    
            if (deckData) {
                setDeckImg(deckData.cards[0].image);
                setDeckSize(deckData.cards.length);
            } else {
                console.log("Error al obtener el deck");
            }
        };
    
        await fetchDeck();
    
        const interval = setInterval(fetchDeck, 1000);
    
        return () => clearInterval(interval);
    };
    
    useEffect(() => {
        viewer();
        playersHandList(roundId);
        playersInfo(gameId);
    }, []); // Empty dependency array to trigger this effect only on mount
    

    const friendIdInt = parseInt(friendId, 10);

    return (
        <div className="wallpaper">
            <div className='contenedor'>
                
                <div className='columnas'>
                    <h1>Watching your friend: {getPlayerName(friendIdInt)}</h1>
                    <br></br>
                    <br></br>
                    <br></br>
                    <br></br>
                    <h1>Current deck</h1>
                    <img src={deckImg} className="circle" alt='img'></img>
                </div>
            </div>
            <div>
                <h2>Cards in the deck: {deckSize} </h2>
                {handList.map((hand) => (
                    <div key={hand.id}>
                        <h3 key={hand.id}> 
                            {hand.player == friendId? 
                                <img
                                alt="Friend Logo"
                                src={friendLogo}
                                style={{ height: 45, width: 45 }}
                                />
                                : null
                            }
                            N. of Cards in the hand of {getPlayerName(hand.player)}: {hand.numCartas}
                        </h3>
                    </div>
                ))}
                <div>
                    <br></br>
                    <br></br>
                    <Link 
                        className='purple-button' 
                        to={`/`}
                    >
                    Go back to the home page
                    </Link>
                </div>
            </div>
        </div>
    )
    
}