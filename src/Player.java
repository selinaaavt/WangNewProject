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
            if (ownedPossessions.get(i).getxCoordinate() == x && ownedPossessions.get(i).getyCoordinate() == y) {
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
            for (int i = 0; i < ownedPossessions.size(); i ++) {
                System.out.println("You currently own " + ownedPossessions.get(i).toString());
            }
        }
    }
    public boolean checkForColorSet(int x, int y ) {
        if (x == 9 && y == 10 && search(7, 10)) {
            return true;
        } else if (x == 7 && y == 10 && search(9, 10)) {
            return true;
        } else if (x == 4 && y == 10 && search(2, 10) && search(1, 10)) {
            return true;
        } else if (x == 2 && y == 10 && search(4, 10) && search(1, 10)) {
            return true;
        } else if (x == 1 && y == 10 && search(4, 10) && search(2, 10)) {
            return true;
        } else if (x == 0 && y == 9 && search(0, 6) && search(0, 7)) {
            return true;
        } else if (x == 0 && y == 6 && search(0, 9) && search(0, 7)) {
            return true;
        } else if (x == 0 && y == 7 && search(0, 6) && search(0, 9)) {
            return true;
        } else if (x == 0 && y == 4 && search(0, 2) && search(0, 1)) {
            return true;
        } else if (x == 0 && y == 2 && search(0, 4) && search(0, 1)) {
            return true;
        } else if (x == 0 && y == 1 && search(0, 2) && search(0, 4)) {
            return true;
        } else if (x == 1 && y == 0 && search(3, 0) && search(4, 0)) {
            return true;
        } else if (x == 3 && y == 0 && search(1, 0) && search(4, 0)) {
            return true;
        } else if (x == 4 && y == 0 && search(3, 0) && search(1, 0)) {
            return true;
        } else if (x == 6 && y == 0 && search(7, 0) && search(9, 0)) {
            return true;
        } else if (x == 7 && y == 0 && search(6, 0) && search(9, 0)) {
            return true;
        } else if (x == 9 && y == 0 && search(6, 0) && search(7, 0)) {
            return true;
        } else if (x == 10 && y == 1 && search(10, 2) && search(10, 4)) {
            return true;
        } else if (x == 10 && y == 2 && search(10, 1) && search(10, 4)) {
            return true;
        } else if (x == 10 && y == 4 && search(10, 1) && search(10, 2)) {
            return true;
        } else if (x == 10 && y == 7 && search(10, 9)) {
            return true;
        } else if (x == 10 && y == 9 && search(10, 7)) {
            return true;
        } else {
            return false;
        }
    }
    public int howManyRailRoads() {
        int howMany = 0;
        if (search(10, 5)) {
            howMany++;
        }
        if (search(5, 0)) {
            howMany++;
        }
        if (search(0, 5)) {
            howMany++;
        }
        if (search(5, 10)) {
            howMany++;
        }
        return howMany;
    }
    public boolean ownAllUtilities(int x, int y) {
        if ( x == 8 && y == 0 && search(0, 8)) {
            return true;
        } else if (x == 0 && y == 8 && search(8, 0))  {
            return true;
        } else {
            return false;
        }
    }
}
