import java.util.ArrayList;
import java.util.List;

public class Game {
    private Map gameMap;
    private List<PlayerForMap> playerForMaps;
    private int currentPlayerIndex;
    private boolean isPlayer1Turn;

    public Game(int numPlayers) {
        int size = (numPlayers == 2) ? 3 : 5;
        gameMap = new Map(size, size);
        isPlayer1Turn = true;

        playerForMaps = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            Cell startingCell = gameMap.getRandomEmptyCell();
            PlayerForMap playerForMap = new PlayerForMap(i, startingCell);
            playerForMaps.add(playerForMap);
            startingCell.setOccupied(true);
        }

        // Ensure that currentPlayerIndex is a valid index within the bounds of the players list
        currentPlayerIndex = (playerForMaps.isEmpty()) ? 0 : currentPlayerIndex % playerForMaps.size();
    }

    public int getCurrentPlayerNumber() {
        return getCurrentPlayer().getPlayerNumber();
    }

    public Map getMap() {
        return gameMap;
    }

    public List<PlayerForMap> getPlayers() {
        return playerForMaps;
    }

    public PlayerForMap getCurrentPlayer() {
        if (!playerForMaps.isEmpty() && currentPlayerIndex >= 0 && currentPlayerIndex < playerForMaps.size()) {
            return playerForMaps.get(currentPlayerIndex);
        } else {
            // Handle the situation where the players list is empty or currentPlayerIndex is invalid
            throw new IllegalStateException("No players or invalid currentPlayerIndex.");
        }
    }

    public void move(Map.Direction direction) throws InvalidMoveException {
        PlayerForMap currentPlayerForMap = getCurrentPlayer();
        Cell currentCell = currentPlayerForMap.getPosition();
        Cell newCell = calculateNewCell(currentCell, direction);

        if (isValidCell(newCell)) {
            currentCell.setOccupied(false);
            newCell.setOccupied(true);
            currentPlayerForMap.setPosition(newCell);

            // Increment currentPlayerIndex to switch to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % playerForMaps.size();
            isPlayer1Turn = !isPlayer1Turn;

            // Uncomment the following line if you want to print the map after each move
            // gameMap.printMapValues();
        } else {
            throw new InvalidMoveException("Invalid move. Trying to move out of bounds.");
        }
    }


    private boolean isValidCell(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        return row >= 0 && row < gameMap.getRows() && col >= 0 && col < gameMap.getCols();
    }


    private Cell calculateNewCell(Cell currentCell, Map.Direction direction) throws InvalidMoveException {
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
