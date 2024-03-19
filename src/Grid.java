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
        board[5][1] = ANSI_GREEN_BACKGROUND + " M";
        board[5][2] = " \uD835\uDD46";
        board[5][3] = " ℕ";
        board[5][4] = " 𝕆";
        board[5][5] = " ℙ";
        board[5][6] = " \uD835\uDD46";
        board[5][7] = " \uD835\uDD43";
        board[5][8] = " \uD835\uDD50";
        board[5][9] = " " + ANSI_RESET;
        board[10][10] = "\u001B[47;1m" + "[]" + ANSI_RESET;
        board[10][9] = "\u001B[40;1m" + "[]" + ANSI_RESET;
        board[10][8] = "\u001B[46;1m" + "\uD83D\uDCBC" + ANSI_RESET;
        board[10][7] = "\u001B[40;1m" + "[]" + ANSI_RESET;
        board[10][6] = "\033[43m" + "!!" + ANSI_RESET;
        board[10][5] =  ANSI_GREEN_BACKGROUND + "\uD83D\uDE82" + ANSI_RESET;
        board[10][1] = "\u001B[44;1m" + "[]" + ANSI_RESET;
        board[10][3] = ANSI_GREEN_BACKGROUND + "❓" + ANSI_RESET;
        board[10][2] = "\u001B[44;1m" + "[]" + ANSI_RESET;
        board[10][4] = "\u001B[44;1m" + "[]" + ANSI_RESET;
        board[10][0] = ANSI_GREEN_BACKGROUND + "||" + ANSI_RESET;
        board[0][0] = ANSI_GREEN_BACKGROUND + "\uD83D\uDE97" + ANSI_RESET;
        board[1][0] = "\033[0;106m" + "[]" + ANSI_RESET;
        board[2][0] = "\033[0;106m" + "[]" + ANSI_RESET;
        board[3][0] = "\u001B[46;1m" + "\uD83D\uDCBC" + ANSI_RESET;
        board[4][0] = "\033[0;106m" + "[]" + ANSI_RESET;
        board[5][0] =  ANSI_GREEN_BACKGROUND + "\uD83D\uDE82" + ANSI_RESET;
        board[6][0] = "\u001B[45;1m" + "[]" + ANSI_RESET;
        board[7][0] = "\u001B[45;1m" + "[]" + ANSI_RESET;
        board[8][0] = "\033[43m" + "!!" + ANSI_RESET;
        board[9][0] = "\u001B[45;1m" + "[]" + ANSI_RESET;
        board[0][1] = "\033[0;101m" + "[]" + ANSI_RESET;
        board[0][3] = "\033[0;101m" + "[]" + ANSI_RESET;
        board[0][4] = "\033[0;101m" + "[]" + ANSI_RESET;
        board[0][5] =  ANSI_GREEN_BACKGROUND + "\uD83D\uDE82" + ANSI_RESET;
        board[0][2] =  ANSI_GREEN_BACKGROUND + "❓" + ANSI_RESET;
        board[0][6] = "\033[0;103m" + "[]" + ANSI_RESET;
        board[0][7] = "\033[0;103m" + "[]" + ANSI_RESET;
        board[0][8] = ANSI_GREEN_BACKGROUND + "WW" + ANSI_RESET;
        board[0][9] = "\033[0;103m" + "[]" + ANSI_RESET;
        board[0][10] = ANSI_GREEN_BACKGROUND + "\uD83D\uDC6E\uD83C\uDFFB" + ANSI_RESET;
        board[1][10] = "\033[0;102m" + "[]" + ANSI_RESET;
        board[2][10] = "\033[0;102m" + "[]" + ANSI_RESET;
        board[4][10] = "\033[0;102m" + "[]" + ANSI_RESET;
        board[3][10] = "\u001B[46;1m" + "\uD83D\uDCBC" + ANSI_RESET;
        board[5][10] =  ANSI_GREEN_BACKGROUND + "\uD83D\uDE82" + ANSI_RESET;
        board[6][10] =  ANSI_GREEN_BACKGROUND + "??" + ANSI_RESET;
        board[7][10] = "\033[0;107m" + "[]" + ANSI_RESET;
        board[8][10] = "\033[43m" + "!!" + ANSI_RESET;
        board[9][10] = "\033[0;107m" + "[]" + ANSI_RESET;
        Game newGame = new Game();
    }
    public static void setBoard(int x , int y, Player player) {
        board[x][y] = ANSI_GREEN_BACKGROUND + player.getName() + ANSI_RESET;
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
