public class RailroadCard extends Card {
    private String color;
    private String content;
    private int maxRow;
    private int maxCol;
    public RailroadCard(int x, int y, String color, String content) {
        super(x, y);
        maxRow = x;
        maxCol = y;
        this.color = color;
        this.content = content;
    }
    @Override
    public void populateGrid() {
        for (int i = 0; i < getBoard().length; i++) {
            for (int x = 0; x < getRowLength(); x++) {
                setBoard(i, x, color + " " + getAnsiReset());
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
                setBoard(row, col, color  + "\u001B[30m" + x + getAnsiReset());
                col++;
            }
        }
    }
}