import java.util.Scanner;
public class Player {
    private int TotolRegion;
    private Cell cityCenter;
    private String name;
    private int Budget;
    private CityCrew crew;
    private boolean GameStatus = true; // The players haven't lost yet.
    private MapCell map;
    private ParserGrammar p;
    private Tokenizer tokenizer;

    public Player(String name, MapCell map) {
        Budget = 10000;
        this.name = name;
        this.map = map;
        crew = new CityCrew(this, map, map.getRandomEmptyCell());
        cityCenter = crew.getPosition();
        cityCenter.setDeposit(100);
        cityCenter.SetCityCenter();
        TotolRegion = 1;
        System.out.print("[ " + name + " ]" + " City Center on : ");
        System.out.print("row = " + (crew.getPosition().getRow() + 1));
        System.out.println(" | col = " + (crew.getPosition().getCol() + 1));
    }

    public int getBudget() {
        return Budget;
    }

    public MapCell getMap() {
        return map;
    }

    public Cell getCityCenter() {
        return cityCenter;
    }

    public String getName() {
        return name;
    }

    public void setGameStatus() {
        GameStatus = false; // players has lost
    }

    public void Plan() throws SyntaxError, InvalidMoveException {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("[ " + name + " ]" + " turn enter plan : ");
        String input = scanner.nextLine();
        tokenizer = new Tokenizer(input);
        p = new ParserGrammar(tokenizer, crew);
        p.ParsePlan();
    }

    public void InvestCost(int cost) {
        DecreaseBudget(cost + 1);
        TotolRegion += 1;
        //don't forget to make it not + when it already have this cell
        if (Budget < 0) {
            Budget = 0;
        }
    }

    public void DecreaseBudget(int cost) {
        Budget -= cost;
        if (Budget < 0) {
            Budget = 0;
        }
    }

    public void MoveCost() {
        DecreaseBudget(1);
        if (Budget < 0) {
            Budget = 0;
        }
    }

    public CityCrew getCrew() {
        return crew;
    }

    public void Relocate() {
        int cost = 0;
        int newRow = crew.getPosition().getRow();
        int newCol = crew.getPosition().getCol();
        int cityRow = cityCenter.getRow();
        int cityCol = cityCenter.getCol();

        if (cityRow == newRow) {
            cost = Math.abs(newCol - cityCol);
        } else if (cityRow != newRow && cityCol == newCol) {
            cost = Math.abs(newRow - cityRow);
        }
    }

    public void loseGame() {
        // Set the player's status to indicate that they have lost the game
        this.setGameStatus();

        // Remove the player's ownership from all cells they own
        for (int row = 0; row < map.getRows(); row++) {
            for (int col = 0; col < map.getCols(); col++) {
                Cell cell = map.getCell(row, col);
                if (cell.getWhoBelong() != null && cell.getWhoBelong().equals(this)) {
                    cityCenter.getWhoBelong();
                    cell.setDeposit(0);  // Reset deposit for ownerless cell
                }
            }
        }
        // Inform the player about losing the game
        System.out.println("Player [ " + this.getName() + " ] has lost the game.");
    }
}
