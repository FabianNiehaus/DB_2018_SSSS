package domain;

import data.BuzzwordCategory;
import data.Game;
import exceptions.IDNotFoundException;

import java.util.LinkedList;

public class GameManagement {

    private LinkedList<Game> games;

    public Game createGame(BuzzwordCategory buzzwordCategory){

        int id = getNextAvailabeGameID();

        Game newGame = new Game(id, true, buzzwordCategory);

        games.add(newGame);

        boolean addedToPersistence = false;
        // TODO: Co-Routine sinnvoller? (ggf. SQL gerade nicht verfügbar)
        while(!addedToPersistence){
            writeSingleGameToPersistence(newGame);
        }

        return newGame;
    }

    public Game getGame(int id) throws IDNotFoundException{
        for (Game game:games)
        {
            if(game.getId() == id) return game;
        }

        throw new IDNotFoundException("Game", id);
    }

    private boolean writeSingleGameToPersistence(Game game){
        // TODO: Logik für Speicherung eines Spiels in SQL
        return false;
    }

    private int getNextAvailabeGameID(){
        // TODO: Logik für nächste verfügbare Game-ID
        return 0;
    }

    private void loadGames(){
        // TODO: Spieler aus SQL laden, wenn Spieler-Management gestartet wird
    }

}
