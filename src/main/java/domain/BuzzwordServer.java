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
import java.util.LinkedHashMap;
import java.util.LinkedList;

@ApplicationScoped
@ServerEndpoint("/actions")
public class BuzzwordServer {

    private GameManagement gameManagement;
    private PlayerManagement playerManagement;
    private BuzzwordCategoryManagement buzzwordCategoryManagement;

    public BuzzwordServer() {
        this.gameManagement = new GameManagement();
        this.playerManagement = new PlayerManagement();
        this.buzzwordCategoryManagement = new BuzzwordCategoryManagement();
    }

    /*public static void main(String[] args){
        BuzzwordServer buzzwordServer = new BuzzwordServer();
    }*/

    LinkedHashMap<Session, Player> userSessions = new LinkedHashMap<>();

    @OnOpen
    public void open(Session session) {
        System.out.println("Neue Verbindung aufgebaut: " + session.getId());
        userSessions.put(session, null);
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Verbindung getrennt: " + session.getId());
        userSessions.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {


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
