import React, { useState, useEffect } from 'react';
import { Navbar, NavbarBrand, NavLink, NavItem, Nav, NavbarText, NavbarToggler, Collapse } from 'reactstrap';
import { Link } from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from "jwt-decode";
import logo from './static/images/dobble_logo.png' 
import friendsLogo from './static/images/friends.png'
import { useNavigate, useLocation } from 'react-router-dom';
import {checkIfUserHasGame } from './MyGame';

function AppNavbar() {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState("");
    const jwt = tokenService.getLocalAccessToken();
    const [collapsed, setCollapsed] = useState(true);
    const user = tokenService.getUser();
    const navigate = useNavigate();
    const [hasGame, setHasGame] = useState(false);
    const [idGame, setIdGame] = useState(null);
    const [idRound, setIdRound] = useState(null);
    const [roundMode, setRoundMode] = useState(null);
    const [status, setStatus] = useState(null);
    const toggleNavbar = () => setCollapsed(!collapsed);
    const location = useLocation();
    const currentPath = location.pathname;

    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
        }
    }, [jwt])

    useEffect(() => {
        async function fetchData() {
            try {
                const jwt = JSON.parse(window.localStorage.getItem('jwt'));

                const gamesMG = await (
                    await fetch(`/api/v1/games/myGame/${user.id}`, {
                        headers: {
                            Authorization: `Bearer ${jwt}`,
                            'Content-Type': 'application/json',
                        },
                    })
                ).json(); 

                const { userHasGame, gameId, statusB, roundB, roundMode } = checkIfUserHasGame(gamesMG, currentPath);
                setHasGame(userHasGame);
                setIdGame(gameId);
                setStatus(statusB);
                setIdRound(roundB);
                setRoundMode(roundMode);
                console.log(roundMode);
            } catch (error) {
                console.error('Error en la función checkIfUserHasGame:', error);
            }
        }
        fetchData();
    }, [currentPath, idGame, status, idRound, roundMode]); 

    async function myGameButton() {
        try {
            if (status === "WAITING" && currentPath !== `/game/quickPlay/${idGame}`) {
                window.location.href = `/game/quickPlay/${idGame}`;
                //navigate(/game/quickPlay/${idGame}); // Usa navigate para cambiar la ruta
            } else if (status === "IN_PROGRESS" && roundMode === "PIT"){
                window.location.href = `/game/quickPlay/${idGame}/${idRound}`;
            } else if (status === "IN_PROGRESS" && roundMode === "INFERNAL_TOWER"){
                window.location.href = `/game/quickPlay/${idGame}/${idRound}/it`;
            }
        } catch (error) {
            console.error('Error en la función gameButton:', error);
        }
    }

    let adminLinks = <></>;
    let ownerLinks = <></>;
    let userLinks = <></>;
    let userLogout = <></>;
    let publicLinks = <></>;
    let friendLogoLink = <></>;

    roles.forEach((role) => {
        if (role === "ADMIN") {
            adminLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/owners">Owners</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/pets">Pets</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/vets">Vets</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/consultations">Consultations</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/clinicOwners">Clinic Owners</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/clinics">Clinics</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/users">Users</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/achievements">Achievements</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/games">Games</NavLink>
                    </NavItem>
                </>
            )
        }
        if (role === "OWNER") {
            ownerLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/myPets">My Pets</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/consultations">Consultations</NavLink>
                    </NavItem>
                    <NavItem>
                    <NavLink style={{ color: "white" }} id="plans" tag={Link} to="/plans">Pricing Plans</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/plan">Plan</NavLink>
                    </NavItem>
                </>
            )
        }
        if (role === "VET") {
            ownerLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/consultations">Consultations</NavLink>
                    </NavItem>
                </>
            )
        }

        if( role === "PLAYER"){
            friendLogoLink = (
                <NavbarBrand tag={Link} to="/friendsList">
                    <img alt="Friends logo" src={friendsLogo} style={{ height: 40, width: 40, marginRight: '10px' }} />
                </NavbarBrand>
            );
            ownerLinks = (
                <>
                <NavItem>
                    <NavLink style={{color: "white"}} tag={Link} to="/game"> New Game</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{color: "white"}} tag={Link} to="/gameRules"> Game Rules</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{color: "white"}} tag={Link} to="/gameHistory"> Game History</NavLink>
                </NavItem>
                {hasGame && (
                    <NavItem>
                        <NavLink style={{color: "white"}} tag={Link} to="#" onClick={() => myGameButton()}> My Game</NavLink>
                    </NavItem>
                )}
                </>
            )
        } 

        if (role === "CLINIC_OWNER") {
            ownerLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/clinics">Clinics</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/owners">Owners</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/consultations">Consultations</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/vets">Vets</NavLink>
                    </NavItem>
                </>
            )
        }
    })

    if (!jwt) {
        publicLinks = (
            <>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="docs" tag={Link} to="/docs">Docs</NavLink>
                </NavItem>
                
                <NavItem>
                    <NavLink style={{ color: "white" }} id="register" tag={Link} to="/register">Register</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="login" tag={Link} to="/login">Login</NavLink>
                </NavItem>
            </>
        )
    } else {
        userLinks = (
            <>
                <NavItem>
                    <NavLink style={{ color: "white" }} tag={Link} to="/dashboard">Dashboard</NavLink>
                </NavItem>
            </>
        )
        userLogout = (
            <>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="docs" tag={Link} to="/docs">Docs</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="profile" tag={Link} to="/profile" className="justify-content-end">{username}</NavLink>
                </NavItem>
                <NavItem className="d-flex">
                    <NavLink style={{ color: "white" }} id="logout" tag={Link} to="/logout">Logout</NavLink>
                </NavItem>
            </>
        )

    }

    return (
        <div>
            <Navbar expand="md" dark color="dark">
                <NavbarBrand href="/">
                    <img alt="logo" src={logo} style={{ height: 40, width: 40, marginRight: 10 }} />
                    Dobble
                </NavbarBrand>
                <NavbarToggler onClick={toggleNavbar} className="ms-2" />
                <Collapse isOpen={!collapsed} navbar>
                    <Nav className="me-auto mb-2 mb-lg-0" navbar>
                        {userLinks}
                        {adminLinks}
                        {ownerLinks}
                    </Nav>
                    <Nav className="ms-auto mb-2 mb-lg-0" navbar>
                        {friendLogoLink}
                        {publicLinks}
                        {userLogout}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default AppNavbar;
