import { useState,useEffect } from 'react';
import tokenService from '../services/token.service';
import { Button, Col, Container, Row, Table, Dropdown,DropdownItem, DropdownToggle,DropdownMenu } from 'reactstrap';
import "../static/css/player/gameHistory.css"


const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function GameHistory() {

    const[games, setGames]= useState([])
    const[playerGames, setPlayerGames]= useState([])
    const [dropDownStates, setDropDownStates] = useState({});


    const toggleDropDown = (gameId) => {
        setDropDownStates({
            ...dropDownStates,
            [gameId]: !dropDownStates[gameId]
        });     
    }

    function getGamesList(games){

        const juegosP = []
        for(let g in games){
            for(let p in games[g].playerList){
                if(games[g].playerList[p].user.id === user.id /*&& games[g].status === 'FINALIZED'*/){
                    juegosP.push(games[g])
                }
            }
        }

        if (juegosP.length === 0)
            return(
                <tr>
                    <td>You havent play any game yet.</td>
                </tr>);
        else{
            return( juegosP.map((game) => {
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


    async function setUp() {
        const partidas = await (
            await fetch(`/api/v1/games`, {
              headers: {
                Authorization: `Bearer ${jwt}`,
                "Content-Type": "application/json",
              },
            })
          ).json();
        setGames(partidas);
    }

    useEffect(() => {
        setUp();
    }, []);

    useEffect(() => {}, [games, playerGames]);

    return(
        <div>
            <Container fluid style={{ marginTop: "15px" }}>
                <div class="containerGamesHistory"> 
                    <div class="centered-rectangle mt-4">
                            <h1 className="text-center">Games History</h1>
                            <Table class='mt-4'>
                            <thead>
                                <tr>
                                    <th>Mode</th>
                                    <th>Creator</th>
                                    <th>Players</th>
                                </tr>
                            </thead>
                            <tbody>
                                {getGamesList(games)}
                            </tbody>
                        </Table>
                    </div>  
                </div>
            </Container>
        </div>

    );

}

