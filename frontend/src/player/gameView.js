import React, {useState , useEffect } from 'react';
import '../App.css';
import "../static/css/player/gameView.css"
import useFetchState from '../util/useFetchState';
import tokenService from "../services/token.service";




export default function GameView() {

   
    const [cardImg,setCardImg]=useState([]);
    const [deckImg,setDeckImg]=useState([]);
    const [handAux, setHandAux] = useState([]);
    const [cardSymbol,setCardSymbols] = useState([]);
    const [deckSymbols,setDeckSymbols] = useState([]);

    useEffect(()=>{ gameView();},[]);
    async function gameView(){

        const jwt = tokenService.getLocalAccessToken();
        
        const responseHand  =  
            await fetch(`/api/v1/hands`,
            {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            })
        if(responseHand.ok){
            const hand = await responseHand.json();
            setCardImg(hand[0].cards[0].image);
            setHandAux(hand[0].cards)
            
            const symbols = hand[0].cards[0].symbols; 
            nameSymbols(symbols);
        }
        const responseDeck  =  
            await fetch(`/api/v1/decks`, // HACER UN GET DECK EN RONDA CONTROLLER
                                         // HACER UN GET HAND WHERE ROUND ID == ? EN PLAYER
            {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            })
        if(responseDeck.ok){
            const deck = await responseDeck.json();
            setDeckSymbols(deck[0].cards[0].symbols)
            setDeckImg(deck[0].cards[0].image);
        }
    }
    function handleButton(event) {
        const symbolaux = event.target.textContent;
        const lsaux = [];
        for (let i = 0; i < deckSymbols.length; i++) {
            const decksymbolName = deckSymbols[i].name;
            lsaux.push(decksymbolName);
        }
        let i = 0;
        if (lsaux.includes(symbolaux)) {
            if (handAux.length >= i) {
                i++;
                const newSymbols = handAux[i].symbols;
                const newSymbolsDeck = handAux[i-1].symbols;
                const newImg = handAux[i].image;
                setCardImg(newImg);
                setDeckImg(handAux[i-1].image);
                setDeckSymbols(nameSymbols(newSymbolsDeck));
                nameSymbols(newSymbols);

            } else {
                alert("WINNER")
            }
            
        } else {
            alert("GODNT");
        }
    }

    function nameSymbols(ls){
        const lsaux = [];
        for (let i = 0; i < ls.length; i++) {
            const symbolName = ls[i].name;
            lsaux.push(symbolName);
        }
        setCardSymbols(lsaux);

    }


    return (

      
        <div className="wallpaper">
            <div className='contenedor'>
                <div className="row">
                    <div className='col-4'>
                        <h1>MY HAND</h1>
                        <img src={cardImg} alt='img'></img>
                    </div>
                    <div className='col-4'>
                        <h1>DECK</h1>
                        <img src={deckImg} alt='img'></img>
                    </div>
                    <div className='col-4'>
                        <img src="https://www.dobblegame.com/wp-content/uploads/sites/2/2020/12/dobble_visuel_page_lesjeux-3.png" alt='img' className='img'></img>
                    </div>

                </div>
                <div className='row'>
                    <div className='col-6'>
                        <div className='row'>
                            <div className='col-4'>
                                <button className="boton2" onClick={handleButton}>{cardSymbol[0]}</button>
                            </div>
                            <div className='col-4'>
                                <button className="boton2"onClick={handleButton}>{cardSymbol[1]}</button>
                            </div>
                            <div className='col-4'>
                                <button className="boton2"onClick={handleButton}>{cardSymbol[2]}</button>
                            </div>

                        </div>
                        <div className='row'>
                            <div className='col-4'>
                                <button className="boton2"onClick={handleButton}>{cardSymbol[3]}</button>
                            </div>
                            <div className='col-4'>
                                <button className="boton2"onClick={handleButton}>{cardSymbol[4]}</button>
                            </div>
                            <div className='col-4'>
                                <button className="boton2"onClick={handleButton}>{cardSymbol[5]}</button>
                            </div>


                        </div>
                    </div>


                </div>
            </div>
        </div>



    );
}