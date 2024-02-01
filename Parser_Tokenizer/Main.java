public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s ="if(-2)then if(-2) then move up else move down else done";
        Tokenizer t = new Tokenizer(s);
        ParserGrammar p = new ParserGrammar(t);
        p.ParsePlan();

    }
}
