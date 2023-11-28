import React, { useState,useEffect }  from 'react';
import "../../static/css/player/friends.css";
import '../../static/css/player/floatingBox.css';
import tokenService from '../../services/token.service';
import { Table, Input, Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function FriendsList() {
    const [friendUsername, setFriendUsername] = useState(''); // Estado para almacenar el nombre de usuario del amigo
    const [modalOpen, setModalOpen] = useState(false);
    const [friends, setFriends] = useState([]);
    const [requests, setRequests] = useState([]);
    const [modalContent, setModalContent] = useState(''); // Estado para el contenido del modal

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
                setModalContent(`Friend request has been sent to ${friendUsername}.`)
                setModalOpen(true); // Abre el diálogo al enviar la solicitud
                setFriendUsername(friendUsername);// Limpia el input
            }
            else {
                const errorMessage = await response.text();
                setModalContent(errorMessage); // Actualiza el mensaje del modal
                setModalOpen(true); // Abre el modal con el mensaje de error
                setFriendUsername(''); // Limpia el input
            }
        } catch (error) {
            if (error.response && error.response.status === 409) {
                const errorMessage = await error.response.text();
                setModalContent(errorMessage); // Actualiza el mensaje del modal
                setModalOpen(true); // Abre el modal con el mensaje de error
                setFriendUsername(''); // Limpia el input
            } else {
                console.error('Error sending friend request:', error);
            }
        }
    };

    const getFriendRequests = async () => {
        try {
            const response = await fetch(`/api/v1/friendship/requests`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            if (response.ok) {
                const responseBody = await response.json();
                setRequests(responseBody);
            }
        } catch (error) {
            console.error('Error fetching friend requests:', error);
        }
    }; 

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

                const friendsResponse = await fetch(`/api/v1/friendship/friends/${playerId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });


                if (friendsResponse.ok) {
                    const friendsList = await friendsResponse.json();
                    setFriends(friendsList);
                }
            }
        } catch (error) {
            console.error('Error fetching friends list:', error);
        }
    };

    const InvitationFloatingBox = ({ invitations }) => {
        const acceptInvitation = async (invitationId) => {
            try {
                const response = await fetch(`/api/v1/friendship/acceptRequest/${invitationId}`, {
                    method: "PUT",
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });
                if (response.ok) {
                    getFriendsList();
                    getFriendRequests();
                }
            } catch (error) {
                console.error('Error accepting friend request:', error);
            }
        };

        const rejectInvitation = async (invitationId) => {
            try {
                const response = await fetch(`/api/v1/friendship/rejectRequest/${invitationId}`, {
                    method: "PUT",
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });
                if (response.ok) {
                    getFriendRequests();
                }
            } catch (error) {
                console.error('Error rejecting friend request:', error);
            }
        };

        return (
            <div className="invitation-box floating-box">
                <h3>Invitations</h3>
                <ul style={{ listStyleType: 'none', padding: 0 }}>
                    {invitations.map((invitation) => (
                        <li key={invitation.id}>
                            {invitation.user_source.user.username}
                            <Button color="success" onClick={() => acceptInvitation(invitation.id)}>✓</Button>
                            <Button color="danger" onClick={() => rejectInvitation(invitation.id)}>X</Button>
                        </li>
                    ))}
                </ul>
            </div>
        );
    };



    useEffect(() => {
        getFriendsList();
        getFriendRequests();
    }, []);

    return (
        <div className="wallpaper">
            <Modal isOpen={modalOpen} toggle={toggleModal}>
                <ModalHeader toggle={toggleModal}></ModalHeader>
                <ModalBody className="text-center">
                    {modalContent}
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
                        <Button color="primary" onClick={sendFriendRequest}>
                            Send Request
                        </Button>
                </div>
                {requests.length > 0 && <InvitationFloatingBox invitations={requests} />}
            </div>
        </div>
    );
}