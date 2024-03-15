import java.awt.*;

public class Grid {
    private String[][] board;
    private String ANSI_GREEN_BACKGROUND;
    private String ANSI_RESET;
    public Grid() {
        board = new String[11][11];
        ANSI_GREEN_BACKGROUND = "\u001B[42m";
        ANSI_RESET = "\u001B[0m";
    }
    public void populateGrid() {
        for (int col = 0; col < board[0].length; col++) {
            board[0][col] = ANSI_GREEN_BACKGROUND + "[]" + ANSI_RESET;
        }
        for (int row = 0; row < board.length; row++) {
            board[row][board[0].length -1 ] = "[]";
        }
        for (int row = 0; row < board.length; row++) {
            board[row][0] = "[]";
        }
        for (int col = 0; col < board[0].length; col++) {
            board[board.length -1 ][col] = "[]";
        }
        for (int i = 0; i < board.length; i++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[i][x] == null) {
                    board[i][x] = ANSI_GREEN_BACKGROUND + "  ";
                }
            }
        }
        board[5][1] = " \uD835\uDD44";
        board[5][2] = " \uD835\uDD46";
        board[5][3] = " ℕ";
        board[5][4] = " 𝕆";
        board[5][5] = " ℙ";
        board[5][6] = " \uD835\uDD46";
        board[5][7] = " \uD835\uDD43";
        board[5][8] = " \uD835\uDD50";
        board[5][9] = " ";
        for (int i = 0; i < board.length; i++) {
            for (int x = 0; x < board[0].length; x++) {
                System.out.print(board[i][x]);
            }
            System.out.println();
        }

    }
}
