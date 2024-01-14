import { useState } from "react";
import { Link } from "react-router-dom";
import { Button, ButtonGroup, Table } from "reactstrap";
import ReactPaginate from "react-paginate";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import "../../static/css/main.css";
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
  const [currentPage, setCurrentPage] = useState(0); // Add state for current page
  const [perPage] = useState(5); // Set the number of items per page to 5
  const [alerts, setAlerts] = useState([]);

  const offset = currentPage * perPage;
  const paginatedUsers = users.slice(offset, offset + perPage);

  const handlePageClick = (data) => {
    const selectedPage = data.selected;
    setCurrentPage(selectedPage);
  };

  const userList = paginatedUsers.map((user) => (
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
              deleteFromList(
                user.id,
                [users, setUsers],
                [alerts, setAlerts],
                setMessage,
                setVisible
              )
            }
          >
            Delete
          </Button>
        </ButtonGroup>
      </td>
    </tr>
  ));

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
          <ReactPaginate
            previousLabel={"Previous"}
            nextLabel={"Next"}
            breakLabel={"..."}
            breakClassName={"break-me"}
            pageCount={Math.ceil(users.length / perPage)}
            marginPagesDisplayed={2}
            pageRangeDisplayed={5}
            onPageChange={handlePageClick}
            containerClassName={"pagination"}
            activeClassName={"active"}
          />
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
