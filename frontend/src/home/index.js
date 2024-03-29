import React, { useState,useEffect }  from 'react';
import '../App.css';
import '../static/css/home/home.css';
import '../static/css/main.css'
import tokenService from '../services/token.service';
import onlineLogo from '../static/images/punto_verde.png'
import { Link } from "react-router-dom";


const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function Home(){
    const [friendsOnline, setFriendsOnline] = useState([]);
    const [InvitationList, setInvitationList] = useState([]);


    const getOnlineFriendsList = async () => {
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

                const friendsResponse = await fetch(`/api/v1/friendship/friends/online/${playerId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });


                if (friendsResponse.ok) {
                    const friendsList = await friendsResponse.json();
                    setFriendsOnline(friendsList);
                }
            }
        } catch (error) {
            console.error('Error fetching friends list:', error);
        }
    };
  
    useEffect(()=>{
        const getInvitations = async () => {
            try {
                const UserName = user.username;
                const jwt = JSON.parse(window.localStorage.getItem("jwt"));
                const invitationsRespone = await fetch(`/api/v1/invitations/${UserName}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });
                if (invitationsRespone.ok) {
                    const responseBody = await invitationsRespone.json();
                    setInvitationList(responseBody);
                   
                }
            } catch (error) {
                console.error('Error fetching invitatiosn:', error);
            }
        };
        
        getInvitations();
        const intervalId = setInterval(getInvitations, 2000);
        return () => clearInterval(intervalId);
    }, [])


    const FriendsAndInvitationsFloatingBox = ({ friends, invitations, handleButton }) => {
        return (
            <div className="floating-box" >
                {/* Online friends section */}
                <div>
                    <h4>Online Friends</h4>
                    <ul style={{ listStyleType: 'none', padding: 0 }}>
                        {friends.map((friend) => (
                            <li key={friend.id} style={{ display: 'flex', alignItems: 'center' }}>
                                <span>
                                    <img alt="Online logo" src={onlineLogo} style={{ height: 10, width: 10, marginRight: '10px' }} />
                                </span>
                                <span>
                                    {friend.user.username}
                                </span>
                            </li>
                        ))}
                    </ul>
                </div>
    
                {/* Invitations section */}
                <div>
                    <h4>Invitations</h4>
                    <ul style={{ listStyleType: 'none', padding: 0 }}>
                        {invitations.map((invitation) => (
                            <li key={invitation.id} style={{ marginBottom: '10px' }}>
                                <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                                    <span style={{ marginRight: '5px' }}>
                                        <a>{invitation.source_user}</a>
                                    </span>
                                    <span style={{ marginRight: '5px' }}>
                                        <div className='aceptar-boton' onClick={() => handleButton(invitation.game, invitation.game.id, invitation.id, 'ACEPTAR')}>ACEPTAR</div>
                                    </span>
                                    <span>
                                        <div className='rechazar-boton' onClick={() => handleButton(invitation.game, invitation.game.id, invitation.id, 'RECHAZAR')}>RECHAZAR</div>
                                    </span>
                                </div>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        );
    };

    useEffect(() => {
        getOnlineFriendsList();
    }, []);

    async function handleButton(game,gameId, invitationId, action) {
        if (action == "ACEPTAR"){
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const invitationResponse = await fetch(`/api/v1/invitations/acceptRequest/${invitationId}`,
            {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json", 
                    Authorization: `Bearer ${jwt}`,
                },
            });
            if(invitationResponse.ok){

                const jwt = JSON.parse(window.localStorage.getItem("jwt"));

                const gameResponse = await fetch(`/api/v1/games/quick/joinInvitation/${gameId}`,
                {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json", 
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                if (gameResponse.ok) {
                    window.location.href = `/game/quickPlay/${game.id}`;
                }
            }
        }else{
            const jwt = JSON.parse(window.localStorage.getItem("jwt"));
            const invitationResponse = await fetch(`/api/v1/invitations/refusededRequest/${invitationId}`,
            {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json", 
                    Authorization: `Bearer ${jwt}`,
                },
            });
            alert("REFUSED")
            
        }
    }

    return(
        <div className="wallpaper">
            <div className="hero-div">
                <h1>Dobble</h1>
                <h3>---</h3>
                <h3>The most funny game</h3>
            </div>
            {(InvitationList.length > 0  || friendsOnline.length) > 0 && <FriendsAndInvitationsFloatingBox friends={friendsOnline} invitations={InvitationList} handleButton={handleButton} />}
        </div>
    );
}