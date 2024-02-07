import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Player {
    private int TotolRegion;
    private Cell cityCenter;
    private Map<String ,Integer> binding = new HashMap<>();
    private boolean GameStatus = true; // The players haven't lost yet.
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
        cityCenter.SetCityCenter();
        cityCenter.setPlayer(this);
        TotolRegion = 1;
        cityCenter.getDeposit().CalDeposit(1);
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
    public int getTotolRegion(){return TotolRegion;}
    public void DecreaseRegion(){
        TotolRegion--;
    }



    public Cell getCityCenter(){
        return cityCenter;
    }

    public String getName(){
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
        p = new ParserGrammar(tokenizer,crew,binding);
        p.ParsePlan();
    }

    public void DecreaseBudget(int cost) {
        Budget -= cost;
        if (Budget < 0) {
            Budget = 0;
        }
    }

    public void IncreaseBudget(int cost) {
        Budget += cost;
    }
    public void InvestCost(int cost){
        DecreaseBudget(cost + 1);
        TotolRegion += 1;
        //don't forget to make it not + when it already have this cell
        if(Budget < 0){
            Budget = 0;
        }
    }

    public void MoveCost(){
        DecreaseBudget(1);
        if(Budget < 0){
            Budget = 0;
        }
    }

    public CityCrew getCrew(){
        return crew;
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

    public void Relocate() {
        int distance = 0;
        int newRow = getCrew().getPosition().getRow() + 1;
        int newCol = getCrew().getPosition().getCol() + 1;
        int cityRow = getCityCenter().getRow() + 1;
        int cityCol = getCityCenter().getCol() + 1;

        if(cityRow == cityCol && newRow == newCol && Math.abs(cityRow - newRow) % 2 == 0){
            distance = (Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol)) - (Math.abs(newRow - cityRow)/2);
            cityCenter = crew.getPosition();
            System.out.println("Distance: " + distance);
            System.out.println("คี่");

        } else if (cityRow == cityCol && newRow == newCol && Math.abs(cityRow - newRow) % 2 == 1) {
            if(cityRow<newRow){
                if(cityRow % 2 == 1 && newRow % 2 == 0 && Math.abs(cityRow-newRow) <= 1){
                    distance = Math.abs(newRow - cityRow);
                    cityCenter = crew.getPosition();
                    System.out.println(Math.abs(cityRow-newRow));
                    System.out.println("มามาตี้มันรวมกันเพะ");
                }else if (Math.abs(cityRow-newRow) > 1){
                    distance = (Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol)) - (newRow)/2;
                    cityCenter = crew.getPosition();
                    System.out.println(Math.abs(cityRow-newRow));
                    System.out.println("ผลต่างมันนักกว่า1");
                }else{
                    distance = (Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol));
                    cityCenter = crew.getPosition();
                    System.out.println(Math.abs(cityRow-newRow));
                    System.out.println("จ้างมันเตอะ");

                }
//                System.out.println("cityRow%2 " + cityRow%2 + "newRow%2 "+ newRow%2);
                System.out.println("ตัวที่จะไปนักกว่า");

            } else if (cityRow>newRow) {
                System.out.println(Math.abs(cityRow-newRow));
                if(cityRow % 2 == 0 || newRow % 2 == 1 && Math.abs(cityRow-newRow) <= 1) {
                    distance = Math.abs(cityRow - newRow );
                    cityCenter = crew.getPosition();
                }else if (Math.abs(cityRow-newRow) > 1){
                    distance = (Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol)) - (newRow)/2;
                    cityCenter = crew.getPosition();
                    System.out.println(Math.abs(cityRow-newRow));
                }else{
                    distance = (Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol));
                    cityCenter = crew.getPosition();
                    System.out.println(Math.abs(cityRow-newRow));
                    System.out.println("มาelse");

                }
//                System.out.println("cityRow%2 " + cityRow%2 + "newRow%2 "+ newRow%2);
                System.out.println("ตัวที่จะไปน้อยกว่า");
            }

//            System.out.println("Distance: " + distance);
            System.out.println("คู่");

        } else if (cityRow == newRow && cityCol != newCol) {
            distance = Math.abs(cityCol - newCol);
            cityCenter = crew.getPosition();
            System.out.println("Distance: " + distance);
            System.out.println("case1");

        } else if (cityRow != newRow && cityCol == newCol) {
            distance = Math.abs(cityRow - newRow);
            cityCenter = crew.getPosition();
            System.out.println("Distance: " + distance);
            System.out.println("case2");

        } else if (cityRow != newRow && cityCol != newCol) {
            System.out.println("case3");
            if(Math.abs(cityRow - newRow) % 2 ==  0 && Math.abs(cityCol - newCol) % 2 != 0){
                if(Math.abs(cityRow - newRow) >= 1){
                    distance = (Math.max(Math.abs(cityRow - newRow) , Math.abs(cityCol - newCol)) + Math.max(Math.abs(cityRow - newRow) , Math.abs(cityCol - newCol))) - 1;
                    cityCenter = crew.getPosition();
                    System.out.println("Distance: " + distance);

                }else{
                    distance = Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol);
                    cityCenter = crew.getPosition();
                    System.out.println("Distance: " + distance);

                }


            } else if(Math.abs(cityRow - newRow) % 2 !=  0 && Math.abs(cityCol - newCol) % 2 == 0){
                if(Math.abs(cityCol - newCol)  >= 1){
                    distance = (Math.max(Math.abs(cityRow - newRow) , Math.abs(cityCol - newCol)) + Math.min(Math.abs(cityRow - newRow) , Math.abs(cityCol - newCol))) - 1;
                    cityCenter = crew.getPosition();
                }else{
                    distance = (Math.max(Math.abs(cityRow - newRow) , Math.abs(cityCol - newCol)) + Math.max(Math.abs(cityRow - newRow) , Math.abs(cityCol - newCol))) - 1;
                    cityCenter = crew.getPosition();
                }

            } else if (Math.abs(cityRow - newRow) % 2 !=  0 && Math.abs(cityCol - newCol) % 2 != 0) {
                if(Math.abs(cityCol - newCol) <= 1){
                    distance = Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol);
                    cityCenter = crew.getPosition();
                    System.out.println("Distance: " + distance);
                    System.out.println("col เป็น 1");

                }else if (Math.abs(cityCol - newCol) > 1 && Math.abs(cityRow - newRow) == 1 ){
                    distance = Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol) - newCol;
                    cityCenter = crew.getPosition();

                    System.out.println("Distance: " + distance);
                    System.out.println("row เป็น 1");

                } else if (Math.abs(cityCol - newCol) >= 3 && Math.abs(cityRow - newRow) >= 3 ) {
                    distance = Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol) - cityCol;
                    cityCenter = crew.getPosition();

                    System.out.println("Distance: " + distance);
                    System.out.println("ตึงกู้นักกว่า3");
                }

            }


        }

        System.out.println("[ " + getName() + " ]" + " Distance to relocate is: " + distance);

    }

}
