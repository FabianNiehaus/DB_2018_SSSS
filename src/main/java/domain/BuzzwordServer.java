package domain;

import data.Game;
import data.Player;
import exceptions.BuzzwordNotOnGameBoardException;
import exceptions.IDNotFoundException;
import exceptions.IncorrectPasswordException;
import exceptions.PlayerNotInGameException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@ServerEndpoint("/actions")
public class BuzzwordServer {

    private GameManagement gameManagement;
    private PlayerManagement playerManagement;
    private BuzzwordCategoryManagement buzzwordCategoryManagement;

    private static LinkedHashMap<Session, Player> userSessions = new LinkedHashMap<>();

    private int playerIncrement = 0;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Neue Verbindung aufgebaut: " + session.getId());
        userSessions.put(session, new Player(playerIncrement, "Spieler" + String.valueOf(playerIncrement), "ABC", "CDE"));
        session.getAsyncRemote().sendText("Welcome ");
        playerIncrement++;
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Verbindung getrennt: " + session.getId());
        session.getAsyncRemote().sendText("Goodbye " + session.getId());
        userSessions.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        int rowIndex = Integer.parseInt(message.substring(0,0));
        int columnIndex = Integer.parseInt(message.substring(1,1));

        session.getAsyncRemote().sendText(String.valueOf(rowIndex) + String.valueOf(columnIndex));

        for(Map.Entry<Session, Player> entry: userSessions.entrySet()){
            if(!entry.getKey().equals(session)){
                int rngRowIndex = (int)(Math.random()*5);
                int rngColumnIndex = (int)(Math.random()*5);

                session.getAsyncRemote().sendText(String.valueOf(rngRowIndex) + String.valueOf(rngColumnIndex));
            }
        }
    }

    public void handleMessageNew(String message, Session session){
        // Get player who made the input (current player)
        Player currentPlayer = userSessions.get(session);

        // Coordinates of clicked cell in table
        int rowIndex = Integer.parseInt(message.substring(0,0));
        int columnIndex = Integer.parseInt(message.substring(1,1));
        int coordinates[] = new int[] {rowIndex, columnIndex};

        try {
            // For all players in the game: set cell with according buzzword as marked and return coordinates of these cells
            LinkedHashMap<Player, int[]> returnValues = gameManagement.handlePlayerInput(currentPlayer, coordinates);

            // For every player in the specific game
            for(Map.Entry<Player, int[]> entry : returnValues.entrySet()){
                Player player = entry.getKey();

                // For all sessions
                for(Map.Entry<Session, Player> userSession : userSessions.entrySet()){
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

    private void startNewGame(int playerID, String buzzwordCategoryName){

        try {
            Player initialPlayer = playerManagement.findPlayerByID(playerID);

            Game game = gameManagement.createGame(buzzwordCategoryManagement.getBuzzwordCategory(buzzwordCategoryName));
            // TODO: Weitere Erstellung eines Spiels
        } catch (IDNotFoundException e){
            // TODO: Was tun, wenn der Player nicht in der Datenbank exisitert?
        }
    }

    private void joinGame(int playerID, int gameID) {
        // TODO: Soll das joinen jederzeit gehen? Oder soll das Game einen State "InProgress" haben, bei dem joinen nicht mehr möglich ist, bzw die Felder zufällig verteilt werden?
        try {
            Player player = playerManagement.findPlayerByID(playerID);

            Game game = gameManagement.getGame(gameID);

            game.addPlayerToGame(player);

            // TODO: Update GUI
        } catch (IDNotFoundException e){
            // TODO: Was tun, wenn der Player oder das Spiel nicht in der Datenbank exisitert?
        }
    }

    private void changeGameState(int gameID, int playerID) {

    }

    public void playerLogin(String loginName, String loginPassword){
        try{
            playerManagement.playerLogin(loginName, loginPassword);
        } catch (IDNotFoundException | IncorrectPasswordException e){
            // TODO: Login failed
        }
    }

}
