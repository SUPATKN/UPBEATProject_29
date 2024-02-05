import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartGame {
    MapCell map;
    private static List<Player> Allplayer  = new ArrayList<>();
    private int CountTurn = 0;
    private static int CountPlayer = 1;

    public void Turn(){
        System.out.println();
        CountTurn +=1;
    }

    public void EnterNumberOfPlayer(){
        Scanner sc = new Scanner((System.in));
        System.out.print("Please enter the number of players : ");
        String count = "";
        count = sc.nextLine();
        if(count.isEmpty() || count.equals(" ") || !count.matches("\\d+")){
            System.out.println("You did not enter the number of players , Set default player = 2");
            CountPlayer = 2;
        }else if(Integer.parseInt(count) <= 1){
            System.out.println("The number of players you entered is too few , Set default player = 2");
            CountPlayer = 2;
        }
        else {
            CountPlayer = Integer.parseInt(count);
        }
    }

    public void namePlayer(String nameplayer, int i) throws SyntaxError, InvalidMoveException {
        if (nameplayer.isEmpty() || nameplayer.equals(" ")) nameplayer = "Unknown player" + i;
        Allplayer.add(new Player(nameplayer,map));
    }

    public void EnterNamePlayer() throws SyntaxError, InvalidMoveException {
        for(int i = 1 ; i <= CountPlayer ; i++){
            Scanner sc = new Scanner((System.in));
            System.out.print("Enter player " + i + " name : ");
            String name = sc.nextLine();
            namePlayer(name,i);
        }
    }

    public void CreateMap(){
        if(CountPlayer == 2){
            map = new MapCell(7,7);
        }else if(CountPlayer >= 3 && CountPlayer <= 5 ){
            map = new MapCell(15,15);
        }else{
            map = new MapCell(30,30);
        }
    }

    private void displayMap() {
        Cell[][] cells = map.getCells();

        // Print the map header
        System.out.println();
        System.out.println("Current Player Positions:");

        // Iterate through all players
        for (Player player : Allplayer) {
            int playerX = player.getCrew().getPosition().getRow();
            int playerY = player.getCrew().getPosition().getCol();

            // Mark the player's position on the map
            cells[playerX][playerY].setOccupied(true);

            // Print the player's name and position
            System.out.println(player.getName() + " is at position (" + (playerX + 1) + "," + (playerY + 1) + ")");
        }

        // Display the map
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print("(" + (i + 1) + "," + (j + 1) + ")");

                boolean playerFound = false;
                for (Player player : Allplayer) {
                    int playerX = player.getCrew().getPosition().getRow();
                    int playerY = player.getCrew().getPosition().getCol();

                    if (i == playerX && j == playerY) {
                        System.out.print("[ " + player.getName() + " ]");
                        playerFound = true;
                        break;
                    }
                    if (!playerFound) {
                        // If no player is in this cell, print a dash
                        System.out.print("-");
                    }
                }
                System.out.print(" ");
            }
                System.out.println();
        }

    }

    public void RunGame() throws SyntaxError, InvalidMoveException {
        for(int i = 0; i<5 ; i++){

            Turn();
            System.out.println("+---------- Turn : " + CountTurn +" ----------+");
            displayMap();
            for(int j=0;j < CountPlayer ; j++){
                Allplayer.get(j).Plan();
                displayMap();
                if(j==CountPlayer){
                    j=0;
                }
            }
        }
    }

    public void Start() throws SyntaxError, InvalidMoveException {
        System.out.println("+----------------------------------------+");
        System.out.println("|     WELCOME TO UPBEAT GROUP29 GAME     |");
        System.out.println("+----------------------------------------+");
        System.out.println();

        // Enter the number of players
        EnterNumberOfPlayer();

        // Create a Map
        CreateMap();

        // Enter player name
        EnterNamePlayer();

        // Run Turn
        RunGame();

    }

    public static void main(String[] args) throws SyntaxError, InvalidMoveException {
        StartGame s = new StartGame();
        s.Start();
    }

}
