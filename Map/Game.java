import java.util.ArrayList;
import java.util.List;

public class Game {
    private Map gameMap;
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean isPlayer1Turn;

    public Game(int numPlayers) {
        int size = (numPlayers == 2) ? 3 : 5;
        gameMap = new Map(size, size);
        isPlayer1Turn = true;

        players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            MapCell startingCell = gameMap.getRandomEmptyCell();
            Player player = new Player(i, startingCell);
            players.add(player);
            startingCell.setOccupied(true);
        }

        // Ensure that currentPlayerIndex is a valid index within the bounds of the players list
        currentPlayerIndex = (players.isEmpty()) ? 0 : currentPlayerIndex % players.size();
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
        if (!players.isEmpty() && currentPlayerIndex >= 0 && currentPlayerIndex < players.size()) {
            return players.get(currentPlayerIndex);
        } else {
            // Handle the situation where the players list is empty or currentPlayerIndex is invalid
            throw new IllegalStateException("No players or invalid currentPlayerIndex.");
        }
    }

    public void move(Map.Direction direction) throws InvalidMoveException {
        Player currentPlayer = getCurrentPlayer();
        MapCell currentCell = currentPlayer.getPosition();
        MapCell newCell = calculateNewCell(currentCell, direction);

        if (isValidCell(newCell)) {
            currentCell.setOccupied(false);
            newCell.setOccupied(true);
            currentPlayer.setPosition(newCell);

            // Increment currentPlayerIndex to switch to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            isPlayer1Turn = !isPlayer1Turn;

            // Uncomment the following line if you want to print the map after each move
            // gameMap.printMapValues();
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
            if (direction == Map.Direction.UP) {
                newRow--;
            } else if (direction == Map.Direction.DOWN) {
                newRow++;
            } else if (direction == Map.Direction.UP_LEFT) {
                newCol--;
                System.out.println(newCol);
                if (newCol % 2 == 0) {
                    newRow--;
                }
            } else if (direction == Map.Direction.UP_RIGHT) {
                newCol++;
                System.out.println(newCol);
                if (newCol % 2 == 0) {
                    newRow--;
                }
            } else if (direction == Map.Direction.DOWN_RIGHT) {
                newCol++;
                System.out.println(newCol);
                if (newCol % 2 != 0) {
                    newRow++;
                }
            } else if (direction == Map.Direction.DOWN_LEFT) {
                newCol--;
                System.out.println(newCol);
                if (newCol % 2 != 0) {
                    newRow++;
                }
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


    // Additional method for checking if a cell is valid by row and col
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < gameMap.getRows() && col >= 0 && col < gameMap.getCols();
    }

}
