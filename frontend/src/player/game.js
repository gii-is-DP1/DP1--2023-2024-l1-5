import React,{useState,useEffect,useRef} from 'react';
import '../App.css';
import "../static/css/player/newGame.css"
import { Link } from "react-router-dom";
import tokenService from '../services/token.service';


export default function game() {

    const [InvitationList, setInvitationList] = useState([]);
    const user = tokenService.getUser();
    const tableRef = useRef(null);

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
        }
        getInvitations()
    })

    async function handleButton(game, invitationId, action) {
        //const text = action.target.textContent;
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

                const gameResponse = await fetch(`/api/v1/games/quick/joinInvitation/${game.id}`,
                {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json", 
                        Authorization: `Bearer ${jwt}`,
                    },
                });
                if(gameResponse.ok){

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

    return (
        <div className="wallpaper">
            <div className='row'>
                <div className='col-6'>
                    <div className='row'>
                    <div className="gameButton">
                        <Link to="/game/quickPlay" className="button">Quick Play</Link>
                        <Link to="/game/competitiveGame" className="button">Competitive</Link>
                    </div>
                    </div>
                    
                </div>
                <div className='col-6'>
                <div className="social">
                        <div className="invitations">
                            <h5>INVITATIONS</h5>
                            <table ref={tableRef}>
                                <thead>
                                    <tr>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {InvitationList.map(invitation => (
                                        <tr key={invitation.id}>
                                            <td><a>{invitation.source_user} </a></td>
                                            <Link to={`/game/quickPlay/${invitation.game.id}`} className='button' onClick={() => handleButton(invitation.game,invitation.id, 'ACEPTAR')}>ACEPTAR</Link>
                                            <div className='button' onClick={() => handleButton(invitation.game,invitation.id, 'REJECT')}>RECHAZAR</div>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>




        </div>
    );
}