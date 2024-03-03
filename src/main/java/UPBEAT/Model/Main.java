package UPBEAT.Model;

public class Main {
    public static void main(String[] args) throws SyntaxError, InvalidMoveException {
        //MapCell mapCell = new MapCell(5,5,1000000);
        GameState game = new GameState();
        game.addPlayer("sss");
        System.out.println(game.getAllPlayer());
//        Player p1 = new Player("Y", mapCell,10);
//        Player p2 = new Player("T", mapCell,10);
//        System.out.println(p1.getTotolRegion());
//        System.out.println(p2.getTotolRegion());

    }
}
