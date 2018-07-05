package domain;

import data.Game;
import data.Player;
import exceptions.IDNotFoundException;
import exceptions.IncorrectPasswordException;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@ServerEndpoint("/actions")
public class BuzzwordServer {

    private GameManagement gameManagement;
    private PlayerManagement playerManagement;
    private BuzzwordCategoryManagement buzzwordCategoryManagement;


    /*
    public BuzzwordServer() {
        this.gameManagement = new GameManagement();
        this.playerManagement = new PlayerManagement();
        this.buzzwordCategoryManagement = new BuzzwordCategoryManagement();
    }
    */

    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());

    @OnOpen
    public void onOpen(Session session) {

        userSessions.add(session);

        for (Session currentSession : userSessions) {

            currentSession.getAsyncRemote().sendText(session.getId() + " ist dem Spiel beigetreten.");

        }

    }

    @OnClose
    public void onClose(Session session) {

        userSessions.remove(session);

        for (Session currentSession : userSessions) {

            currentSession.getAsyncRemote().sendText(session.getId() + " hat das Spiel verlassen.");

        }

    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void onMessage(String message, Session session){

        broadcast(session, message);
    }

    public static void broadcast(Session session, String msg) {

        for (Session currentSession : userSessions) {

            currentSession.getAsyncRemote().sendText(session.getId() + " hat Feld " + msg + " angeklickt");

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
