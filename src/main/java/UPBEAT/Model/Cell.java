package UPBEAT.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;

import java.util.Random;

public class Cell {
    @JsonBackReference
    private Player whoBelong;
    @Getter
    private String whoBelongName;
    private boolean isCityCenter = false;
    private int max_dep;
    private int row;
    private int col;
    private boolean isOccupied;
    private int Ip;
    private Deposit deposit;
    public Cell(int row, int col,int max_dep,int Ip) {
        this.row = row;
        this.col = col;
        this.max_dep = max_dep;
        this.Ip = Ip;
    }

    public Player getWhoBelong(){
        return whoBelong;
    }

    public void SetCityCenter(boolean set){
        isCityCenter = set;
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
        this.deposit = new Deposit(value,max_dep,Ip);
    }

    public void setPlayer(Player player){
        this.whoBelong = player;
        if(whoBelong == null){
            whoBelongName = "";
        }else{
            whoBelongName = whoBelong.getName();
        }
    }

}