import tokenService from "../../services/token.service";
import "../../static/css/auth/authPage.css";
import "../../static/css/auth/authButton.css";
import {Container} from "reactstrap";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import getErrorModal from '../../util/getErrorModal';

const user = tokenService.getUser();
const jwt = tokenService.getLocalAccessToken();
const imgPrueba = "https://assets.goal.com/v3/assets/bltcc7a7ffd2fbf71f5/blt438b6e48a7a5b929/6210e104364542764d38b91e/fekir-ear.jpg?auto=webp&format=pjpg&width=3840&quality=60"

export default function Profile() {
    
    let rol = String(user.roles).toLowerCase()+'s';

    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    let[userInfo, setUserInfo] = useState([]);
    const [alerts, setAlerts] = useState([]);

    
    function visualizarUser(u){
      if(String(user.roles) === 'ADMIN'|| String(user.roles) === 'CLINIC_OWNER'){
        return(
          <div>
            <h1>You are {String(user.roles).toLowerCase().replace("_", " ")}</h1>
          </div>
        )
      }else{
        const usuario = u.filter((x)=> x.user.id === user.id)
        return(
          <div>
            {usuario.map(item => (
              <div className="profile-container">
                <div className="profile-container">
                  <img src={imgPrueba} style={{ height: 300, width: 400 }} alt="img not found"></img>
                 <div className="profile-details">
                    <p>{item.firstName || item.playerUsername}</p>
                 </div>
                </div>
                  <div className="button-container">
                  <Link className="auth-button" to="/" style={{textDecoration: "none"}}>Back</Link>
                  </div>
              </div>
              )
            )}
          </div>
        )    
      }
    }

      async function setUp() {
        const userRolFetch = await (
          await fetch(`/api/v1/${rol}`, {
            headers: {
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
          })
        ).json();
        setUserInfo(userRolFetch);
      }

      useEffect(() => {
        setUp();
      }, []);
      useEffect(() => {}, [userInfo]);

    const modal = getErrorModal(setVisible, visible, message);
    
    return(
            <Container style={{ marginTop: "15px" }} fluid>
                <h1 className="text-center">My Profile</h1>
                {alerts.map((a) => a.alert)}
                {modal} 
                {visualizarUser(userInfo)}    
            </Container>        
    );
}

