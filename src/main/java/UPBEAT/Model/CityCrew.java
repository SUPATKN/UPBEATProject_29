package UPBEAT.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;

public class CityCrew {
    @JsonBackReference
    private final Player player;
    private MapCell mapCell;
    private Cell position;
    @Getter
    private String MyownName;


    public CityCrew(Player player, MapCell mapCell, Cell initialPosition){
        this.player = player;
        this.mapCell = mapCell;
        this.position = initialPosition;
        position.setOccupied(true);
        MyownName = player.getName();
    }
    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell newPosition) {
        this.position = newPosition;
    }


    public void move(String direction) throws InvalidMoveException {
        player.DecreaseBudget(1);
        Cell currentCell = this.getPosition();
        Cell newCell = calculateNewCell(currentCell, direction);

        if (isValidCell(newCell)) {
            if(newCell.getWhoBelong() != player && newCell.getWhoBelong() != null){
                this.setPosition(currentCell);
            }else{
                this.setPosition(newCell);
            }
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
                return new Cell(-1,-1,-1,-1);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return mapCell.getCell(currentCell.getRow(), currentCell.getCol());
        }
    }

    // Additional method for checking if a cell is valid by row and col
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < mapCell.getRows() && col >= 0 && col < mapCell.getCols();
    }

    public void Shoot(String direction, int cost) throws InvalidMoveException {
        int totalCost = cost + 1;  // Calculate total attack cost
        if (player.getBudget() < cost) {
            player.DecreaseBudget(1);
        } else if (player.getBudget() >= totalCost) {  // Check if player has enough budget
            Cell targetCell = calculateNewCell(getPosition(), direction);
            CityCrew target = targetCell.getWhoBelong().getCrew();
            if (isValidCell(targetCell)) {
                if (targetCell.getWhoBelong() != null) {
                    targetCell.getDeposit().DecreaseDeposit(cost);
                    player.DecreaseBudget(totalCost);
                    if (targetCell.getDeposit().getCurrentdep() == 0) {
                        targetCell.getWhoBelong().DecreaseRegion(targetCell);
                        targetCell.setPlayer(null);
                        targetCell.setOccupied(false);
                        if (targetCell.isCityCenter()) {  // Handle loss of city center
                            target.player.loseGame();  // Opponent loses the game
                        }
                    }
                }else{
                    player.DecreaseBudget(totalCost);
                }

            }
        }
    }




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

    public int nearby(String dir) {
        Cell nearestOpponentPosition = null;
        int minDistance = Integer.MAX_VALUE;
        int minDirection = Integer.MAX_VALUE;
            try {
                Cell neighborCell = calculateNewCell(getPosition(), dir);
                int distance = 0;

                // Check if the neighbor cell has a player and calculate distance
                while (isValidCell(neighborCell.getRow(),neighborCell.getCol())) {
                    if (neighborCell.getWhoBelong() != null && !neighborCell.getWhoBelong().equals(this.player)) {
                        distance = calculateDistance(getPosition(), neighborCell);
                        // Update nearest opponent position if closer
                        if (distance < minDistance || (distance == minDistance && getDirectionNumber(dir) < minDirection)) {
                            minDistance = distance;
                            minDirection = getDirectionNumber(dir);
                            nearestOpponentPosition = neighborCell;
                        }
                        break; // Exit the loop after finding the first opponent in this direction
                    }
                    neighborCell = calculateNewCell(neighborCell, dir);
                }
            } catch (InvalidMoveException e) {
                // Handle invalid moves if necessary
                e.printStackTrace();
            }
            int x = minDistance/10;
            int curDep = 0;
            if(nearestOpponentPosition!=null){
                curDep= (int)nearestOpponentPosition.getDeposit().getCurrentdep();
            }
            int y = String.valueOf(curDep).length();

        return nearestOpponentPosition != null ?  (100*x)+y : 0;
    }

    private boolean checkAdjacentCell(){
        String[] directions = {"up", "upright", "downright", "down", "downleft", "upleft"};
        for (String direction : directions) {
            try {
                Cell neighborCell = calculateNewCell(getPosition(), direction);
                int distance = 0;
                if(neighborCell.getWhoBelong() == player){
                    return true;
                }
            } catch (InvalidMoveException e) {
                // Handle invalid moves if necessary
                e.printStackTrace();
            }
        }
        return position.getWhoBelong() == player;
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
        if(colDiff%2 == 0) temp = Math.ceil(temp);
        else temp = Math.floor(temp);
        return 10*(rowDiff + (int)temp);
    }

    public void Invest(int cost){
        if(player.getBudget() < cost){
            player.DecreaseBudget(1);
        }else if((position.getWhoBelong() == null || position.getWhoBelong() == player) && checkAdjacentCell()){
            player.DecreaseBudget(cost+1);
            position.getDeposit().IncreaseDeposit(cost);
            position.setPlayer(player);
            position.setOccupied(true);
            player.IncreaseRegion(position);
        }else{
            player.DecreaseBudget(1);
        }


    }

    public void Collect(int cost){
        player.DecreaseBudget(1);
        if(position.getWhoBelong() == player){
            if((position.getDeposit().getCurrentdep() >= cost)){
                position.getDeposit().DecreaseDeposit(cost);
                player.IncreaseBudget(cost);
                if(position.getDeposit().getCurrentdep() == 0){
                    player.DecreaseRegion(position);
                    position.setPlayer(null);
                    position.setOccupied(false);

                }
            }
        }


    }

    public Player getPlayer(){
        return player;
    }
}
