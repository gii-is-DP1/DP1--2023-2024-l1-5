import React from "react";
import { Link } from "react-router-dom";
import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";
import "../../static/css/main.css";
import tokenService from "../../services/token.service";
const user = tokenService.getUser();


const Logout = () => {
  const sendLogoutRequest = async () => {
    try {
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
          const response = await fetch(`/api/v1/players/playerLoggingOut/${playerId}`, {
            method: "PUT",
            headers: {
              Authorization: `Bearer ${jwt}`,
              'Content-Type': 'application/json',
            },
          });
          if (response.ok) {
            const data2 = await response.json();
          }else {
            console.error("Error al hacer logout:", response.statusText);
          }
          tokenService.removeUser();
          window.location.href = "/";
        } else {
          alert("There is no user logged in");
        }
      } catch (error) {
        console.error("Error:", error);
      }
}


  return (
    <div className="wallpaper">
      <div className="small-section">
        <h2 className="text-center">
          Are you sure you want to log out?
        </h2>
        <div className="button-group mt-3">
          <button className="purple-button" onClick={() => sendLogoutRequest()}>
            Yes
          </button>
          <Link className="purple-button" to="/" style={{textDecoration: "none"}}>
            No
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Logout;
