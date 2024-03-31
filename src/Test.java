public class Test {
    public static void main(String[] args) {
        HouseCard hello = new HouseCard(13, 24,"\u001B[40;1m", "\n  Mediterranean Avenue\nRent:               $2 \nRent w Color set:   $4 \nRent with 1 ⌂:      $10 \nRent with 2 ⌂:      $30 \nRent with 3 ⌂:      $90 \nRent with 4 ⌂:      $160 \nRent with hotel:    $250 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
        hello.populateGrid();
        hello.printGrid();
    }

}
