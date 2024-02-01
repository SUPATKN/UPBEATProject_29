public class PlayerForMap {
    private int playerNumber;
    private Cell position;


    public PlayerForMap(int playerNumber, Cell initialPosition) {
        this.playerNumber = playerNumber;
        this.position = initialPosition;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell newPosition) {
        this.position = newPosition;
    }
}

