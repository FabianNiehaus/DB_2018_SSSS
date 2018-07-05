package domain;

import data.Game;
import data.Player;
import exceptions.IDNotFoundException;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ServerEndpoint("/actions")
public class BuzzwordServer {

    private GameManagement gameManagement;
    private PlayerManagement playerManagement;
    private BuzzwordCategoryManagement buzzwordCategoryManagement;

    @Inject
    private SessionHandler sessionHandler;

    public BuzzwordServer() {
        this.gameManagement = new GameManagement();
        this.playerManagement = new PlayerManagement();
        this.buzzwordCategoryManagement = new BuzzwordCategoryManagement();
    }

    public static void main(String[] args){
        BuzzwordServer buzzwordServer = new BuzzwordServer();
    }

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        sessionHandler.handleMessage(message, session);
    }

    private void startNewGame(int playerID, String buzzwordCategoryName){

        try {
            Player initialPlayer = playerManagement.getPlayer(playerID);

            Game game = gameManagement.createGame(buzzwordCategoryManagement.getBuzzwordCategory(buzzwordCategoryName));
            // TODO: Weitere Erstellung eines Spiels
        } catch (IDNotFoundException e){
            // TODO: Was tun, wenn der Player nicht in der Datenbank exisitert?
        }
    }

}
