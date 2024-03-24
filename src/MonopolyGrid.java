public class MonopolyGrid extends Grid {
    public MonopolyGrid(){
        super();
    }
    @Override
    public void populateGrid() {
        super.populateGrid();
        setBoard(5, 1 ,getAnsiGreenBackground() + " M");
        setBoard(5, 2 , " \uD835\uDD46");
        setBoard(5, 3, " ℕ");
        setBoard(5, 4, " 𝕆");
        setBoard(5, 5, " ℙ");
        setBoard(5, 6, " \uD835\uDD46");
        setBoard(5, 7, " \uD835\uDD43");
        setBoard(5, 8, " \uD835\uDD50");
        setBoard(5, 9, " " + getAnsiReset());
        setBoard(10, 10,"\u001B[47;1m" + "GO" + getAnsiReset());
        setBoard(10, 9, "\u001B[40;1m" + "[]" + getAnsiReset());
        setBoard(10, 8,"\u001B[46;1m" + "\uD83D\uDCBC" + getAnsiReset());
        setBoard(10, 7, "\u001B[40;1m" + "[]" + getAnsiReset());
        setBoard(10, 6, "\033[43m" + "!!" + getAnsiReset());
        setBoard(10, 5, getAnsiGreenBackground() + "\uD83D\uDE82" + getAnsiReset());
        setBoard(10, 1, "\u001B[44;1m" + "[]" + getAnsiReset());
        setBoard(10, 3, getAnsiGreenBackground() + "❓" + getAnsiReset());
        setBoard(10, 2, "\u001B[44;1m" + "[]" + getAnsiReset());
        setBoard(10, 4, "\u001B[44;1m" + "[]" + getAnsiReset());
        setBoard(10, 0, getAnsiGreenBackground() + "||" + getAnsiReset());
        setBoard(0, 0, getAnsiGreenBackground() + "\uD83D\uDE97" + getAnsiReset());
        setBoard(1, 0, "\033[0;106m" + "[]" + getAnsiReset());
        setBoard(2, 0, "\033[0;106m" + "[]" + getAnsiReset());
        setBoard(3, 0, "\u001B[46;1m" + "\uD83D\uDCBC" + getAnsiReset());
        setBoard(4, 0, "\033[0;106m" + "[]" + getAnsiReset());
        setBoard(5, 0, getAnsiGreenBackground() + "\uD83D\uDE82" + getAnsiReset());
        setBoard(6, 0, "\u001B[45;1m" + "[]" + getAnsiReset());
        setBoard(7, 0, "\u001B[45;1m" + "[]" + getAnsiReset());
        setBoard(8, 0, "\033[43m" + "!!" + getAnsiReset());
        setBoard(9, 0, "\u001B[45;1m" + "[]" + getAnsiReset());
        setBoard(0, 1, "\033[0;101m" + "[]" + getAnsiReset());
        setBoard(0, 3, "\033[0;101m" + "[]" + getAnsiReset());
        setBoard(0, 4 , "\033[0;101m" + "[]" + getAnsiReset());
        setBoard(0, 5, getAnsiGreenBackground() + "\uD83D\uDE82" + getAnsiReset());
        setBoard(0, 2, getAnsiGreenBackground() + "❓" + getAnsiReset());
        setBoard(0, 6, "\033[0;103m" + "[]" + getAnsiReset());
        setBoard(0, 7, "\033[0;103m" + "[]" + getAnsiReset());
        setBoard(0, 8, getAnsiGreenBackground() + "WW" + getAnsiReset());
        setBoard(0, 9, "\033[0;103m" + "[]" + getAnsiReset());
        setBoard(0, 10, getAnsiGreenBackground() + "\uD83D\uDC6E\uD83C\uDFFB" + getAnsiReset());
        setBoard(1, 10, "\033[0;102m" + "[]" + getAnsiReset());
        setBoard(2, 10, "\033[0;102m" + "[]" + getAnsiReset());
        setBoard(4, 10, "\033[0;102m" + "[]" + getAnsiReset());
        setBoard(3, 10, "\u001B[46;1m" + "\uD83D\uDCBC" + getAnsiReset());
        setBoard(5, 10, getAnsiGreenBackground() + "\uD83D\uDE82" + getAnsiReset());
        setBoard(6, 10, getAnsiGreenBackground() + "??" + getAnsiReset());
        setBoard(7, 10, "\033[0;107m" + "[]" + getAnsiReset());
        setBoard(8, 10, "\033[43m" + "!!" + getAnsiReset() );
        setBoard(9, 10, "\033[0;107m" + "[]" + getAnsiReset());
        Game hello = new Game();
    }
    public static void repopulate(int x , int y) {
        if (x == 10) {
            if (y == 10 ) {
                setBoard(10, 10,"\u001B[47;1m" + "GO" + getAnsiReset());
            } else if (y == 9) {
                setBoard(9, 10, "\033[0;107m" + "[]" + getAnsiReset());
            } else if (y == 8) {
                setBoard(8, 10, "\033[43m" + "!!" + getAnsiReset() );
            } else if (y == 7) {
                setBoard(7, 10, "\033[0;107m" + "[]" + getAnsiReset());
            } else if (y == 6) {
                setBoard(6, 10, getAnsiGreenBackground() + "??" + getAnsiReset());
            } else if (y == 5) {
                setBoard(5, 10, getAnsiGreenBackground() + "\uD83D\uDE82" + getAnsiReset());
            } else if (y == 4) {
                setBoard(4, 10, "\033[0;102m" + "[]" + getAnsiReset());
            } else if (y == 3) {
                setBoard(3, 10, "\u001B[46;1m" + "\uD83D\uDCBC" + getAnsiReset());
            } else if (y == 2) {
                setBoard(2, 10, "\033[0;102m" + "[]" + getAnsiReset());
            } else if (y == 1) {
                setBoard(1, 10, "\033[0;102m" + "[]" + getAnsiReset());
            } else if (y == 0) {
                setBoard(0, 10, getAnsiGreenBackground() + "\uD83D\uDC6E\uD83C\uDFFB" + getAnsiReset());
            }
        } else if (x == 0) {
            if (y == 0) {
                setBoard(0, 0, getAnsiGreenBackground() + "\uD83D\uDE97" + getAnsiReset());
            } else if ( y == 1) {
                setBoard(1, 0, "\033[0;106m" + "[]" + getAnsiReset());
            } else if (y == 2) {
                setBoard(2, 0, "\033[0;106m" + "[]" + getAnsiReset());
            } else if (y == 3) {
                setBoard(3, 0, "\u001B[46;1m" + "\uD83D\uDCBC" + getAnsiReset());
            } else if (y == 4) {
                setBoard(4, 0, "\033[0;106m" + "[]" + getAnsiReset());
            } else if (y == 5) {
                setBoard(5, 0, getAnsiGreenBackground() + "\uD83D\uDE82" + getAnsiReset());
            } else if (y == 6) {
                setBoard(6, 0, "\u001B[45;1m" + "[]" + getAnsiReset());
            } else if (y == 7) {
                setBoard(7, 0, "\u001B[45;1m" + "[]" + getAnsiReset());
            } else if (y == 8) {
                setBoard(8, 0, "\033[43m" + "!!" + getAnsiReset());
            } else if (y == 9) {
                setBoard(9, 0, "\u001B[45;1m" + "[]" + getAnsiReset());
            } else if (y == 10) {
                setBoard(10, 0, getAnsiGreenBackground() + "||" + getAnsiReset());
            }
        } else if (y == 0) {
            if (x == 1) {
                setBoard(0, 1, "\033[0;101m" + "[]" + getAnsiReset());
            } else if (x == 2) {
                setBoard(0, 2, getAnsiGreenBackground() + "❓" + getAnsiReset());
            } else if (x == 3) {
                setBoard(0, 3, "\033[0;101m" + "[]" + getAnsiReset());
            } else if (x == 4) {
                setBoard(0, 4 , "\033[0;101m" + "[]" + getAnsiReset());
            } else if ( x == 5) {
                setBoard(0, 5, getAnsiGreenBackground() + "\uD83D\uDE82" + getAnsiReset());
            } else if ( x == 6) {
                setBoard(0, 6, "\033[0;103m" + "[]" + getAnsiReset());
            } else if (x == 7) {
                setBoard(0, 7, "\033[0;103m" + "[]" + getAnsiReset());
            } else if (x == 8) {
                setBoard(0, 8, getAnsiGreenBackground() + "WW" + getAnsiReset());
            } else if (x == 9) {
                setBoard(0, 9, "\033[0;103m" + "[]" + getAnsiReset());
            }
        } else if (y == 10) {
            if (x == 1 ) {
                setBoard(10, 1, "\u001B[44;1m" + "[]" + getAnsiReset());
            } else if (x == 2) {
                setBoard(10, 2, "\u001B[44;1m" + "[]" + getAnsiReset());
            } else if (x == 3) {
                setBoard(10, 3, getAnsiGreenBackground() + "❓" + getAnsiReset());
            } else if (x == 4) {
                setBoard(10, 4, "\u001B[44;1m" + "[]" + getAnsiReset());
            } else if ( x == 5) {
                setBoard(10, 5, getAnsiGreenBackground() + "\uD83D\uDE82" + getAnsiReset());
            } else if ( x == 6) {
                setBoard(10, 6, "\033[43m" + "!!" + getAnsiReset());
            } else if ( x == 7) {
                setBoard(10, 7, "\u001B[40;1m" + "[]" + getAnsiReset());
            } else if (x == 8) {
                setBoard(10, 8,"\u001B[46;1m" + "\uD83D\uDCBC" + getAnsiReset());
            } else if ( x == 9) {
                setBoard(10, 9, "\u001B[40;1m" + "[]" + getAnsiReset());
            }
        }
    }
}
