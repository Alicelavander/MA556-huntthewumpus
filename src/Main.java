import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        boolean gameEnd = false;
        int[] wumpus = new int[2];
        int[] player = new int[2];

        int[][] locations = new int[5][2];
        locations = getLocations(locations);

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
                if (target == wumpus)
                    gameEnd = true;

            } else {

            }
        }
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
        // for(int i = 0;)
        return locations;
    }
}
