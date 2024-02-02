public class Main {
    public static void main(String[] args) throws SyntaxError {
        String s ="if(2) then {} else {}";
        Tokenizer t = new Tokenizer(s);
        ParserGrammar p = new ParserGrammar(t);
        p.ParsePlan();

    }
}
