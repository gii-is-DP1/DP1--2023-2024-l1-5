import React, { useEffect, useState } from 'react';
import { useParams, Link} from 'react-router-dom';
import '../App.css';
import tokenService from "../services/token.service";
import "../static/css/player/gameView.css";

const user = tokenService.getUser();

export default function PitGameView() {
    const [cardImg, setCardImg] = useState('');
    const [deckImg, setDeckImg] = useState(null);
    const [handAux, setHandAux] = useState([]);
    const [cardSymbol, setCardSymbols] = useState([]);
    const [deckSymbols, setDeckSymbols] = useState([]);
    const [handSize, setHandSize] = useState(0);
    const [playerId, setPlayerId] = useState(null);
    const { roundId } = useParams();
    const{id} =useParams();
    const[game,setGame]=useState({});
    const [prevDeckImg, setPrevDeckImg] = useState(null);
    const[winnerId, setWinnerId] = useState(null);
    const [isGameOver, setIsGameOver] = useState(false);
    const [timer, setTimer] = useState(0);

    useEffect(() => {
        // Iniciar el temporizador al cargar la vista
        const interval = setInterval(() => {
            // Incrementar el contador del temporizador
            setTimer(prevTimer => prevTimer + 1);
        }, 1000);

        return () => clearInterval(interval);
    }, [isGameOver]);

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
            setHandAux(hand2.cards);
            setHandSize(hand2.numCartas);
            const card = hand2.cards[0];
            nameSymbolsCard(card);
        } else {
            console.log("Error al obtener la mano del jugador");
        }
        const fetchDeck = async () => {
            const responseDeck = await getDeckByRoundId(roundId, jwt);
        
            if (responseDeck.ok) {
                const deck = await responseDeck.json();
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
      
    const winner = async()=>{
        try{
            if(!isGameOver){
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));
                const response = await fetch(`/api/v1/games/winner/${id}/${user.id}?time=${timer}`,
                {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                if(response.ok){
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
                            setIsGameOver(true)
                            window.location.href = `/game/quickPlay/${id}/endGame/${user.id}`;
                        }else{
                            console.log("Error al actualizar el estado de la partida");
                        }
                }else{
                    console.error("Error al obtener el ganador", response.statusText);
                }
            }
        }catch(error){
            console.error("Error al obtener el ganador", error);
        }
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
            if (handSize > 1) {
                const newSymbolsCard = handAux[1];
                const newImg = handAux[1].image;
                const updateHandAux = handAux.slice(1);
                const newSize = handSize - 1;
                const cardId = handAux[0].id;
                try {
                    const response = await fetch(`/api/v1/decks/round/${roundId}?cardId=${cardId}`, {
                        method: 'PUT',
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${jwt}`,
                        },
                    });
                    if (response.ok) {
                        const responseDeck = await getDeckByRoundId(roundId, jwt);
                        const updatedDeck = await responseDeck.json();
                        setDeckImg(updatedDeck.cards[0].image);
                        nameSymbolsDeck(updatedDeck.cards[0]);
                        console.log(updatedDeck.cards[0]);
                        
                    } else {
                        console.error("Error al actualizar el deck", response.statusText);
                    }
                } catch (error) {
                    console.error("Error al actualizar el deck", error);
                }
                

                setCardImg(newImg);
                nameSymbolsCard(newSymbolsCard);
                setHandAux(updateHandAux);
                setHandSize(newSize);

            } else {
                winner();
            }
        }
    }

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
                    <h1>My hand</h1> 
                    <img src={cardImg} className="circle" alt='img'></img>
                    <h5>Cards left: {handSize}</h5>
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

