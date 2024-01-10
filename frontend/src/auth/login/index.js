import React, { useState } from "react";
import { Alert } from "reactstrap";
import FormGenerator from "../../components/formGenerator/formGenerator";
import tokenService from "../../services/token.service";
import "../../static/css/auth/authButton.css";
import { loginFormInputs } from "./form/loginFormInputs";


export default function Login() {
  const [message, setMessage] = useState(null)
  const loginFormRef = React.createRef();      
  

  async function handleSubmit({ values }) {

    const reqBody = values;
    setMessage(null);
    await fetch("/api/v1/auth/signin", {
      headers: { "Content-Type": "application/json" },
      method: "POST",
      body: JSON.stringify(reqBody),
    })
      .then(function (response) {
        if (response.status === 200) return response.json();
        else return Promise.reject("Invalid login attempt");
      })
      .then(function (data) {
        tokenService.setUser(data);
        tokenService.updateLocalAccessToken(data.token);
      })
      .catch((error) => {         
        setMessage(error);
      }); 
      try {
        const user = tokenService.getUser();
        const jwt = JSON.parse(window.localStorage.getItem("jwt"));
        const playerResponse = await fetch(`/api/v1/players/user/${user.id}`,
            {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            })
        if (playerResponse.ok) {
          const data = await playerResponse.json();
          const playerId = data.id;
          const jwt = JSON.parse(window.localStorage.getItem('jwt'));
          const response = await fetch(`/api/v1/players/playerLoggingIn/${playerId}`, {
            method: "PUT",
            headers: {
              Authorization: `Bearer ${jwt}`,
              'Content-Type': 'application/json',
            },
          });
          if (response.ok) {
            const data2 = await response.json();
          }else {
            console.error("Error al hacer login:", response.statusText);
          }
          window.location.href = "/";
        } else {
          if(user.roles == "ADMIN"){
            window.location.href = "/";
          }else{
            alert("There is no user logged in");
          }
        }
      } catch (error) {
        console.error("Error:", error);
      }           
  }

  
    return (
      <div className="wallpaper">
        <div className="section">
          {message ? (
            <Alert color="primary">{message}</Alert>
          ) : (
            <></>
          )}
          <h1>Login</h1>
          <div className="auth-form-container">
            <FormGenerator
              ref={loginFormRef}
              inputs={loginFormInputs}
              onSubmit={handleSubmit}
              numberOfColumns={1}
              listenEnterKey
              buttonText="Login"
              buttonClassName="purple-button"
            />
          </div>
        </div>
      </div>
    );  
}