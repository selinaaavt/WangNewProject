public class HouseCard extends Card {
    private String color;
    private String content;
    private int maxRow;
    private int maxCol;
    public HouseCard(int x, int y, String color, String content) {
        super(x, y);
        maxRow = x;
        maxCol = y;
        this.color = color;
        this.content = content;
    }
    @Override
    public void populateGrid() {
        if (color.equals("\u001B[40;1m")) {
            for (int i = 0; i < getBoard().length; i++) {
                for (int x = 0; x < getRowLength(); x++) {
                    if (i == 0 ||i == 1 || i == 2) {
                        setBoard(i, x, color  +  " " + getAnsiReset());
                    } else {
                        setBoard(i, x, "\033[0;107m" + " " + getAnsiReset());
                    }
                }
            }
            int row = 0;
            int col = 0;
            for (int i = 0; i < content.length(); i++) {
                String x = content.substring(i, i+1);
                if (content.substring(i, i+1).equals("\n")) {
                    row++;
                    col= 0;
                    if (row >= maxRow) {
                        break;
                    }
                } else {
                    if (col >= maxCol) {
                        continue;
                    }
                    if (row == 0 || row == 1 || row == 2) {
                        setBoard(row, col, color  + x + getAnsiReset());
                    } else {
                        setBoard(row, col, "\033[0;107m"  + x + getAnsiReset());
                    }
                    col++;
                }
            }
        } else {
            for (int i = 0; i < getBoard().length; i++) {
                for (int x = 0; x < getRowLength(); x++) {
                    if (i == 0 ||i == 1 || i == 2) {
                        setBoard(i, x, color  +  " " + getAnsiReset());
                    } else {
                        setBoard(i, x, "\033[0;107m" + " " + getAnsiReset());
                    }
                }
            }
            int row = 0;
            int col = 0;
            for (int i = 0; i < content.length(); i++) {
                String x = content.substring(i, i+1);
                if (content.substring(i, i+1).equals("\n")) {
                    row++;
                    col= 0;
                    if (row >= maxRow) {
                        break;
                    }
                } else {
                    if (col >= maxCol) {
                        continue;
                    }
                    if (row == 0 || row == 1 || row == 2) {
                        setBoard(row, col, color  +  "\u001B[30m" + x + getAnsiReset());
                    } else {
                        setBoard(row, col, "\033[0;107m" + "\u001B[30m" + x + getAnsiReset());
                    }
                    col++;
                }
            }
        }

    }
}
