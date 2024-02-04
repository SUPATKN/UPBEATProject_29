import java.util.Scanner;
public class Player {
    private int TotolRegion;
    private Cell cityCenter;
    private String name;
    private int Budget;
    private CityCrew crew;
    private MapCell map;
    private ParserGrammar p;
    private Tokenizer tokenizer;
    public Player(String name,MapCell map){
        Budget = 10000;
        this.name = name;
        this.map = map;
        crew = new CityCrew(this,map,map.getRandomEmptyCell());
        cityCenter = crew.getPosition();
        cityCenter.setDeposit(100);
        TotolRegion = 1;
        System.out.print("[ " + name + " ]" + " City Center on : ");
        System.out.print("row = " + (crew.getPosition().getRow()+1));
        System.out.println(" | col = " + (crew.getPosition().getCol()+1));
    }

    public int getBudget(){
        return Budget;
    }
    public MapCell getMap(){
        return map;
    }

    public Cell getCityCenter(){
        return cityCenter;
    }

    public String getName(){
        return name;
    }

    public void Plan() throws SyntaxError, InvalidMoveException {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("[ " + name + " ]" + " turn enter plan : ");
        String input = scanner.nextLine();
        tokenizer = new Tokenizer(input);
        p = new ParserGrammar(tokenizer,crew);
        p.ParsePlan();
    }

    public void InvestCost(int cost){
        Budget -= cost + 1;
        TotolRegion += 1;
        //don't forget to make it not + when it already have this cell
        if(Budget < 0){
            Budget = 0;
        }
    }

    public void MoveCost(){
        Budget -= 1;
        if(Budget < 0){
            Budget = 0;
        }
    }

    public CityCrew getCrew(){
        return crew;
    }

    public void Relocate(){
        int cost = 0;
        int newRow = crew.getPosition().getRow();
        int newCol = crew.getPosition().getCol();
        int cityRow = cityCenter.getRow();
        int cityCol = cityCenter.getCol();

        if(cityRow == newRow){
            cost = Math.abs(newCol - cityCol);
        }else if(cityRow != newRow && cityCol == newCol){
            cost =  Math.abs(newRow - cityRow);
        }
    }

}
