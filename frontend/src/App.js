import React, { useState, useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { ErrorBoundary } from "react-error-boundary";
import AppNavbar from "./AppNavbar";
import Home from "./home";
import PrivateRoute from "./privateRoute";
import Register from "./auth/register";
import Login from "./auth/login";
import Logout from "./auth/logout";
import Profile from "./auth/profile";
import ProfileEdit from"./auth/profile/profileEdit"
import PlanList from "./public/plan";
import tokenService from "./services/token.service";
import UserListAdmin from "./admin/users/UserListAdmin";
import UserEditAdmin from "./admin/users/UserEditAdmin";
import SwaggerDocs from "./public/swagger";
import QuickPlay from "./player/quickPlay";
import WaitingRoom from "./player/waitingRoom";
import Game from "./player/game";
import PitGameView from "./player/pitGameView";
import ItGameView from "./player/itGameView";
import GameViewerView from "./player/gameViewerView";
import AchievementListAdmin from "./admin/achievements/AchievementListAdmin";
import AchievementEditAdmin from "./admin/achievements/AchievementEditAdmin";
import GameRules from "./player/gameRules";
import GameHistory from "./player/gameHistory";
import GamesListAdmin from "./admin/games/GameListAdmin";
import FriendsList from "./player/friends/friendsList";
import GameEndWinnerLoser from "./player/gameEndWinnerLoser";
import Error from "./player/error";
import getErrorModal from "./util/getErrorModal";
import Construction from "./player/construction";
import Statistics from "./statistics";

const user = tokenService.getUser();

function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  )
}

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
        if(user.roles === "ADMIN"){
          tokenService.removeUser();
          window.location.href = "/";
        }else{
          alert("There is no user logged in");
        }
      }
    } catch (error) {
      console.error("Error:", error);
    }
}

function App() {
  const jwt = tokenService.getLocalAccessToken();
  let roles = []
  if (jwt) {
    roles = getRolesFromJWT(jwt);
  }

  function getRolesFromJWT(jwt) {
    return jwt_decode(jwt).authorities;
  }

  let adminRoutes = <></>;
  let ownerRoutes = <></>;
  let userRoutes = <></>;
  let vetRoutes = <></>;
  let publicRoutes = <></>;
  let playerRoutes = <></>;

  roles.forEach((role) => {
    if (role === "ADMIN") {
      adminRoutes = (
        <>
          <Route path="/users" exact={true} element={<PrivateRoute><UserListAdmin /></PrivateRoute>} />
          <Route path="/users/:username" exact={true} element={<PrivateRoute><UserEditAdmin /></PrivateRoute>} />
          <Route path="/achievements/" exact={true} element={<PrivateRoute><AchievementListAdmin /></PrivateRoute>} />
          <Route path="/achievements/:achievementId" exact={true} element={<PrivateRoute><AchievementEditAdmin /></PrivateRoute>} />
          <Route path="/games" exact={true} element={<PrivateRoute><GamesListAdmin /></PrivateRoute>} />
        </>)
    }
    if(role==="PLAYER"){
      playerRoutes = (
        <>
          <Route path="/game" exact={true} element={<PrivateRoute><Game/></PrivateRoute>} />	
          <Route path="/game/quickPlay" exact={true} element={<PrivateRoute><QuickPlay/></PrivateRoute>}></Route>
          <Route path="/game/quickPlay/:id" exact={true} element={<PrivateRoute><WaitingRoom/></PrivateRoute>}></Route>
          <Route path="/game/quickPlay/:id/:roundId/pit" exact={true} element={<PrivateRoute><PitGameView/></PrivateRoute>} />	
          <Route path="/game/quickPlay/:id/:roundId/it" exact={true} element={<PrivateRoute><ItGameView/></PrivateRoute>} />          
          <Route path="/game/quickPlay/:id/:roundId/viewer/:playerId" exact={true} element={<PrivateRoute><GameViewerView/></PrivateRoute>} />
          <Route path="/gameRules" exact={true} element={<PrivateRoute><GameRules/></PrivateRoute>}></Route>
          <Route path="/gameHistory" exact={true} element={<PrivateRoute><GameHistory/></PrivateRoute>}></Route>
          <Route path="/friendsList" exact={true} element={<PrivateRoute><FriendsList/></PrivateRoute>}></Route>
          <Route path="/game/quickPlay/:id/endGame/:winnerId" exact={true} element={<PrivateRoute><GameEndWinnerLoser/></PrivateRoute>}></Route>
          <Route path="/error" exact={true} element={<PrivateRoute><Error/></PrivateRoute>}></Route>
          <Route path="/underConstruction" exact={true} element={<PrivateRoute><Construction/></PrivateRoute>}></Route>
          <Route path="/statistics" exact={true} element={<PrivateRoute><Statistics/></PrivateRoute>}></Route>
        </>)


    }
  })
  if (!jwt) {
    publicRoutes = (
      <>        
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  } else {
    userRoutes = (
      <>       
        <Route path="/logout" element={<Logout />} />
        <Route path="/login" element={<Login />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/profile/edit" element={<ProfileEdit />} />
      </>
    )
  }

  // Estado y constantes para la detección de inactividad y la modal
  const INACTIVITY_TIME = 60 * 1000; // 60 segundos de inactividad
  const WARNING_TIME = 10 * 1000; // 10 segundos adicionales después de la alerta
  const [isInactive, setIsInactive] = useState(false);
  const [showModal, setShowModal] = useState(false);

  // Reiniciar el temporizador de inactividad
  const resetInactivityTimer = () => {
    setIsInactive(false);
    setShowModal(false); // Ocultar la ventana modal
    clearTimeout(window.inactivityTimer);
    clearTimeout(window.warningTimer);
    window.inactivityTimer = setTimeout(() => setIsInactive(true), INACTIVITY_TIME);
  };

  // Establecer eventos para detectar actividad del usuario
  useEffect(() => {
    const events = ['mousemove', 'keydown', 'scroll', 'click'];
    events.forEach(event => window.addEventListener(event, resetInactivityTimer));
    resetInactivityTimer();

    // Limpieza al desmontar el componente
    return () => {
      events.forEach(event => window.removeEventListener(event, resetInactivityTimer));
      clearTimeout(window.inactivityTimer);
      clearTimeout(window.warningTimer);
    };
  }, []);

  // Función para comprobar si el usuario está logueado
  const isUserLoggedIn = () => {
    const jwt = window.localStorage.getItem("jwt");
    return jwt !== null && jwt !== "undefined";
  };  

  // Efecto para manejar la inactividad
  useEffect(() => {
    if (isInactive && isUserLoggedIn()) {
      setShowModal(true); // Mostrar la ventana modal
      // Iniciar temporizador de advertencia para cierre de sesión
      window.warningTimer = setTimeout(() => {
        if (showModal) { // Si la modal sigue activa, cerrar sesión
          sendLogoutRequest();
        }
      }, WARNING_TIME);
    }
  }, [isInactive, showModal]);

  // Renderizar el componente modal
  const inactivityModal = getErrorModal(() => setShowModal(false), showModal, "You've been inactive for a while. You still there?");

  return (
    <div>
      <ErrorBoundary FallbackComponent={ErrorFallback} >
        <AppNavbar />
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/plans" element={<PlanList />} />
          <Route path="/docs" element={<SwaggerDocs />} />
          {publicRoutes}
          {userRoutes}
          {adminRoutes}
          {ownerRoutes}
          {vetRoutes}
          {playerRoutes}
        </Routes>
      </ErrorBoundary>
      {inactivityModal}
    </div>
  );
}

export default App;