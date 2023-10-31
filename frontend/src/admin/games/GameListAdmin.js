import { useState,useEffect } from 'react';
import { Button, Col, Container, Row, Table } from 'reactstrap';
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

    function handleSearch(event) {

        // Obtenemos el valor ingresado en el campo de búsqueda
        const value = event.target.value;
        // Inicializamos una variable para almacenar las consultas filtradas
        let filteredGames;

        if(value ===""){
            filteredGames = games;
        }else{
            filteredGames = [...games].filter((i) => i.status === value);
        }
        // Actualizamos el estado "filtered" con las consultas filtradas
        setFiltered(filteredGames)
        setFilter(value)
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
                <td>{game.creator}</td>
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