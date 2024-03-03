package UPBEAT.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Random;

public class Cell {
    @JsonBackReference
    private Player whoBelong;
    private boolean isCityCenter = false;
    private int max_dep;
    private int row;
    private int col;
    private boolean isOccupied;
    private Deposit deposit;
    public Cell(int row, int col,int max_dep) {
        this.row = row;
        this.col = col;
        this.max_dep = max_dep;
    }

    public Player getWhoBelong(){
        return whoBelong;
    }

    public void SetCityCenter(){
        isCityCenter = true;
    }

    public boolean isCityCenter(){
        return isCityCenter;
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
    public Deposit getDeposit(){
        return deposit;
    }
    public void setDeposit(int value){
        this.deposit = new Deposit(value,max_dep);
    }

    public void setPlayer(Player player){
        this.whoBelong = player;
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