import java.util.Scanner;
public class Player {
    private String name;
    private int Budget;
    private CityCrew crew;
    private Map map;
    private ParserGrammar p;
    private Tokenizer tokenizer;
    public Player(String name,Map map){
        Budget = 10000;
        this.name = name;
        this.map = map;
        crew = new CityCrew(this,map,map.getRandomEmptyCell());
        System.out.println("row = " + crew.getPosition().getRow());
        System.out.println("col = " + crew.getPosition().getCol());
    }

    public int getBudget(){
        return Budget;
    }
    public Map getMap(){
        return map;
    }

    public void Plan() throws SyntaxError, InvalidMoveException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter plan");
        String input = scanner.nextLine();
        tokenizer = new Tokenizer(input);
        p = new ParserGrammar(tokenizer,crew);
        p.ParsePlan();
    }

    public void Invest(int cost){
        Budget -= cost + 1;
    }

}
