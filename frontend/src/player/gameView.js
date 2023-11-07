import React, { useState, useEffect } from 'react';
import '../App.css';
import "../static/css/player/gameView.css"
import useFetchState from '../util/useFetchState';
import tokenService from "../services/token.service";




export default function GameView() {



    const [cardImg, setCardImg] = useState(null);
    const [deckImg, setDeckImg] = useState(null);
    const [handAux, setHandAux] = useState([]);
    const [cardSymbol, setCardSymbols] = useState([]);
    const [deckSymbols, setDeckSymbols] = useState([]);
    const [handSize, setHandSize] = useState(0);

    useEffect(() => { gameView(); }, []);
    async function gameView() {


        const jwt = tokenService.getLocalAccessToken();

        const responseHand =
            await fetch(`/api/v1/hands`,
                {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                })
        if (responseHand.ok) {
            const hand = await responseHand.json();
            setCardImg(hand[0].cards[0].image);
            setHandAux(hand[0].cards);
            setHandSize(hand[0].numCartas);
            const card = hand[0].cards[0];
            nameSymbolsCard(card);
        }
        const responseDeck =
            await fetch(`/api/v1/decks`, // HACER UN GET DECK EN RONDA CONTROLLER
                // HACER UN GET HAND WHERE ROUND ID == ? EN PLAYER
                {
                    method: 'GET',
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                })
        if (responseDeck.ok) {
            const deck = await responseDeck.json();
            //setDeckSymbols(deck[0].cards[0].symbols)
            const cardDeck = deck[0].cards[0];
            nameSymbolsDeck(cardDeck);
            setDeckImg(deck[0].cards[0].image);
        }
    }

    // async function handleButton(event) {
    //     const symbolaux = event.target.textContent;
    //     //const lsauxButt = []
    //     for (let i = 0; i < deckSymbols.length; i++) {
    //         const decksymbolName = deckSymbols[i].name;
    //         lsauxButt.push(decksymbolName);
    //     }

    async function handleButton(event) {

        const symbolaux = event.target.textContent;
        console.log(deckSymbols)
        const lsauxButt = deckSymbols.map(deckSymbol => deckSymbol);
        console.log(lsauxButt)
        console.log("1",handAux)
        console.log("handSize", handSize)

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
                alert("SIUUUU");
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
                                <button className="boton2" onClick={handleButton}>{cardSymbol[1]}</button>
                            </div>
                            <div className='col-4'>
                                <button className="boton2" onClick={handleButton}>{cardSymbol[2]}</button>
                            </div>

                        </div>
                        <div className='row'>
                            <div className='col-4'>
                                <button className="boton2" onClick={handleButton}>{cardSymbol[3]}</button>
                            </div>
                            <div className='col-4'>
                                <button className="boton2" onClick={handleButton}>{cardSymbol[4]}</button>
                            </div>
                            <div className='col-4'>
                                <button className="boton2" onClick={handleButton}>{cardSymbol[5]}</button>
                            </div>


                        </div>
                    </div>


                </div>
            </div>
        </div>



    );
}