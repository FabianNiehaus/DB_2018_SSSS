package domain;

import data.Game;
import data.GameState;
import data.Player;
import exceptions.BuzzwordNotOnGameBoardException;
import exceptions.IDNotFoundException;
import exceptions.IncorrectPasswordException;
import exceptions.PlayerNotInGameException;
import http.GetHttpSessionConfigurator;

import javax.ejb.Singleton;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

@ApplicationScoped
@ServerEndpoint(value = "/actions", configurator = GetHttpSessionConfigurator.class)
public class BuzzwordServer implements Singleton {

    private static Singleton serverInstance;

    private BuzzwordServer(){
        try {
            playerManagement = new PlayerManagement();
            gameManagement = new GameManagement();
            buzzwordCategoryManagement = new BuzzwordCategoryManagement();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void checkManagementStates(){
        try {
            if(playerManagement == null) playerManagement = new PlayerManagement();
            if(gameManagement == null) gameManagement = new GameManagement();
            if(buzzwordCategoryManagement == null) buzzwordCategoryManagement = new BuzzwordCategoryManagement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Singleton getInstance(){
        if(BuzzwordServer.serverInstance == null){
            BuzzwordServer.serverInstance = new BuzzwordServer();
        }
        return BuzzwordServer.serverInstance;
    }

    private GameManagement gameManagement;
    private PlayerManagement playerManagement;
    private BuzzwordCategoryManagement buzzwordCategoryManagement;

    private static LinkedHashMap<Session, Player> gameSessions = new LinkedHashMap<>();
    private static LinkedHashMap<HttpSession, Player> userSessions = new LinkedHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        checkManagementStates();

        System.out.println("Neue Verbindung aufgebaut: " + session.getId());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        Player player = userSessions.get(httpSession);

        gameSessions.put(session, player);
        session.getAsyncRemote().sendText("Welcome " + player.getUsername());
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Verbindung getrennt: " + session.getId());
        session.getAsyncRemote().sendText("Goodbye " + session.getId());
        gameSessions.remove(session);
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
        Player currentPlayer = gameSessions.get(session);

        if(message.equals("gameStarted")){
            try {
                Game currentGame = gameManagement.getPlayerInGame(currentPlayer);
                if(gameManagement.isPlayerAdminInGame(currentGame, currentPlayer)){

                    gameManagement.changeGameState(currentGame, GameState.ACTIVE);
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
            LinkedHashMap<Player, int[]> returnValues = gameManagement.handlePlayerInput(currentPlayer, coordinates);

            // For every player in the specific game
            for(Map.Entry<Player, int[]> entry : returnValues.entrySet()){
                Player player = entry.getKey();

                // For all sessions
                for(Map.Entry<Session, Player> userSession : gameSessions.entrySet()){
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

    public void createNewGame(int playerID, String buzzwordCategoryName){

        try {
            Player initialPlayer = playerManagement.findPlayerByID(playerID);

            Game game = gameManagement.createGame(initialPlayer, buzzwordCategoryManagement.getBuzzwordCategory(buzzwordCategoryName));

            game.addPlayerToGame(initialPlayer);
            // TODO: Weitere Erstellung eines Spiels
        } catch (IDNotFoundException e){
            // TODO: Was tun, wenn der Player nicht in der Datenbank exisitert?
        }
    }

    private void joinExistingGame(int playerID, int gameID) {
        try {
            Player player = playerManagement.findPlayerByID(playerID);

            Game game = gameManagement.getGame(gameID);

            game.addPlayerToGame(player);

            // TODO: Update GUI
        } catch (IDNotFoundException e){
            // TODO: Was tun, wenn der Player oder das Spiel nicht in der Datenbank exisitert?
        }
    }

    public int playerLogin(String loginName, String loginPassword, HttpSession httpSession){

        checkManagementStates();

        try{
            Player player = playerManagement.playerLogin(loginName, loginPassword);
            userSessions.put(httpSession, player);
            return player.getId();
        } catch (IDNotFoundException | IncorrectPasswordException e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public boolean checkPlayerLoginState(String SessionID, int playerID){

        checkManagementStates();

        try {
            Player player = playerManagement.findPlayerByID(playerID);

            for(Map.Entry<HttpSession, Player> entry : userSessions.entrySet()) {
                if (entry.getKey().getId().equals(SessionID)){
                    return entry.getValue().equals(player) && playerManagement.isPlayerLoggedIn(playerID);
                }
            }
        } catch (IDNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkPlayerInGameState(int playerID){
        try {
            Player player = playerManagement.findPlayerByID(playerID);
            gameManagement.getPlayerInGame(player);
            return true;
        } catch (PlayerNotInGameException e) {
            return false;
        } catch (IDNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPlayerAdminState(int playerID){
        try {
            Player player = playerManagement.findPlayerByID(playerID);
            return player.isAdmin();
        } catch (IDNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LinkedList<String> getBuzzwordCategoryNames () {
        return buzzwordCategoryManagement.getBuzzwordCategorieNames();
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String mappedName() {
        return null;
    }

    @Override
    public String description() {
        return "Der BuzzwordServer!";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
