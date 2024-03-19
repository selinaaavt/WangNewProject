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
            System.out.println(currentPlayer.getName() + " rolled " + moves + " moves! " );
            if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 10) {
                if (currentPlayer.getYCoordinate() - moves >= 0) {
                    int[] stuffs = {currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate() - moves};
                    currentPlayer.setCoordinates(stuffs);
                    Grid.setBoard(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate(), currentPlayer);
                } else {
                    int leftOvers = moves - currentPlayer.getYCoordinate();
                    if (currentPlayer.getXCoordinate() - leftOvers >= 0) {
                        int[] stuffs = {currentPlayer.getXCoordinate() - leftOvers, 0};
                        currentPlayer.setCoordinates(stuffs);
                        Grid.setBoard(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate(), currentPlayer);
                    } else {
                        int smth = leftOvers - currentPlayer.getXCoordinate();
                        int[] stuffs = {0, leftOvers};
                        currentPlayer.setCoordinates(stuffs);
                        Grid.setBoard(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate(), currentPlayer);
                    }
                }
            } else if (currentPlayer.getXCoordinate() == 10 && currentPlayer.getYCoordinate() == 0) {
                if (currentPlayer.getYCoordinate() - moves >= 0) {
                    int[] stuffs = {currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate() - moves};
                    currentPlayer.setCoordinates(stuffs);
                    Grid.setBoard(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate(), currentPlayer);
                } else {
                    int leftOvers = moves - currentPlayer.getYCoordinate();
                    if (currentPlayer.getXCoordinate() - leftOvers >= 0) {
                        int[] stuffs = {currentPlayer.getXCoordinate() - leftOvers, 0};
                        currentPlayer.setCoordinates(stuffs);
                        Grid.setBoard(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate(), currentPlayer);
                    } else {
                        int smth = leftOvers - currentPlayer.getXCoordinate();
                        int[] stuffs = {0, leftOvers};
                        currentPlayer.setCoordinates(stuffs);
                        Grid.setBoard(currentPlayer.getXCoordinate(), currentPlayer.getYCoordinate(), currentPlayer);
                    }
                } 
            }
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


}
