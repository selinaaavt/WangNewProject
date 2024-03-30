import java.awt.*;

public class Grid {
    private String[][] board;
    private static String ANSI_GREEN_BACKGROUND;
    private static String ANSI_RESET;
    public Grid(int howMuch, int howLong) {
        board = new String[howMuch][howLong];
        ANSI_GREEN_BACKGROUND = "\u001B[42m";
        ANSI_RESET = "\u001B[0m";

    }
    public void setBoard(int x, int y, String s) {
        board[x][y] = s;
    }
    public static String getAnsiGreenBackground() {
        return ANSI_GREEN_BACKGROUND;
    }
    public static String getAnsiReset() {
        return ANSI_RESET;
    }
    public  void populateGrid(String color) {
        for (int col = 0; col < board[0].length; col++) {
            board[0][col] = color + "[]" + ANSI_RESET;
        }
        for (int row = 0; row < board.length; row++) {
            board[row][board[0].length -1 ] = color + "[]" + ANSI_RESET;
        }
        for (int row = 0; row < board.length; row++) {
            board[row][0] = color + "[]" + ANSI_RESET;
        }
        for (int col = 0; col < board[0].length; col++) {
            board[board.length -1 ][col] = color + "[]" + ANSI_RESET;
        }
        for (int i = 0; i < board.length; i++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[i][x] == null) {
                    board[i][x] = color + "  ";
                }
            }
        }
    }
    public void setPlayer(int x , int y, Player player) {
        board[y][x] = ANSI_GREEN_BACKGROUND + player.getName() + ANSI_RESET;
    }
    public void printGrid() {
        for (int i = 0; i < board.length; i++) {
            for (int x = 0; x < board[0].length; x++) {
                System.out.print(board[i][x]);
            }
            System.out.println();
        }
    }
    public String[][] getBoard() {
        return board;
    }
    public int getRowLength() {
        return board[0].length;
    }
    public String getBoardString(int x, int y) {
        return board[x][y];
    }

}
