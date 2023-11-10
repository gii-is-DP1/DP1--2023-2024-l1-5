import tokenService from "../../services/token.service";
import {
    Button,
    ButtonGroup,
    Col,
    Container,
    Input,
    Row,
    Table,
  } from "reactstrap";
import { useEffect, useState } from "react";
import getErrorModal from '../../util/getErrorModal';
import useFetchState from "../../util/useFetchState";

const user = tokenService.getUser();
const jwt = tokenService.getLocalAccessToken();


export default function Profile() {
    
    let rol = String(user.roles).toLowerCase()+'s';

    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    let[userInfo, setUserInfo] = useState([]);
    const [alerts, setAlerts] = useState([]);


    function aux(u){
      return u.map((c) =>{
        return(
          <div>
              <h4>{c.user.id=== user.id ? c.user.id:""}</h4>
              <h4>{c.user.id=== user.id ? c.firstName||c.playerUsername:""}</h4>
          </div>
        )
      })
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
        let oneUser = userInfo.filter((p) => p.user.id === user.id);
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
                {aux(userInfo)}    
            </Container>        
    );
}

