public class Possession {
    private int yCoordinate;
    private int xCoordinate;
    private String name;
    private int houses;
    private boolean ownHotel;
    private int price;
    public Possession(String name, int xCoordinate, int yCoordinate, int price) {
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        houses = 0;
        ownHotel = false;
        this.price = price;
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
    public int getHouses() {
        return houses;
    }
    public void setHouses(int houses) {
        this.houses = houses;
    }
    public boolean isOwnHotel() {
        return ownHotel;
    }
    public void setOwnHotel(boolean ownHotel) {
        this.ownHotel = ownHotel;
    }
    public int getPrice() {
        return price;
    }
}
