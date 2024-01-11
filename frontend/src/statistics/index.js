import React, { useState, useEffect } from 'react';
import '../App.css';
import '../static/css/home/home.css';
import '../static/css/main.css';
import tokenService from '../services/token.service';
import {PieChart, Pie, Sector, Cell, Tooltip} from "recharts";

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function Statistics() {
    const [stats, setStats] = useState({
        nGames: 0,
        gamesWon: 0,
        gamesLost: 0,
        gamesAvgTime: 0,
        gamesMaxTime: 0,
        gamesMinTime: 0,
        totalTimePlayed: 0,
    });

    const [playerId, setPlayerId] = useState(null);
    const [player, setPlayer] = useState(null);
    const [ranking, setRanking] = useState([]);
    

    async function fetchPlayerData() {
        try {
            const response = await fetch(`/api/v1/players/user/${user.id}`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            });

            if (response.ok) {
                const data = await response.json();
                setPlayer(data);
                setPlayerId(data.id);
            }
        } catch (error) {
            console.error('Error al obtener datos del jugador:', error);
        }
    }

    async function fetchRanking() {
        try {
            const response = await fetch(`/api/v1/games/ranking`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${jwt}`,
                },
            });

            if (response.ok) {
                const data = await response.json();
                setRanking(data);
            }
        } catch (error) {
            console.error('Error al obtener datos de ranking:', error);
        }
    }

    async function fetchData(url, setDataCallback) {
        try {
            const response = await fetch(url, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });

            if (response.ok) {
                const data = await response.json();
                setDataCallback(data);
            } else {
                console.error(`Error al obtener datos: ${url}`, response.status);
            }
        } catch (error) {
            console.error(`Error al obtener datos: ${url}`, error);
        }
    }

    useEffect(() => {
        fetchPlayerData();
        fetchRanking();
    }, []);

    useEffect(() => {
        fetchData(`/api/v1/games/numGames/${playerId}`, (numGamesData) => {
            setStats((prevStats) => ({ ...prevStats, nGames: numGamesData }));
        });

        fetchData(`/api/v1/games/numGamesWin/${user.id}`, (numGamesWinData) => {
            setStats((prevStats) => ({ ...prevStats, gamesWon: numGamesWinData }));
        });

        fetchData(`/api/v1/games/timeGames/${playerId}`, (numTimeGamesData) => {
            setStats((prevStats) => ({ ...prevStats, totalTimePlayed: numTimeGamesData }));
        });

        fetchData(`/api/v1/games/maxTimeGames/${playerId}`, (maxTimeGamesData) => {
            setStats((prevStats) => ({ ...prevStats, gamesMaxTime: maxTimeGamesData }));
        });

        fetchData(`/api/v1/games/minTimeGames/${playerId}`, (minTimeGamesData) => {
            setStats((prevStats) => ({ ...prevStats, gamesMinTime: minTimeGamesData }));
        });

        fetchData(`/api/v1/games/avgTimeGames/${playerId}`, (avgTimeGamesData) => {
            // Redondear el valor a dos decimales
            const roundedAvgTime = Number(avgTimeGamesData.toFixed(2));
        
            setStats((prevStats) => ({ ...prevStats, gamesAvgTime: roundedAvgTime }));
        });
        
    }, [jwt, playerId]);

    const data = [
        {name:"Games Win", value: stats.gamesWon}, 
        {name:"Games los", value: stats.nGames - stats.gamesWon}, 
    ]

    const renderStatistics = () => {
        return (
            <div className="page">
                <div className="section">
                    <h1 className="text-center">Player Statistics</h1>
                    <div className="statistics-list">
                        <div>
                            <strong>Number of Games:</strong> {stats.nGames}
                        </div>
                        <div>
                            <strong>Games Won:</strong> {stats.gamesWon}
                        </div>
                        <div>
                            <strong>Games Lost:</strong> {stats.nGames - stats.gamesWon}
                        </div>
                        <div>
                            <strong>Total Time Played:</strong> {stats.totalTimePlayed} seconds
                        </div>
                        <div>
                            <strong>Max Time Played:</strong> {stats.gamesMaxTime} seconds
                        </div>
                        <div>
                            <strong>Min Time Played:</strong> {stats.gamesMinTime} seconds
                        </div>
                        <div>
                            <strong>Avg Time Played:</strong> {stats.gamesAvgTime} seconds
                        </div>
                    </div>
                    <div>
                        <PieChart width={400} height={400}>
                            <Pie
                                dataKey="value"
                                isAnimationActive={false}
                                data={data}
                                cx={200}
                                cy={200}
                                outerRadius={80}
                                fill="#8884d8"
                                label
                            />
                            <Tooltip />
                        </PieChart>
                    </div>
                </div>
                <div className="section">
                    <div>
                        <h2>Player Ranking</h2>
                        <ol>
                            {ranking.map((player, index) => (
                            <li key={player.id}>
                                 {player.username}: {player.numGames} games
                            </li>
                            ))}
                        </ol>
                    </div>
                </div>
            </div>
        );
    };

    return (
        <div className="wallpaper">
            {renderStatistics()}
        </div>
    );
}
