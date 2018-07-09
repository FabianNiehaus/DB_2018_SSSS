package websocket;

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
import java.util.Map;

@ApplicationScoped
@ServerEndpoint(value = "/info", configurator = GetHttpSessionConfigurator.class)
public class InfoSocketHandler {

    BuzzwordServer gameServer;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        gameServer = (BuzzwordServer) BuzzwordServer.getInstance();

        gameServer.addInfoSocketHandler(session, this);

        System.out.println("Neue Verbindung aufgebaut: " + session.getId());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        Player player = gameServer.getUserSession(httpSession);

        gameServer.addInfoSession(session, player);

        session.getAsyncRemote().sendText("Willkommen " + player.getUsername());

    }

    @OnClose
    public void close(Session session) {
        session.getAsyncRemote().sendText("Sitzung getrennt");
        System.out.println("Verbindung getrennt: " + session.getId());
        gameServer.removeGameSession(session);
        gameServer.removeInfoSocketHandler(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session){
        if(session.isOpen()){
            session.getAsyncRemote().sendText(message);
        }
    }


}
