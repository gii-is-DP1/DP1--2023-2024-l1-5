import { useState,useEffect } from 'react';
import {Container, Table, Dropdown,DropdownItem, DropdownToggle,DropdownMenu } from 'reactstrap';
import getErrorModal from '../../util/getErrorModal';
import tokenService from '../../services/token.service';

const jwt = tokenService.getLocalAccessToken();

export default function GameListAdmin(){

    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [waitingGames, setWaitingGames] = useState([]);
    const [inProgressgGames, setInProgressGames] = useState([]);
    const [finalizedGames, setFinalizedGames] = useState([]);
    const [dropDownStates, setDropDownStates] = useState({});

    const toggleDropDown = (gameId) => {
        setDropDownStates({
            ...dropDownStates,
            [gameId]: !dropDownStates[gameId]
        });     
    }

    function getGamesList(games){
        if (games.length === 0)
            return(
                <tr>
                    <td>There are no games in this status.</td>
                </tr>);
        else{
            return( games.map((game) => {
                return (
                <tr key={game.id}>
                    <td>{game.gameMode}</td>
                    <td>{game.playerList.filter((x) => x.id === game.creator).map((x) => x.user.username)}</td>
                    <td>
                        <Dropdown isOpen={dropDownStates[game.id]} toggle={() => toggleDropDown(game.id)} direction='right'>
                            <DropdownToggle>
                                <p> &#128065;</p>
                            </DropdownToggle>
                            <DropdownMenu>
                                {game.playerList.map((x)=> (
                                            <DropdownItem text>{x.user.username}</DropdownItem>
                                        ))}
                            </DropdownMenu>
                        </Dropdown>
                    </td>
                </tr>
                );
            }));
        } 
    }
    

    async function getFinalizedGames(){
        const games1 = await (
            await fetch(`/api/v1/games/finalized`, {
              headers: {
                Authorization: `Bearer ${jwt}`,
                "Content-Type": "application/json",
              },
            })
          ).json();
        ;
        setFinalizedGames(games1);
    }

    async function getWaitingGames(){
        const games2 = await (
            await fetch(`/api/v1/games/waiting`, {
              headers: {
                Authorization: `Bearer ${jwt}`,
                "Content-Type": "application/json",
              },
            })
          ).json();
        ;
        setWaitingGames(games2);
    }

    async function getInProgressGames(){
        const games3 = await (
            await fetch(`/api/v1/games/inProgress`, {
              headers: {
                Authorization: `Bearer ${jwt}`,
                "Content-Type": "application/json",
              },
            })
          ).json();
        ;
        setInProgressGames(games3);
    }

      useEffect(() => {
        getWaitingGames();
        getInProgressGames();
        getFinalizedGames();  
      }, []);

      useEffect(() => {},[waitingGames], [inProgressgGames], [finalizedGames]);

    const modal = getErrorModal(setVisible, visible, message);

    return(
        <div>
            <Container fluid style={{ marginTop: "15px" }}>
                {modal} 
                <div class="containerGames">  
                    <div class="half-width mt-4">
                        <h1 className="text-center">Waiting Games</h1>
                        <Table class='mt-4'>
                        <thead>
                                <tr>
                                    <th>Mode</th>
                                    <th>Creator</th>
                                    <th>Players</th>
                                </tr>
                            </thead>
                            <tbody>
                                {getGamesList(waitingGames)}
                            </tbody>
                        </Table>
                    </div>  
                    <div class="half-width mt-4">
                        <h1 className="text-center">Current Games</h1>
                        <Table class='mt-4'>
                            <thead>
                                <tr>
                                    <th>Mode</th>
                                    <th>Creator</th>
                                    <th>Players</th>
                                </tr>
                            </thead>
                            <tbody>
                                {getGamesList(inProgressgGames)}
                            </tbody>
                        </Table>
                    </div>
                    <div class="half-width mt-4">
                        <h1 className="text-center">Finalized Games</h1>
                        <Table className='mt-4'>
                        <thead>
                                <tr>
                                    <th>Mode</th>
                                    <th>Creator</th>
                                    <th>Players</th>
                                </tr>
                            </thead>
                            <tbody>
                                {getGamesList(finalizedGames)}
                            </tbody>
                        </Table>
                    </div>
                </div>  
            </Container>
        </div>

    );
}   