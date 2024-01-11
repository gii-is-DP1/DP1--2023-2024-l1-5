import React, { useState, useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { ErrorBoundary } from "react-error-boundary";
import AppNavbar from "./AppNavbar";
import Home from "./home";
import PrivateRoute from "./privateRoute";
import PricingPlan from "./owner/plan";
import Register from "./auth/register";
import Login from "./auth/login";
import Logout from "./auth/logout";
import Profile from "./auth/profile";
import ProfileEdit from"./auth/profile/profileEdit"
import OwnerPetList from "./owner/pets/petList";
import OwnerPetEdit from "./owner/pets/petEdit";
import OwnerVisitEdit from "./owner/visits/visitEdit";
import PlanList from "./public/plan";
import tokenService from "./services/token.service";
import OwnerDashboard from "./owner/dashboard";
import OwnerConsultationList from "./owner/consultations/consultationList";
import OwnerConsultationEdit from "./owner/consultations/consultationEdit";
import OwnerConsultationTickets from "./owner/consultations/tickets/ticketList";
import VetConsultationList from "./vet/consultations/consultationList";
import VetConsultationTickets from "./vet/consultations/tickets/ticketList";
import PetEditAdmin from "./admin/pets/PetEditAdmin";
import PetListAdmin from "./admin/pets/PetListAdmin";
import UserListAdmin from "./admin/users/UserListAdmin";
import UserEditAdmin from "./admin/users/UserEditAdmin";
import OwnerListAdmin from "./admin/owners/OwnerListAdmin";
import OwnerEditAdmin from "./admin/owners/OwnerEditAdmin";
import SpecialtyListAdmin from "./admin/vets/SpecialtyListAdmin";
import SpecialtyEditAdmin from "./admin/vets/SpecialtyEditAdmin";
import VetListAdmin from "./admin/vets/VetListAdmin";
import VetEditAdmin from "./admin/vets/VetEditAdmin";
import VisitListAdmin from "./admin/visits/VisitListAdmin";
import VisitEditAdmin from "./admin/visits/VisitEditAdmin";
import ConsultationListAdmin from "./admin/consultations/ConsultationListAdmin";
import TicketListAdmin from "./admin/consultations/TicketListAdmin";
import ConsultationEditAdmin from "./admin/consultations/ConsultationEditAdmin";
import SwaggerDocs from "./public/swagger";
import ClinicsList from "./clinicOwner/clinicsList"
import EditClinic from "./clinicOwner/clinicEdit"
import OwnerListClinicOwner from "./clinicOwner/ownersList"
import ClinicOwnerListAdmin from "./admin/clinicOwners/ClinicOwnerListAdmin";
import ClinicOwnerEditAdmin from "./admin/clinicOwners/ClinicOwnerEditAdmin";
import ClinicListAdmin from "./admin/clinics/ClinicListAdmin";
import ClinicEditAdmin from "./admin/clinics/ClinicEditAdmin";
import ConsultationListClinicOwner from "./clinicOwner/consultations/ConsultationListClinicOwner";
import ConsultationEditClinicOwner from "./clinicOwner/consultations/ConsultationEditClinicOwner";
import VetListClinicOwner from "./clinicOwner/vets/VetListClinicOwner";
import VetEditClinicOwner from "./clinicOwner/vets/VetEditClinicOwner";
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

function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  )
}

