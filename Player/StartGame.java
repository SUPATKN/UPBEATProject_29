import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartGame {
    Map map;
    private static List<Player> Allplayer  = new ArrayList<>();
    private int CountTurn = 0;
    private static int CountPlayer = 1;

    public void Turn(){
        CountTurn +=1;
    }

    public void EnterNumberOfPlayer(){
        Scanner sc = new Scanner((System.in));
        System.out.print("Please enter the number of players : ");
        String count = "";
        count = sc.nextLine();
        if(count.isEmpty() || count.equals(" ")){
            System.out.println("You did not enter the number of players , Set default player = 2");
            CountPlayer = 2;
        }else {
            CountPlayer = Integer.parseInt(count);
        }
    }

    public void namePlayer(String nameplayer, int i){
        if (nameplayer.isEmpty() || nameplayer.equals(" ")) nameplayer = "Unknown player" + i;
        Allplayer.add(new Player(nameplayer,map));
    }

    public void EnterNamePlayer(){
        for(int i = 1 ; i <= CountPlayer ; i++){
            Scanner sc = new Scanner((System.in));
            System.out.print("Enter player " + i + " name : ");
            String name = sc.nextLine();
            namePlayer(name,i);
        }
    }

    public void CreateMap(){
        if(CountPlayer == 2){
            map = new Map(7,7);
        }else if(CountPlayer >= 3 && CountPlayer <= 5 ){
            map = new Map(15,15);
        }else{
            map = new Map(30,30);
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
        for(int i = 0; i<5 ; i++){
            Allplayer.get(0).Plan();
            Allplayer.get(1).Plan();
        }

    }

    public static void main(String[] args) throws SyntaxError, InvalidMoveException {
        StartGame s = new StartGame();
        s.Start();
    }

}
