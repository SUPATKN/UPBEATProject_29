public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s ="while (2) if(-2 ) then move up else move down";
        Tokenizer t = new Tokenizer(s);
        ParserGrammar p = new ParserGrammar(t);
        p.ParsePlan();

    }
}
