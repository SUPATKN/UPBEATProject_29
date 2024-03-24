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

//    private void randomizeCellValues() {
//        Random random = new Random();
//        for (int i = 0; i < cells.length; i++) {
//            for (int j = 0; j < cells[i].length; j++) {
//                // Assume values are integers for simplicity
//                int randomValue = random.nextInt(1000001); // Adjust the range as needed
//                cells[i][j].setDeposit(0);
//            }
//        }
//    }

    //    public void printMapValues() {
//        for (int i = 0; i < cells.length; i++) {
//            for (int j = 0; j < cells[i].length; j++) {
//                System.out.printf("(%d, %d): %d\t", i+1, j+1, cells[i][j].getValue());
//            }
//            System.out.println();
//        }
//    }
//    public void printMapPositions() {
//        for (int i = 0; i < cells.length; i++) {
//            for (int j = 0; j < cells[i].length; j++) {
//                System.out.printf("(%d, %d)\t", i+1, j+1);
//            }
//            System.out.println();
//        }
//    }

//    public void checkValue(int row, int col) {
//        if (isValidPosition(row, col)) {
//            System.out.printf("Position: (%d, %d), Value: %d%n", row, col, cells[row-1][col-1].getDeposit());
//        } else {
//            System.out.println("Invalid position.");
//        }
//    }
//
//    private boolean isValidPosition(int row, int col) {
//        return row >= 0 && row < getRows() && col >= 0 && col < getCols();
//    }


    public int getRows() {
        return cells.length;
    }

    public int getCols() {
        return cells[0].length;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
    public Cell[][] getCells() {
        return cells;
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