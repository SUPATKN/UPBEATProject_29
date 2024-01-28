public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s = "move up down";
        Tokenizer t = new Tokenizer(s);
        ParserTest p = new ParserTest(t);
        System.out.println(p.ParsePlan());

    }
}
