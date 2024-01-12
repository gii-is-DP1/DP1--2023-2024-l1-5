import React, { useEffect, useState } from 'react';
import { Container } from 'reactstrap';
import { Link } from 'react-router-dom';
import tokenService from '../../services/token.service';
import getErrorModal from '../../util/getErrorModal';
import '../../static/css/auth/profile.css'; 
import '../../static/css/main.css';

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
                <td>
                    <img src={achievement.imageUrl} alt={achievement.name} width="50px" />
                </td>
                <td>{achievement.name}</td>
                <td>{achievement.description}</td>
            </tr>
        ));
    };

    const renderUserProfile = (users) => {
        const currentUser = users.find((x) => x.user.id === user.id);
        if (!currentUser) {
            return <p>User not found</p>;
        }

        return (
            <div className="page">
                <div className="section">
                    <h1 className="text-center">My Profile</h1>
                    <div className="profile-data">
                        <div className='text-center'>
                            <img src={currentUser.image || 'path/to/default/image.jpg'} className="profile-image" alt="Profile" />
                            <h5>Username: {user.username}</h5>
                            <h5>First Name: {currentUser.firstName}</h5>
                            <h5>Last Name: {currentUser.lastName}</h5>
                        </div>
                        <Link 
                            to={"/profile/edit"} 
                            className="purple-button"
                            style={{ textDecoration: 'none' }}
                            >Edit
                        </Link>
                    </div>
                </div>
                <div className="section">
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
                    <table className='table'>
                        <thead>
                            <tr>
                                <th></th>
                                <th>NAME</th>
                                <th>DESCRIPTION</th>
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
        <div className="wallpaper">
            {alerts.map((a) => a.alert)}
            {modal}
            {renderUserProfile(userInfo)}
        </div>
    );
}
