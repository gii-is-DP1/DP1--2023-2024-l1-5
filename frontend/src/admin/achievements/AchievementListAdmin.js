import { Button, ButtonGroup, Table } from "reactstrap";
import { useState } from "react";
import tokenService from "../../services/token.service";
import useFetchState from "../../util/useFetchState";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import { Link } from "react-router-dom";
import "../../static/css/main.css"

const jwt = tokenService.getLocalAccessToken();
export default function AchievementList() {
  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [alerts, setAlerts] = useState([]);
  const [achievements, setAchievements] = useFetchState(
    [],
    `/api/v1/achievements`,
    jwt
  );
  const achievementList = achievements.map((a) => {
    return (
      <tr key={a.id}>
        <td> {a.name} </td>
        <td> {a.description} </td>
        <td>
          <img
            src={a.imageUrl}
            alt={a.name}
            width="50px"
          />
        </td>
        <td> {a.threshold} </td>
        <td> {a.metric} </td>
        <td>
          <ButtonGroup>
            <Button
                size="sm"
                color="primary"
                aria-label={"edit-" + a.id}
                tag={Link}
                to={"/achievements/" + a.id}
              >
                Edit
            </Button>
            <Button
              size="sm"
              color="danger"
              aria-label={"delete-" + a.id}
              onClick={() =>
                deleteFromList(
                  `/api/v1/achievements/${a.id}`,
                  a.id,
                  [achievements, setAchievements],
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
    );
  });
  const modal = getErrorModal(setVisible, visible, message);
  return (
    <div className="wallpaper">
      <div className="big-section">
        <h1 className="text-center">Achievements</h1>
        {alerts.map((a) => a.alert)}
        {modal}
        <div>
          <Table className="table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Image</th>
                <th>Threshold</th>
                <th>Metric</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>{achievementList}</tbody>
          </Table>
        </div>
          <Link
            to={`/achievements/new`}
            className="purple-button"
            style={{ textDecoration: "none" }}
          >
            Create achievement
          </Link>
      </div>
    </div>
  );
}
