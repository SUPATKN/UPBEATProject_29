package demoMap;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Map gameMap;
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean isPlayer1Turn;

    public Game(int numPlayers) {
        int size = (numPlayers == 1) ? 3 : 5;
        gameMap = new Map(size, size);
        isPlayer1Turn = true;


        players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            MapCell startingCell = gameMap.getRandomEmptyCell();
            Player player = new Player(i, startingCell);
            players.add(player);
            startingCell.setOccupied(true);
        }

        currentPlayerIndex = 0;
    }
    public int getCurrentPlayerNumber() {
        return getCurrentPlayer().getPlayerNumber();
    }

    public Map getMap() {
        return gameMap;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void move(Map.Direction direction) throws InvalidMoveException {
        Player currentPlayer = getCurrentPlayer();
        MapCell currentCell = currentPlayer.getPosition();
        MapCell newCell = calculateNewCell(currentCell, direction);

        if (isValidCell(newCell)) {
            currentCell.setOccupied(false);
            newCell.setOccupied(true);
            currentPlayer.setPosition(newCell);

            currentPlayerIndex = (isPlayer1Turn) ? 1 : 0;
            isPlayer1Turn = !isPlayer1Turn;

            System.out.println("Current Map:");
            gameMap.printMapValues(); // Display the map after each move
        } else {
            throw new InvalidMoveException("Invalid move. Trying to move out of bounds.");
        }
    }


    private boolean isValidCell(MapCell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        return row >= 0 && row < gameMap.getRows() && col >= 0 && col < gameMap.getCols();
    }



    private MapCell calculateNewCell(MapCell currentCell, Map.Direction direction) throws InvalidMoveException {
        int newRow = currentCell.getRow();
        int newCol = currentCell.getCol();

        try {
            if (direction == Map.Direction.UP_LEFT) {
                newRow--;
                newCol--;
            } else if (direction == Map.Direction.UP) {
                newRow--;
            } else if (direction == Map.Direction.UP_RIGHT) {

                newCol++;
            } else if (direction == Map.Direction.DOWN_RIGHT) {
                newCol++;
            } else if (direction == Map.Direction.DOWN) {
                newRow++;
            } else if (direction == Map.Direction.DOWN_LEFT) {
                if (newRow != currentCell.getRow()) {
                    newRow--;
                }
                newCol--;
            }


            if (isValidCell(newRow, newCol)) {
                return gameMap.getCell(newRow, newCol);
            } else {
                throw new InvalidMoveException("Invalid move. Trying to move out of bounds.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidMoveException("Invalid move. Trying to move out of bounds.");
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < gameMap.getRows() && col >= 0 && col < gameMap.getCols();
    }

}
