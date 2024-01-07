import React from "react";
import { Link } from "react-router-dom";
import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";
import "../../static/css/main.css";
import tokenService from "../../services/token.service";

const Logout = () => {
  function sendLogoutRequest() {
    const jwt = window.localStorage.getItem("jwt");
    if (jwt || typeof jwt === "undefined") {
      tokenService.removeUser();
      window.location.href = "/";
    } else {
      alert("There is no user logged in");
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
