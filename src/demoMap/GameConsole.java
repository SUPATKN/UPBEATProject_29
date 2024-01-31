package demoMap;

import java.util.Scanner;

public class GameConsole {
    private Game game;

    public GameConsole(int numPlayers) {
        game = new Game(numPlayers);
        displayMap();
        displayPlayerPositions();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter direction (UP_LEFT, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN_LEFT, LEFT): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                Map.Direction direction = Map.Direction.valueOf(input.toUpperCase());
                Player currentPlayer = game.getCurrentPlayer();
                game.move(direction);
                displayMap();
                displayPlayerPositions();
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid direction. Try again.");
            }
        }
    }

    private void displayMap() {
        MapCell[][] cells = game.getMap().getCells();

        System.out.println("\nCurrent turn: Player " + game.getCurrentPlayerNumber());

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print("(" + (i + 1) + "," + (j + 1) + ")");
                if (cells[i][j].isOccupied()) {
                    Player occupyingPlayer = getPlayerAtPosition(i, j);
                    System.out.print("P" + occupyingPlayer.getPlayerNumber());
                } else {
                    System.out.print("-");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }


    private Player getPlayerAtPosition(int row, int col) {
        for (Player player : game.getPlayers()) {
            MapCell position = player.getPosition();
            if (position.getRow() == row && position.getCol() == col) {
                return player;
            }
        }
        return null;
    }


    private void displayPlayerPositions() {
        for (Player player : game.getPlayers()) {
            MapCell position = player.getPosition();
            System.out.println("\nPlayer " + player.getPlayerNumber() + " position: (" +
                    (position.getRow() + 1) + "," + (position.getCol() + 1) + ")");
        }
        System.out.println();
    }

    public static void main(String[] args) {
//        new GameConsole(2);
//        MapCell myCell = new MapCell(1, 2);
//        System.out.println("value cell at (" + myCell.getRow() +","+ myCell.getCol() + ") = "+ myCell.generateRandomOccupiedValue() );

        Map myMap = new Map(3, 3);
        myMap.printMapValues();
//        myMap.printMapPositions();
        myMap.checkValue(1,2);

    }
}
