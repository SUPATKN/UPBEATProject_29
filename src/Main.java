public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s = "if(1) then {move up} else {move down} ";
        Tokenizer t = new Tokenizer(s);
        ParserTest p = new ParserTest(t);
        p.ParsePlan();

    }
}
