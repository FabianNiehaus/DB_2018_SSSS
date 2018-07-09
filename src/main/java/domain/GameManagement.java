package domain;

import data.*;
import exceptions.BuzzwordNotOnGameBoardException;
import exceptions.GameInWrongStateException;
import exceptions.IDNotFoundException;
import exceptions.PlayerNotInGameException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class GameManagement {

    private LinkedList<Game> games = new LinkedList<>();

    public Game createGame(Player admin, BuzzwordCategory buzzwordCategory){

        int id = getNextAvailabeGameID();

        Game newGame = new Game(admin, id, buzzwordCategory);

        games.add(newGame);

        boolean addedToPersistence = false;
        // TODO: Co-Routine sinnvoller? (ggf. SQL gerade nicht verfügbar)
        /*while(!addedToPersistence){
            writeSingleGameToPersistence(newGame);
        }*/
        return newGame;
    }

    public Game getGame(int id) throws IDNotFoundException{
        for (Game game:games)
        {
            if(game.getId() == id) return game;
        }

        throw new IDNotFoundException("Game", id);
    }

    public LinkedHashMap<Player, int[]> handlePlayerInput(Player player, int[] coordinates) throws PlayerNotInGameException, BuzzwordNotOnGameBoardException, GameInWrongStateException {
        Game game = null;
        LinkedHashMap<Player, GameBoard> currentGamePlayersAndBoards = null;
        LinkedHashMap<Player, int[]> coordinatesAndPlayers = new LinkedHashMap<>();

        // Get game participants and boards for current player
        for(Game currentGame: games){
            LinkedHashMap<Player, GameBoard> currentGameToCheck = currentGame.getGamePlayersAndBoards();
            if(currentGameToCheck.containsKey(player)){
                currentGamePlayersAndBoards = currentGameToCheck;
                game = currentGame;
            }
        }

        // Fail if player is not in a game
        if(currentGamePlayersAndBoards == null){
            throw new PlayerNotInGameException(player.getId());
        }

        if(game.getGameState() == GameState.ACTIVE){

            LinkedList<Player> winners = new LinkedList<>();

            // Get game board of current player
            GameBoard currentPlayerBoard = currentGamePlayersAndBoards.get(player);

            // Set cell at coordinates as marked and get Buzzword at marked coordinates
            Buzzword buzzword = currentPlayerBoard.setSingleCellMarked(coordinates);

            // Add player and coordinates to return map
            coordinatesAndPlayers.put(player, coordinates);

            // Check if player has won game
            if(currentPlayerBoard.checkWinState()){
                winners.add(player);
            }

            // Circle through all other players in current game
            for(Map.Entry<Player, GameBoard> entry :currentGamePlayersAndBoards.entrySet()){
                // Make sure to not add the current player twice
                if(!entry.getKey().equals(player)){
                    // Get game board of other player
                    GameBoard gameBoard = entry.getValue();
                    Player currentPlayer = entry.getKey();

                    // Get coordinates of the buzzword on the players board
                    int[] buzzwordPosition = gameBoard.getBuzzwordPosition(buzzword);
                    // Set cell at coordinates as marked
                    gameBoard.setSingleCellMarked(buzzwordPosition);
                    // Add player and coordinates to return map
                    coordinatesAndPlayers.put(currentPlayer, buzzwordPosition);

                    // Check if player has won game
                    if(gameBoard.checkWinState()){
                        winners.add(currentPlayer);
                    }
                }
            }

            if(winners.size() > 0){
                game.setGameState(GameState.FINISHED);
                game.setWinners(winners);
            }
            return coordinatesAndPlayers;
        }

        throw new GameInWrongStateException(game.getGameState(), GameState.ACTIVE);
    }

    public void changeGameState(Game game, GameState gameState) {
        game.setGameState(gameState);
    }

    public Game getPlayerInGame(Player player) throws PlayerNotInGameException {
        // Get game participants and boards for current player
        for(Game game: games){
            LinkedHashMap<Player, GameBoard> currentGameToCheck = game.getGamePlayersAndBoards();
            if(currentGameToCheck.containsKey(player)){
                return game;
            }
        }

        throw new PlayerNotInGameException(player.getId());

    }

    public boolean isPlayerAdminInGame(Game game, Player player){
        return game.getAdmin() == player;
    }

    private boolean writeSingleGameToPersistence(Game game){
        // TODO: Logik für Speicherung eines Spiels in SQL
        return false;
    }

    private int getNextAvailabeGameID(){
        int highestID = 0;
        for(Game game : games){
            if(game.getId() > highestID) highestID = game.getId();
        }
        return highestID + 1;
    }

    private void loadGames(){
        // TODO: Spieler aus SQL laden, wenn Spieler-Management gestartet wird
    }

}
