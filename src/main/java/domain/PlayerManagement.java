package domain;

import data.Player;
import exceptions.IDNotFoundException;
import exceptions.IncorrectPasswordException;
import exceptions.NameAlreadyExistsException;
import persistence.GenericSQLManager;
import persistence.PlayerSQLManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlayerManagement {

    private GenericSQLManager<Player> playerSQLManager = new PlayerSQLManager();
    // Player and is Logged In
    private LinkedHashMap<Player, Boolean> players = new LinkedHashMap<>();


    PlayerManagement() throws Exception {
//        createPlayer("TestPlayer", "test", "test", false);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxHALLO");

        loadPlayers();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxx" + players.toString());
    }

    public Player createPlayer(String username, String loginname, String password, boolean isAdmin) throws NameAlreadyExistsException {

        int id = getNextAvailabePlayerID();

        for (Map.Entry<Player, Boolean> entry : players.entrySet()){
            if(entry.getKey().getLoginname().equals(loginname)) throw new NameAlreadyExistsException();
        }

        Player newPlayer = new Player(id, username, loginname, password);

        players.put(newPlayer, false);

        boolean addedToPersistence = false;
        // TODO: Co-Routine sinnvoller? (ggf. SQL gerade nicht verfügbar)
        /*while(!addedToPersistence){
            writeSinglePlayerToPersistence(newPlayer);
        }
*/
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
