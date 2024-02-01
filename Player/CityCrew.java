

public class CityCrew {
    private Player player;
    private Map map;
    private Cell position;

    public CityCrew(Player player,Map map,Cell initialPosition){
        this.player = player;
        this.map = map;
        this.position = initialPosition;
    }
    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell newPosition) {
        this.position = newPosition;
    }


    public void move(Map.Direction direction) throws InvalidMoveException {
        Cell currentCell = this.getPosition();
        Cell newCell = calculateNewCell(currentCell, direction);

        if (isValidCell(newCell)) {
            currentCell.setOccupied(false);
            newCell.setOccupied(true);
            this.setPosition(newCell);
        } else {
            throw new InvalidMoveException("Invalid move. Trying to move out of bounds.");
        }
    }


    private boolean isValidCell(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        return row >= 0 && row < map.getRows() && col >= 0 && col < map.getCols();
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
                return map.getCell(newRow, newCol);
            } else {
                throw new InvalidMoveException("Invalid move. Trying to move out of bounds.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidMoveException("Invalid move. Trying to move out of bounds.");
        }
    }


    // Additional method for checking if a cell is valid by row and col
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < map.getRows() && col >= 0 && col < map.getCols();
    }


    public void Shoot(){

    }

}
