public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s ="m = 0 while (2) { }";
        Tokenizer t = new Tokenizer(s);
        ParserTest p = new ParserTest(t);
        p.ParsePlan();

    }
}
