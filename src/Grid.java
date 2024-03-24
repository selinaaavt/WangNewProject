import java.awt.*;

public class Grid {
    private static String[][] board;
    private static String ANSI_GREEN_BACKGROUND;
    private static String ANSI_RESET;
    public Grid() {
        board = new String[11][11];
        ANSI_GREEN_BACKGROUND = "\u001B[42m";
        ANSI_RESET = "\u001B[0m";

    }
    public static void setBoard(int x, int y, String s) {
        board[x][y] = s;
    }
    public static String getAnsiGreenBackground() {
        return ANSI_GREEN_BACKGROUND;
    }
    public static String getAnsiReset() {
        return ANSI_RESET;
    }
    public  void populateGrid() {
        for (int col = 0; col < board[0].length; col++) {
            board[0][col] = ANSI_GREEN_BACKGROUND + "[]" + ANSI_RESET;
        }
        for (int row = 0; row < board.length; row++) {
            board[row][board[0].length -1 ] = ANSI_GREEN_BACKGROUND + "[]" + ANSI_RESET;
        }
        for (int row = 0; row < board.length; row++) {
            board[row][0] = ANSI_GREEN_BACKGROUND + "[]" + ANSI_RESET;
        }
        for (int col = 0; col < board[0].length; col++) {
            board[board.length -1 ][col] = ANSI_GREEN_BACKGROUND + "[]" + ANSI_RESET;
        }
        for (int i = 0; i < board.length; i++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[i][x] == null) {
                    board[i][x] = ANSI_GREEN_BACKGROUND + "  ";
                }
            }
        }
    }
    public static void setPlayer(int x , int y, Player player) {
        board[y][x] = ANSI_GREEN_BACKGROUND + player.getName() + ANSI_RESET;
    }
    public static void printGrid() {
        for (int i = 0; i < board.length; i++) {
            for (int x = 0; x < board[0].length; x++) {
                System.out.print(board[i][x]);
            }
            System.out.println();
        }
    }
}
