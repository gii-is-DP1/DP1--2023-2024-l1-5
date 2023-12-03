import React, { useState,useEffect }  from 'react';
import '../App.css';
import '../static/css/home/home.css';
import tokenService from '../services/token.service';
import onlineLogo from '../static/images/punto_verde.png'

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function Home(){
    const [friendsOnline, setFriendsOnline] = useState([]);

    const getOnlineFriendsList = async () => {
        try {
            const userIdResponse = await fetch(`/api/v1/players/user/${user.id}`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            if (userIdResponse.ok) {
                const responseBody = await userIdResponse.json();
                const playerId = responseBody.id;

                const friendsResponse = await fetch(`/api/v1/friendship/friends/online/${playerId}`, {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "application/json",
                    },
                });


                if (friendsResponse.ok) {
                    const friendsList = await friendsResponse.json();
                    setFriendsOnline(friendsList);
                }
            }
        } catch (error) {
            console.error('Error fetching friends list:', error);
        }
    };

    const FriendsFloatingBox = ({ friends }) => {

        return (
            <div className="invitation-box floating-box">
                <h3>Online friends</h3>
                <ul style={{ listStyleType: 'none', padding: 0 }}>
                    {friends.map((friend) => (
                        <li key={friend.id} style={{ display: 'flex', alignItems: 'center' }}>
                            <span>
                            <img alt="Online logo" src={onlineLogo} style={{ height: 10, width: 10, marginRight: '10px' }} />
                            </span>
                            <span>
                            {friend.user.username}
                            </span>
                        </li>
                    ))}
                </ul>
            </div>
        );
    };

    useEffect(() => {
        getOnlineFriendsList();
    }, []);


    return(
        <div className="home-page-container">
            <div className="hero-div">
                <h1>Dobble</h1>
                <h3>---</h3>
                <h3>The most funny game</h3>     
                {console.log(friendsOnline.length)}
                <FriendsFloatingBox friends={friendsOnline} />   
            </div>
        </div>
    );
}