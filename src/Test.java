public class Test {
    public static void main(String[] args) {
        HouseCard hello = new HouseCard(13, 25,"\033[0;107m", "\n        Boardwalk \nRent:               $50 \nRent w Color set:   $100 \nRent with 1 ⌂:      $200 \nRent with 2 ⌂:      $600 \nRent with 3 ⌂:      $1400 \nRent with 4 ⌂:      $1700 \nRent with hotel:    $2000 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
        hello.populateGrid();
        hello.printGrid();
    }

}
