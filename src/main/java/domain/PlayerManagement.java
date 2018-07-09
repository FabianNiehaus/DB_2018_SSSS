package domain;

import data.Player;
import exceptions.IDNotFoundException;
import exceptions.IncorrectPasswordException;
import exceptions.NameAlreadyExistsException;
import persistence.GenericSQLManager;
import persistence.PlayerSQLManager;

import java.sql.SQLException;
import java.util.*;

public class PlayerManagement {

    private GenericSQLManager<Player> playerSQLManager = new PlayerSQLManager();
    // Player and is Logged In
    private LinkedHashMap<Player, Boolean> players = new LinkedHashMap<>();


    PlayerManagement() throws Exception {
//        createPlayer("TestPlayer", "test", "test", false);

        loadPlayers();
        //Debug Ausgabe
        Set<Player> playerSet = players.keySet();
        System.out.println("Spieler: \n");
        for(Player p:playerSet) {
            System.out.println(p.toString() + "\n");
        }
    }

    public Player createPlayer(String username, String loginname, String password) throws NameAlreadyExistsException {

        int id = getNextAvailabePlayerID();

        for (Map.Entry<Player, Boolean> entry : players.entrySet()){
            if(entry.getKey().getLoginname().equals(loginname)) throw new NameAlreadyExistsException();
        }

        Player newPlayer = new Player(id, username, loginname, password);

        players.put(newPlayer, false);

        try {
            playerSQLManager.create(newPlayer);
        } catch (SQLException e) {
            e.printStackTrace();
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


    private int getNextAvailabePlayerID(){
        int maxID = 0;

        for(Map.Entry<Player, Boolean> entry: players.entrySet()){
            if(entry.getKey().getId() > maxID){
                maxID = entry.getKey().getId();
            }
        }

        return maxID;
    }

    private void setIsAdmin(Player player, boolean isAdmin){
        player.setAdmin(isAdmin);
    }

    private  void loadPlayers() throws Exception {
        List<Player> playerlist = playerSQLManager.readAll();
        for(Player p : playerlist){
            players.put(p,false);
        }
    }

    public Player playerLogin(String loginName, String password) throws IDNotFoundException, IncorrectPasswordException {
        Player playertoLogIn = findPlayerByLoginName(loginName);

        if(password.equals(playertoLogIn.getPassword())){
            players.put(playertoLogIn, true);
            return playertoLogIn;
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
