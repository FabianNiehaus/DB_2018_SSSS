package domain;

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

    @Inject
    private SessionHandler sessionHandler;

    public BuzzwordServer() {
        this.gameManagement = new GameManagement();
        this.playerManagement = new PlayerManagement();
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


}
