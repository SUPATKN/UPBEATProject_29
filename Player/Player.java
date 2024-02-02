import java.util.Scanner;
public class Player {
    private String name;
    private int Budjet;
    private CityCrew crew;
    private Map map;
    private ParserGrammar p;
    private Tokenizer tokenizer;
    public Player(String name,Map map){
        this.name = name;
        this.map = map;
        crew = new CityCrew(this,map,map.getRandomEmptyCell());
        System.out.println(crew.getPosition().getCol());
        System.out.println(crew.getPosition().getRow());
    }

    public void Plan() throws SyntaxError, InvalidMoveException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter plan");
        String input = scanner.nextLine();
        tokenizer = new Tokenizer(input);
        p = new ParserGrammar(tokenizer,crew);
        p.ParsePlan();
    }

}
