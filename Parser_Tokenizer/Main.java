public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s ="if(-2) then collect 2+2 else invest 5+5";
        Tokenizer t = new Tokenizer(s);
        ParserGrammar p = new ParserGrammar(t);
        p.ParsePlan();

    }
}
