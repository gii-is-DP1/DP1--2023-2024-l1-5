import React, {useState,useEffect}  from 'react';
import '../App.css';
import "../static/css/player/gameView.css";
import tokenService from "../services/token.service";


export default function GameView() {
    const [cardImg, setCardImg] = useState('');
    const [deckImg, setDeckImg] = useState(null);
    const [handAux, setHandAux] = useState([]);
    const [cardSymbol, setCardSymbols] = useState([]);
    const [deckSymbols, setDeckSymbols] = useState([]);
    const [handSize, setHandSize] = useState(0);
    const user = tokenService.getUser();
    const [playerId, setPlayerId] = useState(null);
    const[cardId, setCardId] = useState(null);
    const[hand, setHand] = useState({});



    useEffect(() => {
        const getPlayer = async () => {
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
        getPlayer();
    });

    useEffect(() => { gameView() }, []);
    async function gameView(){
        const jwt = tokenService.getLocalAccessToken();

        const responseHand =
            await fetch(`/api/v1/hands/${playerId}`,
                {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                })
        if (responseHand.ok) {
            const hand2 = await responseHand.json();
            setHand(hand2);
            setCardImg(hand2.cards[0].image);
            setHandAux(hand2.cards);
            setCardId(hand2.cards[0].id);
            setHandSize(hand2.numCartas);
            const card = hand2.cards[0]; 
            nameSymbolsCard(card);

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









    return (

 

        <div className="wallpaper">
        <div className='contenedor'>
            <div className="row">
                <div className='col-4'>
                    <h1>MY HAND</h1>
                    <h2> esta es la url {playerId} </h2>
                    <img src='https://i.imgur.com/pbHhrvm.jpeg' alt='img'></img>
                </div>
                <div className='col-4'>
                    <img src="https://www.dobblegame.com/wp-content/uploads/sites/2/2020/12/dobble_visuel_page_lesjeux-3.png" alt='img' className='img'></img>
                </div>

            </div>
            <div className='row'>
                <div className='col-6'>
                    <div className='row'>

                </div>


            </div>
        </div>
    </div>
    </div>



    )
}