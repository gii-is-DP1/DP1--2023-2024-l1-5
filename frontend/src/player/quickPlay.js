import React from 'react';
import '../App.css';
import "../static/css/player/newGame.css"
import { Link } from "react-router-dom";

export default function quickPlay() {
    return (
        <div className="wallpaper   ">
            <div className='buttonQP'>
                <div className="inButton">
                    <Link to="" className="button">The Pit</Link>
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
                </div>
                <div className="inButton">
                    <Link to="" className="button">Infernal Tower</Link>
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

                </div>

                <div className="inButton">
                    <Link to="" className="button">Join Game</Link>
                </div>
            </div>

        </div>
    );
}