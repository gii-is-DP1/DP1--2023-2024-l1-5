import { useState } from 'react';
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
            filteredGames = games.filter((i) => i.status === value);
        }
    
        // Actualizamos el estado "filtered" con las consultas filtradas
        setFiltered(filteredGames);
    }

    function handleClear() {
        setFiltered(games);
        setFilter("");
    }

    let gameList;
    if (filtered.length === 0 && filter !== "" ) gameList =
        <tr>
            <td>There are no games with those filter parameters.</td>
        </tr>
    else{
        gameList = games.map((game) => {
            return (
              <tr key={game.id}>
                <td>{game.status}</td>
                <td>{game.status}</td>
                <td>{game.creator}</td>
             </tr>
            );
        });
    } 
    const modal = getErrorModal(setVisible, visible, message);

    return(
        <div>
            <Container fluid style={{ marginTop: "15px" }}>
                <h1 className="text-center">Games</h1>
                {modal}
                <Row className="row-cols-auto g-3 align-items-center">
                    <Col>
                        <Button aria-label='waiting-filter' color="link" onClick={handleSearch} value="WAITING">Waiting</Button>
                        <Button aria-label='in-prigress-filter' color="link" onClick={handleSearch} value="IN_PROGRESS">In Progress</Button>
                        <Button aria-label='finalized-filter' color="link" onClick={handleSearch} value="FINALIZED">Finalized</Button>
                        <Button aria-label='all-filter' color="link" onClick={handleSearch} value="">All</Button>
                    </Col>
                    <Col className="col-sm-3">
                        <Button aria-label='clear-all' color="link" onClick={handleClear} >Clear All</Button>
                    </Col>
                </Row>
                <Table aria-label='consultations' className="mt-4">
                    <thead>
                        <tr>
                            <th>Mode</th>
                            <th>Status</th>
                            <th>Owner</th>
                        </tr>
                    </thead>
                   <tbody>{gameList}</tbody>
                </Table>
            </Container>
        </div>

    );
}