public class Player {
    private int[] coordinates;
    private String name;

    public Player(String name) {
        this.name = name;
        coordinates = new int[2];
        coordinates[0] = 10;
        coordinates[1] = 10;
    }

    public int[] getCoordinates() {
        return coordinates;
    }
    public int getXCoordinate() {
        return coordinates[0];
    }
    public int getYCoordinate() {
        return coordinates[1];
    }
    public String getName() {
        return name;
    }
}
