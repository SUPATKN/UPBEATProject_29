

public class CityCrew {
    private final Player player;
    private MapCell mapCell;
    private Cell position;


    public CityCrew(Player player, MapCell mapCell, Cell initialPosition){
        this.player = player;
        this.mapCell = mapCell;
        this.position = initialPosition;
        position.setOccupied(true);
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
            this.setPosition(newCell);
        } else {
            this.setPosition(currentCell);
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

                if (newCol % 2 == 0) {
                    newRow--;
                }
            } else if (direction.equals("upright")) {
                newCol++;

                if (newCol % 2 == 0) {
                    newRow--;
                }
            } else if (direction.equals("downright")) {
                newCol++;

                if (newCol % 2 != 0) {
                    newRow++;
                }
            } else if (direction.equals("downleft")) {
                newCol--;

                if (newCol % 2 != 0) {
                    newRow++;
                }
            }

            if (isValidCell(newRow, newCol)) {
                return mapCell.getCell(newRow, newCol);
            } else {
                return new Cell(-1,-1);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return mapCell.getCell(currentCell.getRow(), currentCell.getCol());
        }
    }

    // Additional method for checking if a cell is valid by row and col
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < mapCell.getRows() && col >= 0 && col < mapCell.getCols();
    }

//    public void Shoot(String direction, int expenditure) throws InvalidMoveException {
//        int totalCost = expenditure + 1;  // Calculate total attack cost
//        if (player.getBudget() >= totalCost) {  // Check if player has enough budget
//            Cell targetCell = calculateNewCell(getPosition(), direction);
//
//            if (isValidCell(targetCell)) {
//                CityCrew targetOwner = targetCell.getWhoBelong().getCrew();
//                if (targetOwner != null) {
//                    if (targetOwner.equals(this)) {  // Handle attack on own region
//                        targetCell.setDeposit((int) Math.max(0, targetCell.getDeposit().getCurrentdep() - expenditure));
//                        if (targetCell.getDeposit().getCurrentdep() == 0) {
//                            // Handle loss of region due to self-attack
//                            targetCell.setPlayer(null);
//                            targetCell.setOccupied(false);
//                        }
//                    } else {  // Handle attack on opponent's region
//                        targetOwner.player.DecreaseBudget(expenditure);  // Deduct expenditure from opponent's budget
//                        targetCell.setDeposit((int) Math.max(0, targetCell.getDeposit().getCurrentdep() - expenditure));
//                        if (targetCell.getDeposit().getCurrentdep() == 0) {
//                            // Handle opponent's loss of region
//                            targetCell.setPlayer(null);
//                            targetCell.setOccupied(false);
//
//                            if (targetCell.isCityCenter()) {  // Handle loss of city center
//                                targetOwner.player.loseGame();  // Opponent loses the game
//                            }
//                        }
//                    }
//                } else {
//                    // Player pays the cost, but no effect on the region
//                }
//            }
//
//            player.DecreaseBudget(totalCost);  // Deduct total attack cost from player's budget
//        } else {  // Handle insufficient budget
//            // No-op, attack fails due to lack of budget
//        }
//    }




    public int nearby() {
        Cell nearestOpponentPosition = null;
        int minDistance = Integer.MAX_VALUE;
        int minDirection = Integer.MAX_VALUE;

        // Define the directions to check
        String[] directions = {"up", "upright", "downright", "down", "downleft", "upleft"};

        for (String direction : directions) {
            try {
                Cell neighborCell = calculateNewCell(getPosition(), direction);
                int distance = 0;

                // Check if the neighbor cell has a player and calculate distance
                while (isValidCell(neighborCell.getRow(),neighborCell.getCol())) {
                    if (neighborCell.getWhoBelong() != null && !neighborCell.getWhoBelong().equals(this.player)) {
                        distance = calculateDistance(getPosition(), neighborCell);
                        // Update nearest opponent position if closer
                        if (distance < minDistance || (distance == minDistance && getDirectionNumber(direction) < minDirection)) {
                            minDistance = distance;
                            minDirection = getDirectionNumber(direction);
                            nearestOpponentPosition = neighborCell;
                        }
                        break; // Exit the loop after finding the first opponent in this direction
                    }
                    neighborCell = calculateNewCell(neighborCell, direction);

                }
            } catch (InvalidMoveException e) {
                // Handle invalid moves if necessary
                e.printStackTrace();
            }
        }

        return nearestOpponentPosition != null ?  minDistance+minDirection : 0;
    }

    // Helper method to get the direction number
    private int getDirectionNumber(String direction) {
        switch (direction) {
            case "up":
                return 1;
            case "upright":
                return 2;
            case "downright":
                return 3;
            case "down":
                return 4;
            case "downleft":
                return 5;
            case "upleft":
                return 6;
            default:
                return Integer.MAX_VALUE; // Unknown direction
        }
    }


    // Helper method to calculate the distance between two cells
    private int calculateDistance(Cell cell1, Cell cell2) {
        int rowDiff = Math.abs(cell1.getRow() - cell2.getRow());
        int colDiff = Math.abs(cell1.getCol() - cell2.getCol());
        double temp = (double)(colDiff) / 2;
        if(temp == 0.5) temp = 1;
        else Math.floor(temp);
        return 10*(rowDiff + (int)temp);
    }

    public void Invest(int cost){
        if(player.getBudget() < cost){
            player.InvestCost(0);
        }else{
            player.InvestCost(cost);
            position.InvestDeposit(cost);
            position.setPlayer(player);
            position.setOccupied(true);
        }


    }

    public Player getPlayer(){
        return player;
    }
}
