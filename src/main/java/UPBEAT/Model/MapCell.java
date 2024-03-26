package UPBEAT.Model;

import java.util.Random;

public class MapCell {
    private Cell[][] cells;
    private int max_dep;

    private int Ip;
    public MapCell(int rows, int cols,int max_dep,int interestP) {
        cells = new Cell[rows][cols];
        this.max_dep = max_dep;
        this.Ip = interestP;
        initializeMap();
    }

    private void initializeMap() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j,max_dep,Ip);
                cells[i][j].setDeposit(0);
            }
        }
    }

    public int getRows() {
        return cells.length;
    }

    public int getCols() {
        return cells[0].length;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public Cell getRandomEmptyCell() {
        Random random = new Random();
        Cell randomCell;

        do {
            int randomRow = random.nextInt(getRows());
            int randomCol = random.nextInt(getCols());
            randomCell = getCell(randomRow, randomCol);
        } while (randomCell.isOccupied());

        return randomCell;
    }



}