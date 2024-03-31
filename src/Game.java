import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Scanner scan;
    private int amountOfPlayers;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private ArrayList<Player> listOfPlayers;
    private MonopolyGrid monopolyGrid;
    private boolean status;
    public Game() {
        monopolyGrid = new MonopolyGrid();
        monopolyGrid.populateGrid();
        scan = new Scanner(System.in);
        amountOfPlayers = 1;
        listOfPlayers = new ArrayList<>();
        play();

        status = false;
    }
    public void play() {
        System.out.print("Hello! Do you wish to play monopoly? y/n: ");
        String answer = scan.nextLine();
        if (answer.equals("y")) {
            System.out.print("Enter the amount of players, including you (up to 4 and more than 1): ");
            int howMany = scan.nextInt();
            createPlayers(howMany);
        } else {
            System.out.println("Bye!");
            System.exit(0);
        }
    }
    public void createPlayers(int howMany) {
        while (howMany <= 1 || howMany >= 5) {
            System.out.println("That is an invalid number of player you inserted.");
            System.out.print("Enter the amount of players: ");
            howMany = scan.nextInt();
        }
        amountOfPlayers = howMany;
        scan.nextLine();
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
        while (status == false) {
            turns();
        }
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
                System.out.println(currentPlayer.getName() + " rolled a " + moves);
                determiningLanding(moves, currentPlayer);
                monopolyGrid.printGrid();
                determiningOptions(currentPlayer);
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
        String answer = scan.nextLine();
        if (currentPlayer.getXCoordinate() == 9 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Mediterranean Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[40;1m", "\n  Mediterranean Avenue\nRent:               $2 \nRent w Color set:   $4 \nRent with 1 ⌂:      $10 \nRent with 2 ⌂:      $30 \nRent with 3 ⌂:      $90 \nRent with 4 ⌂:      $160 \nRent with hotel:    $250 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $60. Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(60);
                    int[] coordinates = {9, 10};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 8 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Community Chest.");
            chest(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 7 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Baltic Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[40;1m", "\n     Baltic Avenue\nRent:               $4 \nRent w Color set:   $8 \nRent with 1 ⌂:      $20 \nRent with 2 ⌂:      $60 \nRent with 3 ⌂:      $180 \nRent with 4 ⌂:      $320 \nRent with hotel:    $450 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $60.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(60);
                    int[] coordinates = {7, 10};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 6 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Income Tax! It's tax season. Pay $200");
        }
        if (currentPlayer.getXCoordinate() == 5 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Reading Railroad.");
            if (!(checkIfOwnedForAnyPlayer())) {
                RailroadCard hello = new RailroadCard(13, 28,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n------Reading Railroad------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200. Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(200);
                    int[] coordinates = {5, 10};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 4 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Oriental Avenue. ");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n    Oriental Avenue \nRent:               $6 \nRent w Color set:   $12 \nRent with 1 ⌂:      $30 \nRent with 2 ⌂:      $90 \nRent with 3 ⌂:      $270 \nRent with 4 ⌂:      $400 \nRent with hotel:    $550 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $100. Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(100);
                    int[] coordinates = {4, 10};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 3 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Chance.");
            chance(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 2 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Vermont Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n     Vermont Avenue\nRent:               $6 \nRent w Color set:   $12 \nRent with 1 ⌂:      $30 \nRent with 2 ⌂:      $90 \nRent with 3 ⌂:      $270 \nRent with 4 ⌂:      $400 \nRent with hotel:    $550 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $100.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(100);
                    int[] coordinates = {2, 10};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 1 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Connecticut Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n   Connecticut Avenue\nRent:               $8 \nRent w Color set:   $16 \nRent with 1 ⌂:      $40 \nRent with 2 ⌂:      $100 \nRent with 3 ⌂:      $300 \nRent with 4 ⌂:      $450 \nRent with hotel:    $600 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $100.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(100);
                    int[] coordinates = {1, 10};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Jail! But you’re just visiting!");
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 9 ) {
            System.out.println("You landed on St. Charles Place. ");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n   St. Charles Place \nRent:               $10 \nRent w Color set:   $20 \nRent with 1 ⌂:      $50 \nRent with 2 ⌂:      $150 \nRent with 3 ⌂:      $450 \nRent with 4 ⌂:      $625 \nRent with hotel:    $750 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $140.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(140);
                    int[] coordinates = {0, 10};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                } else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 8 ) {
            System.out.println("You landed on Electric company.");
            if (!(checkIfOwnedForAnyPlayer())) {
                RailroadCard hello = new RailroadCard(9, 37,"\033[0;107m", "\n-----------Electric company--------------- \n \nIf one utility is owned, \nrent is 4 times amount shown on dice. \n\nIf both utilities are owned,\nrent is 10 times shown on the dice.");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this utility, pay $150.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(150);
                    int[] coordinates = {0, 8};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 7 ) {
            System.out.println("You landed on States Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n      States Avenue \nRent:               $10 \nRent w Color set:   $20 \nRent with 1 ⌂:      $50 \nRent with 2 ⌂:      $150 \nRent with 3 ⌂:      $450 \nRent with 4 ⌂:      $625 \nRent with hotel:    $750 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $140.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(140);
                    int[] coordinates = {0, 7};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 6 ) {
            System.out.println("You landed on Virginia Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n     Virginia Avenue \nRent:               $12 \nRent w Color set:   $24 \nRent with 1 ⌂:      $60 \nRent with 2 ⌂:      $180 \nRent with 3 ⌂:      $500 \nRent with 4 ⌂:      $700 \nRent with hotel:    $900 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $160.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(160);
                    int[] coordinates = {0, 6};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 5 && currentPlayer.getYCoordinate() == 5 ) {
            System.out.println("You landed on Pennsylvania Railroad.");
            if (!(checkIfOwnedForAnyPlayer())) {
                RailroadCard hello = new RailroadCard(13, 29,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n----Pennsylvania Railroad------\n     Rent: $25 \n     If 2 RR is owned: $50 \n     If 3: $100 \n     If 4: $200 \n     Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200. Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(200);
                    int[] coordinates = {5, 5};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 4 ) {
            System.out.println("You landed on St. James Place.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 24,"\033[0;106m", "\n    St. James Place \nRent:               $14 \nRent w Color set:   $28 \nRent with 1 ⌂:      $70 \nRent with 2 ⌂:      $200 \nRent with 3 ⌂:      $550 \nRent with 4 ⌂:      $750 \nRent with hotel:    $950 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $180.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(140);
                    int[] coordinates = {0, 10};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 8 && currentPlayer.getYCoordinate() == 3 ) {
            System.out.println("You landed on Community Chest.");
            chest(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 2 ) {
            System.out.println("You landed on Tennessee Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;106m", "\n    Tennessee Avenue \nRent:               $12 \nRent w Color set:   $24 \nRent with 1 ⌂:      $60 \nRent with 2 ⌂:      $180 \nRent with 3 ⌂:      $500 \nRent with 4 ⌂:      $700 \nRent with hotel:    $900 \n------------------------- \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $180.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(180);
                    int[] coordinates = {0, 2};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 1 ) {
            System.out.println("You landed on New York Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;106m", "\n     New York Avenue \nRent:               $16 \nRent w Color set:   $32 \nRent with 1 ⌂:      $80 \nRent with 2 ⌂:      $220 \nRent with 3 ⌂:      $600 \nRent with 4 ⌂:      $800 \nRent with hotel:    $1000 \n------------------------- \n⌂ cost:         $100 each \nHotel cost:     $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(200);
                    int[] coordinates = {0, 1};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Free Parking.");
        }
        if (currentPlayer.getXCoordinate() == 1 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Kentucky Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n     Kentucky Avenue \nRent:               $18 \nRent w Color set:   $36 \nRent with 1 ⌂:      $90 \nRent with 2 ⌂:      $250 \nRent with 3 ⌂:      $700 \nRent with 4 ⌂:      $875 \nRent with hotel:    $1050 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $220.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(200);
                    int[] coordinates = {0, 0};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 2 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Chance.");
            chance(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 3 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Indiana Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n      Indiana Avenue \nRent:               $18 \nRent w Color set:   $36 \nRent with 1 ⌂:      $90 \nRent with 2 ⌂:      $250 \nRent with 3 ⌂:      $700 \nRent with 4 ⌂:      $875 \nRent with hotel:    $1050 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $220.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(220);
                    int[] coordinates = {2, 0};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 4 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Illinois Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n     Illinois Avenue \nRent:               $20 \nRent w Color set:   $40 \nRent with 1 ⌂:      $100 \nRent with 2 ⌂:      $300 \nRent with 3 ⌂:      $750 \nRent with 4 ⌂:      $925 \nRent with hotel:    $1100 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $240.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(240);
                    int[] coordinates = {4, 0};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 5 && currentPlayer.getYCoordinate() == 0) {
            System.out.println("You landed on B. & O. Railroad.");
            if (!(checkIfOwnedForAnyPlayer())) {
                RailroadCard hello = new RailroadCard(13, 27,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n-----B. & O. Railroad------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200.  Yes or no? ");
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(200);
                    int[] coordinates = {5, 0};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 6 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Atlantic Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Atlantic Avenue \nRent:               $22 \nRent w Color set:   $44 \nRent with 1 ⌂:      $110 \nRent with 2 ⌂:      $330 \nRent with 3 ⌂:      $800 \nRent with 4 ⌂:      $975 \nRent with hotel:    $1150 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $260.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(260);
                    int[] coordinates = {6, 0};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 7 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Ventnor Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Ventnor Avenue \nRent:               $22 \nRent w Color set:   $44 \nRent with 1 ⌂:      $110 \nRent with 2 ⌂:      $330 \nRent with 3 ⌂:      $800 \nRent with 4 ⌂:      $975 \nRent with hotel:    $1150 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $260.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(260);
                    int[] coordinates = {7, 0};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 8 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Water Works.");
            if (!(checkIfOwnedForAnyPlayer())) {
                RailroadCard hello = new RailroadCard(9, 37,"\033[0;107m", "\n-------------Water Works--------------- \n \nIf one utility is owned, \nrent is 4 times amount shown on dice. \n\nIf both utilities are owned,\nrent is 10 times shown on the dice.");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this utility, pay $150.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(150);
                    int[] coordinates = {8, 0};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 9 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Marvin Gardens.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Marvin Gardens \nRent:               $24 \nRent w Color set:   $48 \nRent with 1 ⌂:      $120 \nRent with 2 ⌂:      $360 \nRent with 3 ⌂:      $850 \nRent with 4 ⌂:      $1025 \nRent with hotel:    $1200 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $280.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(280);
                    int[] coordinates = {9, 0};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("GO TO JAIL.");
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 1 ) {
            System.out.println("You landed on Pacific avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n      Pacific Avenue \nRent:               $26 \nRent w Color set:   $52 \nRent with 1 ⌂:      $130 \nRent with 2 ⌂:      $390 \nRent with 3 ⌂:      $900 \nRent with 4 ⌂:      $1100 \nRent with hotel:    $1275 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $300.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(300);
                    int[] coordinates = {10, 1};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 2 ) {
            System.out.println("You landed on North Carolina Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n  North Carolina Avenue \nRent:               $26 \nRent w Color set:   $52 \nRent with 1 ⌂:      $130 \nRent with 2 ⌂:      $390 \nRent with 3 ⌂:      $900 \nRent with 4 ⌂:      $1100 \nRent with hotel:    $1275 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $300.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(300);
                    int[] coordinates = {10, 2};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 3 ) {
            System.out.println("You landed on Community Chest.");
            chest(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 4 ) {
            System.out.println("You landed on Pennsylvania Avenue.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n   Pennsylvania Avenue \nRent:               $28 \nRent w Color set:   $56 \nRent with 1 ⌂:      $150 \nRent with 2 ⌂:      $450 \nRent with 3 ⌂:      $1000 \nRent with 4 ⌂:      $1200 \nRent with hotel:    $1400 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $320.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(320);
                    int[] coordinates = {10, 4};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 5 ) {
            System.out.println("You landed on Short line.");
            if (!(checkIfOwnedForAnyPlayer())) {
                RailroadCard hello = new RailroadCard(13, 27,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n--------Short Line---------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(200);
                    int[] coordinates = {10, 5};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 6 ) {
            System.out.println("You landed on Chance.");
            chance(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 7 ) {
            System.out.println("You landed on Park Place.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;107m", "\n       Park Place \nRent:               $35 \nRent w Color set:   $70 \nRent with 1 ⌂:      $175 \nRent with 2 ⌂:      $500 \nRent with 3 ⌂:      $1100 \nRent with 4 ⌂:      $1300 \nRent with hotel:    $1500 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $350.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(350);
                    int[] coordinates = {10, 7};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 8 ) {
            System.out.println("You landed on Luxury Tax! It's tax season. Pay $100");
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 9 ) {
            System.out.println("You landed on Boardwalk.");
            if (!(checkIfOwnedForAnyPlayer())) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;107m", "\n        Boardwalk \nRent:               $50 \nRent w Color set:   $100 \nRent with 1 ⌂:      $200 \nRent with 2 ⌂:      $600 \nRent with 3 ⌂:      $1400 \nRent with 4 ⌂:      $1700 \nRent with hotel:    $2000 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $400.  Yes or no? ");
                answer = scan.nextLine();
                if (answer.toLowerCase().equals("yes")) {
                    player1.subtractMoney(400);
                    int[] coordinates = {10, 9};
                    currentPlayer.addToOwnedCoordinates(coordinates);
                }else {
                    System.out.println("aight");
                }
            }
        }

    }
    public void chance(Player currentplayer) {
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
    public void determiningLanding(int moves, Player currentPlayer) {
        while (moves != 0 ) {
            if (currentPlayer.getYCoordinate() == 10) {
                while (currentPlayer.getXCoordinate() != 0 && moves != 0) {
                    int remainder = moves - currentPlayer.getXCoordinate();
                    if (moves > currentPlayer.getXCoordinate() || moves == currentPlayer.getXCoordinate()) {
                        monopolyGrid.setPlayer(0, 10, currentPlayer);
                        int[] newCoord = {0, 10};
                        monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
                        currentPlayer.setCoordinates(newCoord);
                    } else if (moves < currentPlayer.getXCoordinate()) {
                        monopolyGrid.setPlayer(currentPlayer.getXCoordinate() - moves , 10, currentPlayer);
                        int[] newCoord = {currentPlayer.getXCoordinate() - moves , 10};
                        monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
                        currentPlayer.setCoordinates(newCoord);
                        remainder = 0;
                    }
                    moves = remainder;
                }
            }
            if (currentPlayer.getXCoordinate() == 0) {
                while (currentPlayer.getYCoordinate() != 0 && moves != 0) {
                    int remainder = moves - currentPlayer.getYCoordinate();
                    if (moves > currentPlayer.getYCoordinate() || moves == currentPlayer.getYCoordinate()) {
                        monopolyGrid.setPlayer(0, 0, currentPlayer);
                        int[] newCoord = {0, 0};
                        monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
                        currentPlayer.setCoordinates(newCoord);
                    } else if (moves < currentPlayer.getYCoordinate()) {
                        monopolyGrid.setPlayer(0, currentPlayer.getYCoordinate() - moves, currentPlayer);
                        int[] newCoord = {0, currentPlayer.getYCoordinate() - moves};
                        monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
                        currentPlayer.setCoordinates(newCoord);
                        remainder = 0;
                    }
                    moves = remainder;
                }
            }
            if (currentPlayer.getYCoordinate() == 0) {
                while (currentPlayer.getXCoordinate() != 10 && moves != 0) {
                    int remainder = moves -( 10 - currentPlayer.getXCoordinate() );
                    if (moves > ( 10 - currentPlayer.getXCoordinate())  || moves == ( 10 - currentPlayer.getXCoordinate())) {
                        monopolyGrid.setPlayer(10, 0, currentPlayer);
                        int[] newCoord = {10, 0};
                        monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
                        currentPlayer.setCoordinates(newCoord);
                    } else if (moves < ( 10 - currentPlayer.getXCoordinate())) {
                        monopolyGrid.setPlayer(moves + currentPlayer.getXCoordinate(), 0, currentPlayer);
                        int[] newCoord = {moves + currentPlayer.getXCoordinate(), 0};
                        monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
                        currentPlayer.setCoordinates(newCoord);
                        remainder = 0;
                    }
                    moves = remainder;
                }
            }
            if (currentPlayer.getXCoordinate() == 10) {
                while (currentPlayer.getYCoordinate() != 10 && moves != 0) {
                    int remainder = moves - (10 - currentPlayer.getYCoordinate() );
                    if (moves > ( 10 - currentPlayer.getYCoordinate() ) || moves == ( 10 - currentPlayer.getYCoordinate() ) ) {
                        monopolyGrid.setPlayer(10, 10, currentPlayer);
                        int[] newCoord = {10, 10};
                        monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
                        currentPlayer.setCoordinates(newCoord);
                    } else if (moves < 10) {
                        monopolyGrid.setPlayer(10, moves + currentPlayer.getYCoordinate(), currentPlayer);
                        int[] newCoord = {10 , moves + currentPlayer.getYCoordinate()};
                        monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
                        currentPlayer.setCoordinates(newCoord);
                        remainder = 0;
                    }
                    moves = remainder;
                }
            }
        }
    }
    public void chest(Player currentplayer) {
        int x = (int) (Math.random()*20 ) + 1;
        if (x > 18) {
            System.out.println("You inherit $100");
            currentplayer.addMoney(100);
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
            System.out.println("Bank pays you 50 dollars +50");
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
