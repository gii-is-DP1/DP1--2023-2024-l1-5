import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import '../App.css';
import '../static/css/player/newGame.css';
import tokenService from '../services/token.service';

export default function Game() {
    const [InvitationList, setInvitationList] = useState([]);
    const user = tokenService.getUser();
    const tableRef = useRef(null);

    useEffect(() => {
        const getInvitations = async () => {
            try {
                const UserName = user.username;
                const jwt = JSON.parse(window.localStorage.getItem('jwt'));
                const invitationsRespone = await fetch(`/api/v1/invitations/${UserName}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        'Content-Type': 'application/json',
                    },
                });
                if (invitationsRespone.ok) {
                    const responseBody = await invitationsRespone.json();
                    setInvitationList(responseBody);
                }
            } catch (error) {
                console.error('Error fetching invitations:', error);
            }
        };

        getInvitations();
        const intervalId = setInterval(getInvitations, 10000);
        return () => clearInterval(intervalId);
    }, []);

    async function handleButton(game, gameId, invitationId, action) {
        if (action === 'ACEPTAR') {
            const jwt = JSON.parse(window.localStorage.getItem('jwt'));
            const invitationResponse = await fetch(`/api/v1/invitations/acceptRequest/${invitationId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwt}`,
                },
            });
            if (invitationResponse.ok) {
                const jwt = JSON.parse(window.localStorage.getItem('jwt'));
                const gameResponse = await fetch(`/api/v1/games/quick/joinInvitation/${gameId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                if (gameResponse.ok) {
                    window.location.href = `/game/quickPlay/${game.id}`;
                }
            }
        } else {
            const jwt = JSON.parse(window.localStorage.getItem('jwt'));
            const invitationResponse = await fetch(`/api/v1/invitations/refusededRequest/${invitationId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwt}`,
                },
            });
            alert('REFUSED');
        }
    }

    const InvitationsFloatingBox = ({ invitations }) => {
        return (
            <div className="invitation-box floating-box" style={{ maxWidth: '800px' }}>
                <h3>INVITATIONS</h3>
                <ul style={{ listStyleType: 'none', padding: 0 }}>
                    {invitations.map((invitation) => (
                        <li key={invitation.id} style={{ marginBottom: '10px' }}>
                            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                                <span style={{ marginRight: '5px' }}>
                                    <a>{invitation.source_user}</a>
                                </span>
                                <span style={{ marginRight: '5px' }}>
                                    <div className='aceptar-boton' onClick={() => handleButton(invitation.game,invitation.game.id,  invitation.id, 'ACEPTAR')}>ACEPTAR</div>
                                </span>
                                <span>
                                    <div className='rechazar-boton' onClick={() => handleButton(invitation.game,invitation.game.id,  invitation.id, 'RECHAZAR')}>RECHAZAR</div>
                                </span>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        );
    };

    return (
        <div className="wallpaper">
            <div className="gameButton">
                <Link to="/game/quickPlay" className="button">
                    Quick Play
                </Link>
                <Link to="/game/competitiveGame" className="button">
                    Competitive
                </Link>
                {InvitationList.length > 0 && <InvitationsFloatingBox invitations={InvitationList} />}
            </div>
        </div>
    );
}
