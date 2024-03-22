import java.util.ArrayList;

public class Player {
    private int[] coordinates;
    private String name;
    private ArrayList<int[]> ownedCoordinates;

    public Player(String name) {
        this.name = name;
        coordinates = new int[2];
        coordinates[0] = 10;
        coordinates[1] = 10;
        ownedCoordinates = new ArrayList<>();
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
    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
    public String getName() {
        return name;
    }
    public void addToOwnedCoordinates(int[] aa) {
        ownedCoordinates.add(aa);
    }
    public boolean search(int[] aa) {
        for (int i = 0; i < ownedCoordinates.size(); i++) {
            if (ownedCoordinates.get(i) == aa) {
                return true;
            }
        }
        return false;
    }
}
