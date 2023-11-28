import React, { useEffect, useState } from 'react';
import { Container } from 'reactstrap';
import { Link } from 'react-router-dom';
import tokenService from '../../services/token.service';
import getErrorModal from '../../util/getErrorModal';
import '../../static/css/auth/profile.css'; 

export default function Profile() {
    const [userInfo, setUserInfo] = useState([]);
    const [alerts, setAlerts] = useState([]);
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [achievements, setAchievements] = useState([]);
    const [showUnlocked, setShowUnlocked] = useState(true);

    const user = tokenService.getUser();
    const jwt = tokenService.getLocalAccessToken();
    const rol = String(user.roles).toLowerCase() + 's';

    useEffect(() => {
        const setUp = async () => {
            try {
                const response = await fetch(`/api/v1/${rol}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setUserInfo(data);

                const currentUser = data.find((x) => x.user.id === user.id);
                if (currentUser) {
                    fetchAchievements(currentUser.id);
                }
            } catch (error) {
                setMessage('Error fetching data: ' + error.message);
                setVisible(true);
            }
        };

        setUp();
    }, [rol, jwt, user.id, showUnlocked]);

    const fetchAchievements = async (currentUserId) => {
        const url = `/api/v1/achievements/${showUnlocked ? 'unlocked' : 'locked'}/${currentUserId}`;
        const response = await fetch(url, {
            headers: {
                Authorization: `Bearer ${jwt}`,
                "Content-Type": "application/json",
            },
        });
        const data = await response.json();
        setAchievements(data);
    };

    const renderAchievements = () => {
        return achievements.map((achievement) => (
            <tr key={achievement.id}>
                <td className="text-center">
                    <img src={achievement.imageUrl} alt={achievement.name} width="50px" />
                </td>
                <td className="text-center">{achievement.name}</td>
                <td className="text-center">{achievement.description}</td>
            </tr>
        ));
    };

    const renderUserProfile = (users) => {
        const currentUser = users.find((x) => x.user.id === user.id);
        if (!currentUser) {
            return <p>User not found</p>;
        }

        return (
            <div className="profile-container">
                <div className="profile-section">
                    <h1 className="text-center">My Profile</h1>
                    <div className="container-image">
                        <img src={currentUser.image || 'path/to/default/image.jpg'} className="profile-image" alt="Profile" />
                    </div>
                    <div className="profile-data">
                        <h4>Username: {user.username}</h4>
                        <h4>First Name: {currentUser.firstName}</h4>
                        <h4>Last Name: {currentUser.lastName}</h4>
                        <Link to={"/profile/edit"} className="profile-auth-button blue">Edit</Link>
                    </div>
                </div>
                <div className="achievements-section">
                    <h1 className="text-center">Achievements</h1>
                    <div className="toggle-buttons">
                    <button 
                        onClick={() => setShowUnlocked(true)}
                        className={`toggle-button ${showUnlocked ? "active" : ""}`}
                    >
                        Unlocked
                    </button>
                    <button 
                        onClick={() => setShowUnlocked(false)}
                        className={`toggle-button ${!showUnlocked ? "active" : ""}`}
                    >
                        Locked
                    </button>
                </div>
                    <table className="achievement-table">
                        <thead>
                            <tr>
                                <th className="text-center">Image</th>
                                <th className="text-center">Name</th>
                                <th className="text-center">Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            {renderAchievements()}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    };

    const modal = getErrorModal(setVisible, visible, message);

    return (
        <Container style={{ marginTop: "15px" }} fluid>
            {alerts.map((a) => a.alert)}
            {modal}
            {renderUserProfile(userInfo)}
            <div className="button-container-back center-bottom">
                <Link className="profile-auth-button" to="/">Back</Link>
            </div>
        </Container>
    );
}
