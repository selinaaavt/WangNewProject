public class Card extends Grid {
    private String white = "\u001B[46;1m";
    public Card(int x, int y) {
        super(x, y);
    }
    @Override
    public void populateGrid() {
        for (int i = 0; i < getBoard().length; i++) {
            for (int x = 0; x < getBoard()[0].length; x++) {
                if (getBoardString(i, x) == null) {
                    setBoard(i, x, white + "  ");
                }
            }
        }
    }

}
