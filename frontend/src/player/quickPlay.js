import React, { useState, useEffect } from 'react';
import '../App.css';
import "../static/css/player/newGame.css"
import { Link } from "react-router-dom";
import tokenService from '../services/token.service';

export default function QuickPlay() {
    const[error,setError]=useState(null);
    const [error2,setError2]=useState(null);
    const [playerId,setPlayerId]=useState(null);
    const requestBody1={
        gameMode:"QUICK_PLAY"
    }
    const user = tokenService.getUser();
    useEffect(()=>{ setUp();},[]);

    async function setUp(){
        const myplayer = await (
            await fetch(`/api/v1/players`, 
            {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${user.jwt}`,
                },
            })
    ).json();
    if(myplayer.ok){
        const playerList = await myplayer.json();
        playerList.forEach((player)=>{
            if(player.user.id===user.id){
                setPlayerId(player);
                console.log("todo bien")
                
            }

        })
    }else{
        console.error("Error al obtener el jugador", myplayer.statusText);
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
                if(response1.ok){
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
                }else{
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
    // const joinGame = async () =>{
    //     try{
    //         const jwt = JSON.parse(window.localStorage.getItem("jwt"));
    //         const requestBody3={

    //         }


    //     }catch{

    //     }
    // }




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
                            {playerId}
                        </span>
                    </div>
                    <p className='error'>{error2}</p>
                </div>

                <div className="inButton">
                    <Link to="" className="button">Join Game</Link>
                    <div className="blockText">
                        <span className="text">
                        </span>
                    </div>
                </div>
            </div>

        </div>
    );
}