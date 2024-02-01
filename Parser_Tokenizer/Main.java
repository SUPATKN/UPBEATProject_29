public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s ="move up = 2";
        Tokenizer t = new Tokenizer(s);
        ParserGrammar p = new ParserGrammar(t);
        p.ParsePlan();

    }
}
