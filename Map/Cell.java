import java.util.Random;

public class Cell {
    private Player whoBelong;
    private int row;
    private int col;
    private boolean isOccupied;
    private Deposit deposit;
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
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
        this.deposit = new Deposit(value);
    }

    public void InvestDeposit(int cost){
        this.deposit.InvestDeposit(cost);
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