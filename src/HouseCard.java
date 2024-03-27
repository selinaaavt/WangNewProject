public class HouseCard extends Card {
    private String color;
    private String content;
    public HouseCard(String color, String content) {
        super(6, 10);
        this.color = color;
        this.content = content;
    }
    public void populate() {
        int row = 0;
       for (int i = 0 ; i < 10; i++) {
           for (int x = 0; x < content.length() - 1; x++) {
               if (content.substring(x, x+1).equals("\n")) {
                   row++;
                   content = content.substring(0, x) + content.substring(x+2);
                   x-= 2;
               } else {
                   setBoard(row, i, content.substring(x, x + 1));
               }
           }
       }
    }
}
