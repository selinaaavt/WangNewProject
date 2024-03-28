public class HouseCard extends Card {
    private String color;
    private String content;
    public HouseCard(String color, String content) {
        super(6, 10);
        this.color = color;
        this.content = content;
    }
    public void populateGrid() {
        super.populateGrid(color);
        int row = 0;
        int x = 0;
        for (int i = 0 ; i < content.length() - 1; i++) {
            x = i;
            if (content.substring(i, i+1).equals("\n")) {
                row++;
                content = content.substring(0, i) + content.substring(i + 1);
                x = 0;
            } else {
                setBoard(row, x, content.substring(i, i + 1));
            }
        }
    }
}
