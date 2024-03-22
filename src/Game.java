import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;

public class Game {
    private Scanner scan;
    private int amountOfPlayers;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private ArrayList<Player> listOfPlayers;
    public Game() {
        scan = new Scanner(System.in);
        amountOfPlayers = 1;
        play();
        listOfPlayers = new ArrayList<>();
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
            listOfPlayers.add(player1);
        } else if (amountOfPlayers == 2) {
            player1 = new Player("P1");
            player2 = new Player("P2");
            listOfPlayers.add(player1);
            listOfPlayers.add(player2);
        } else if (amountOfPlayers == 3) {
            player1 = new Player("P1");
            player2 = new Player("P2");
            player3 = new Player("P3");
            listOfPlayers.add(player1);
            listOfPlayers.add(player2);
            listOfPlayers.add(player3);
        } else if (amountOfPlayers == 4) {
            player1 = new Player("P1");
            player2 = new Player("P2");
            player3 = new Player("P3");
            player4 = new Player("P4");
            listOfPlayers.add(player1);
            listOfPlayers.add(player2);
            listOfPlayers.add(player3);
            listOfPlayers.add(player4);

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
            System.out.println("You landed on Mediterranean Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                System.out.println("\nRent:  $2 \nRent with Color set: $4 \nRent with 1 house: $10 \nRent with 2 house: $30 + \nRent with 3 house: $90 \nRent with 4 house: $160 \nRent with Hotel: $250 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each.");
                System.out.print("To purchase this, pay $60.  Yes or no? ");
            }
        }
        if (currentPlayer.getXCoordinate() == 8 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Community Chest.");
            chest();
        }

        if (currentPlayer.getXCoordinate() == 7 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Baltic Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                System.out.println("\nRent: $4 \nRent with Color set: $8 \nRent with 1 house: $20 \nRent with 2 house: $60 + \nRent with 3 house: $180 \nRent with 4 house: $320 \nRent with Hotel: $450 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each");
                System.out.print("To purchase this, pay $60.  Yes or no? ");
            }
        }
        if (currentPlayer.getXCoordinate() == 6 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Income Tax! It's tax season. Pay $200");
        }
        if (currentPlayer.getXCoordinate() == 5 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Reading Railroad.");
            if (!(checkIfOwnedForAnyPlayer())) {
                System.out.println("\nRent: $25 \nIf 2 R R is owned: $50 \nIf 3: $100 \nIf 4: $200 \nMortgage Value: $100");
                System.out.print("To purchase this, pay $200.  Yes or no? ");
            }
        }
        if (currentPlayer.getXCoordinate() == 4 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Oriental Avenue. ");
            if (!(checkIfOwnedForAnyPlayer())) {
                System.out.println("\nRent:  $6 \nRent with Color set: $12 \nRent with 1 house: $30 \nRent with 2 house: $90 + \nRent with 3 house: $270 \nRent with 4 house: $400 \nRent with Hotel: $550 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each");
                System.out.print("To purchase this, pay $100.  Yes or no? ");
            }
        }
        if (currentPlayer.getXCoordinate() == 3 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Change.");
            chance();
        }
        if (currentPlayer.getXCoordinate() == 2 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Vermont Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                System.out.println(" \nRent:  $6 \nRent with Color set: $12 \nRent with 1 house: $30 \nRent with 2 house: $90 + \nRent with 3 house: $270 \nRent with 4 house: $400 \nRent with Hotel: $550 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each");
                System.out.print("To purchase this, pay $100.  Yes or no? ");
            }
        }
        if (currentPlayer.getXCoordinate() == 1 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Connecticut Avenue. \nRent:  $8 \nRent with Color set: $16 \nRent with 1 house: $40 \nRent with 2 house: $100 + \nRent with 3 house: $300 \nRent with 4 house: $450 \nRent with Hotel: $600 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each");
            if (!(checkIfOwnedForAnyPlayer())) {
                System.out.println(" \nRent:  $6 \nRent with Color set: $12 \nRent with 1 house: $30 \nRent with 2 house: $90 + \nRent with 3 house: $270 \nRent with 4 house: $400 \nRent with Hotel: $550 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each");
                System.out.print("To purchase this, pay $100.  Yes or no? ");
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Jail! But you’re just visiting!");
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 9 ) {
            System.out.println("You landed on St. Charles Place. ");
            if (!(checkIfOwnedForAnyPlayer())) {
                System.out.println("\nRent:  $10 \nRent with Color set: $20 \nRent with 1 house: $50 \nRent with 2 house: $150 + \nRent with 3 house: $450 \\nRent with 4 house: $625 \nRent with Hotel: $750 \n --------------------- \nHouse cost: $50 each \nHotel cost: $50 each");
                System.out.print("To purchase this, pay $140.  Yes or no? ");
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 8 ) {
            System.out.println("You landed on Electric company.");
            if (!(checkIfOwnedForAnyPlayer())) {
                System.out.print("To purchase this utility, pay $150.  Yes or no? ");
            }
        }
    }
    public void chance() {
        int x = (int) (Math.random()*20 ) + 1;
        if (x > 18) {
            System.out.println("Go back three spaces");
        } else if (x > 16) {
            System.out.println("Advance to go! Collect $200");
        } else if (x > 14) {
            System.out.println("Go to Jail. Do not collect $200");
        } else if (x > 12 ) {
            System.out.println("Speeding fee. Pay $15");
        } else if (x > 10) {
            System.out.println("Get out of jail free. Card should be kept until needed, sold, or traded.");
        } else if ( x > 8) {
            System.out.println("Advance to illinois avenue. If you pass go, collect $200");
        } else if (x > 6) {
            System.out.println("Advance to the next railroad. If UNOWNED, you may buy it from the bank. If OWNED, pay the owner twice the rent. If you pass GO, collect $200");
        } else if ( x > 4 ){
            System.out.println("Bank pays you 50 dollars");
        } else {
            System.out.println("Advance to St. Charles Place. If you pass GO, collect $200.");
        }
    }
    public void chest() {
        int x = (int) (Math.random()*20 ) + 1;
        if (x > 18) {
            System.out.println("You inherit $100");
        } else if (x > 16) {
            System.out.println("Doctor's fee: Pay $50");
        } else if (x > 14) {
            System.out.println("Go to Jail. Do not collect $200");
        } else if (x > 12 ) {
            System.out.println("Xmas funds matures. Collect $100");
        } else if (x > 10) {
            System.out.println("Get out of jail free. Card should be kept until needed, sold, or traded.");
        } else if ( x > 8) {
            System.out.println("Life insurance funds matures. Collect $100");
        } else if (x > 6) {
            System.out.println("You are assessed for street repairs. $40 per house and $115 per hotel");
        } else if ( x > 4 ){
            System.out.println("Bank pays you 50 dollars");
        } else {
            System.out.println("Advance to go! Collect $200");
        }
    }
    public boolean checkIfOwnedForAnyPlayer() {
        for (int i = 0; i < listOfPlayers.size(); i++) {
            if (listOfPlayers.get(i).search(listOfPlayers.get(i).getCoordinates())) {
                return true;
            }
        }
        return false;
    }


}
