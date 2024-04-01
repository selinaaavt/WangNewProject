public class Possession {
    private int yCoordinate;
    private int xCoordinate;
    private String name;
    public Possession(String name, int xCoordinate, int yCoordinate) {
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
    public int getxCoordinate() {
        return xCoordinate;
    }
    public int getyCoordinate() {
        return yCoordinate;
    }
    public String toString() {
        return "You own " + name;
    }

}
