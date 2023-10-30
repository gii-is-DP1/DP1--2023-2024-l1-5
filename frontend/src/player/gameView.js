import React from 'react';
import '../App.css';
import "../static/css/player/gameView.css"
//import { Button, ButtonGroup } from "reactstrap";

export default function GameView() {
    return (

 

        <div className="fondo">
            <div className='contenedor'>
                <div className="row">
                    <div className='col-4'>
                        <img src="https://www.dobblegame.com/wp-content/uploads/sites/2/2020/12/dobble_visuel_page_lesjeux-3.png" alt='img' className='img'></img>
                    </div>
                    <div className='col-4'>
                        <img src="https://www.dobblegame.com/wp-content/uploads/sites/2/2020/12/dobble_visuel_page_lesjeux-3.png" alt='img' className='img'></img>
                    </div>
                    <div className='col-4'>
                        <img src="https://www.dobblegame.com/wp-content/uploads/sites/2/2020/12/dobble_visuel_page_lesjeux-3.png" alt='img' className='img'></img>
                    </div>

                </div>
                <div className='row'>
                    <div className='col-6'>
                        <div className='row'>
                            <div className='col-3'>
                                <button className="boton2">Symbol</button>
                            </div>
                            <div className='col-3'>
                                <button className="boton2">Symbol</button>
                            </div>
                            <div className='col-3'>
                                <button className="boton2">Symbol</button>
                            </div>
                            <div className='col-3'>
                                <button className="boton2">Symbol</button>
                            </div>
                            {/* <ButtonGroup>
                                <Button
                                    size="sm"
                                    color="primary"
                                    aria-label={"edit-" + clinic.name}
                                    to={"/clinics/" + clinic.id}
                                >
                                    Edit
                                </Button>
                            </ButtonGroup> */}
                        </div>
                        <div className='row'>
                            <div className='col-3'>
                                <button className="boton2">Symbol</button>
                            </div>
                            <div className='col-3'>
                                <button className="boton2">Symbol</button>
                            </div>
                            <div className='col-3'>
                                <button className="boton2">Symbol</button>
                            </div>
                            <div className='col-3'>
                                <button className="boton2">Symbol</button>
                            </div>

                        </div>
                    </div>


                </div>
            </div>
        </div>



    );
}