function sendLogoutRequest() {
  const jwt = window.localStorage.getItem("jwt");
  if (jwt || typeof jwt === "undefined") {
    tokenService.removeUser();
    window.location.href = "/";
  } else {
    alert("There is no user logged in");
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
          <Route path="/owners" exact={true} element={<PrivateRoute><OwnerListAdmin /></PrivateRoute>} />
          <Route path="/owners/:id" exact={true} element={<PrivateRoute><OwnerEditAdmin /></PrivateRoute>} />
          <Route path="/clinics" exact={true} element={<PrivateRoute><ClinicListAdmin /></PrivateRoute>} />
          <Route path="/clinics/:id" exact={true} element={<PrivateRoute><ClinicEditAdmin /></PrivateRoute>} />
          <Route path="/clinicOwners" exact={true} element={<PrivateRoute><ClinicOwnerListAdmin /></PrivateRoute>} />
          <Route path="/clinicOwners/:id" exact={true} element={<PrivateRoute><ClinicOwnerEditAdmin /></PrivateRoute>} />
          <Route path="/pets" exact={true} element={<PrivateRoute><PetListAdmin /></PrivateRoute>} />
          <Route path="/pets/:id" exact={true} element={<PrivateRoute><PetEditAdmin /></PrivateRoute>} />
          <Route path="/pets/:petId/visits" exact={true} element={<PrivateRoute><VisitListAdmin /></PrivateRoute>} />
          <Route path="/pets/:petId/visits/:visitId" exact={true} element={<PrivateRoute><VisitEditAdmin /></PrivateRoute>} />
          <Route path="/vets" exact={true} element={<PrivateRoute><VetListAdmin /></PrivateRoute>} />
          <Route path="/vets/:id" exact={true} element={<PrivateRoute><VetEditAdmin /></PrivateRoute>} />
          <Route path="/vets/specialties" exact={true} element={<PrivateRoute><SpecialtyListAdmin /></PrivateRoute>} />
          <Route path="/vets/specialties/:specialtyId" exact={true} element={<PrivateRoute><SpecialtyEditAdmin /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><ConsultationListAdmin /></PrivateRoute>} />
          <Route path="/consultations/:consultationId" exact={true} element={<PrivateRoute><ConsultationEditAdmin /></PrivateRoute>} />
          <Route path="/consultations/:consultationId/tickets" exact={true} element={<PrivateRoute><TicketListAdmin /></PrivateRoute>} />
          <Route path="/achievements/" exact={true} element={<PrivateRoute><AchievementListAdmin /></PrivateRoute>} />
          <Route path="/achievements/:achievementId" exact={true} element={<PrivateRoute><AchievementEditAdmin /></PrivateRoute>} />
          <Route path="/games" exact={true} element={<PrivateRoute><GamesListAdmin /></PrivateRoute>} />
        </>)
    }
    if (role === "OWNER") {
      ownerRoutes = (
        <>
          <Route path="/dashboard" element={<PrivateRoute><OwnerDashboard /></PrivateRoute>} />
          <Route path="/plan" exact={true} element={<PrivateRoute><PricingPlan /></PrivateRoute>} />
          <Route path="/myPets" exact={true} element={<PrivateRoute><OwnerPetList /></PrivateRoute>} />
          <Route path="/myPets/:id" exact={true} element={<PrivateRoute><OwnerPetEdit /></PrivateRoute>} />
          <Route path="/myPets/:id/visits/:id" exact={true} element={<PrivateRoute><OwnerVisitEdit /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><OwnerConsultationList /></PrivateRoute>} />
          <Route path="/consultations/:consultationId" exact={true} element={<PrivateRoute><OwnerConsultationEdit /></PrivateRoute>} />
          <Route path="/consultations/:consultationId/tickets" exact={true} element={<PrivateRoute><OwnerConsultationTickets /></PrivateRoute>} />
        </>)
    }
    if (role === "VET") {
      vetRoutes = (
        <>
          {/* <Route path="/dashboard" element={<PrivateRoute><OwnerDashboard /></PrivateRoute>} /> */}
          <Route path="/myPets" exact={true} element={<PrivateRoute><OwnerPetList /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><VetConsultationList /></PrivateRoute>} />
          <Route path="/consultations/:consultationId/tickets" exact={true} element={<PrivateRoute><VetConsultationTickets /></PrivateRoute>} />
        </>)
    }
    if (role === "CLINIC_OWNER") {
      vetRoutes = (
        <>
          <Route path="/owners" exact={true} element={<PrivateRoute><OwnerListClinicOwner /></PrivateRoute>} />
          <Route path="/clinics" exact={true} element={<PrivateRoute><ClinicsList /></PrivateRoute>} />
          <Route path="/clinics/:id" exact={true} element={<PrivateRoute><EditClinic /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><ConsultationListClinicOwner /></PrivateRoute>} />
          <Route path="/consultations/:id" exact={true} element={<PrivateRoute><ConsultationEditClinicOwner /></PrivateRoute>} />
          <Route path="/consultations/:id/tickets" exact={true} element={<PrivateRoute><VetConsultationTickets /></PrivateRoute>} />
          <Route path="/vets" exact={true} element={<PrivateRoute><VetListClinicOwner /></PrivateRoute>} />
          <Route path="/vets/:id" exact={true} element={<PrivateRoute><VetEditClinicOwner /></PrivateRoute>} />
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
        {/* <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} /> */}        
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
  const inactivityModal = getErrorModal(() => setShowModal(false), showModal, "Has estado inactivo durante un tiempo. ¿Sigues ahí?");

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