import java.util.ArrayList;

public class Player {
    private int[] coordinates;
    private String name;
    private ArrayList<Possession> ownedPossessions;
    private int money;
    private boolean getOutJailCardPossession;

    public Player(String name) {
        this.name = name;
        coordinates = new int[2];
        coordinates[0] = 10;
        coordinates[1] = 10;
        ownedPossessions = new ArrayList<>();
        money = 1500;
        getOutJailCardPossession = false;
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
    public void addToOwnedCoordinates(Possession newPos) {
        ownedPossessions.add(newPos);
    }
    public boolean search(int x, int y) {
        for (int i = 0; i < ownedPossessions.size(); i++) {
            if (ownedPossessions.get(i).getxCoordinate() ==x && ownedPossessions.get(i).getyCoordinate() == y) {
                return true;
            }
        }
        return false;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public void addMoney(int amount) {
        money += amount;
    }
    public void subtractMoney(int amount) {
        money -= amount;
    }
    public void setGetOutJailCardPossession(boolean value) {
        getOutJailCardPossession = value;
    }
    public void printProperties() {
        if (ownedPossessions.size() == 0) {
            System.out.println("You currently do not have any properties");
        } else {

        }
    }
    public boolean colorSet() {

    }
    public void find(int x, int y) {
        if (y == 10) {
            if (x == 9) {
                System.out.println("You own Mediterranean Avenue. ");
            }
        }
    }
}
