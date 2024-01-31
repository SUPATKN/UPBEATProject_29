package demoMap;

import java.util.Random;

public class MapCell {
    private int row;
    private int col;
    private boolean isOccupied;
    private int value;

    public MapCell(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void printCellValues() {
        System.out.println("Cell at (" + row + ", " + col + ") is : " );
    }
    protected int generateRandomOccupiedValue() {
        Random random = new Random();
        int randomValue = random.nextInt(1000000);
        return randomValue;
    }
}
