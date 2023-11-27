import React, { useState,useEffect }  from 'react';
import "../../static/css/player/friends.css";
import tokenService from '../../services/token.service';
import { Table, Input, Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function FriendsList() {
    const [friendUsername, setFriendUsername] = useState(''); // Estado para almacenar el nombre de usuario del amigo
    const [modalOpen, setModalOpen] = useState(false);
    const [friends, setFriends] = useState([]);
    const [requests, setRequests] = useState([]);

    const handleInputChange = (e) => {
        setFriendUsername(e.target.value); // Actualiza el estado con el valor del input
    };

    const toggleModal = () => {
        setModalOpen(!modalOpen); // Función para alternar la visibilidad del diálogo
    };

    const closeAndClearInput = () => {
        setModalOpen(false); // Cierra el modal
        setFriendUsername(''); // Vacía el input al cerrar el modal
    };

    const sendFriendRequest = async () => {
        try {
            const response = await fetch(`/api/v1/friendship/${friendUsername}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            
            if (response.ok) {
                setModalOpen(true); // Abre el diálogo al enviar la solicitud
                setFriendUsername(friendUsername);// Limpia el input
            }
        } catch (error) {
            console.error('Error sending friend request:', error);
        }
    };

    const getFriendRequests = async () => {
        try {
            const response = await fetch(`/api/v1/friendship/request`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            if (response.ok) {
                setRequests(response); // Abre el diálogo al enviar la solicitud
            }
        } catch (error) {
            console.error('Error fetching friend requests:', error);
        }
    }; // FALTA

    const getFriendsList = async () => {
        try {
            const userIdResponse = await fetch(`/api/v1/players/user/${user.id}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            if (userIdResponse.ok) {
                const responseBody = await userIdResponse.json();
                const playerId = responseBody.id;

                console.log('Player ID:', playerId);

                const friendsResponse = await fetch(`/api/v1/friendship/friends/${playerId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });


                if (friendsResponse.ok) {
                    const friendsList = await friendsResponse.json();
                    setFriends(friendsList);
                    console.log('Friends list:', friendsList);
                }
            }
        } catch (error) {
            console.error('Error fetching friends list:', error);
        }
    };

    useEffect(() => {
        getFriendsList();
    }, []);

    return (
        <div className="wallpaper">
            <Modal isOpen={modalOpen} toggle={toggleModal}>
                <ModalHeader toggle={toggleModal}></ModalHeader>
                <ModalBody className="text-center">
                    Friend request has been sent to {friendUsername}.
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={closeAndClearInput}>Close</Button>
                </ModalFooter>
            </Modal>
            <div className="container">
                <div className="half-width mt-4">
                    <h1 className="text-center">FriendsList</h1>
                        <Table class='mt-4'>
                            <thead>
                                <tr>
                                    <th>Nickname</th>
                                    <th>Username</th>
                                </tr>
                            </thead>
                            <tbody>
                                {friends.map((friend) => (
                                    <tr key={friend.id}>
                                        <td>{friend.playerUsername}</td>
                                        <td>{friend.user.username}</td>
                                    </tr>
                                ))}
                            </tbody>
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