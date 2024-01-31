public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s = "while (2+10) {move up}";
        Tokenizer t = new Tokenizer(s);
        ParserTest p = new ParserTest(t);
        p.ParsePlan();

    }
}
