import tokenService from "../services/token.service";
import getDeleteAlertsOrModal from "./getDeleteAlertsOrModal";

export default async function deleteFromList(id, [state, setState], [alerts, setAlerts], setMessage, setVisible, options = {}) {
    const jwt = tokenService.getLocalAccessToken();
    let confirmMessage = window.confirm("Are you sure you want to delete it?");
    if (confirmMessage) {
        try {
            const playerIdResponse = await fetch(`/api/v1/players/user/${id}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${jwt}`,
                },
            });
            const player = await playerIdResponse.json();

            const deleteResponse = await fetch(`/api/v1/players/${player.id}`, {
                method: "DELETE",
                headers: {
                    "Authorization": `Bearer ${jwt}`,
                    Accept: "application/json",
                    "Content-Type": "application/json",
                },
            });

            if (deleteResponse.status === 200 || deleteResponse.status === 204) {
                if (options.date)
                    setState(state.filter((i) => i.id !== id && i.creationDate < options.date));
                else if (options.filtered && options.setFiltered) {
                    setState(state.filter((i) => i.id !== id));
                    options.setFiltered(options.filtered.filter((i) => i.id !== id));
                } else
                    setState(state.filter((i) => i.id !== id));
            }

            const text = await deleteResponse.text();
            if (text !== '') {
                getDeleteAlertsOrModal(JSON.parse(text), id, alerts, setAlerts, setMessage, setVisible);
            }
        } catch (err) {
            console.log(err);
            alert("Error deleting entity");
        }
    }
}
