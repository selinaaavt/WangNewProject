import java.sql.PseudoColumnUsage;
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
            scan.nextLine();
        } else {
            System.out.println("Bye!");
            System.exit(0);
        }
    }
    public void createPlayers(int howMany) {
        while (howMany <= 1 || howMany >= 5) {
            System.out.println("That is an invalid number of players.");
            System.out.print("Enter the amount of players: ");
            howMany = scan.nextInt();
            scan.nextLine();
        }
        amountOfPlayers = howMany;
        scan.nextLine();
        if (amountOfPlayers == 2) {
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
        while (currentPlayer != null && currentPlayer.isAlive()) {
            String answer = "";
            System.out.println("It is " + currentPlayer.getName() + "'s turn! ");
            if (currentPlayer.isInJail()) {
                if (!(currentPlayer.jailTurn())) {
                    System.out.print("Do you wish to pay to get out of jail? ");
                    answer = scan.nextLine();
                    if (answer.equals("yes") && currentPlayer.getMoney() >= 50) {
                        System.out.println("You are out of jail!");
                        currentPlayer.setInJail(false);
                    } else if (currentPlayer.getMoney() < 50) {
                        System.out.println("You do not have enough money. :(");
                    } else {
                        System.out.println("aight");
                    }
                }
            } else {
                while (!(answer.toLowerCase().equals("r"))) {
                    System.out.println("Options: \n(V)iew your properties \n(C)heck bank \n(R)oll");
                    answer = scan.nextLine();
                    if (answer.equals("r")) {
                        int moves = rollDice();
                        System.out.println(currentPlayer.getName() + " rolled a " + moves);
                        determiningLanding(moves, currentPlayer);
                        monopolyGrid.printGrid();
                    } else if (answer.equals("c")) {
                        System.out.println("You currently have $" + currentPlayer.getMoney() + " in your bank account");
                    } else if (answer.equals("v")) {
                        currentPlayer.printProperties();
                    }
                }
            }
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
        String okie = " ";
        if (currentPlayer.getXCoordinate() == 9 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Mediterranean Avenue.");
            if (!(checkIfOwnedForAnyPlayer(9, 10))) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[40;1m", "\n  Mediterranean Avenue\nRent:               $2 \nRent w Color set:   $4 \nRent with 1 ⌂:      $10 \nRent with 2 ⌂:      $30 \nRent with 3 ⌂:      $90 \nRent with 4 ⌂:      $160 \nRent with hotel:    $250 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $60. Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 60) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(60);
                        Possession newHouse = new Possession("Mediterranean Avenue", 9, 10, 50);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(9, 10) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(9, 10,50);
            } else {
                String x = determiningHierachy(9, 10);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$250. +$250 for " + checkWhichPlayerOwns(9, 10).getName());
                    currentPlayer.subtractMoney(250);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(9, 10).addMoney(250);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$4. +$4 for " + checkWhichPlayerOwns(9, 10).getName());
                    currentPlayer.subtractMoney(4);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(9, 10).addMoney(4);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$2. +$2 for " + checkWhichPlayerOwns(9, 10).getName());
                    currentPlayer.subtractMoney(2);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(9, 10).addMoney(2);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$10. +$10 for " + checkWhichPlayerOwns(9, 10).getName());
                        currentPlayer.subtractMoney(10);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(9, 10).addMoney(10);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$30. +$30 for " + checkWhichPlayerOwns(9, 10).getName());
                        currentPlayer.subtractMoney(30);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(9, 10).addMoney(30);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$90. +$90 for " + checkWhichPlayerOwns(9, 10).getName());
                        currentPlayer.subtractMoney(90);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(9, 10).addMoney(90);
                    } else {
                        System.out.println("Wow thats a lotta money, -$160. +$160 for " + checkWhichPlayerOwns(9, 10).getName());
                        currentPlayer.subtractMoney(160);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(9, 10).addMoney(160);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[40;1m", "\n  Mediterranean Avenue\nRent:               $2 \nRent w Color set:   $4 \nRent with 1 ⌂:      $10 \nRent with 2 ⌂:      $30 \nRent with 3 ⌂:      $90 \nRent with 4 ⌂:      $160 \nRent with hotel:    $250 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 8 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Community Chest.");
            chest(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 7 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Baltic Avenue.");
            if (!(checkIfOwnedForAnyPlayer(7, 10))) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[40;1m", "\n     Baltic Avenue\nRent:               $4 \nRent w Color set:   $8 \nRent with 1 ⌂:      $20 \nRent with 2 ⌂:      $60 \nRent with 3 ⌂:      $180 \nRent with 4 ⌂:      $320 \nRent with hotel:    $450 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $60.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 60) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(60);
                        Possession newHouse = new Possession("Baltic Avenue", 7, 10, 50);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                } else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(7, 10) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(7, 10,50);
            } else {
                String x = determiningHierachy(7, 10);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$450. +$450 for " + checkWhichPlayerOwns(7, 10).getName());
                    currentPlayer.subtractMoney(450);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(7, 10).addMoney(450);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$8. +$8 for " + checkWhichPlayerOwns(7, 10).getName());
                    currentPlayer.subtractMoney(8);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(7, 10).addMoney(8);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$4. +$4 for " + checkWhichPlayerOwns(7, 10).getName());
                    currentPlayer.subtractMoney(4);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(7, 10).addMoney(4);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$20. +$20 for " + checkWhichPlayerOwns(7, 10).getName());
                        currentPlayer.subtractMoney(20);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(7, 10).addMoney(20);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$60. +$60 for " + checkWhichPlayerOwns(7, 10).getName());
                        currentPlayer.subtractMoney(60);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(7, 10).addMoney(60);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$180. +$180 for " + checkWhichPlayerOwns(7, 10).getName());
                        currentPlayer.subtractMoney(180);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(7, 10).addMoney(180);
                    } else {
                        System.out.println("Wow thats a lotta money, -$320. +$320 for " + checkWhichPlayerOwns(7, 10).getName());
                        currentPlayer.subtractMoney(320);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(7, 10).addMoney(320);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[40;1m", "\n     Baltic Avenue\nRent:               $4 \nRent w Color set:   $8 \nRent with 1 ⌂:      $20 \nRent with 2 ⌂:      $60 \nRent with 3 ⌂:      $180 \nRent with 4 ⌂:      $320 \nRent with hotel:    $450 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 6 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Income Tax! It's tax season. Pay $200");
            currentPlayer.subtractMoney(200);
            currentPlayer.checkIfDead();
        }
        if (currentPlayer.getXCoordinate() == 5 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Reading Railroad.");
            if (!(checkIfOwnedForAnyPlayer(5,10))) {
                RailroadCard hello = new RailroadCard(13, 28,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n------Reading Railroad------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200. Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 200) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(200);
                        Railroad newHouse = new Railroad("Reading Railroad", 6, 10, 0);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(5, 10) != currentPlayer) {
                int railroadOwned = checkWhichPlayerOwns(5, 10).howManyRailRoads();
                if (railroadOwned == 1) {
                    System.out.println("Wow thats a lotta money, -$25. +$25 for " + checkWhichPlayerOwns(5, 10).getName());
                    currentPlayer.subtractMoney(25);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(5, 10).addMoney(25);
                } else if (railroadOwned == 2) {
                    System.out.println("Wow thats a lotta money, -$25. +$25 for " + checkWhichPlayerOwns(5, 10).getName());
                    currentPlayer.subtractMoney(50);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(5, 10).addMoney(50);
                } else if (railroadOwned == 3) {
                    System.out.println("Wow thats a lotta money, -$100. +$100 for " + checkWhichPlayerOwns(5, 10).getName());
                    currentPlayer.subtractMoney(100);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(5, 10).addMoney(100);
                } else if (railroadOwned == 4) {
                    System.out.println("Wow thats a lotta money, -$200. +$200 for " + checkWhichPlayerOwns(5, 10).getName());
                    currentPlayer.subtractMoney(200);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(5, 10).addMoney(200);
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                RailroadCard hello = new RailroadCard(13, 28,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n------Reading Railroad------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
            }
            System.out.print("Do you wish to buy any houses/hotels on any of ur property? ");
            okie = scan.nextLine();
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 4 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Oriental Avenue. ");
            if (!(checkIfOwnedForAnyPlayer(4, 10))) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n    Oriental Avenue \nRent:               $6 \nRent w Color set:   $12 \nRent with 1 ⌂:      $30 \nRent with 2 ⌂:      $90 \nRent with 3 ⌂:      $270 \nRent with 4 ⌂:      $400 \nRent with hotel:    $550 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $100. Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 100) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(100);
                        Possession newHouse = new Possession("Oriental Avenue", 4, 10, 50);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(4, 10) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(4, 10,50);
            } else {
                String x = determiningHierachy(4, 10);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$550. +$550 for " + checkWhichPlayerOwns(4, 10).getName());
                    currentPlayer.subtractMoney(550);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(4, 10).addMoney(550);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$12. +$12 for " + checkWhichPlayerOwns(4, 10).getName());
                    currentPlayer.subtractMoney(12);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(4, 10).addMoney(12);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$6. +$6 for " + checkWhichPlayerOwns(4, 10).getName());
                    currentPlayer.subtractMoney(6);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(4, 10).addMoney(6);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$30. +$30 for " + checkWhichPlayerOwns(4, 10).getName());
                        currentPlayer.subtractMoney(30);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(4, 10).addMoney(30);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$90. +$90 for " + checkWhichPlayerOwns(4, 10).getName());
                        currentPlayer.subtractMoney(90);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(4, 10).addMoney(90);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$270. +$270 for " + checkWhichPlayerOwns(4, 10).getName());
                        currentPlayer.subtractMoney(270);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(4, 10).addMoney(270);
                    } else {
                        System.out.println("Wow thats a lotta money, -$400. +$400 for " + checkWhichPlayerOwns(4, 10).getName());
                        currentPlayer.subtractMoney(400);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(4, 10).addMoney(400);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n    Oriental Avenue \nRent:               $6 \nRent w Color set:   $12 \nRent with 1 ⌂:      $30 \nRent with 2 ⌂:      $90 \nRent with 3 ⌂:      $270 \nRent with 4 ⌂:      $400 \nRent with hotel:    $550 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 3 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Chance.");
            chance(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 2 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Vermont Avenue.");
            if (!(checkIfOwnedForAnyPlayer(2, 10))) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n     Vermont Avenue\nRent:               $6 \nRent w Color set:   $12 \nRent with 1 ⌂:      $30 \nRent with 2 ⌂:      $90 \nRent with 3 ⌂:      $270 \nRent with 4 ⌂:      $400 \nRent with hotel:    $550 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $100.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 100) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(100);
                        Possession newHouse = new Possession("Vermont Avenue", 2, 10, 50);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(2, 10) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(2, 10,50);
            } else {
                String x = determiningHierachy(2, 10);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$550. +$550 for " + checkWhichPlayerOwns(2, 10).getName());
                    currentPlayer.subtractMoney(550);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(2, 10).addMoney(550);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$12. +$12 for " + checkWhichPlayerOwns(2, 10).getName());
                    currentPlayer.subtractMoney(12);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(2, 10).addMoney(12);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$6. +$6 for " + checkWhichPlayerOwns(2, 10).getName());
                    currentPlayer.subtractMoney(6);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(2, 10).addMoney(6);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$30. +$30 for " + checkWhichPlayerOwns(2, 10).getName());
                        currentPlayer.subtractMoney(30);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(2, 10).addMoney(30);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$90. +$90 for " + checkWhichPlayerOwns(2, 10).getName());
                        currentPlayer.subtractMoney(90);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(2, 10).addMoney(90);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$270. +$270 for " + checkWhichPlayerOwns(2, 10).getName());
                        currentPlayer.subtractMoney(270);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(2, 10).addMoney(270);
                    } else {
                        System.out.println("Wow thats a lotta money, -$400. +$400 for " + checkWhichPlayerOwns(2, 10).getName());
                        currentPlayer.subtractMoney(400);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(2, 10).addMoney(400);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n     Vermont Avenue\nRent:               $6 \nRent w Color set:   $12 \nRent with 1 ⌂:      $30 \nRent with 2 ⌂:      $90 \nRent with 3 ⌂:      $270 \nRent with 4 ⌂:      $400 \nRent with hotel:    $550 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 1 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Connecticut Avenue.");
            if (!(checkIfOwnedForAnyPlayer(1, 10))) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n   Connecticut Avenue\nRent:               $8 \nRent w Color set:   $16 \nRent with 1 ⌂:      $40 \nRent with 2 ⌂:      $100 \nRent with 3 ⌂:      $300 \nRent with 4 ⌂:      $450 \nRent with hotel:    $600 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $100.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    currentPlayer.subtractMoney(100);
                    Possession newHouse = new Possession("Connecticut Avenue", 1, 10, 50);
                    currentPlayer.addToOwnedCoordinates(newHouse);
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(1, 10) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(1, 10,50);
            } else {
                String x = determiningHierachy(1, 10);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$600. +$600 for " + checkWhichPlayerOwns(1, 10).getName());
                    currentPlayer.subtractMoney(600);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(1, 10).addMoney(600);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$16. +$16 for " + checkWhichPlayerOwns(1, 10).getName());
                    currentPlayer.subtractMoney(16);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(1, 10).addMoney(16);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$8. +$8 for " + checkWhichPlayerOwns(1, 10).getName());
                    currentPlayer.subtractMoney(8);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(1, 10).addMoney(8);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$40. +$40 for " + checkWhichPlayerOwns(1, 10).getName());
                        currentPlayer.subtractMoney(40);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(1, 10).addMoney(40);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$100. +$100 for " + checkWhichPlayerOwns(1, 10).getName());
                        currentPlayer.subtractMoney(100);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(1, 10).addMoney(100);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$300. +$300 for " + checkWhichPlayerOwns(1, 10).getName());
                        currentPlayer.subtractMoney(300);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(1, 10).addMoney(300);
                    } else {
                        System.out.println("Wow thats a lotta money, -$450. +$450 for " + checkWhichPlayerOwns(1, 10).getName());
                        currentPlayer.subtractMoney(450);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(1, 10).addMoney(450);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[44;1m", "\n   Connecticut Avenue\nRent:               $8 \nRent w Color set:   $16 \nRent with 1 ⌂:      $40 \nRent with 2 ⌂:      $100 \nRent with 3 ⌂:      $300 \nRent with 4 ⌂:      $450 \nRent with hotel:    $600 \n------------------------ \n⌂ cost:         $50 each \nHotel cost:     $50 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 10 ) {
            System.out.println("You landed on Jail! But you’re just visiting!");
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 9 ) {
            System.out.println("You landed on St. Charles Place. ");
            if (!(checkIfOwnedForAnyPlayer(0, 9))) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n   St. Charles Place \nRent:               $10 \nRent w Color set:   $20 \nRent with 1 ⌂:      $50 \nRent with 2 ⌂:      $150 \nRent with 3 ⌂:      $450 \nRent with 4 ⌂:      $625 \nRent with hotel:    $750 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $140.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 140) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(140);
                        Possession newHouse = new Possession("St. Charles Place", 0, 9, 100);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                } else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(0, 9) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(0, 9,100);
            } else {
                String x = determiningHierachy(0, 9);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$750. +$750 for " + checkWhichPlayerOwns(0, 9).getName());
                    currentPlayer.subtractMoney(750);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 9).addMoney(750);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$20. +$20 for " + checkWhichPlayerOwns(0, 9).getName());
                    currentPlayer.subtractMoney(20);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 9).addMoney(20);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$10. +$10 for " + checkWhichPlayerOwns(0, 9).getName());
                    currentPlayer.subtractMoney(10);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 9).addMoney(10);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$50. +$50 for " + checkWhichPlayerOwns(0, 9).getName());
                        currentPlayer.subtractMoney(50);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 9).addMoney(50);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$150. +$150 for " + checkWhichPlayerOwns(0, 9).getName());
                        currentPlayer.subtractMoney(150);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 9).addMoney(150);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$450. +$450 for " + checkWhichPlayerOwns(0, 9).getName());
                        currentPlayer.subtractMoney(450);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 9).addMoney(450);
                    } else {
                        System.out.println("Wow thats a lotta money, -$625. +$625 for " + checkWhichPlayerOwns(0, 9).getName());
                        currentPlayer.subtractMoney(625);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 9).addMoney(625);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n   St. Charles Place \nRent:               $10 \nRent w Color set:   $20 \nRent with 1 ⌂:      $50 \nRent with 2 ⌂:      $150 \nRent with 3 ⌂:      $450 \nRent with 4 ⌂:      $625 \nRent with hotel:    $750 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 8 ) {
            System.out.println("You landed on Electric company.");
            if (!(checkIfOwnedForAnyPlayer(0, 8))) {
                RailroadCard hello = new RailroadCard(9, 37,"\033[0;107m", "\n-----------Electric company--------------- \n \nIf one utility is owned, \nrent is 4 times amount shown on dice. \n\nIf both utilities are owned,\nrent is 10 times shown on the dice.");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this utility, pay $150.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 150) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(150);
                        Possession newHouse = new Possession("Electric Company", 0, 8, 0);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                } else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(0, 8) == currentPlayer) {
                System.out.println("You landed on your property!");
            } else if (checkWhichPlayerOwns(0, 8).ownAllUtilities(0, 8) && checkWhichPlayerOwns(0, 8) != currentPlayer){
                int moneyAtFirst = (int) (Math.random()*6) + 1;
                int moneyOwned = moneyAtFirst*10;
                System.out.println("You rolled a " + moneyAtFirst + "Since both utilities are owned, x 10: " + moneyOwned);
                System.out.println("Wow thats a lotta money, -" + moneyOwned + ". +" + moneyOwned + " for " + checkWhichPlayerOwns(0, 8).getName());
                currentPlayer.subtractMoney(moneyOwned);
                currentPlayer.checkIfDead();
                checkWhichPlayerOwns(0, 8).addMoney(moneyOwned);
            } else {
                int moneyAtFirst = (int) (Math.random()*6) + 1;
                int moneyOwned = moneyAtFirst*4;
                System.out.println("You rolled a " + moneyAtFirst + "Since one utility are owned, x 4: " + moneyOwned);
                System.out.println("Wow thats a lotta money, -" + moneyOwned + ". +" + moneyOwned + " for " + checkWhichPlayerOwns(0, 8).getName());
                currentPlayer.subtractMoney(moneyOwned);
                currentPlayer.checkIfDead();
                checkWhichPlayerOwns(0, 8).addMoney(moneyOwned);
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                RailroadCard hello = new RailroadCard(9, 37,"\033[0;107m", "\n-----------Electric company--------------- \n \nIf one utility is owned, \nrent is 4 times amount shown on dice. \n\nIf both utilities are owned,\nrent is 10 times shown on the dice.");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 7 ) {
            System.out.println("You landed on States Avenue.");
            if (!(checkIfOwnedForAnyPlayer(0, 7)) ) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n      States Avenue \nRent:               $10 \nRent w Color set:   $20 \nRent with 1 ⌂:      $50 \nRent with 2 ⌂:      $150 \nRent with 3 ⌂:      $450 \nRent with 4 ⌂:      $625 \nRent with hotel:    $750 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $140.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 140) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(140);
                        Possession newHouse = new Possession("States Avenue", 0, 7, 100);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(0, 7) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(0, 7,100);
            } else {
                String x = determiningHierachy(0, 7);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$750. +$750 for " + checkWhichPlayerOwns(0, 7).getName());
                    currentPlayer.subtractMoney(750);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 7).addMoney(750);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$20. +$20 for " + checkWhichPlayerOwns(0, 7).getName());
                    currentPlayer.subtractMoney(20);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 7).addMoney(20);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$10. +$10 for " + checkWhichPlayerOwns(0, 7).getName());
                    currentPlayer.subtractMoney(10);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 7).addMoney(10);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$50. +$50 for " + checkWhichPlayerOwns(0, 7).getName());
                        currentPlayer.subtractMoney(50);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 7).addMoney(50);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$150. +$150 for " + checkWhichPlayerOwns(0, 7).getName());
                        currentPlayer.subtractMoney(150);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 7).addMoney(150);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$450. +$450 for " + checkWhichPlayerOwns(0, 7).getName());
                        currentPlayer.subtractMoney(450);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 7).addMoney(450);
                    } else {
                        System.out.println("Wow thats a lotta money, -$625. +$625 for " + checkWhichPlayerOwns(0, 7).getName());
                        currentPlayer.subtractMoney(625);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 7).addMoney(625);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n      States Avenue \nRent:               $10 \nRent w Color set:   $20 \nRent with 1 ⌂:      $50 \nRent with 2 ⌂:      $150 \nRent with 3 ⌂:      $450 \nRent with 4 ⌂:      $625 \nRent with hotel:    $750 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 6 ) {
            System.out.println("You landed on Virginia Avenue.");
            if (!(checkIfOwnedForAnyPlayer(0, 6))) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n     Virginia Avenue \nRent:               $12 \nRent w Color set:   $24 \nRent with 1 ⌂:      $60 \nRent with 2 ⌂:      $180 \nRent with 3 ⌂:      $500 \nRent with 4 ⌂:      $700 \nRent with hotel:    $900 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $160.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 160) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(160);
                        Possession newHouse = new Possession("Virginia Avenue", 0, 6, 100);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            }  else if (checkWhichPlayerOwns(0, 6) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(0, 6,100);
            } else {
                String x = determiningHierachy(0, 6);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$900. +$900 for " + checkWhichPlayerOwns(0, 6).getName());
                    currentPlayer.subtractMoney(900);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 6).addMoney(900);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$24. +$24 for " + checkWhichPlayerOwns(0, 6).getName());
                    currentPlayer.subtractMoney(24);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 6).addMoney(24);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$12. +$12 for " + checkWhichPlayerOwns(0, 6).getName());
                    currentPlayer.subtractMoney(12);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 6).addMoney(12);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$60. +$60 for " + checkWhichPlayerOwns(0, 6).getName());
                        currentPlayer.subtractMoney(60);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 6).addMoney(60);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$180. +$180 for " + checkWhichPlayerOwns(0, 6).getName());
                        currentPlayer.subtractMoney(180);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 6).addMoney(180);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$500. +$500 for " + checkWhichPlayerOwns(0, 6).getName());
                        currentPlayer.subtractMoney(500);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 6).addMoney(500);
                    } else {
                        System.out.println("Wow thats a lotta money, -$700. +$700 for " + checkWhichPlayerOwns(0, 6).getName());
                        currentPlayer.subtractMoney(700);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 6).addMoney(700);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\u001B[45;1m", "\n     Virginia Avenue \nRent:               $12 \nRent w Color set:   $24 \nRent with 1 ⌂:      $60 \nRent with 2 ⌂:      $180 \nRent with 3 ⌂:      $500 \nRent with 4 ⌂:      $700 \nRent with hotel:    $900 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 5 ) {
            System.out.println("You landed on Pennsylvania Railroad.");
            if (!(checkIfOwnedForAnyPlayer(0, 5)) ) {
                RailroadCard hello = new RailroadCard(13, 29,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n----Pennsylvania Railroad------\n     Rent: $25 \n     If 2 RR is owned: $50 \n     If 3: $100 \n     If 4: $200 \n     Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200. Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 200) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(200);
                        Railroad newHouse = new Railroad("Pennsylvania Railroad", 0, 5,0);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(0, 5) != currentPlayer) {
                int railroadOwned = checkWhichPlayerOwns(0, 5).howManyRailRoads();
                if (railroadOwned == 1) {
                    System.out.println("Wow thats a lotta money, -$25. +$25 for " + checkWhichPlayerOwns(0, 5).getName());
                    currentPlayer.subtractMoney(25);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 5).addMoney(25);
                } else if (railroadOwned == 2) {
                    System.out.println("Wow thats a lotta money, -$25. +$25 for " + checkWhichPlayerOwns(0, 5).getName());
                    currentPlayer.subtractMoney(50);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 5).addMoney(50);
                } else if (railroadOwned == 3) {
                    System.out.println("Wow thats a lotta money, -$100. +$100 for " + checkWhichPlayerOwns(0, 5).getName());
                    currentPlayer.subtractMoney(100);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 5).addMoney(100);
                } else if (railroadOwned == 4) {
                    System.out.println("Wow thats a lotta money, -$200. +$200 for " + checkWhichPlayerOwns(0, 5).getName());
                    currentPlayer.subtractMoney(200);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 5).addMoney(200);
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                RailroadCard hello = new RailroadCard(13, 29,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n----Pennsylvania Railroad------\n     Rent: $25 \n     If 2 RR is owned: $50 \n     If 3: $100 \n     If 4: $200 \n     Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 4 ) {
            System.out.println("You landed on St. James Place.");
            if (!(checkIfOwnedForAnyPlayer(0, 4)) ) {
                HouseCard hello = new HouseCard(13, 24,"\033[0;106m", "\n    St. James Place \nRent:               $14 \nRent w Color set:   $28 \nRent with 1 ⌂:      $70 \nRent with 2 ⌂:      $200 \nRent with 3 ⌂:      $550 \nRent with 4 ⌂:      $750 \nRent with hotel:    $950 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $180.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 180) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(180);
                        Possession newHouse = new Possession("St. James Place", 0, 4,100);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(0, 4) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(0, 4,100);
            } else {
                String x = determiningHierachy(0, 4);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$950. +$950 for " + checkWhichPlayerOwns(0, 4).getName());
                    currentPlayer.subtractMoney(950);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 4).addMoney(950);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$28. +$28 for " + checkWhichPlayerOwns(0, 4).getName());
                    currentPlayer.subtractMoney(28);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 4).addMoney(28);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$14. +$14 for " + checkWhichPlayerOwns(0, 4).getName());
                    currentPlayer.subtractMoney(14);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 4).addMoney(14);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$70. +$70 for " + checkWhichPlayerOwns(0, 4).getName());
                        currentPlayer.subtractMoney(70);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 4).addMoney(70);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$200. +$200 for " + checkWhichPlayerOwns(0, 4).getName());
                        currentPlayer.subtractMoney(200);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 4).addMoney(200);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$550. +$550 for " + checkWhichPlayerOwns(0, 4).getName());
                        currentPlayer.subtractMoney(550);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 4).addMoney(550);
                    } else {
                        System.out.println("Wow thats a lotta money, -$750. +$750 for " + checkWhichPlayerOwns(0, 4).getName());
                        currentPlayer.subtractMoney(750);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 4).addMoney(750);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 24,"\033[0;106m", "\n    St. James Place \nRent:               $14 \nRent w Color set:   $28 \nRent with 1 ⌂:      $70 \nRent with 2 ⌂:      $200 \nRent with 3 ⌂:      $550 \nRent with 4 ⌂:      $750 \nRent with hotel:    $950 \n------------------------ \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 8 && currentPlayer.getYCoordinate() == 3 ) {
            System.out.println("You landed on Community Chest.");
            chest(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 2 ) {
            System.out.println("You landed on Tennessee Avenue.");
            if (!(checkIfOwnedForAnyPlayer(0, 2)) ) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;106m", "\n    Tennessee Avenue \nRent:               $12 \nRent w Color set:   $24 \nRent with 1 ⌂:      $60 \nRent with 2 ⌂:      $180 \nRent with 3 ⌂:      $500 \nRent with 4 ⌂:      $700 \nRent with hotel:    $900 \n------------------------- \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $180.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 180) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(180);
                        Possession newHouse = new Possession("Tennessee Avenue", 0, 2, 100);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(0, 2) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(0, 2,100);
            } else {
                String x = determiningHierachy(0, 2);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$900. +$900 for " + checkWhichPlayerOwns(0, 2).getName());
                    currentPlayer.subtractMoney(900);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 2).addMoney(900);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$24. +$24 for " + checkWhichPlayerOwns(0, 2).getName());
                    currentPlayer.subtractMoney(24);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 2).addMoney(24);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$12. +$12 for " + checkWhichPlayerOwns(0, 2).getName());
                    currentPlayer.subtractMoney(12);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 2).addMoney(12);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$60. +$60 for " + checkWhichPlayerOwns(0, 2).getName());
                        currentPlayer.subtractMoney(60);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 2).addMoney(60);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$180. +$180 for " + checkWhichPlayerOwns(0, 2).getName());
                        currentPlayer.subtractMoney(180);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 2).addMoney(180);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$500. +$500 for " + checkWhichPlayerOwns(0, 2).getName());
                        currentPlayer.subtractMoney(500);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 2).addMoney(500);
                    } else {
                        System.out.println("Wow thats a lotta money, -$700. +$700 for " + checkWhichPlayerOwns(0, 2).getName());
                        currentPlayer.subtractMoney(700);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 2).addMoney(700);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;106m", "\n    Tennessee Avenue \nRent:               $12 \nRent w Color set:   $24 \nRent with 1 ⌂:      $60 \nRent with 2 ⌂:      $180 \nRent with 3 ⌂:      $500 \nRent with 4 ⌂:      $700 \nRent with hotel:    $900 \n------------------------- \n⌂ cost:        $100 each \nHotel cost:    $100 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 1 ) {
            System.out.println("You landed on New York Avenue.");
            if (!(checkIfOwnedForAnyPlayer(0, 1))) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;106m", "\n     New York Avenue \nRent:               $16 \nRent w Color set:   $32 \nRent with 1 ⌂:      $80 \nRent with 2 ⌂:      $220 \nRent with 3 ⌂:      $600 \nRent with 4 ⌂:      $800 \nRent with hotel:    $1000 \n------------------------- \n⌂ cost:         $100 each \nHotel cost:     $100 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 200) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(200);
                        Possession newHouse = new Possession("New York Avenue", 0, 1, 100);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(0, 1) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(0, 1,100);
            } else {
                String x = determiningHierachy(0, 1);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1000. +$1000 for " + checkWhichPlayerOwns(0, 1).getName());
                    currentPlayer.subtractMoney(1000);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 1).addMoney(1000);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$32. +$32 for " + checkWhichPlayerOwns(0, 1).getName());
                    currentPlayer.subtractMoney(32);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 1).addMoney(32);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$16. +$16 for " + checkWhichPlayerOwns(0, 1).getName());
                    currentPlayer.subtractMoney(16);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(0, 1).addMoney(16);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$80. +$80 for " + checkWhichPlayerOwns(0, 1).getName());
                        currentPlayer.subtractMoney(80);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 1).addMoney(80);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$220. +$220 for " + checkWhichPlayerOwns(0, 1).getName());
                        currentPlayer.subtractMoney(220);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 1).addMoney(220);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$600. +$600 for " + checkWhichPlayerOwns(0, 1).getName());
                        currentPlayer.subtractMoney(600);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 1).addMoney(600);
                    } else {
                        System.out.println("Wow thats a lotta money, -$800. +$800 for " + checkWhichPlayerOwns(0, 1).getName());
                        currentPlayer.subtractMoney(800);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(0, 1).addMoney(800);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;106m", "\n     New York Avenue \nRent:               $16 \nRent w Color set:   $32 \nRent with 1 ⌂:      $80 \nRent with 2 ⌂:      $220 \nRent with 3 ⌂:      $600 \nRent with 4 ⌂:      $800 \nRent with hotel:    $1000 \n------------------------- \n⌂ cost:         $100 each \nHotel cost:     $100 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 0 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Free Parking.");
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 1 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Kentucky Avenue.");
            if (!(checkIfOwnedForAnyPlayer(1, 0))) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n     Kentucky Avenue \nRent:               $18 \nRent w Color set:   $36 \nRent with 1 ⌂:      $90 \nRent with 2 ⌂:      $250 \nRent with 3 ⌂:      $700 \nRent with 4 ⌂:      $875 \nRent with hotel:    $1050 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $220.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 200) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(200);
                        Possession newHouse = new Possession("Kentucky Avenue", 1, 0, 150);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(1, 0) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(1, 0,150);
            } else {
                String x = determiningHierachy(1, 0);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1050. +$1050 for " + checkWhichPlayerOwns(1, 0).getName());
                    currentPlayer.subtractMoney(1050);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(1, 0).addMoney(1050);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$36. +$36 for " + checkWhichPlayerOwns(1, 0).getName());
                    currentPlayer.subtractMoney(36);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(1, 0).addMoney(36);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$18. +$18 for " + checkWhichPlayerOwns(1, 0).getName());
                    currentPlayer.subtractMoney(18);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(1, 0).addMoney(18);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$90. +$90 for " + checkWhichPlayerOwns(1, 0).getName());
                        currentPlayer.subtractMoney(90);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(1, 0).addMoney(90);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$250. +$250 for " + checkWhichPlayerOwns(1, 0).getName());
                        currentPlayer.subtractMoney(250);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(1, 0).addMoney(250);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$700. +$700 for " + checkWhichPlayerOwns(1, 0).getName());
                        currentPlayer.subtractMoney(700);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(1, 0).addMoney(700);
                    } else {
                        System.out.println("Wow thats a lotta money, -$875. +$875 for " + checkWhichPlayerOwns(1, 0).getName());
                        currentPlayer.subtractMoney(875);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(1, 0).addMoney(875);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n     Kentucky Avenue \nRent:               $18 \nRent w Color set:   $36 \nRent with 1 ⌂:      $90 \nRent with 2 ⌂:      $250 \nRent with 3 ⌂:      $700 \nRent with 4 ⌂:      $875 \nRent with hotel:    $1050 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 2 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Chance.");
            chance(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 3 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Indiana Avenue.");
            if (!(checkIfOwnedForAnyPlayer(3, 0)) ) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n      Indiana Avenue \nRent:               $18 \nRent w Color set:   $36 \nRent with 1 ⌂:      $90 \nRent with 2 ⌂:      $250 \nRent with 3 ⌂:      $700 \nRent with 4 ⌂:      $875 \nRent with hotel:    $1050 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $220.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 220) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(220);
                        Possession newHouse = new Possession("Indiana Avenue", 3, 0, 150);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(3, 0) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(3, 0,150);
            } else {
                String x = determiningHierachy(3, 0);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1050. +$1050 for " + checkWhichPlayerOwns(3, 0).getName());
                    currentPlayer.subtractMoney(1050);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(3, 0).addMoney(1050);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$36. +$36 for " + checkWhichPlayerOwns(3, 0).getName());
                    currentPlayer.subtractMoney(36);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(3, 0).addMoney(36);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$18. +$18 for " + checkWhichPlayerOwns(3, 0).getName());
                    currentPlayer.subtractMoney(18);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(3, 0).addMoney(18);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$90. +$90 for " + checkWhichPlayerOwns(3, 0).getName());
                        currentPlayer.subtractMoney(90);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(3, 0).addMoney(90);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$250. +$250 for " + checkWhichPlayerOwns(3, 0).getName());
                        currentPlayer.subtractMoney(250);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(3, 0).addMoney(250);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$700. +$700 for " + checkWhichPlayerOwns(3, 0).getName());
                        currentPlayer.subtractMoney(700);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(3, 0).addMoney(700);
                    } else {
                        System.out.println("Wow thats a lotta money, -$875. +$875 for " + checkWhichPlayerOwns(3, 0).getName());
                        currentPlayer.subtractMoney(875);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(3, 0).addMoney(875);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n      Indiana Avenue \nRent:               $18 \nRent w Color set:   $36 \nRent with 1 ⌂:      $90 \nRent with 2 ⌂:      $250 \nRent with 3 ⌂:      $700 \nRent with 4 ⌂:      $875 \nRent with hotel:    $1050 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 4 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Illinois Avenue.");
            if (!(checkIfOwnedForAnyPlayer(4, 0))  ) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n     Illinois Avenue \nRent:               $20 \nRent w Color set:   $40 \nRent with 1 ⌂:      $100 \nRent with 2 ⌂:      $300 \nRent with 3 ⌂:      $750 \nRent with 4 ⌂:      $925 \nRent with hotel:    $1100 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $240.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 240) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(240);
                        Possession newHouse = new Possession("Illinois Avenue", 4, 0,150);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(4, 0) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(4, 0,150);
            } else {
                String x = determiningHierachy(4, 0);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1100. +$1100 for " + checkWhichPlayerOwns(4, 0).getName());
                    currentPlayer.subtractMoney(1100);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(4, 0).addMoney(1100);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$40. +$40 for " + checkWhichPlayerOwns(4, 0).getName());
                    currentPlayer.subtractMoney(40);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(4, 0).addMoney(40);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$20. +$20 for " + checkWhichPlayerOwns(4, 0).getName());
                    currentPlayer.subtractMoney(20);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(4, 0).addMoney(20);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$100. +$100 for " + checkWhichPlayerOwns(4, 0).getName());
                        currentPlayer.subtractMoney(100);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(4, 0).addMoney(100);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$300. +$300 for " + checkWhichPlayerOwns(4, 0).getName());
                        currentPlayer.subtractMoney(300);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(4, 0).addMoney(300);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$750. +$750 for " + checkWhichPlayerOwns(4, 0).getName());
                        currentPlayer.subtractMoney(750);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(4, 0).addMoney(750);
                    } else {
                        System.out.println("Wow thats a lotta money, -$925. +$925 for " + checkWhichPlayerOwns(4, 0).getName());
                        currentPlayer.subtractMoney(925);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(4, 0).addMoney(925);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;101m", "\n     Illinois Avenue \nRent:               $20 \nRent w Color set:   $40 \nRent with 1 ⌂:      $100 \nRent with 2 ⌂:      $300 \nRent with 3 ⌂:      $750 \nRent with 4 ⌂:      $925 \nRent with hotel:    $1100 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 5 && currentPlayer.getYCoordinate() == 0) {
            System.out.println("You landed on B. & O. Railroad.");
            if (!(checkIfOwnedForAnyPlayer(5, 0)) ) {
                RailroadCard hello = new RailroadCard(13, 27,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n-----B. & O. Railroad------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 200) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(200);
                        Railroad newHouse = new Railroad("B. & O. Railroad", 5, 0, 0);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(5, 0) != currentPlayer) {
                int railroadOwned = checkWhichPlayerOwns(5, 0).howManyRailRoads();
                if (railroadOwned == 1) {
                    System.out.println("Wow thats a lotta money, -$25. +$25 for " + checkWhichPlayerOwns(5, 0).getName());
                    currentPlayer.subtractMoney(25);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(5, 0).addMoney(25);
                } else if (railroadOwned == 2) {
                    System.out.println("Wow thats a lotta money, -$25. +$25 for " + checkWhichPlayerOwns(5, 0).getName());
                    currentPlayer.subtractMoney(50);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(5, 0).addMoney(50);
                } else if (railroadOwned == 3) {
                    System.out.println("Wow thats a lotta money, -$100. +$100 for " + checkWhichPlayerOwns(5, 0).getName());
                    currentPlayer.subtractMoney(100);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(5, 0).addMoney(100);
                } else if (railroadOwned == 4) {
                    System.out.println("Wow thats a lotta money, -$200. +$200 for " + checkWhichPlayerOwns(5, 0).getName());
                    currentPlayer.subtractMoney(200);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(5, 0).addMoney(200);
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                RailroadCard hello = new RailroadCard(13, 27,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n-----B. & O. Railroad------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 6 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Atlantic Avenue.");
            if (!(checkIfOwnedForAnyPlayer(6, 0)) ) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Atlantic Avenue \nRent:               $22 \nRent w Color set:   $44 \nRent with 1 ⌂:      $110 \nRent with 2 ⌂:      $330 \nRent with 3 ⌂:      $800 \nRent with 4 ⌂:      $975 \nRent with hotel:    $1150 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $260.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 260) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(260);
                        Possession newHouse = new Possession("Atlantic Avenue", 6, 0,150);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(6, 0) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(6, 0,150);
            } else {
                String x = determiningHierachy(6, 0);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1150. +$1150 for " + checkWhichPlayerOwns(6, 0).getName());
                    currentPlayer.subtractMoney(1150);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(6, 0).addMoney(1150);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$44. +$44 for " + checkWhichPlayerOwns(6, 0).getName());
                    currentPlayer.subtractMoney(44);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(6, 0).addMoney(44);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$22. +$22 for " + checkWhichPlayerOwns(6, 0).getName());
                    currentPlayer.subtractMoney(22);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(6, 0).addMoney(22);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$110. +$110 for " + checkWhichPlayerOwns(6, 0).getName());
                        currentPlayer.subtractMoney(110);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(6, 0).addMoney(110);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$330. +$330 for " + checkWhichPlayerOwns(6, 0).getName());
                        currentPlayer.subtractMoney(330);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(6, 0).addMoney(330);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$800. +$800 for " + checkWhichPlayerOwns(6, 0).getName());
                        currentPlayer.subtractMoney(800);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(6, 0).addMoney(800);
                    } else {
                        System.out.println("Wow thats a lotta money, -$975. +$975 for " + checkWhichPlayerOwns(6, 0).getName());
                        currentPlayer.subtractMoney(975);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(6, 0).addMoney(975);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Atlantic Avenue \nRent:               $22 \nRent w Color set:   $44 \nRent with 1 ⌂:      $110 \nRent with 2 ⌂:      $330 \nRent with 3 ⌂:      $800 \nRent with 4 ⌂:      $975 \nRent with hotel:    $1150 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 7 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Ventnor Avenue.");
            if (!(checkIfOwnedForAnyPlayer(7, 0))) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Ventnor Avenue \nRent:               $22 \nRent w Color set:   $44 \nRent with 1 ⌂:      $110 \nRent with 2 ⌂:      $330 \nRent with 3 ⌂:      $800 \nRent with 4 ⌂:      $975 \nRent with hotel:    $1150 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $260.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 260) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(260);
                        Possession newHouse = new Possession("Ventnor Avenue", 7, 0, 150);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(7, 0) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(7, 0,150);
            } else {
                String x = determiningHierachy(7, 0);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1150. +$1150 for " + checkWhichPlayerOwns(7, 0).getName());
                    currentPlayer.subtractMoney(1150);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(7, 0).addMoney(1150);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$44. +$44 for " + checkWhichPlayerOwns(7, 0).getName());
                    currentPlayer.subtractMoney(44);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(7, 0).addMoney(44);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$22. +$22 for " + checkWhichPlayerOwns(7, 0).getName());
                    currentPlayer.subtractMoney(22);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(7, 0).addMoney(22);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$110. +$110 for " + checkWhichPlayerOwns(7, 0).getName());
                        currentPlayer.subtractMoney(110);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(7, 0).addMoney(110);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$330. +$330 for " + checkWhichPlayerOwns(7, 0).getName());
                        currentPlayer.subtractMoney(330);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(7, 0).addMoney(330);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$800. +$800 for " + checkWhichPlayerOwns(7, 0).getName());
                        currentPlayer.subtractMoney(800);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(7, 0).addMoney(800);
                    } else {
                        System.out.println("Wow thats a lotta money, -$975. +$975 for " + checkWhichPlayerOwns(7, 0).getName());
                        currentPlayer.subtractMoney(975);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(7, 0).addMoney(975);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Ventnor Avenue \nRent:               $22 \nRent w Color set:   $44 \nRent with 1 ⌂:      $110 \nRent with 2 ⌂:      $330 \nRent with 3 ⌂:      $800 \nRent with 4 ⌂:      $975 \nRent with hotel:    $1150 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 8 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Water Works.");
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (!(checkIfOwnedForAnyPlayer(8, 0))|| okie.equals("yes")) {
                RailroadCard hello = new RailroadCard(9, 37,"\033[0;107m", "\n-------------Water Works--------------- \n \nIf one utility is owned, \nrent is 4 times amount shown on dice. \n\nIf both utilities are owned,\nrent is 10 times shown on the dice.");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this utility, pay $150.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 150) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(150);
                        Possession newHouse = new Possession("Water Works", 8, 0, 0);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(8, 0) == currentPlayer) {
                System.out.println("You landed on your property!");
            } else if (checkWhichPlayerOwns(8, 0).ownAllUtilities(8, 0) && checkWhichPlayerOwns(8, 0) != currentPlayer){
                int moneyAtFirst = (int) (Math.random()*6) + 1;
                int moneyOwned = moneyAtFirst*10;
                System.out.println("You rolled a " + moneyAtFirst + "Since both utilities are owned, x 10: " + moneyOwned);
                System.out.println("Wow thats a lotta money, -" + moneyOwned + ". +" + moneyOwned + " for " + checkWhichPlayerOwns(8, 0).getName());
                currentPlayer.subtractMoney(moneyOwned);
                currentPlayer.checkIfDead();
                checkWhichPlayerOwns(8, 0).addMoney(moneyOwned);
            } else {
                int moneyAtFirst = (int) (Math.random()*6) + 1;
                int moneyOwned = moneyAtFirst*4;
                System.out.println("You rolled a " + moneyAtFirst + "Since one utility are owned, x 4: " + moneyOwned);
                System.out.println("Wow thats a lotta money, -" + moneyOwned + ". +" + moneyOwned + " for " + checkWhichPlayerOwns(8, 0).getName());
                currentPlayer.subtractMoney(moneyOwned);
                currentPlayer.checkIfDead();
                checkWhichPlayerOwns(8, 0).addMoney(moneyOwned);
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                RailroadCard hello = new RailroadCard(9, 37,"\033[0;107m", "\n-------------Water Works--------------- \n \nIf one utility is owned, \nrent is 4 times amount shown on dice. \n\nIf both utilities are owned,\nrent is 10 times shown on the dice.");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 9 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("You landed on Marvin Gardens.");
            if (!(checkIfOwnedForAnyPlayer(9, 0))) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Marvin Gardens \nRent:               $24 \nRent w Color set:   $48 \nRent with 1 ⌂:      $120 \nRent with 2 ⌂:      $360 \nRent with 3 ⌂:      $850 \nRent with 4 ⌂:      $1025 \nRent with hotel:    $1200 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $280.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 280) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(280);
                        Possession newHouse = new Possession("Marvin Gardens", 9, 0, 150);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(9, 0) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(9, 0,150);
            } else {
                String x = determiningHierachy(9, 0);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1200. +$1200 for " + checkWhichPlayerOwns(9, 0).getName());
                    currentPlayer.subtractMoney(1200);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(9, 0).addMoney(1200);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$48. +$48 for " + checkWhichPlayerOwns(9, 0).getName());
                    currentPlayer.subtractMoney(48);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(9, 0).addMoney(48);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$24. +$24 for " + checkWhichPlayerOwns(9, 0).getName());
                    currentPlayer.subtractMoney(24);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(9, 0).addMoney(24);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$120. +$120 for " + checkWhichPlayerOwns(9, 0).getName());
                        currentPlayer.subtractMoney(120);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(9, 0).addMoney(120);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$360. +$360 for " + checkWhichPlayerOwns(9, 0).getName());
                        currentPlayer.subtractMoney(360);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(9, 0).addMoney(360);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$850. +$850 for " + checkWhichPlayerOwns(9, 0).getName());
                        currentPlayer.subtractMoney(850);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(9, 0).addMoney(850);
                    } else {
                        System.out.println("Wow thats a lotta money, -$1025. +$1025 for " + checkWhichPlayerOwns(9, 0).getName());
                        currentPlayer.subtractMoney(1025);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(9, 0).addMoney(1025);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;103m", "\n     Marvin Gardens \nRent:               $24 \nRent w Color set:   $48 \nRent with 1 ⌂:      $120 \nRent with 2 ⌂:      $360 \nRent with 3 ⌂:      $850 \nRent with 4 ⌂:      $1025 \nRent with hotel:    $1200 \n------------------------- \n⌂ cost:         $150 each \nHotel cost:     $150 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 0 ) {
            System.out.println("GO TO JAIL.");
            monopolyGrid.setPlayer(0, 10, currentPlayer);
            int[] newCoord = {0 , 10};
            monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
            currentPlayer.setCoordinates(newCoord);
            currentPlayer.setInJail(true);

        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 1 ) {
            System.out.println("You landed on Pacific Avenue.");
            if (!(checkIfOwnedForAnyPlayer(10, 1)) ) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n      Pacific Avenue \nRent:               $26 \nRent w Color set:   $52 \nRent with 1 ⌂:      $130 \nRent with 2 ⌂:      $390 \nRent with 3 ⌂:      $900 \nRent with 4 ⌂:      $1100 \nRent with hotel:    $1275 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $300.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 300) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(300);
                        Possession newHouse = new Possession("Pacific Avenue", 10, 1, 200);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(10, 1) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(10, 1,200);
            } else {
                String x = determiningHierachy(10, 1);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1275. +$1275 for " + checkWhichPlayerOwns(10, 1).getName());
                    currentPlayer.subtractMoney(1275);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 1).addMoney(1275);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$52. +$52 for " + checkWhichPlayerOwns(10, 1).getName());
                    currentPlayer.subtractMoney(52);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 1).addMoney(52);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$26. +$26 for " + checkWhichPlayerOwns(10, 1).getName());
                    currentPlayer.subtractMoney(26);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 1).addMoney(26);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$130. +$130 for " + checkWhichPlayerOwns(10, 1).getName());
                        currentPlayer.subtractMoney(130);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 1).addMoney(130);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$390. +$390 for " + checkWhichPlayerOwns(10, 1).getName());
                        currentPlayer.subtractMoney(390);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 1).addMoney(390);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$900. +$900 for " + checkWhichPlayerOwns(10, 1).getName());
                        currentPlayer.subtractMoney(900);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 1).addMoney(900);
                    } else {
                        System.out.println("Wow thats a lotta money, -$1100. +$1100 for " + checkWhichPlayerOwns(10, 1).getName());
                        currentPlayer.subtractMoney(1100);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 1).addMoney(1100);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n      Pacific Avenue \nRent:               $26 \nRent w Color set:   $52 \nRent with 1 ⌂:      $130 \nRent with 2 ⌂:      $390 \nRent with 3 ⌂:      $900 \nRent with 4 ⌂:      $1100 \nRent with hotel:    $1275 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 2 ) {
            System.out.println("You landed on North Carolina Avenue.");
            if (!(checkIfOwnedForAnyPlayer(10, 2)))  {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n  North Carolina Avenue \nRent:               $26 \nRent w Color set:   $52 \nRent with 1 ⌂:      $130 \nRent with 2 ⌂:      $390 \nRent with 3 ⌂:      $900 \nRent with 4 ⌂:      $1100 \nRent with hotel:    $1275 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $300.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 300) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(300);
                        Possession newHouse = new Possession("North Carolina Avenue", 10, 2, 200);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(10, 2) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(10, 2,200);
            } else {
                String x = determiningHierachy(10, 1);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1275. +$1275 for " + checkWhichPlayerOwns(10, 2).getName());
                    currentPlayer.subtractMoney(1275);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 2).addMoney(1275);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$52. +$52 for " + checkWhichPlayerOwns(10, 2).getName());
                    currentPlayer.subtractMoney(52);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 2).addMoney(52);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$26. +$26 for " + checkWhichPlayerOwns(10, 2).getName());
                    currentPlayer.subtractMoney(26);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 2).addMoney(26);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$130. +$130 for " + checkWhichPlayerOwns(10, 2).getName());
                        currentPlayer.subtractMoney(130);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 2).addMoney(130);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$390. +$390 for " + checkWhichPlayerOwns(10, 2).getName());
                        currentPlayer.subtractMoney(390);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 2).addMoney(390);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$900. +$900 for " + checkWhichPlayerOwns(10, 2).getName());
                        currentPlayer.subtractMoney(900);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 2).addMoney(900);
                    } else {
                        System.out.println("Wow thats a lotta money, -$1100. +$1100 for " + checkWhichPlayerOwns(10, 2).getName());
                        currentPlayer.subtractMoney(1100);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 2).addMoney(1100);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n  North Carolina Avenue \nRent:               $26 \nRent w Color set:   $52 \nRent with 1 ⌂:      $130 \nRent with 2 ⌂:      $390 \nRent with 3 ⌂:      $900 \nRent with 4 ⌂:      $1100 \nRent with hotel:    $1275 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 3 ) {
            System.out.println("You landed on Community Chest.");
            chest(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 4 ) {
            System.out.println("You landed on Pennsylvania Avenue.");
            if (!(checkIfOwnedForAnyPlayer(10, 4)) ) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n   Pennsylvania Avenue \nRent:               $28 \nRent w Color set:   $56 \nRent with 1 ⌂:      $150 \nRent with 2 ⌂:      $450 \nRent with 3 ⌂:      $1000 \nRent with 4 ⌂:      $1200 \nRent with hotel:    $1400 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $320.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 320) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(320);
                        Possession newHouse = new Possession("Pennsylvania Avenue", 10, 4, 200);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(10, 4) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(10, 4,200);
            } else {
                String x = determiningHierachy(10, 4);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1400. +$1400 for " + checkWhichPlayerOwns(10, 4).getName());
                    currentPlayer.subtractMoney(1400);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 4).addMoney(1400);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$56. +$56 for " + checkWhichPlayerOwns(10, 4).getName());
                    currentPlayer.subtractMoney(56);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 4).addMoney(56);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$28. +$28 for " + checkWhichPlayerOwns(10, 4).getName());
                    currentPlayer.subtractMoney(28);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 4).addMoney(28);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$150. +$150 for " + checkWhichPlayerOwns(10, 4).getName());
                        currentPlayer.subtractMoney(150);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 4).addMoney(150);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$450. +$450 for " + checkWhichPlayerOwns(10, 4).getName());
                        currentPlayer.subtractMoney(450);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 4).addMoney(450);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$1000. +$1000 for " + checkWhichPlayerOwns(10, 4).getName());
                        currentPlayer.subtractMoney(1000);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 4).addMoney(1000);
                    } else {
                        System.out.println("Wow thats a lotta money, -$1200. +$1200 for " + checkWhichPlayerOwns(10, 4).getName());
                        currentPlayer.subtractMoney(1200);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 4).addMoney(1200);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;102m", "\n   Pennsylvania Avenue \nRent:               $28 \nRent w Color set:   $56 \nRent with 1 ⌂:      $150 \nRent with 2 ⌂:      $450 \nRent with 3 ⌂:      $1000 \nRent with 4 ⌂:      $1200 \nRent with hotel:    $1400 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 5 ) {
            System.out.println("You landed on Short line.");
            if (!(checkIfOwnedForAnyPlayer(10, 5)) ) {
                RailroadCard hello = new RailroadCard(13, 27,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n--------Short Line---------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $200.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 200) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(200);
                        Railroad newHouse = new Railroad("Short Line", 10, 5, 0);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(10, 5) != currentPlayer) {
                int railroadOwned = checkWhichPlayerOwns(0, 5).howManyRailRoads();
                if (railroadOwned == 1) {
                    System.out.println("Wow thats a lotta money, -$25. +$25 for " + checkWhichPlayerOwns(10, 5).getName());
                    currentPlayer.subtractMoney(25);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 5).addMoney(25);
                } else if (railroadOwned == 2) {
                    System.out.println("Wow thats a lotta money, -$25. +$25 for " + checkWhichPlayerOwns(10, 5).getName());
                    currentPlayer.subtractMoney(50);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 5).addMoney(50);
                } else if (railroadOwned == 3) {
                    System.out.println("Wow thats a lotta money, -$100. +$100 for " + checkWhichPlayerOwns(10, 5).getName());
                    currentPlayer.subtractMoney(100);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 5).addMoney(100);
                } else if (railroadOwned == 4) {
                    System.out.println("Wow thats a lotta money, -$200. +$200 for " + checkWhichPlayerOwns(10, 5).getName());
                    currentPlayer.subtractMoney(200);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 5).addMoney(200);
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                RailroadCard hello = new RailroadCard(13, 27,"\033[0;107m", "\n     ╭━ ♥━ ♥━ ♥━ ♥━\n" +
                        "     ╰╮┏┳┳┳┓┏┳┳┳┳┓\n" +
                        "     ┏┻╋╋┻┻┫┣┻╋╋┻┫\n" +
                        "     ┗ⓞ┻┻━ⓞ┻┻ⓞ┻┻▄ ▄     \n--------Short Line---------\n    Rent: $25 \n    If 2 RR is owned: $50 \n    If 3: $100 \n    If 4: $200 \n    Mortgage Value: $100");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 6 ) {
            System.out.println("You landed on Chance.");
            chance(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 7 ) {
            System.out.println("You landed on Park Place.");
            if (!(checkIfOwnedForAnyPlayer(10, 7)) ) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;107m", "\n       Park Place \nRent:               $35 \nRent w Color set:   $70 \nRent with 1 ⌂:      $175 \nRent with 2 ⌂:      $500 \nRent with 3 ⌂:      $1100 \nRent with 4 ⌂:      $1300 \nRent with hotel:    $1500 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $350.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 350) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(350);
                        Possession newHouse = new Possession("Park Place", 10, 7, 200);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                } else {
                    System.out.println("aight");
                }
            } else if (checkWhichPlayerOwns(10, 7) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(10, 7,200);
            } else {
                String x = determiningHierachy(10, 7);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1500. +$1500 for " + checkWhichPlayerOwns(10, 7).getName());
                    currentPlayer.subtractMoney(1500);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 7).addMoney(1500);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$70. +$70 for " + checkWhichPlayerOwns(10, 7).getName());
                    currentPlayer.subtractMoney(70);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 7).addMoney(70);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$35. +$35 for " + checkWhichPlayerOwns(10, 7).getName());
                    currentPlayer.subtractMoney(35);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 7).addMoney(35);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$175. +$175 for " + checkWhichPlayerOwns(10, 7).getName());
                        currentPlayer.subtractMoney(175);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 7).addMoney(175);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$500. +$500 for " + checkWhichPlayerOwns(10, 7).getName());
                        currentPlayer.subtractMoney(500);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 7).addMoney(500);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$1100. +$1100 for " + checkWhichPlayerOwns(10, 7).getName());
                        currentPlayer.subtractMoney(1100);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 7).addMoney(1100);
                    } else {
                        System.out.println("Wow thats a lotta money, -$1300. +$1300 for " + checkWhichPlayerOwns(10, 7).getName());
                        currentPlayer.subtractMoney(1300);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 7).addMoney(1300);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;107m", "\n       Park Place \nRent:               $35 \nRent w Color set:   $70 \nRent with 1 ⌂:      $175 \nRent with 2 ⌂:      $500 \nRent with 3 ⌂:      $1100 \nRent with 4 ⌂:      $1300 \nRent with hotel:    $1500 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 8 ) {
            System.out.println("You landed on Luxury Tax! It's tax season. Pay $100");
            currentPlayer.subtractMoney(100);
            currentPlayer.checkIfDead();
        }
        if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 9 ) {
            System.out.println("You landed on Boardwalk.");
            if (!(checkIfOwnedForAnyPlayer(10, 9)) ) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;107m", "\n        Boardwalk \nRent:               $50 \nRent w Color set:   $100 \nRent with 1 ⌂:      $200 \nRent with 2 ⌂:      $600 \nRent with 3 ⌂:      $1400 \nRent with 4 ⌂:      $1700 \nRent with hotel:    $2000 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
                System.out.print("To purchase this, pay $400.  Yes or no? ");
                okie = scan.nextLine();
                if (okie.toLowerCase().equals("yes")) {
                    if (currentPlayer.getMoney() < 400) {
                        System.out.println("You dont have enough money for this purchase :(");
                    } else {
                        currentPlayer.subtractMoney(400);
                        Possession newHouse = new Possession("Boardwalk", 10, 9, 200);
                        currentPlayer.addToOwnedCoordinates(newHouse);
                    }
                }else {
                    System.out.println("aight");
                }
            }  else if (checkWhichPlayerOwns(10, 9) == currentPlayer) {
                System.out.println("You landed on your property!");
                buyingHouses(10, 9,200);
            } else {
                String x = determiningHierachy(10, 9);
                if (x.equals("hotel")) {
                    System.out.println("Wow thats a lotta money, -$1500. +$1500 for " + checkWhichPlayerOwns(10, 9).getName());
                    currentPlayer.subtractMoney(1500);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 9).addMoney(1500);
                } else if (x.equals("colorset")) {
                    System.out.println("Wow thats a lotta money, -$70. +$70 for " + checkWhichPlayerOwns(10, 9).getName());
                    currentPlayer.subtractMoney(70);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 9).addMoney(70);
                } else if (x.equals("na")) {
                    System.out.println("Wow thats a lotta money, -$35. +$35 for " + checkWhichPlayerOwns(10, 9).getName());
                    currentPlayer.subtractMoney(35);
                    currentPlayer.checkIfDead();
                    checkWhichPlayerOwns(10, 9).addMoney(35);
                } else {
                    int yy = Integer.parseInt(x);
                    if (yy == 1) {
                        System.out.println("Wow thats a lotta money, -$175. +$175 for " + checkWhichPlayerOwns(10, 9).getName());
                        currentPlayer.subtractMoney(175);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 9).addMoney(175);
                    } else if (yy == 2) {
                        System.out.println("Wow thats a lotta money, -$500. +$500 for " + checkWhichPlayerOwns(10, 9).getName());
                        currentPlayer.subtractMoney(500);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 9).addMoney(500);
                    } else if (yy == 3) {
                        System.out.println("Wow thats a lotta money, -$1100. +$1100 for " + checkWhichPlayerOwns(10, 9).getName());
                        currentPlayer.subtractMoney(1100);
                        checkWhichPlayerOwns(10, 9).addMoney(1100);
                        currentPlayer.checkIfDead();
                    } else {
                        System.out.println("Wow thats a lotta money, -$1300. +$1300 for " + checkWhichPlayerOwns(10, 9).getName());
                        currentPlayer.subtractMoney(1300);
                        currentPlayer.checkIfDead();
                        checkWhichPlayerOwns(10, 9).addMoney(1300);
                    }
                }
            }
            System.out.println("Do you wish to check the card? ");
            okie = scan.nextLine();
            if (okie.equals("yes")) {
                HouseCard hello = new HouseCard(13, 25,"\033[0;107m", "\n        Boardwalk \nRent:               $50 \nRent w Color set:   $100 \nRent with 1 ⌂:      $200 \nRent with 2 ⌂:      $600 \nRent with 3 ⌂:      $1400 \nRent with 4 ⌂:      $1700 \nRent with hotel:    $2000 \n------------------------- \n⌂ cost:         $200 each \nHotel cost:     $200 each");
                hello.populateGrid();
                hello.printGrid();
            }
            yippee(currentPlayer);
        }

    }
    public void chance(Player currentPlayer) {
        int x = (int) (Math.random()*20 ) + 1;
        if (x > 18) {
            System.out.println("You found $100 on the ground");
            currentPlayer.addMoney(100);
        } else if (x > 16) {
            System.out.println("Advance to go! Collect $200");
            monopolyGrid.setPlayer(10, 10, currentPlayer);
            int[] newCoord = {10 , 10};
            monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
            currentPlayer.setCoordinates(newCoord);
            currentPlayer.addMoney(200);
        } else if (x > 14) {
            System.out.println("Go to Jail. Do not collect $200");
            monopolyGrid.setPlayer(10, 0, currentPlayer);
            int[] newCoord = {10 , 0};
            monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
            currentPlayer.setCoordinates(newCoord);
            currentPlayer.setInJail(true);
        } else if (x > 12 ) {
            System.out.println("Speeding fee. Pay $15");
            currentPlayer.subtractMoney(15);
        } else if (x > 10) {
            System.out.println("Get out of jail free. Card should be kept until needed, sold, or traded.");
            currentPlayer.setGetOutJailCardPossession(true);
        } else if ( x > 8) {
            System.out.println("Advance to illinois avenue. If you pass go, collect $200");
            monopolyGrid.setPlayer(4, 0, currentPlayer);
            int[] newCoord = {4 , 0};
            monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
            currentPlayer.setCoordinates(newCoord);
        } else if (x > 6) {
            System.out.println("Bank pays you 50 dollars");
            currentPlayer.addMoney(50);
        } else {
            System.out.println("Advance to St. Charles Place. If you pass GO, collect $200.");
            monopolyGrid.setPlayer(0, 9, currentPlayer);
            int[] newCoord = {0 , 9};
            monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
            currentPlayer.setCoordinates(newCoord);
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
    public void chest(Player currentPlayer) {
        int x = (int) (Math.random()*20 ) + 1;
        if (x > 18) {
            System.out.println("You inherit $100");
            currentPlayer.addMoney(100);
        } else if (x > 16) {
            System.out.println("Doctor's fee: Pay $50");
            currentPlayer.subtractMoney(50);
        } else if (x > 14) {
            System.out.println("Go to Jail. Do not collect $200");
            monopolyGrid.setPlayer(10, 0, currentPlayer);
            int[] newCoord = {10 , 0};
            currentPlayer.setInJail(true);
            monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
            currentPlayer.setCoordinates(newCoord);
        } else if (x > 12 ) {
            System.out.println("Xmas funds matures. Collect $100");
            currentPlayer.addMoney(100);
        } else if (x > 10) {
            System.out.println("Get out of jail free. Card should be kept until needed, sold, or traded.");
            currentPlayer.setGetOutJailCardPossession(true);
        } else if ( x > 8) {
            System.out.println("Life insurance funds matures. Collect $100");
            currentPlayer.addMoney(100);
        } else if (x > 6) {
            System.out.println("You are assessed for street repairs. $40 per house and $115 per hotel");
        } else if ( x > 4 ){
            System.out.println("Bank pays you 50 dollars +50");
            currentPlayer.addMoney(50);
        } else {
            System.out.println("Advance to go! Collect $200");
            monopolyGrid.setPlayer(10, 10, currentPlayer);
            int[] newCoord = {10 , 10};
            monopolyGrid.repopulate(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate());
            currentPlayer.setCoordinates(newCoord);
            currentPlayer.addMoney(200);
        }
    }
    public boolean checkIfOwnedForAnyPlayer(int x, int y) {
        for (int i = 0; i < listOfPlayers.size(); i++) {
            if (listOfPlayers.get(i).search(x, y)) {
                return true;
            }
        }
        return false;
    }
    public Player checkWhichPlayerOwns(int x, int y) {
        for (int i = 0; i < listOfPlayers.size(); i++) {
            if (listOfPlayers.get(i).search(x, y)) {
                return listOfPlayers.get(i);
            }
        }
        return null;
    }
    public void buyingHouses(int x, int y, int money) {
        boolean colorSet = checkWhichPlayerOwns(x, y).checkForColorSet(x,y);
        if (checkWhichPlayerOwns(x, y).searchAndReturn(x, y).getHouses() == 4 && checkWhichPlayerOwns(x, y).searchAndReturn(x, y).isOwnHotel() == false) {
            System.out.println("You can start purchasing hotels!");
            System.out.println("To purchase a hotel, you will have to pay $" + money);
            System.out.print("y/n? ");
            String anw = scan.nextLine();
            if (anw.equals("y") && money <= checkWhichPlayerOwns(x, y).getMoney()) {
                checkWhichPlayerOwns(x, y).searchAndReturn(x, y).setOwnHotel(true);
                System.out.println("Purchase successful");
            } else if (money > checkWhichPlayerOwns(x, y).getMoney()) {
                System.out.println("Sorry u dont have enough money for the hotel :/");
            } else {
                System.out.println("aight");
            }
        } else if (colorSet){
            System.out.println("You can start buying houses!");
            System.out.println("To purchase a house, you will have to pay $" + money);
            System.out.print("y/n? ");
            String anw = scan.nextLine();
            if (anw.equals("y") && money <= checkWhichPlayerOwns(x, y).getMoney()) {
                int currentAmountOfHouses = checkWhichPlayerOwns(x, y).searchAndReturn(x, y).getHouses();
                checkWhichPlayerOwns(x, y).searchAndReturn(x, y).setHouses(currentAmountOfHouses + 1);
                System.out.println("Purchase successful");
            } else if (money > checkWhichPlayerOwns(x, y).getMoney()) {
                System.out.println("Sorry u dont have enough money for the house :/");
            } else {
                System.out.println("aight");
            }
        } else {
            System.out.println("Sorry you still have to complete the colorSet first!");
        }
    }
    public String determiningHierachy(int x, int y) {
        boolean colorSet = checkWhichPlayerOwns(x, y).checkForColorSet(x,y);
        String highest = "";
        if (checkWhichPlayerOwns(x, y).searchAndReturn(x, y).isOwnHotel() ) {
            highest = "hotel";
        } else if (checkWhichPlayerOwns(x, y).searchAndReturn(x, y).getHouses() > 0){
            highest = "" + checkWhichPlayerOwns(x, y).searchAndReturn(x, y).getHouses();
        } else if (colorSet) {
            highest = "colorset";
        } else {
            highest = "na";
        }
        return highest;
    }
    public void yippee(Player currentPlayer) {
        System.out.print("Do you wish to buy any houses/hotels on any of ur property? ");
        String okie = scan.nextLine();
        if (okie.equals("yes")) {
            System.out.print("What is the xcoordinate of your property?? (IDC U MUST KNOW): ");
            int xCord = scan.nextInt();
            System.out.print("What is ycoordinate of your property?? (IDC U MUST KNOW): ");
            int yCord = scan.nextInt();
            scan.nextLine();
            if (checkIfOwnedForAnyPlayer(xCord, yCord) && checkWhichPlayerOwns(xCord, yCord) == currentPlayer) {
                buyingHouses(xCord, yCord, checkWhichPlayerOwns(xCord, yCord).searchAndReturn(xCord, yCord).getPrice());
            } else {
                System.out.println("THAT IS NOT UR PROPERTY...");
            }
        }
    }


}
