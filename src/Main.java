import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean gameEnd = false;
        //0: player, 1: wumpus, 2: arrow, 3: bat, 4: pit
        int[][] locations = new int[5][2];
        locations = getLocations(locations);

        int arrowAmount = 5;
        int roomNumber;

        while (!gameEnd) {
            System.out.println("Player: " + Arrays.toString(locations[0]));
            System.out.println("Wumpus: " + Arrays.toString(locations[1]));
            System.out.println("Arrow: " + Arrays.toString(locations[2]));
            System.out.println("Bat: " + Arrays.toString(locations[3]));
            System.out.println("Pit: " + Arrays.toString(locations[4]));

            roomNumber = 5 * locations[0][1] + locations[0][0] + 1;
            System.out.println("You are in Room " + roomNumber);

            if (adjacentToPlayer(locations[1], locations[0])){
                System.out.println("I smell a Wumpus nearby.");
            }
            if (adjacentToPlayer(locations[3], locations[0])){
                System.out.println("I hear flapping nearby.");
            }
            if (adjacentToPlayer(locations[4], locations[0])){
                System.out.println("I feel a breeze nearby.");
            }

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
                case 'n' -> target[1] = (target[1] - 1 + 5) % 5;
                case 'e' -> target[0] = (target[0] + 1 + 5) % 5;
                case 's' -> target[1] = (target[1] + 1 + 5) % 5;
                case 'w' -> target[0] = (target[0] - 1 + 5) % 5;
            }

            if (command == 's') {
                if(arrowAmount == 0) System.out.println("You ran out of arrows :(");
                else {
                    arrowAmount--;
                    if(target == locations[1]) {
                        System.out.println("You killed the wumpus");
                        gameEnd = true;
                    } else {
                        System.out.println("The wumpus ran away!");
                        moveToAdjacentCave(locations[1]);
                    }
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

    public static boolean adjacentToPlayer(int[] target, int[] player){
        return Math.abs(player[0] - target[0]) <= 1 && Math.abs(player[1] - target[1]) <= 1;

    }

    public static void moveToAdjacentCave(int[] object) {
        if(probability(0.5)){
            if(probability(0.5)) object[0] = (object[0] + 1 + 5) % 5;
            else object[0] = (object[0] - 1 + 5) % 5;;
        } else {
            if(probability(0.5)) object[1] = (object[1] + 1 + 5) % 5;
            else object[1] = (object[1] - 1 + 5) % 5;
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

    public static int[][] getLocations(int[][] locations) {
        for (int i = 0; i < locations.length; i++) { // sets locations of each object in the game
            locations[i] = randomCoordinates();
            int newXCoords = locations[i][0];
            int newYCoords = locations[i][1];

            for (int l = 0; l < i; l++) { // check to ensure no repeats of locations
                int oldXCoords = locations[l][0]; // already initialized x locations of the game objects
                int oldYCoords = locations[l][1]; // already initialized y locations of the game objects
                if (newXCoords == oldXCoords && newYCoords == oldYCoords) {
                    i--;
                    break;
                }
            }
            System.out.println("\n");
        }
        return locations;
    }
}
