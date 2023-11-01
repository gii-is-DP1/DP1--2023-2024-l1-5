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
    const [filter, setFilter] = useState("");
    const [filtered, setFiltered] = useState([]);
    const [dropDownStates, setDropDownStates] = useState({});

    function handleSearch(event) {

        const value = event.target.value;
        let filteredGames;                              

        if(value ===""){
            filteredGames = games;
        }else{
            filteredGames = [...games].filter((i) => i.status === value);
        }
        setFiltered(filteredGames)
        setFilter(value)
    }

    const toggleDropDown = (gameId) => {
        setDropDownStates({
            ...dropDownStates,
            [gameId]: !dropDownStates[gameId]
        });     
    }

    function getGamesList(game){
    if (game.length === 0 && filter !== "" )
        return(
            <tr>
                <td>There are no games with those filter parameters.{filter}</td>
            </tr>);
    else{
        return( game.map((game) => {
            return (
              <tr key={game.id}>
                <td>{game.gameMode}</td>
                <td>{game.status}</td>
                <td>{game.players.filter((x) => x.id === game.creator).map((x) => x.playerUsername)}</td>
                <td>
                    <Dropdown isOpen={dropDownStates[game.id]} toggle={() => toggleDropDown(game.id)} direction='right'>
                        <DropdownToggle caret>
                            <p> &#128065;</p>
                        </DropdownToggle>
                        <DropdownMenu>
                            {game.players.map((x)=> (
                                        <DropdownItem text>{x.playerUsername}</DropdownItem>
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
        setFiltered(games);
      }
    
      useEffect(() => {
        setUp();
      }, []);
    
      useEffect(() => {}, [filtered]);

    const modal = getErrorModal(setVisible, visible, message);

    return(
        <div>
            <Container fluid style={{ marginTop: "15px" }}>
                <h1 className="text-center">Games</h1>
                {modal}
                <Row className="row-cols-auto g-3 align-items-center">
                    <Col>
                        <Button aria-label='waiting-filter' color="link" onClick={handleSearch} value="WAITING">Waiting</Button>
                        <Button aria-label='in-prigress-filter' color="link" onClick={handleSearch}  value="IN_PROGRESS">In Progress</Button>
                        <Button aria-label='finalized-filter' color="link" onClick={handleSearch}  value="FINALIZED">Finalized</Button>
                        <Button aria-label='all-filter' color="link" onClick={handleSearch}  value="">All</Button>
                    </Col>
                </Row>
                <Table aria-label='games' className="mt-4">
                    <thead>
                        <tr>
                            <th>Mode</th>
                            <th>Status</th>
                            <th>Owner</th>
                            <th>Players</th>
                        </tr>
                    </thead>
                   <tbody>{filtered
                   ? getGamesList(filtered)
                   : getGamesList(games)}
                   </tbody>
                </Table>    
            </Container>
        </div>

    );
}   