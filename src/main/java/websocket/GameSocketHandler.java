package websocket;

import data.Game;
import data.GameState;
import data.Player;
import domain.BuzzwordServer;
import exceptions.BuzzwordNotOnGameBoardException;
import exceptions.GameInWrongStateException;
import exceptions.PlayerNotInGameException;
import http.GetHttpSessionConfigurator;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@ApplicationScoped
@ServerEndpoint(value = "/game", configurator = GetHttpSessionConfigurator.class)
public class GameSocketHandler {

    BuzzwordServer gameServer;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        gameServer = (BuzzwordServer) BuzzwordServer.getInstance();

        System.out.println("Neue Verbindung aufgebaut: " + session.getId());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        Player player = gameServer.getUserSession(httpSession);

        gameServer.addGameSession(session, player);
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Verbindung getrennt: " + session.getId());
        gameServer.removeGameSession(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        // Get player who made the input (current player)
        Player currentPlayer = gameServer.getGameSession(session);

        // TODO: Input validation
        /*LinkedList<String> validMessages = new LinkedList<>();
        for(int i = 1; i < 6; i++){
            for(int j = 1; j < 6; j++){
                if(i != 3 && j != 3) validMessages.add(String.valueOf(i) + String.valueOf(j));
            }
        }
        if(validMessages.contains(message));*/

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
//            session.getAsyncRemote().sendText(e.getMessage());
            e.printStackTrace();
        } catch (GameInWrongStateException e) {
            try {
                gameServer.notifyInfoSocketHandlers(currentPlayer, e.getMessage());
            } catch (PlayerNotInGameException e1) {
                e1.printStackTrace();
            }
        }
    }

}
