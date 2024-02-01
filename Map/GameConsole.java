
import java.util.Scanner;

public class GameConsole {
    private Game game;


    public GameConsole(int numPlayers) {
        game = new Game(numPlayers);
        displayMap();
        displayPlayerPositions();


        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter direction (UP, UP_LEFT, UP_RIGHT, DOWN, DOWN_RIGHT, DOWN_LEFT): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                Map.Direction direction = Map.Direction.valueOf(input.toUpperCase());
                PlayerForMap currentPlayer = game.getCurrentPlayer();
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
        Cell[][] cells = game.getMap().getCells();

        System.out.println("\nCurrent turn: Player " + game.getCurrentPlayerNumber());

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print("(" + (i + 1) + "," + (j + 1) + ")");
                if (cells[i][j].isOccupied()) {
                    PlayerForMap occupyingPlayer = getPlayerAtPosition(i, j);
                    System.out.print("P" + occupyingPlayer.getPlayerNumber());
                } else {
                    System.out.print("-");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }


    private PlayerForMap getPlayerAtPosition(int row, int col) {
        for (PlayerForMap player : game.getPlayers()) {
            Cell position = player.getPosition();
            if (position.getRow() == row && position.getCol() == col) {
                return player;
            }
        }
        return null;
    }


    private void displayPlayerPositions() {
        for (PlayerForMap player : game.getPlayers()) {
            Cell position = player.getPosition();
            System.out.println("\nPlayer " + player.getPlayerNumber() + " position: (" +
                    (position.getRow() + 1) + "," + (position.getCol() + 1) + ")" + " value position is : " +game.getMap().getCell(position.getRow(), position.getCol()).getValue());
        }
        System.out.println();
    }

    public static void main(String[] args) {
//        new GameConsole(2);
//        MapCell myCell = new MapCell(1, 2);
//        System.out.println("value cell at (" + myCell.getRow() +","+ myCell.getCol() + ") = "+ myCell.generateRandomOccupiedValue() );

//        Map myMap = new Map(3, 3);
//        myMap.printMapValues();
//        myMap.printMapPositions();
//        myMap.checkValue(1,2);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        GameConsole gameConsole = new GameConsole(numPlayers);

    }
}