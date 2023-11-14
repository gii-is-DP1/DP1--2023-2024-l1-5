import tokenService from "../../services/token.service";
import "../../static/css/auth/authPage.css";
import "../../static/css/auth/authButton.css";
import "../../static/css/owner/petList.css";
import {Container} from "reactstrap";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import getErrorModal from '../../util/getErrorModal';

const jwt = tokenService.getLocalAccessToken();
const imgPrueba = 'https://img.freepik.com/vector-premium/icono-perfil-avatar_188544-4755.jpg'

export default function Profile() {
    const user = tokenService.getUser();

    
    let rol = String(user.roles).toLowerCase()+'s';

    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const[userInfo, setUserInfo] = useState([]);
    const [alerts, setAlerts] = useState([]);

    
    function visualizarUser(u){
      if(String(user.roles) === 'ADMIN'|| String(user.roles) === 'CLINIC_OWNER'){
        return(
          <div className="pet-list-page-container">
            <h1>You are {String(user.roles).toLowerCase().replace("_", " ")} so you dont have profile</h1>
          </div>
        )
      }else{
        const usuario = u.filter((x)=> x.user.id === user.id)
        return(
          <div className="pet-list-page-container">
            {usuario.map(item => (
              <div className="profile-row">
                <div className="pet-options">
                  <h1 className="text-center">My Profile</h1>
                </div>
                <div className="container-image">
                    <img src={item.image || imgPrueba} className="profile-image" alt="img not found"></img>
                </div>
                <div className="profile-data">
                  <span>
                    <h4>Username: {user.username}</h4>
                  </span>
                  <span>
                    <h4>First Name: {item.firstName}</h4>
                  </span>
                  <span>
                    <h4>Last Name: {item.lastName}</h4>
                  </span>
                </div>
                <div className="button-container-edit">
                  <Link to={"/profile/edit"} className="auth-button blue" style={{ textDecoration: "none" }}>Edit</Link>
                </div>
                <div className="button-container-back">
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
        const userRolFetch = 
          await fetch(`/api/v1/${rol}`, {
            headers: {
              Authorization: `Bearer ${jwt}`,
              "Content-Type": "application/json",
            },
          })
          const data = await userRolFetch.json();
          setUserInfo(data);
          console.log(data);
      }

      useEffect(() => {
        setUp();
      }, []);
    const modal = getErrorModal(setVisible, visible, message);
    
    return(
            <Container style={{ marginTop: "15px" }} fluid>
                {alerts.map((a) => a.alert)}
                {modal} 
                {visualizarUser(userInfo)}    
            </Container>        
    );
}

