package UPBEAT;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.*;

public class Player {
    //    private int TotolRegion;
    @JsonManagedReference
    private Cell cityCenter;
    private Map<String, Integer> binding = new HashMap<>();
    private boolean GameStatus = true; // The players haven't lost yet.
    private String name;
    private int Budget;
    private CityCrew crew;
    private MapCell map;
    private ParserGrammar p;
    private Tokenizer tokenizer;
    private Set<Cell> TotalRegion = new HashSet<>();
    private int index;

    public Player(String name, MapCell map) {
        Budget = 10000;
        this.name = name;
        this.map = map;
        crew = new CityCrew(this, map, map.getRandomEmptyCell());
        cityCenter = crew.getPosition();
        cityCenter.setDeposit(100);
        cityCenter.SetCityCenter();
        cityCenter.setPlayer(this);
        TotalRegion.add(cityCenter);
        cityCenter.getDeposit().CalDeposit(1);
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

    public int getTotolRegion() {
        return TotalRegion.size();
    }

    public void DecreaseRegion(Cell element) {
        TotalRegion.remove(element);
    }

    public void IncreaseRegion(Cell element) {
        TotalRegion.add(element);
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
        p = new ParserGrammar(tokenizer, crew, binding);
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

    public CityCrew getCrew() {
        return crew;
    }

    public void loseGame() {
        // Set the player's status to indicate that they have lost the game
        this.setGameStatus();
        Iterator<Cell> Region = TotalRegion.iterator();

        // Remove the player's ownership from all cells they own
        while(Region.hasNext()){
            Region.next().setPlayer(null);
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

        if (cityRow == cityCol && newRow == newCol) {
            System.out.println("case 1");
            if (Math.abs(cityRow - newRow) > 1 ) {
                distance = (Math.abs(cityRow - newRow) + Math.abs(cityCol - newCol)) - (Math.abs(newRow - cityRow) / 2);
                cityCenter = crew.getPosition();
                System.out.println("Distance: " + distance);
                System.out.println("คู่");
            } else if (Math.abs(cityRow - newRow) <= 1) {
                if (cityRow < newRow) {
                    if(cityRow % 2 != 0 && newRow % 2 == 0 ){
                        double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                        double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                        double calculateDistance = Math.sqrt(rowDiff + colDiff);

                        distance = (int) Math.round(calculateDistance);
                        cityCenter = crew.getPosition();
                        System.out.println("Distance: " + distance);
                    } else if (cityRow % 2 == 0 && newRow % 2 != 0) {
                        double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                        double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                        double calculateDistance = Math.sqrt(rowDiff + colDiff + newRow + newCol);

                        distance = (int) Math.round(calculateDistance);
                        cityCenter = crew.getPosition();
                        System.out.println("Distance: " + distance);
                    }

                    System.out.println("คี่ : ตัวที่จะไปมากกว่าเรา");
                } else if (cityRow > newRow) {
                    if(cityRow % 2 != 0 && newRow % 2 == 0 ){
                        double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                        double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                        double calculateDistance = Math.sqrt(rowDiff + colDiff + newRow + newCol);

                        distance = (int) Math.round(calculateDistance);
                        cityCenter = crew.getPosition();
                        System.out.println("Distance: " + distance);
                    } else if (cityRow % 2 == 0 && newRow % 2 != 0) {
                        double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                        double colDiff = Math.pow(Math.abs(cityCol - newCol),2);


                        double calculateDistance = Math.sqrt(rowDiff + colDiff);

                        distance = (int) Math.round(calculateDistance);
                        cityCenter = crew.getPosition();
                        System.out.println("Distance: " + distance);
                    }
                    System.out.println("คี่ : ตัวที่จะไปน้อยกว่าเรา");
                }
            }


        }else if (cityRow == newCol  && cityCol == newRow ){

            double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
            double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

            double calculateDistance = Math.sqrt(rowDiff + colDiff + newCol + newRow);

            distance = (int) Math.round(calculateDistance);
            cityCenter = crew.getPosition();
            System.out.println("Distance: " + distance);
            System.out.println("case 2");

        } else if (cityCol % 2 == 0 && newRow % 2 == 0 || cityCol % 2 != 0 && newRow % 2 != 0) {
            double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
            double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

            double calculateDistance = Math.sqrt(rowDiff + colDiff);

            distance = (int) Math.round(calculateDistance);
            cityCenter = crew.getPosition();
            System.out.println("Distance: " + distance);
            System.out.println("case 3");
        } else if (cityRow == newRow && cityCol != newCol) {
            System.out.println("case 4");
            distance = Math.abs(cityCol - newCol);
            cityCenter = crew.getPosition();
            System.out.println("Distance: " + distance);


        } else if (cityRow != newRow && cityCol == newCol) {
            System.out.println("case 5");
            distance = Math.abs(cityRow - newRow);
            cityCenter = crew.getPosition();
            System.out.println("Distance: " + distance);


        } else if (cityRow != newRow && cityCol != newCol) {
            System.out.println("case 6");
            int calDiffBefore = Math.abs(cityRow-cityCol);
            int calDiffAffter = Math.abs(newRow-newCol);

            if(calDiffBefore % 2 == 0 && calDiffAffter % 2 == 0){
                double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                double calculateDistance = Math.sqrt(rowDiff + colDiff + newRow +newCol);

                distance = (int) Math.round(calculateDistance);
                cityCenter = crew.getPosition();
                System.out.println("Distance: " + distance);

            } else if (calDiffBefore % 2 == 0 && calDiffAffter % 2 != 0) {
                double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                double calculateDistance = Math.sqrt(rowDiff + colDiff );

                distance = (int) Math.round(calculateDistance);
                cityCenter = crew.getPosition();
                System.out.println("Distance: " + distance);

            } else if (calDiffBefore % 2 != 0 && calDiffAffter % 2 == 0) {

            } else if (calDiffBefore % 2 != 0 && calDiffAffter % 2 != 0) {

            }

            if((cityRow % 2 != 0 && cityCol % 2 != 0) && (newRow % 2 == 0 && newCol % 2 == 0)){
                double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                double calculateDistance = Math.sqrt(rowDiff + colDiff + newRow +newCol);

                distance = (int) Math.round(calculateDistance);
                cityCenter = crew.getPosition();
                System.out.println("Distance: " + distance);
//                System.out.println("คู่คู่");
            } else if((cityRow % 2 == 0 && cityCol % 2 == 0) && (newRow % 2 != 0 && newCol % 2 != 0)){
                double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                double calculateDistance = Math.sqrt(rowDiff + colDiff + newRow + newCol);

                distance = (int) Math.round(calculateDistance);
                cityCenter = crew.getPosition();
                System.out.println("Distance: " + distance);
                System.out.println("คู่คู่");
            }else if (Math.abs(newRow - cityRow) >= 2 && Math.abs(newCol-cityCol) >= 2) {
                double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                double calculateDistance = Math.sqrt(rowDiff + colDiff + newCol + newRow);

                distance = (int) Math.round(calculateDistance);
                cityCenter = crew.getPosition();
                System.out.println("Distance: " + distance);
                System.out.println("คี่คี่");
            }else if(newCol == newRow || cityCol == cityRow ){
                double rowDiff = Math.pow(Math.abs(cityRow - newRow),2);
                double colDiff = Math.pow(Math.abs(cityCol - newCol),2);

                double calculateDistance = Math.sqrt(rowDiff + colDiff);

                distance = (int) Math.round(calculateDistance);
                cityCenter = crew.getPosition();
                System.out.println("Distance: " + distance);
                System.out.println("else");
            }


        } else {
            distance = 0;
            cityCenter = crew.getPosition();
            System.out.println("Distance: " + distance);
        }

        System.out.println("[ " + getName() + " ]" + " Distance to relocate is: " + distance);

    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

