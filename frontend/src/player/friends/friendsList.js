import React, { useState } from 'react';
import "../../static/css/player/friends.css";
import { Table, Input, Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

export default function FriendsList() {
    const [friendUsername, setFriendUsername] = useState(''); // Estado para almacenar el nombre de usuario del amigo
    const [modalOpen, setModalOpen] = useState(false);

    const handleInputChange = (e) => {
        setFriendUsername(e.target.value); // Actualiza el estado con el valor del input
    };

    const sendFriendRequest = () => {
        // Lógica para enviar la solicitud...
        // Comprobar que existe el usuario y que no son amigos ya
        console.log(`Solicitud de amistad enviada a: ${friendUsername}`);
        setModalOpen(true); // Abre el diálogo al enviar la solicitud
    };

    const toggleModal = () => {
        setModalOpen(!modalOpen); // Función para alternar la visibilidad del diálogo
    };

    return (
        <div className="wallpaper">
            <Modal isOpen={modalOpen} toggle={toggleModal}>
                <ModalHeader toggle={toggleModal}></ModalHeader>
                <ModalBody className="text-center">
                    Friend request has been sent to {friendUsername}.
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={toggleModal}>Close</Button>
                </ModalFooter>
            </Modal>
            <div className="container">
                <div className="half-width mt-4">
                    <h1 className="text-center">FriendsList</h1>
                        <Table class='mt-4'>
                        </Table>
                </div>
                <div className="half-width mt-4">
                    <h1 className="text-center">Add Friends</h1>
                        <label>Insert here your friend username!</label>
                        <br />
                        <br />
                        <Input
                            type="text"
                            placeholder="Enter username"
                            value={friendUsername}
                            onChange={handleInputChange}
                        />
                        <br />
                        <Button color="success" onClick={sendFriendRequest}>
                            Send Request
                        </Button>
                </div>
            </div>
        </div>
    );
}