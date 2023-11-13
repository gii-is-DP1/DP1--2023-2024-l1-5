import { useState,useEffect } from 'react';
import { Button, Col, Container, Row, Table, Dropdown,DropdownItem, DropdownToggle,DropdownMenu } from 'reactstrap';
import useFetchState from '../../util/useFetchState';
import getErrorModal from '../../util/getErrorModal';
import tokenService from '../../services/token.service';

const jwt = tokenService.getLocalAccessToken();

export default function GameListAdmin(){

    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [games, setGames] = useFetchState([], `/api/v1/games`, jwt, setMessage, setVisible);
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
                <td>{game.players.filter((x) => x.id === game.creator.id).map((x) => x.user.username)}</td>
                <td>
                    <Dropdown isOpen={dropDownStates[game.id]} toggle={() => toggleDropDown(game.id)} direction='right'>
                        <DropdownToggle>
                            <p> &#128065;</p>
                        </DropdownToggle>
                        <DropdownMenu>
                            {game.players.map((x)=> (
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

    async function setUp() {
        const games = await (
          await fetch(`/api/v1/games`, {
            headers: {
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
          })
        ).json();

        setGames(games);

        let lsWaiting = [...games].filter((i) => i.status === "WAITING");
        setWaitingGames(lsWaiting)

        let lsProgress = [...games].filter((i) => i.status === "IN_PROGRESS");
        setInProgressGames(lsProgress)

        let lsFinalized = [...games].filter((i) => i.status === "FINALIZED");
        setFinalizedGames(lsFinalized)
      }

      useEffect(() => {
        setUp();
      }, []);

      useEffect(() => {}, [games]);

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
                                    <th>Owner</th>
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
                                    <th>Owner</th>
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