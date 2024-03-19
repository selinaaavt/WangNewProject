import java.util.Scanner;

public class Game {
    private Scanner scan;
    private int amountOfPlayers;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    public Game() {
        scan = new Scanner(System.in);
        amountOfPlayers = 1;
        play();
    }
    public void play() {
        System.out.print("Hello! Do you wish to play monopoly? y/n: ");
        String answer = scan.nextLine();
        if (answer.equals("y")) {
            System.out.print("Enter the amount of players, including you (up to 4 and more than 1): ");
            amountOfPlayers = scan.nextInt();
        } else {
            System.out.println("Bye!");
        }
        createPlayers();
    }
    public void createPlayers() {
        while (amountOfPlayers <= 1 || amountOfPlayers > 5) {
            System.out.println("That is an invalid number of player you inserted.");
            System.out.print("Enter the amount of players: ");
            amountOfPlayers = scan.nextInt();
        }
        if (amountOfPlayers == 1) {
            player1 = new Player("P1");
        } else if (amountOfPlayers == 2) {
            player1 = new Player("P1");
            player2 = new Player("P2");
        } else if (amountOfPlayers == 3) {
            player1 = new Player("P1");
            player2 = new Player("P2");
            player3 = new Player("P3");
        } else if (amountOfPlayers == 4) {
            player1 = new Player("P1");
            player2 = new Player("P2");
            player3 = new Player("P3");
            player4 = new Player("P4");
        }
        turns();
    }
    public int rollDice() {
        int outcome = (int) (Math.random()*12) + 1;
        return outcome;
    }
    public void turns() {
        Player currentPlayer = player1;
        while (currentPlayer != null) {
            System.out.println("It is " + currentPlayer.getName() + "'s turn! ");
            int moves = rollDice();

            Grid.printGrid();
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else if (currentPlayer == player2) {
                currentPlayer = player3;
            } else if (currentPlayer == player3) {
                currentPlayer = player4;
            } else if (currentPlayer == player4) {
                currentPlayer = player1;
            }
        }
    }
    public void determiningOptions(Player currentPlayer) {
        if (currentPlayer.getXCoordinate() == 9 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Mediterranean Avenue. \nCost: $60 \nRent: $2 \nRent with Color set: $4 \nRent with 1 house: $10 \nRent with 2 house: $30 + \nRent with 3 house: $90 \nRent with 4 house: $160 \nRent with Hotel: $250 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each");
        }
        if (currentPlayer.getXCoordinate() == 8 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Baltic Avenue. \nCost: $60 \nRent: $4 \nRent with Color set: $8 \nRent with 1 house: $20 \nRent with 2 house: $60 + \nRent with 3 house: $180 \nRent with 4 house: $320 \nRent with Hotel: 450 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each");
        }
        if (currentPlayer.getXCoordinate() == 7 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Income Tax! It's tax season. Pay $200");
        }
        if (currentPlayer.getXCoordinate() == 6 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Reading Railroad. \nRent: $25 \nIf 2 R R is owned: $50 \nIf 3: $100 \nIf 4: $200 \nMortgage Value: $100");
        }
        if (currentPlayer.getXCoordinate() == 6 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Reading Railroad. \nRent: $25 \nIf 2 R R is owned: $50 \nIf 3: $100 \nIf 4: $200 \nMortgage Value: $100");
        }
    }


}
