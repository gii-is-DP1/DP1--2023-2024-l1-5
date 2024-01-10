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
    const [winnerId, setWinnerId] = useState(null);
    const [winnerName, setWinnerName] = useState(null);

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

    async function getHandByRoundId(roundId, jwt) {
        try {
            const response = await fetch(`/api/v1/hands/round/${roundId}`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                const hand = await response.json(); // Await the JSON parsing
                return hand; // Return the parsed JSON data
            } else {
                console.error('Error al obtener la baraja por ID de ronda');
                return null;
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
            return null;
        }
    }

    async function getGameWinnerByGameId(gameId, jwt) {
        try {
            const responseWinner = await fetch(`/api/v1/games/winner/${gameId}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${jwt}`,
                },
            }); 
            if (responseWinner.ok) {
                const winner = await responseWinner.json(); // Await the JSON parsing
                return winner; // Return the parsed JSON data
            } else {
                console.error('Error al obtener el ganador del juego');
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
    async function getWinnerName(winnerId, jwt){
        try {
            const response = await fetch(`/api/v1/users/${winnerId}`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                const winner = await response.json(); // Await the JSON parsing
                return winner.username; // Return the parsed JSON data
            } else {
                console.error('Error al obtener el ganador');
                return null;
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
            return null;
        }
    }
    
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
        
        const fetchHand = async () => {
            const handData = await getHandByRoundId(roundId, jwt);
            if (handData) {
                setHandList(handData);
            } else {
                console.log("Error al obtener la mano");
            }
        }

        const fetchWinner = async () => {
            const winnerData = await getGameWinnerByGameId(gameId, jwt);
            if (winnerData) {
                setWinnerId(winnerData)
                const winnerName = await getWinnerName(winnerData, jwt);
                setWinnerName(winnerName);
            } else {
                console.log("Error al obtener el ganador");
            }
        }

        await fetchDeck();
        await fetchHand();
        await fetchWinner();
    
        const fetchDeckInterval = setInterval(fetchDeck, 1000);
        const fetchHandInterval = setInterval(fetchHand, 1000);
        const fetchWinnerInterval = setInterval(fetchWinner, 1000);


        return () => {
            clearInterval(fetchDeckInterval);
            clearInterval(fetchHandInterval);
            clearInterval(fetchWinnerInterval);
        };
    };
    
    useEffect(() => {
        viewer();
        playersHandList(roundId);
        playersInfo(gameId);
    }, []); // Empty dependency array to trigger this effect only on mount
    

    const friendIdInt = parseInt(friendId, 10);

    const GameInProgress = () => (
        <div>
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
    );

    const GameEnd = () => (
        <div className="container">
            <h1>👑{winnerName} won the game👑</h1>
            <div className="botones-container">
                    <Link 
                        className='purple-button' 
                        to={`/`}
                    >
                    Go back to the home page
                    </Link>
            </div>
        </div>
    );

    return (
        <div className="wallpaper">
            {winnerId === null || winnerId === 0? <GameInProgress/>: <GameEnd/>}
        </div>
    )
    
}