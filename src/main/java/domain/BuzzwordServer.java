package domain;

import data.Game;
import data.GameState;
import data.Player;
import exceptions.*;
import websocket.InfoSocketHandler;

import javax.ejb.Singleton;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


public class BuzzwordServer implements Singleton {

    private static Singleton serverInstance;
    private GameManagement gameManagement;
    private PlayerManagement playerManagement;
    private BuzzwordCategoryManagement buzzwordCategoryManagement;
    private LinkedHashMap<Session, Player> gameSessions = new LinkedHashMap<>();
    private LinkedHashMap<Session, Player> infoSessions = new LinkedHashMap<>();
    private LinkedHashMap<HttpSession, Player> userSessions = new LinkedHashMap<>();
    private LinkedHashMap<Session, InfoSocketHandler> infoSocketHandlers = new LinkedHashMap<>();

    public void notifyInfoSocketHandlers (Player initialPlayer, String message) throws PlayerNotInGameException {
        Game game = gameManagement.getPlayerInGame(initialPlayer);

        LinkedList<Session> playerSessionsToNotify = new LinkedList<>();
        LinkedList<Player> playersInGame = new LinkedList<>(game.getGamePlayersAndBoards().keySet());
        for (Player player: playersInGame) {
            for(Map.Entry<Session, Player> entry: infoSessions.entrySet()){
                if(entry.getValue().equals(player)) playerSessionsToNotify.add(entry.getKey());
            }
        }

        for (Session session: playerSessionsToNotify){
            infoSocketHandlers.get(session).handleMessage(message, session);
        }
    }


    private BuzzwordServer() {
        try {
            playerManagement = new PlayerManagement();
            gameManagement = new GameManagement();
            buzzwordCategoryManagement = new BuzzwordCategoryManagement();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static Singleton getInstance() {
        if (BuzzwordServer.serverInstance == null) {
            BuzzwordServer.serverInstance = new BuzzwordServer();
        }
        return BuzzwordServer.serverInstance;
    }

    public LinkedHashMap<Session, InfoSocketHandler> getInfoSocketHandlers() {
        return infoSocketHandlers;
    }

    public void addInfoSocketHandler(Session session ,InfoSocketHandler infoSocketHandler) {
        infoSocketHandlers.put(session, infoSocketHandler);
    }

    public void removeInfoSocketHandler(Session session) {
        infoSocketHandlers.remove(session);
    }

    public void createNewGame(int playerID, String buzzwordCategoryName) {

        try {
            Player initialPlayer = playerManagement.findPlayerByID(playerID);

            Game game = gameManagement.createGame(initialPlayer, buzzwordCategoryManagement.getBuzzwordCategory(buzzwordCategoryName));

            game.addPlayerToGame(initialPlayer);
        } catch (IDNotFoundException e) {
            // TODO: Was tun, wenn der Player nicht in der Datenbank exisitert?
        }
    }

    public void joinExistingGame(int playerID, int gameID) throws IDNotFoundException, GameInWrongStateException {

        Player player = playerManagement.findPlayerByID(playerID);

        Game game = gameManagement.getGame(gameID);


        if (game.getGameState() == GameState.OPEN) {
            game.addPlayerToGame(player);
        } else throw new GameInWrongStateException(game.getGameState());

        try {
            notifyInfoSocketHandlers(player, player.getUsername() + " ist dem Spiel beigetreten!");
        } catch (PlayerNotInGameException e) {
            e.printStackTrace();
        }

    }

    public int playerLogin(String loginName, String loginPassword, HttpSession httpSession) {


        try {
            Player player = playerManagement.playerLogin(loginName, loginPassword);
            userSessions.put(httpSession, player);
            return player.getId();
        } catch (IDNotFoundException | IncorrectPasswordException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public boolean checkPlayerLoginState(String SessionID, int playerID) {

        try {
            Player player = playerManagement.findPlayerByID(playerID);

            for (Map.Entry<HttpSession, Player> entry : userSessions.entrySet()) {
                if (entry.getKey().getId().equals(SessionID)) {
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

    public Game checkPlayerInGameState(int playerID) {
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

    public boolean checkPlayerAdminState(int playerID) {
        try {
            Player player = playerManagement.findPlayerByID(playerID);
            return player.isAdmin();
        } catch (IDNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Player findPlayerByID(int playerID) {
        try {
            return playerManagement.findPlayerByID(playerID);
        } catch (IDNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LinkedList<String> getBuzzwordCategoryNames() {
        return buzzwordCategoryManagement.getBuzzwordCategorieNames();
    }

    public Player getUserSession(HttpSession httpSession) {
        return userSessions.get(httpSession);
    }

    public void addGameSession(Session session, Player player) {
        for (Map.Entry<Session, Player> entry : gameSessions.entrySet()) {
            if (entry.getValue().equals(player)) gameSessions.remove(entry.getKey());
        }
        gameSessions.put(session, player);
    }

    public void removeGameSession(Session session) {
        gameSessions.remove(session);
    }

    public Player getGameSession(Session session) {
        return gameSessions.get(session);
    }

    public void addInfoSession(Session session, Player player) {
        for (Map.Entry<Session, Player> entry : infoSessions.entrySet()) {
            if (entry.getValue().equals(player)) infoSessions.remove(entry.getKey());
        }
        infoSessions.put(session, player);
    }

    public void removeInfoSession(Session session) {
        infoSessions.remove(session);
    }

    public Player getInfoSession(Session session) {
        return infoSessions.get(session);
    }

    public Game getPlayerInGame(Player player) throws PlayerNotInGameException {
        return gameManagement.getPlayerInGame(player);
    }

    public boolean isPlayerAdminInGame(Game game, Player player) {
        return gameManagement.isPlayerAdminInGame(game, player);
    }

    public void changeGameState(Game game, GameState gameState) {
        gameManagement.changeGameState(game, gameState);
    }

    public LinkedHashMap<Player, int[]> handlePlayerInput(Player player, int[] coordinates) throws BuzzwordNotOnGameBoardException, PlayerNotInGameException, GameInWrongStateException {
        return gameManagement.handlePlayerInput(player, coordinates);
    }

    public LinkedHashMap<Session, Player> getGameSessions() {
        return gameSessions;
    }

    public int getPlayerGameID(Player player) throws PlayerNotInGameException {
        return gameManagement.getPlayerInGame(player).getId();
    }

    public void startGame() {

    }

    public Player registerNewPlayer(String newUsername, String newPassword) throws InvalidCharacterException, NameAlreadyExistsException {

        String pattern = "[A-Za-z][A-Za-z0-9]*";

        if (newUsername.matches(pattern)) {
            return playerManagement.createPlayer(newUsername, newUsername, newPassword, false);
        } else throw new InvalidCharacterException();

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
