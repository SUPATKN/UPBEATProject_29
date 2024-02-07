
public class Main {
    public static void main(String[] args) throws SyntaxError, InvalidMoveException {
        MapCell mapCell = new MapCell(5,5);
        Player p1 = new Player("Y", mapCell);
        Player p2 = new Player("T", mapCell);
        System.out.println(p1.getCrew().nearby());
        System.out.println(p2.getCrew().nearby());

    }
}
