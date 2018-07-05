package domain;

import data.Player;
import exceptions.IDNotFoundException;
import exceptions.IncorrectPasswordException;

import java.util.HashMap;

public class PlayerManagement {

    // Player and is Logged In
    private HashMap<Player, Boolean> players;

    public Player createPlayer(String username, String loginname, String password, boolean isAdmin){

        int id = getNextAvailabePlayerID();

        Player newPlayer = new Player(id, username, loginname, password);

        players.put(newPlayer, false);

        boolean addedToPersistence = false;
        // TODO: Co-Routine sinnvoller? (ggf. SQL gerade nicht verfügbar)
        while(!addedToPersistence){
            writeSinglePlayerToPersistence(newPlayer);
        }

        return newPlayer;
    }

    public Player findPlayerByID(int id) throws IDNotFoundException {
        for (HashMap.Entry<Player, Boolean> entry: players.entrySet())
        {
            Player player = entry.getKey();
            if(player.getId() == id) return player;
        }

        throw new IDNotFoundException("Player", id);
    }

    public Player findPlayerByLoginName(String loginName) throws IDNotFoundException {
        for (HashMap.Entry<Player, Boolean> entry: players.entrySet())
        {
            Player player = entry.getKey();
            if(player.getLoginname().equals(loginName)) return player;
        }

        throw new IDNotFoundException("Player", loginName);
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

    public void playerLogin(String loginName, String password) throws IDNotFoundException, IncorrectPasswordException {
        Player playertoLogIn = findPlayerByLoginName(loginName);

        if(password == playertoLogIn.getPassword()){
            players.put(playertoLogIn, true);
        } else {
            throw new IncorrectPasswordException();
        }

    }

    public boolean isPlayerLoggedIn(int playerID) throws IDNotFoundException{
        Player player = findPlayerByID(playerID);
        // Return boolean value for player login state. true = logged in.
        return players.get(player);
    }
}
