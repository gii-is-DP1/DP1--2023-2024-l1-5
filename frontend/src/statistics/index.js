import React, { useState, useEffect } from 'react';
import '../App.css';
import '../static/css/home/home.css';
import '../static/css/main.css';
import tokenService from '../services/token.service';
import {PieChart, Pie, Sector, Cell, Tooltip, Legend} from "recharts";

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
        totalGamesWonPit: 0,
        totalGamesWonIT: 0,
        myRank: 0,
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

        fetchData(`/api/v1/games/numGamesMode/${playerId}/${"PIT"}`, (numPit) => {
            setStats((prevStats) => ({ ...prevStats, totalGamesWonPit: numPit }));
        });

        fetchData(`/api/v1/games/numGamesMode/${playerId}/${"INFERNAL_TOWER"}`, (numIT) => {
            setStats((prevStats) => ({ ...prevStats, totalGamesWonIT: numIT }));
        });

        fetchData(`/api/v1/games/ranking/${playerId}`, (rank) => {
            setStats((prevStats) => ({ ...prevStats, myRank: rank }));
        });
        
    }, [jwt, playerId]);

    const data = [
        {name:"GAMES WON", value: stats.gamesWon}, 
        {name:"LOST GAMES", value: stats.nGames - stats.gamesWon}, 
    ]

    const data2 = [
        {name:"PIT", value: stats.totalGamesWonPit}, 
        {name:"INFERNAL TOWER", value: stats.totalGamesWonIT}, 
    ]

    const renderStatistics = () => {
        return (
            <div className="page">
                <div className="section">
                    <h1 className="text-center">Player Statistics</h1>
                    <div className="statistics-list">
                        <div>
                            <strong>Total Games:</strong> {stats.nGames}
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
                            <strong>Average time per game:</strong> {stats.gamesAvgTime} seconds
                        </div>
                    </div>
                    <div className="chart-container">
                        <div className="chart">
                            <PieChart width={340} height={340}>
                            <text x={170} y={20} textAnchor="middle" dominantBaseline="middle">
                                GAMES WIN AND LOST
                            </text>
                                <Pie
                                    dataKey="value"
                                    isAnimationActive={false}
                                    data={data}
                                    cx={190}
                                    cy={200}
                                    outerRadius={80}
                                    fill="#8884d8"
                                    label
                                >
                                    <Cell key={1} fill="#82ca9d" />
                                    <Cell key={2} fill="#8884d8" />
                                </Pie>
                                <Tooltip />
                                <Legend 
                                    align="center"
                                    payload={[
                                        { id: 'Games Win', type: 'square', value: 'GAMES WON', color: '#82ca9d' },
                                        { id: 'Games los', type: 'square', value: 'LOST GAMES', color: '#8884d8' },
                                    ]}
                                />
                            </PieChart>
                        </div>
                        <div className="chart">
                            <PieChart width={340} height={340}>
                            <text x={170} y={20} textAnchor="middle" dominantBaseline="middle">
                                GAMES PLAYED BY MODE
                            </text>
                                <Pie
                                    dataKey="value"
                                    isAnimationActive={false}
                                    data={data2}
                                    cx={150}
                                    cy={200}
                                    outerRadius={80}
                                    fill="#8884d8"
                                    label
                                >
                                    <Cell key={1} fill="#82ca9d" />
                                    <Cell key={2} fill="#8884d8" />
                                </Pie>
                                <Tooltip />
                                <Legend 
                                    align="center"
                                    payload={[
                                        { id: 'Games Win of PIT', type: 'square', value: 'PIT', color: '#82ca9d' },
                                        { id: 'Games Win of IT', type: 'square', value: 'IT', color: '#8884d8' },
                                    ]}
                                />
                            </PieChart>
                        </div>
                    </div>
                </div>
                <div className="section">
                    <div>
                        <h2>Global Top 5</h2>
                        <ol>
                            {ranking.map((player, index) => (
                            <li key={player.id}>
                                 {player.username}: {player.numGames} games won
                            </li>
                            ))}
                        </ol>
                    </div>
                    <div>
                        <h3>Your position in the Ranking</h3>
                        <ul>
                            {stats.myRank === 0 ? (
                                <li>You need to win a game to appear in the ranking</li>
                            ) : (
                                <li>myRank: {stats.myRank}</li>
                            )}
                        </ul>
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
