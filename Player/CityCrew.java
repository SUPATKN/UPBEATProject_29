

public class CityCrew {
    private final Player player;
    private MapCell mapCell;
    private Cell position;


    public CityCrew(Player player, MapCell mapCell, Cell initialPosition){
        this.player = player;
        this.mapCell = mapCell;
        this.position = initialPosition;
    }
    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell newPosition) {
        this.position = newPosition;
    }


    public void move(String direction) throws InvalidMoveException {
        player.MoveCost();
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
        return row >= 0 && row < mapCell.getRows() && col >= 0 && col < mapCell.getCols();
    }


    private Cell calculateNewCell(Cell currentCell, String direction) throws InvalidMoveException {
        int newRow = currentCell.getRow();
        int newCol = currentCell.getCol();

        try {
            if (direction.equals("up")) {
                newRow--;
            } else if (direction.equals("down")) {
                newRow++;
            } else if (direction.equals("upleft")) {
                newCol--;
                System.out.println(newCol);
                if (newCol % 2 == 0) {
                    newRow--;
                }
            } else if (direction.equals("upright")) {
                newCol++;
                System.out.println(newCol);
                if (newCol % 2 == 0) {
                    newRow--;
                }
            } else if (direction.equals("downright")) {
                newCol++;
                System.out.println(newCol);
                if (newCol % 2 != 0) {
                    newRow++;
                }
            } else if (direction.equals("downleft")) {
                newCol--;
                System.out.println(newCol);
                if (newCol % 2 != 0) {
                    newRow++;
                }
            }

            if (isValidCell(newRow, newCol)) {
                return mapCell.getCell(newRow, newCol);
            } else {
                return mapCell.getCell(currentCell.getRow(), currentCell.getCol());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return mapCell.getCell(currentCell.getRow(), currentCell.getCol());
        }
    }


    // Additional method for checking if a cell is valid by row and col
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < mapCell.getRows() && col >= 0 && col < mapCell.getCols();
    }


    public void Shoot(){

    }

    public void Invest(int cost){
        player.InvestCost(cost);
        position.InvestDeposit(cost);
        position.setPlayer(player);

    }

    public Player getPlayer(){
        return player;
    }
}
