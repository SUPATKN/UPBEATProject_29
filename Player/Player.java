public class Player {
    private String name;
    private int Budjet;
    private CityCrew crew;
    private Map map;
    public Player(String name,Map map){
        this.name = name;
        this.map = map;
        crew = new CityCrew(this,map,map.getRandomEmptyCell());
    }

    public void callCrewMove(String direction) throws InvalidMoveException {
        crew.move(Map.Direction.valueOf(direction));
    }
}
