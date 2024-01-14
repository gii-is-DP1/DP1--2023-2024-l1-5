import { useState } from "react";
import { Link } from "react-router-dom";
import { Button, ButtonGroup, Table } from "reactstrap";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import "../../static/css/main.css"
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import useFetchState from "../../util/useFetchState";

const jwt = tokenService.getLocalAccessToken();

export default function UserListAdmin() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [users, setUsers] = useFetchState(
    [],
    `/api/v1/users`,
    jwt,
    setMessage,
    setVisible
  );
  const [alerts, setAlerts] = useState([]);


  async function getPlayer(userId){
    try {
      const response = await fetch(`/api/v1/players/user/${userId}`, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
        },
      });
      if(response.ok){
        const player = await response.json();
        return player;
      } else {
        console.error("Error al obtener el jugador");
        return null;
      }
    }catch(error){
      console.error("Error en la solicitud:", error);
      return null;
    }
  }
  async function deletePlayerFromGames(playerId, userId){
    try {
      const response = await fetch(`/api/v1/games/player/${playerId}`, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
        },
      });
      if(response.ok){
        const games = await response.json();
        for(const game of games){
          const deleted = await deletePlayerFromGame(game.id, userId);
          if(!deleted){
            console.log("Error al borrar el jugador del juego");
          }
        }
      } else {
        console.error("Error al obtener los juegos");
        return null;
      }
    }catch(error){
      console.error("Error en la solicitud:", error);
      return null;
    }
  }

  async function deletePlayerFromGame(gameId, userId){
    try {
      const response = await fetch(`/api/v1/games/${gameId}/players/${userId}`, {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
        },
      });
      if(response.ok){
        return true;
      } else {
        console.error("Error al borrar el jugador del juego");
        return false;
      }
    }catch(error){
      console.error("Error en la solicitud:", error);
      return false;
    }
  }

  async function deletePlayer(playerId){
    try {
      const response = await fetch(`/api/v1/players/${playerId}`, {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`,
        },
      });
      if(response.ok){
        return true;
      } else {
        console.error("Error al borrar el jugador");
        return false;
      }
    }catch(error){
      console.error("Error en la solicitud:", error);
      return false;
    }
  }

  async function deletePlayerFromList(userId){
    const player = await getPlayer(userId);
    if(player){
      // const deletedFromGames = await deletePlayerFromGames(player.id, userId);
      // if(deletedFromGames){
      //   const deleted = await deletePlayer(player.id);
      //   if(deleted){
      //     deleteFromList(setUsers, users, userId);
      //   } else {
      //     console.log("Error al borrar el jugador");
      //   }
      // }
      const deleted = await deletePlayer(player.id);
    }
  };

  const userList = users.map((user) => {
    return (
      <tr key={user.id}>
        <td>{user.username}</td>
        <td>{user.authority.authority}</td>
        <td>
          <ButtonGroup>
            <Button
              size="sm"
              color="primary"
              aria-label={"edit-" + user.id}
              tag={Link}
              to={"/users/" + user.id}
            >
              Edit
            </Button>
            <Button
              size="sm"
              color="danger"
              aria-label={"delete-" + user.id}
              onClick={() =>
                deletePlayerFromList(user.id)
              }
            >
              Delete
            </Button>
          </ButtonGroup>
        </td>
      </tr>
    );
  });
  const modal = getErrorModal(setVisible, visible, message);

  return (
    <div className="wallpaper">
      <div className="page user-list">
        <div className="section">
          <h1 className="text-center">Users</h1>
          {alerts.map((a) => a.alert)}
          {modal}
          <Table className="table">
            <thead>
              <tr>
                <th>Username</th>
                <th>Authority</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>{userList}</tbody>
          </Table>
          <Link 
            to="/users/new" 
            className="purple-button" 
            style={{ textDecoration: "none" }}
            >
              Add User
          </Link>
        </div>
      </div>
    </div>
  );
}
