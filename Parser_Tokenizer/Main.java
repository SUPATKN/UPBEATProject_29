
public class Main {
    public static void main(String[] args) throws SyntaxError, InvalidMoveException {
        Map map = new Map(5,5);
        Player p1 = new Player("Y",map);
        p1.Plan();
//        String s ="if(2) then {} else {}";
//        Tokenizer t = new Tokenizer(s);
//        ParserGrammar p = new ParserGrammar(t);
//        p.ParsePlan();
    }
}
