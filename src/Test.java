public class Test {
    public static void main(String[] args) {
        RailroadCard hello = new RailroadCard(9, 37,"\033[0;107m", "              .=\"\"\"\"=,\n" +
                "             ><><><><><\n" +
                "             />   `\\   )\n" +
                "            /_     ))  )\n" +
                "             }     |_/`\n" +
                "             `\"\"\\   \\\n" +
                "               /`~~~~`\\ \n \nIf one utility is owned, \nrent is 4 times amount shown on dice. \n\nIf both utilities are owned,\nrent is 10 times shown on the dice.");
        hello.populateGrid();
        hello.printGrid();
    }

}
