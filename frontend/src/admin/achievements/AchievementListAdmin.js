import { Button, ButtonGroup, Table } from "reactstrap";
import { useState } from "react";
import tokenService from "../../services/token.service";
import useFetchState from "../../util/useFetchState";
import deleteFromList from "../../util/deleteFromList";
import getErrorModal from "../../util/getErrorModal";
import { Link } from "react-router-dom";

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
        <td className="text-center"> {a.name} </td>
        <td className="text-center"> {a.description} </td>
        <td className="text-center">
          <img
            src={a.imageUrl}
            alt={a.name}
            width="50px"
          />
        </td>
        <td className="text-center"> {a.threshold} </td>
        <td className="text-center"> {a.metric} </td>
        <td className="text-center">
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
    <div>
      <div className="admin-page-container">
        <h1 className="text-center">Achievements</h1>
        {alerts.map((a) => a.alert)}
        {modal}
        <div>
          <Table aria-label="achievements" className="mt-4">
            <thead>
              <tr>
                <th className="text-center">Name</th>
                <th className="text-center">Description</th>
                <th className="text-center">Image</th>
                <th className="text-center">Threshold</th>
                <th className="text-center">Metric</th>
                <th className="text-center">Actions</th>
                <th className="text-center"></th>
              </tr>
            </thead>
            <tbody>{achievementList}</tbody>
          </Table>
        </div>
        <Button outline color="success">
          <Link
            to={`/achievements/new`}
            className="btn sm"
            style={{ textDecoration: "none" }}
          >
            Create achievement
          </Link>
        </Button>
      </div>
    </div>
  );
}
