import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean gameEnd = false;
        //0: player, 1: wumpus, 2: arrow, 3: bat, 4: pit
        int[][] locations = new int[5][2];

        for (int i = 0; i < locations.length; i++) { // sets locations of each object in the game
            locations[i] = randomCoordinates();
            int newXCoords = locations[i][0];
            int newYCoords = locations[i][1];
            System.out.println("new location" + Arrays.toString(locations[i]));

            for (int l = 0; l < i; l++) { // check to ensure no repeats of locations
                int oldXCoords = locations[l][0]; // already initialized x locations of the game objects
                int oldYCoords = locations[l][1]; // already initialized y locations of the game objects
                System.out.println("old locations" + Arrays.toString(locations[l]));
                if (newXCoords == oldXCoords && newYCoords == oldYCoords) {
                    i--;
                    break;
                }
            }
            System.out.println("\n");
        }

        int arrowAmount = 5;
        int roomNumber;

        while (!gameEnd) {
            roomNumber = 5 * locations[0][1] + locations[0][0] + 1;
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

            int[] target = locations[0];
            switch (direction) {
                case 'n' -> target[1]--;
                case 'e' -> target[0]++;
                case 's' -> target[1]++;
                case 'w' -> target[0]--;
            }

            if (command == 's') {
                if(target == locations[1]) {
                    System.out.println("You killed the wumpus");
                    gameEnd = true;
                } else {
                    moveToAdjacentCave(locations[1]);
                }
            } else {
                if (target == locations[2]) {
                    System.out.println("Found an arrow...perhaps dropped by another, unsuccessful hunter.");
                    arrowAmount++;
                } else if (target == locations[3]){
                    System.out.println("The bats took you to a random place...");
                    target = randomCoordinates();
                } else if (target == locations[1]) {
                    if(probability(0.7)){
                        System.out.println("You were killed by the wumpus...");
                        gameEnd = true;
                    } else {
                        System.out.println("The wumpus saw you and escaped to an adjacent cave...");
                        moveToAdjacentCave(locations[1]);
                    }
                } else if (target == locations[4]){
                    System.out.println("You falled into a bottomless pit!!");
                    gameEnd = true;
                }
                locations[0] = target;
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
