import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import '../App.css';
import tokenService from "../services/token.service";
import "../static/css/player/gameView.css";

const user = tokenService.getUser();

export default function ItGameView() {
    const [cardImg, setCardImg] = useState('');
    const [deckImg, setDeckImg] = useState(null);
    const [deckAux,setDeckAux] = useState([]);
    const [cardSymbol, setCardSymbols] = useState([]);
    const [deckSymbols, setDeckSymbols] = useState([]);
    const [deckSize,setDeckSize] = useState(0);
    const [playerId, setPlayerId] = useState(null);
    const { roundId } = useParams();
    const{id} =useParams();
    const [prevDeckImg, setPrevDeckImg] = useState(null);
    const [isGameOver, setIsGameOver] = useState(false);
    const[winnerId, setWinnerId] = useState(null);

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
        setUp();
    }, []);

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
                return response;
            } else {
                console.error('Error al obtener la baraja por ID de ronda');
                return null;
            }
            } catch (error) {
            console.error('Error en la solicitud:', error);
            return null;
            }
    }

    async function getHandByPlayerId(playerId, jwt) {
        try{
            const response = await fetch (`/api/v1/hands/player/${playerId}`,{
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                return response;
            } else {
                console.error('Error al obtener la mano por ID de jugador');
                return null;
            }
        }catch (error) {
            console.error('Error en la solicitud:', error);
            return null;
        }
    }
    

    useEffect(() => {
        if (playerId !== null) {
          gameView();
        }
    }, [playerId]);

    async function gameView() {
        const jwt = JSON.parse(window.localStorage.getItem("jwt"));

        const fetchWinner = async () => {
            if (isGameOver) {
                return;
            }
            const responseWinner = await fetch(`/api/v1/games/winner/${id}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${jwt}`,
                },
            }); 
            if (responseWinner.ok) {
                const responseText = await responseWinner.text();
                if (responseText.trim() === "") {
                } else {
                    const win = JSON.parse(responseText);
                    setWinnerId(win);
                    setIsGameOver(true)
                    window.location.href = `/game/quickPlay/${id}/endGame/${win}`;
                }
            } else {
                console.log("Error en la respuesta del servidor:", responseWinner.status);
            }    
        };

        const responseHand =
            await fetch(`/api/v1/hands/player/${playerId}`,
                {
                    method: 'GET',
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${jwt}`,
                    },
                });
        if (responseHand.ok) {
            const hand2 = await responseHand.json();
            setCardImg(hand2.cards[0].image);
            nameSymbolsCard(hand2.cards[0]);
        } else {
            console.log("Error al obtener la mano del jugador");
        }
        const fetchDeck = async () => {
            const responseDeck = await getDeckByRoundId(roundId, jwt);
            if (responseDeck.ok) {
                const deck = await responseDeck.json();
                setDeckAux(deck.cards);
                setDeckSize(deck.cards.length) ;
                setDeckImg(deck.cards[0].image);
                nameSymbolsDeck(deck.cards[0]);
            } else {
                console.log("Error al obtener el deck");
            
            }
        };
        
        await fetchDeck();

        const interval = setInterval(fetchDeck, 1000);
        const interval2 = setInterval(fetchWinner,2000);

        return () => clearInterval(interval, interval2);
    }
    useEffect(() => {
        if (prevDeckImg !== deckImg) {

          console.log('deckImg ha cambiado:', deckImg);

          setPrevDeckImg(deckImg);
        }
      }, [deckImg, prevDeckImg]);


      async function handleButton(event) {
        const jwt = JSON.parse(window.localStorage.getItem("jwt"));
        const symbolaux = event.target.textContent;
        const lsauxButt = deckSymbols.map(deckSymbol => deckSymbol);


        if (lsauxButt.includes(symbolaux)) {
            if (deckSize > 1) {
                const newSymbolsCard = deckAux[1];
                const newImg = deckAux[1].image;
                const updateDeckAux = deckAux.slice(1);
                const newSize = deckSize - 1;
                const cardId = deckAux[0].id;
                try {
                    const response = await fetch(`/api/v1/hands/round/${playerId}?cardId=${cardId}`, {
                        method: 'PUT',
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${jwt}`,
                        },
                    });
                    if (response.ok) {
                        const responseHand = await getHandByPlayerId(playerId, jwt);
                        const updatedHand = await responseHand.json();
                        setCardImg(updatedHand.cards[0].image);
                        nameSymbolsCard(updatedHand.cards[0]);
                        
                    } else {
                        console.error("Error al actualizar el deck", response.statusText);
                    }
                } catch (error) {
                    console.error("Error al actualizar el deck", error);
                }
                try{
                    const response = await fetch(`/api/v1/decks/round/${roundId}/discard?cardId=${cardId}`, {
                        method: 'PUT',
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${jwt}`,
                        },
                        });
                    if (response.ok) {
                        console.log("Carta descartada");
                    }
                    else {
                        console.error("Error al descartar la carta", response.statusText);
                    }
                }catch (error) {
                    console.error("Error al descartar la carta", error);
                }
                

                setDeckImg(newImg);
                nameSymbolsDeck(newSymbolsCard);
                setDeckAux(updateDeckAux);
                setDeckSize(newSize);

            } else {
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
                            const handsPromises = data.playerList.map(async (player) => {
                                const playerHand = await fetch(`/api/v1/hands/player/${player.id}`,
                                    {
                                        method: 'GET',
                                        headers: {
                                            "Content-Type": "application/json",
                                            Authorization: `Bearer ${jwt}`,
                                        },
                                    });
                                if (playerHand.ok) {
                                    const hand = await playerHand.json();
                                    return hand;
                                }else{
                                    console.log("Error al obtener la mano del jugador");
                                }
                            }
                            );
                            const hands = await Promise.all(handsPromises);
                            const sortedHands = hands.sort((a, b) => b.numCartas - a.numCartas);
                            const winnerPlayerId = sortedHands[0].player.id;
                            const responsePlayer = await fetch(`/api/v1/players/${winnerPlayerId}`,
                            {
                                method: 'GET',
                                headers: {
                                    "Content-Type": "application/json",
                                    Authorization: `Bearer ${jwt}`,
                                },
                            });
                            const winner = await responsePlayer.json();
                            const winnerUserId = winner.user.id;
                            try {
                                const reponseWinner = await fetch(`/api/v1/games/winner/${id}/${winnerUserId}`,
                                {
                                    method: 'PUT',
                                    headers: {
                                        "Content-Type": "application/json",
                                        Authorization: `Bearer ${jwt}`,
                                    },
                                });
                                if (reponseWinner.ok) {
                                    const updateGameStatus = await fetch(`/api/v1/games/updateFinalized/${id}`,
                                    {
                                        method: 'PUT',
                                        headers: {
                                            "Content-Type": "application/json",
                                            Authorization: `Bearer ${jwt}`,
                                        },
                                    });
                                    if(updateGameStatus.ok){
                                        console.log("Tenemos ganador");
                                        
                                    }else{
                                        console.log("Error al actualizar el estado de la partida");
                                    }
                                }else {
                                    console.error("Error al obtener el ganador", reponseWinner.statusText);
                                }
                            } catch (error) {
                                console.error("Error al obtener el ganador", error);
                            }
                        } else {
                            console.error("Error al obtener la partida", response.statusText);
                        }
                    }
                    catch (error) {
                        console.error("Error al obtener la partida", error);
                    }
                }
                getGame();
                
            }
        }
    }

    async function nameSymbolsCard(card) {
        if (card && card.symbols) {
            const ls = await card.symbols;
            const lsLength = ls.length;
            const lsaux = [];
            for (let i = 0; i < lsLength; i++) {
                const symbolName = ls[i].name;
                lsaux.push(symbolName);
            }
            setCardSymbols(lsaux);
        }
    }
    async function nameSymbolsDeck(card) {
        if (card && card.symbols) {
            const ls = await card.symbols;
            const lsLength = ls.length;
            const lsaux = [];
            for (let i = 0; i < lsLength; i++) {
                const symbolName = ls[i].name;
                lsaux.push(symbolName);
            }
            setDeckSymbols(lsaux);
        }
    }

    function colorSymbol(simbolo){
        const colorMap = {
            red:['LADYBUG','SHOT','CLOWN','HEART'],
            blue:['WATER','IGLOO','DOLPHIN','SNOWMAN','PENCIL','GHOST'],
            green:['CACTUS','INTERROGATION','TURTLE','APPLE','CLOVER'],
            yellow:['CHEESE','EXCLAMATION','THUNDER','DOG'],
            purple:['SCISSORS','CAT','EYE','BIRD'],
            orange:['KEY','HAMMER','MUSIC','BABY_BOTTLE'],
            black:['GLASSES','ZEBRA','SPIDER','YIN_YAN'],
        };
        for (const color in colorMap){
            if (colorMap[color].includes(simbolo)){
                return color;
            }
        }
    }
    
    
    return (
        <div className="wallpaper">
            <div className='contenedor'>
                <div className="filas">
                    <div className='columnas'>
                        <h1>MY HAND</h1> 
                        <img src={cardImg} className="circle" alt='img'></img>
                    </div>
                    <div className='columnas'>
                        <h1>Deck</h1>
                        <img src={deckImg} className="circle" alt='img'></img>
                    </div>
                </div>
                <div className='filas2'>
                    {cardSymbol.map((symbol,index)=>(
                        <div key={index} className='columnas'> 
                            <Link className={`${colorSymbol(symbol)}`} onClick={handleButton}>{symbol}</Link>
                        </div>
                    ))}
                </div>
            </div>
        </div>
        )

}