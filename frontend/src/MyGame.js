export function checkIfUserHasGame(games, currentPath) {
    try {
        let userHasGame = false;
        let gameId = null;
        let statusB = null;
        let roundB = null;
        let roundMode = null;

        if(games.length > 0){
            userHasGame = true;
            gameId = games[0].id;
            statusB = games[0].status;
            roundB = games[0].rounds[0].id;
            roundMode = games[0].rounds[0].roundMode;
        }

        const isInGameURL = currentPath.startsWith("/game/quickPlay/");

        if (isInGameURL) {
            userHasGame = false;
        }

        return { userHasGame, gameId, statusB, roundB, roundMode };
    } catch (error) {
        console.error('Error en la función checkIfUserHasGame:', error);
        return { userHasGame: false, gameId: null, statusB: null, roundB: null, roundMode: null };
    }
}
