import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean gameEnd = false;
        int[] wumpus = new int[2];
        int[] bat = new int[2];
        int[] pit = new int[2];
        int[] player = new int[2];
        int[] arrow = new int[2];
        int[][] locations = new int[5][2];

        locations[0] = wumpus;
        locations[1] = bat;
        locations[2] = pit;
        locations[3] = player;
        locations[4] = arrow;

        
        /*
         * TODO: use while loop & randomCoordinates() to place all 5 object in different
         * places
         */

        wumpus = randomCoordinates();
        bat = randomCoordinates();
        pit = randomCoordinates();
        player = randomCoordinates();
        arrow = randomCoordinates();

        int arrowAmount = 5;
        int roomNumber;

        while (!gameEnd) {
            roomNumber = 5 * player[1] + player[0] + 1;
            System.out.println("You are in Room" + roomNumber);
            /*
             * TODO: check adjacent places and show the hint
             */
            System.out.print("Shoot or move (s/m)? ");
            char command = s.nextLine().toCharArray()[0];
            while (command != 's' && command != 'm') {
                System.out.print("Shoot or move (s/m)? ");
                command = s.nextLine().toCharArray()[0];
            }

            System.out.print("Choose direction (n/e/s/w): ");
            char direction = s.nextLine().toCharArray()[0];
            while (direction != 'n' && direction != 'e' && direction != 's' && direction != 'w') {
                System.out.print("Choose direction (n/e/s/w): ");
                direction = s.nextLine().toCharArray()[0];
            }

            int[] target = player;
            switch (direction) {
                case 'n' -> target[1]--;
                case 'e' -> target[0]++;
                case 's' -> target[1]++;
                case 'w' -> target[0]--;
            }

            if (command == 's') {
                if(target == wumpus) {
                    System.out.println("You killed the wumpus");
                    gameEnd = true;
                } else {
                    moveToAdjacentCave(wumpus);
                }
            } else {
                if (target == arrow) {
                    System.out.println("Found an arrow...perhaps dropped by another, unsuccessful hunter.");
                    arrowAmount++;
                } else if (target == bat){
                    System.out.println("The bats took you to a random place...");
                    target = randomCoordinates();
                } else if (target == wumpus) {
                    if(probability(0.7)){
                        System.out.println("You were killed by the wumpus...");
                        gameEnd = true;
                    } else {
                        System.out.println("The wumpus saw you and escaped to an adjacent cave...");
                        moveToAdjacentCave(wumpus);
                    }
                } else if (target == pit){
                    System.out.println("You falled into a bottomless pit!!");
                    gameEnd = true;
                }
                player = target;
            }
        }
    }

    public static void moveToAdjacentCave(int[] object) {
        if(probability(0.5)){
            if(probability(0.5)) object[0]++;
            else object[0]--;
        } else {
            if(probability(0.5)) object[1]++;
            else object[1]--;
        }
    }

    public static boolean probability(double threshold){
        return Math.random() > threshold;
    }

    public static int[] randomCoordinates() {
        int x = (int) (Math.random() * 5);
        int y = (int) (Math.random() * 5);
        return new int[] { x, y };
    }
}
