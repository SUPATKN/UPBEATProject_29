package demoMap;

public class Player {
    private int playerNumber;
    private MapCell position;

    public Player(int playerNumber, MapCell initialPosition) {
        this.playerNumber = playerNumber;
        this.position = initialPosition;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public MapCell getPosition() {
        return position;
    }

    public void setPosition(MapCell newPosition) {
        this.position = newPosition;
    }
}


