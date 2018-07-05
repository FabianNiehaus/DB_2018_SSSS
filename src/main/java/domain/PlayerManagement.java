package domain;

import data.Player;
import exceptions.IDNotFoundException;

import java.util.LinkedList;

public class PlayerManagement {

    private LinkedList<Player> players;

    public Player createPlayer(String username, String loginname, String password, boolean isAdmin){

        int id = getNextAvailabePlayerID();

        Player newPlayer = new Player(id, username, loginname, password);

        players.add(newPlayer);

        boolean addedToPersistence = false;
        // TODO: Co-Routine sinnvoller? (ggf. SQL gerade nicht verfügbar)
        while(!addedToPersistence){
            writeSinglePlayerToPersistence(newPlayer);
        }

        return newPlayer;
    }

    public Player getPlayer(int id) throws IDNotFoundException {
        for (Player player: players)
        {
            if(player.getId() == id) return player;
        }

        throw new IDNotFoundException("Player", id);
    }

    private boolean writeSinglePlayerToPersistence(Player player){
        // TODO: Logik für Speicherung eines Spielers in SQL
        return false;
    }

    private int getNextAvailabePlayerID(){
        // TODO: Logik für nächste verfügbare Spieler-ID
        return 0;
    }

    private void setIsAdmin(Player player, boolean isAdmin){
        player.setAdmin(isAdmin, "i3ßfnzr984jf02");
    }

    private void loadPlayers(){
        // TODO: Spieler aus SQL laden, wenn Spieler-Management gestartet wird
    }
}
