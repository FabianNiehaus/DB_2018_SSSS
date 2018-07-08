package websocket;

import data.Buzzword;
import data.Game;
import data.GameState;
import data.Player;
import domain.BuzzwordServer;
import exceptions.BuzzwordNotOnGameBoardException;
import exceptions.IDNotFoundException;
import exceptions.PlayerNotInGameException;
import http.GetHttpSessionConfigurator;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
@ServerEndpoint(value = "/actions", configurator = GetHttpSessionConfigurator.class)
public class WebsocketHandler {

    BuzzwordServer gameServer;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        gameServer = (BuzzwordServer) BuzzwordServer.getInstance();

        System.out.println("Neue Verbindung aufgebaut: " + session.getId());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        Player player = gameServer.getUserSession(httpSession);

        gameServer.addGameSession(session, player);
        try {
            session.getAsyncRemote().sendText("Welcome to game " + gameServer.getPlayerGameID(player) + ", " + player.getUsername() + "!");
        } catch (PlayerNotInGameException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Verbindung getrennt: " + session.getId());
        session.getAsyncRemote().sendText("Goodbye " + session.getId());
        gameServer.removeGameSession(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    /*public void handleMessageOLD(String message, Session session) {
        int rowIndex = Integer.parseInt(message.substring(0,0));
        int columnIndex = Integer.parseInt(message.substring(1,1));

        session.getAsyncRemote().sendText(String.valueOf(rowIndex) + String.valueOf(columnIndex));

        for(Map.Entry<Session, Player> entry: gameSessions.entrySet()){
            if(!entry.getKey().equals(session)){
                int rngRowIndex = (int)(Math.random()*5);
                int rngColumnIndex = (int)(Math.random()*5);

                session.getAsyncRemote().sendText(String.valueOf(rngRowIndex) + String.valueOf(rngColumnIndex));
            }
        }
    }*/

    @OnMessage
    public void handleMessage(String message, Session session){
        // Get player who made the input (current player)
        Player currentPlayer = gameServer.getGameSession(session);

        if(message.equals("gameStarted")){
            try {
                Game currentGame = gameServer.getPlayerInGame(currentPlayer);
                if(gameServer.isPlayerAdminInGame(currentGame, currentPlayer)){

                    gameServer.changeGameState(currentGame, GameState.ACTIVE);
                } else {
                    session.getAsyncRemote().sendText("Fehler: Spieler ist kein Admin!");
                }
            } catch (PlayerNotInGameException e) {
                e.printStackTrace();
            }
        }

        // Coordinates of clicked cell in table
        int rowIndex =  Character.getNumericValue(message.charAt(0));
        int columnIndex =  Character.getNumericValue(message.charAt(1));
        int coordinates[] = new int[] {rowIndex, columnIndex};

        try {
            // For all players in the game: set cell with according buzzword as marked and return coordinates of these cells
            LinkedHashMap<Player, int[]> returnValues = gameServer.handlePlayerInput(currentPlayer, coordinates);

            // For every player in the specific game
            for(Map.Entry<Player, int[]> entry : returnValues.entrySet()){
                Player player = entry.getKey();

                // For all sessions
                for(Map.Entry<Session, Player> userSession : gameServer.getGameSessions().entrySet()){
                    // Player has a session!
                    if(userSession.getValue().equals(player)){
                        // Parse coordinates to string
                        int[] sendCoordinates = entry.getValue();
                        String sendText = String.valueOf(sendCoordinates[0]) + String.valueOf(sendCoordinates[1]);

                        // Send coordinates of cell to mark to session as string
                        userSession.getKey().getAsyncRemote().sendText(sendText);
                    }
                }
            }
        } catch (PlayerNotInGameException | BuzzwordNotOnGameBoardException e) {
            // TODO: Exception handling für Player-Inputs
            session.getAsyncRemote().sendText(e.getMessage());
            //e.printStackTrace();
        }
    }


}
