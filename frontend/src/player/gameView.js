import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import '../App.css';
import tokenService from "../services/token.service";
import "../static/css/player/gameView.css";

const user = tokenService.getUser();

export default function GameView() {
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


    // useEffect(() => {
    //     const getPlayer = async () => {
    //         const jwt = JSON.parse(window.localStorage.getItem("jwt"));
    //         const myplayer = await fetch(`/api/v1/players/user/${user.id}`,
    //             {
    //                 method: 'GET',
    //                 headers: {
    //                     Authorization: `Bearer ${jwt}`,
    //                 },
    //             })
    //         if (myplayer.ok) {
    //             const data = await myplayer.json();
    //             setPlayerId(data.id);
    //             gameView();
    //         }
    //     }
    //     getPlayer();


    // }, [user.id]);
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



    useEffect(() => { gameView() }, [playerId]);
    
    async function gameView() {
        const jwt = JSON.parse(window.localStorage.getItem("jwt"));

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

        }
        else {
            console.log("error");
        }

        const responseDeck =
            await fetch(`/api/v1/decks/round/${roundId}`, 
                {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                })
        if (responseDeck.ok) {
            const deck = await responseDeck.json();
            setDeckImg(deck.cards[0].image);
            nameSymbolsDeck(deck.cards[0]);

        }
    }
    const winner = async()=>{
        try{
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const response = await fetch(`/api/v1/games/winner`,
            {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${jwt}`,
                },
                body: JSON.stringify(game.id),
            });
            if(response.ok){
                const data = await response.json();
                console.log(data);
            }else{
                console.error("Error al obtener el ganador", response.statusText);
            }
        }catch(error){
            console.error("Error al obtener el ganador", error);
        }
    }


    async function handleButton(event) {

        const symbolaux = event.target.textContent;
        console.log(deckSymbols)
        const lsauxButt = deckSymbols.map(deckSymbol => deckSymbol);
        console.log(lsauxButt)
        console.log("1",handAux)
        console.log("handSize", handSize)

        const ganador = null;

        if (lsauxButt.includes(symbolaux)) {
            if (handSize > 1) {
                console.log("HANDAUX", handAux)
                const newSymbolsCard = handAux[1];
                const newSymbolsDeck = handAux[0];
                console.log("new card",newSymbolsCard)
                console.log("new deck",newSymbolsDeck)
                const newImg = handAux[1].image;
                const updateHandAux = handAux.slice(1);
                const newSize = handSize - 1;
                setCardImg(newImg);
                setDeckImg(handAux[0].image);
                nameSymbolsDeck(newSymbolsDeck);
                nameSymbolsCard(newSymbolsCard);
                setHandAux(updateHandAux);
                setHandSize(newSize);
                console.log(handSize);
                console.log(newSize);
                console.log(updateHandAux);

            } else {
                winner();
                console.log("GANADOR");
                alert("Enhorabuena! Has ganado la partida");

                
                
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
                    <h1>MY HAND</h1> 
                    <img src={cardImg} class="circle" alt='img'></img>
                </div>
                <div className='columnas'>
                    <h1>Deck</h1>
                    <img src={deckImg} class="circle" alt='img'></img>
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

