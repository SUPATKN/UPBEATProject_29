package demoMap;

import java.util.Random;

public class Map {
    private MapCell[][] cells;

    public Map(int rows, int cols) {
        cells = new MapCell[rows][cols];
        initializeMap();
    }

    private void initializeMap() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new MapCell(i, j);
            }
        }
    }

    public int getRows() {
        return cells.length;
    }

    public int getCols() {
        return cells[0].length;
    }

    public MapCell getCell(int row, int col) {
        return cells[row][col];
    }
    public MapCell[][] getCells() {
        return cells;
    }

    public MapCell getRandomEmptyCell() {
        Random random = new Random();
        MapCell randomCell;

        do {
            int randomRow = random.nextInt(getRows());
            int randomCol = random.nextInt(getCols());
            randomCell = getCell(randomRow, randomCol);
        } while (randomCell.isOccupied());

        return randomCell;
    }

    public enum Direction {
        UP_LEFT,
        UP,
        UP_RIGHT,
        RIGHT,
        DOWN_RIGHT,
        DOWN,
        DOWN_LEFT,
        LEFT
    }

}
