import { useState } from 'react';
import { Button, Col, Container, Input, Row, Table } from 'reactstrap';



export default function GameListAdmin(){

    return(
        <div>
            <Container fluid style={{ marginTop: "15px" }}>
                <h1 className="text-center">Games</h1>
                <Row className="row-cols-auto g-3 align-items-center">
                    <Col>
                        <Button aria-label='pending-filter' color="link" /*</Col>onClick={handleFilter}*/ value="WAITING">Waiting</Button>
                        <Button aria-label='answered-filter' color="link" /*</Col>onClick={handleFilter}*/ value="IN_PROGRESS">In Progress</Button>
                        <Button aria-label='closed-filter' color="link" /*</Col>onClick={handleFilter}*/ value="FINALIZED">Finalized</Button>
                        <Button aria-label='all-filter' color="link" /*</Col>onClick={handleFilter}*/ value="">All</Button>
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
                   <tbody>buenas</tbody>
                </Table>
            </Container>
        </div>

    );
}