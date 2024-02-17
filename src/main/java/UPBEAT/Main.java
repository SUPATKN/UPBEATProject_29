package UPBEAT;

public class Main {
    public static void main(String[] args) throws SyntaxError, InvalidMoveException {
        MapCell mapCell = new MapCell(5,5);
        Player p1 = new Player("Y", mapCell);
        Player p2 = new Player("T", mapCell);
        System.out.println(p1.getTotolRegion());
        System.out.println(p2.getTotolRegion());

    }
}
