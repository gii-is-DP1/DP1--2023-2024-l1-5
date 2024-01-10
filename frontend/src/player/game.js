import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import '../App.css';
import '../static/css/player/newGame.css';
import '../static/css/main.css'
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
            <div className="page">
                <div className='small-section'>
                    <h1 className='text-center'>
                        Quick Mode
                    </h1>
                    <span className='text-center mt-2'>
                    Quick Mode is super fun and speedy! It's like a quick playtime where you can just enjoy the game, laugh, and have a blast. 
                    You don't have to worry about keeping score or winning - it's all about having a good time. 
                    Whether you're playing with friends or family, Quick Mode is perfect for when you just want to play fast and have fun!
                    </span>
                    <br></br>
                    <Link 
                        to="/game/quickPlay" 
                        className="purple-button"
                        style={{ textDecoration: 'none' }}
                        >
                        Play
                    </Link>
                </div>
                <div className='small-section'>
                    <h1 className='text-center'>
                        Competitive Mode
                    </h1>
                    <span className='text-center mt-2'>
                    Competitive Mode is where you get to show off your skills! In this mode, every move is a chance to earn points and climb up the leaderboard. 
                    It's like a fun challenge where you try to be the best player you can be. 
                    If you love making strategies and winning games, this is the mode for you. Let's see how high you can climb in the rankings!
                    </span>
                    <br></br>
                    <Link 
                        to="/underConstruction" 
                        className="purple-button"
                        style={{ textDecoration: 'none' }}
                        >
                        Play
                    </Link>
                </div>
                {InvitationList.length > 0 && <InvitationsFloatingBox invitations={InvitationList} />}
            </div>
        </div>
    );
}
