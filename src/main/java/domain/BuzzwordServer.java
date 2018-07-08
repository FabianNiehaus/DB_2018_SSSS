package domain;

import data.Game;
import data.GameState;
import data.Player;
import exceptions.BuzzwordNotOnGameBoardException;
import exceptions.IDNotFoundException;
import exceptions.IncorrectPasswordException;
import exceptions.PlayerNotInGameException;

import javax.ejb.Singleton;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


public class BuzzwordServer implements Singleton {

    private static Singleton serverInstance;

    /*public static void main(String[] args){
        serverInstance = new BuzzwordServer();
    }
*/
    private BuzzwordServer(){
        try {
            playerManagement = new PlayerManagement();
            gameManagement = new GameManagement();
            buzzwordCategoryManagement = new BuzzwordCategoryManagement();
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

    public void createNewGame(int playerID, String buzzwordCategoryName){

        try {
            Player initialPlayer = playerManagement.findPlayerByID(playerID);

            Game game = gameManagement.createGame(initialPlayer, buzzwordCategoryManagement.getBuzzwordCategory(buzzwordCategoryName));

            game.addPlayerToGame(initialPlayer);
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

    public Game checkPlayerInGameState(int playerID){
        try {
            Player player = playerManagement.findPlayerByID(playerID);
            return gameManagement.getPlayerInGame(player);
        } catch (PlayerNotInGameException e) {
            return null;
        } catch (IDNotFoundException e) {
            e.printStackTrace();
            return null;
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

    public Player findPlayerByID(int playerID){
        try {
            return playerManagement.findPlayerByID(playerID);
        } catch (IDNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LinkedList<String> getBuzzwordCategoryNames () {
        return buzzwordCategoryManagement.getBuzzwordCategorieNames();
    }

    public Player getUserSession(HttpSession httpSession){
        return userSessions.get(httpSession);
    }

    public void addGameSession(Session session, Player player){
        gameSessions.put(session, player);
    }

    public void removeGameSession(Session session){
        gameSessions.remove(session);
    }

    public Player getGameSession(Session session){
        return gameSessions.get(session);
    }

    public Game getPlayerInGame(Player player) throws PlayerNotInGameException {
        return gameManagement.getPlayerInGame(player);
    }

    public boolean isPlayerAdminInGame(Game game, Player player){
        return gameManagement.isPlayerAdminInGame(game, player);
    }

    public void changeGameState(Game game, GameState gameState){
        gameManagement.changeGameState(game, gameState);
    }

    public LinkedHashMap<Player, int[]> handlePlayerInput(Player player, int[] coordinates) throws BuzzwordNotOnGameBoardException, PlayerNotInGameException {
        return gameManagement.handlePlayerInput(player, coordinates);
    }

    public LinkedHashMap<Session, Player> getGameSessions(){
        return gameSessions;
    }

    public int getPlayerGameID(Player player) throws PlayerNotInGameException {
        return gameManagement.getPlayerInGame(player).getId();
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
