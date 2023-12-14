import java.util.Arrays;

public class Main {
    // 0: player, 1: wumpus, 2: arrow, 3: bat, 4: pit
    public static int[][] locations;
    // 0: wait, 1: space(shoot) or wasd(move), 2: wasd(shoot)
    private static int inputState;
    static boolean gameEnd = false;
    static boolean winLose;
    private static int arrowAmount = 5;

    public static int[] getLocation(int index) {
        return locations[index];
    }

    public static boolean getGameEnd() {
        return gameEnd;
    }

    public static boolean getWinLose() {
        return winLose;
    }

    public static int getInputState() {
        return inputState;
    }

    public static void main(String[] args) {
        locations = initLocations(); // generate random locations for each thing in the game
        System.out.println("start location: " + Arrays.toString(locations[0]));
        new WumpusGraphics();
    }

    // core logic of the game. Checks for target location & appropriate operation
    public static void setInputState(int state, char input) {
        int[] target = locations[0]; // target = userLocation after their move
        int[] arrowTarget = new int[] { locations[0][0], locations[0][1] }; // target = userLocation after their move
        if (input == 'w' || input == 'a' || input == 's' || input == 'd') {
            switch (input) { // determines where you will be aimed regardless of the state
                // for movement
                case 'w' -> target[1] = (target[1] - 1 + 5) % 5;
                case 'd' -> target[0] = (target[0] + 1 + 5) % 5;
                case 's' -> target[1] = (target[1] + 1 + 5) % 5;
                case 'a' -> target[0] = (target[0] - 1 + 5) % 5;
            }
            // move to target position
            if (Arrays.equals(target, locations[3])) {
                // bat
                target = randomCoordinates();
                if (Arrays.equals(target, locations[4])) {
                    System.out.println("The bats dropped you into the bottomless pit...\nYou Lose");
                    gameEnd = true;
                    winLose = false;
                } else {
                    System.out.println("The bats took you to a random place...");
                }
            } else if (Arrays.equals(target, locations[1])) {
                // wumpus
                if (probability(0.7)) {
                    System.out.println("You were killed by the Wumpus...\nYou Lose.");
                    gameEnd = true;
                    winLose = false;
                } else {
                    System.out.println("The Wumpus saw you and escaped to an adjacent cave...");
                    moveToAdjacentCave(locations[1], locations[0]);
                }
            } else if (Arrays.equals(target, locations[4])) {
                // pit
                System.out.println("You fell into a bottomless pit!\nYou Lose.");
                gameEnd = true;
                winLose = false;
            } else if (Arrays.equals(target, locations[2])) {
                // arrow
                System.out.println("Found an arrow...perhaps dropped by another, unsuccessful hunter.");
                arrowAmount++;
                locations[2] = randomCoordinates();
            }
            locations[0] = target;
        } else if (input == 'i' || input == 'j' || input == 'k' || input == 'l') { // handles logic of the arrow
            switch (input) { // gets the end destination of the arrow when fired.
                // for arrows
                case 'i' -> arrowTarget[1] = (arrowTarget[1] - 1 + 5) % 5;
                case 'l' -> arrowTarget[0] = (arrowTarget[0] + 1 + 5) % 5;
                case 'k' -> arrowTarget[1] = (arrowTarget[1] + 1 + 5) % 5;
                case 'j' -> arrowTarget[0] = (arrowTarget[0] - 1 + 5) % 5;
            }
            if (arrowAmount == 0)
                System.out.println("No arrows :(");
            else {
                // what happens when you fire an arrow
                arrowAmount--;
                System.out.println("target: " + Arrays.toString(arrowTarget));
                System.out.println("Wumpus: " + Arrays.toString(locations[1]));
                if (Arrays.equals(arrowTarget, locations[1])) {
                    System.out.println("You killed the Wumpus!\n You Win.");
                    gameEnd = true;
                    winLose = true;
                } else {
                    System.out.println("The Wumpus ran away!");
                    moveToAdjacentCave(locations[1], locations[0]);
                }
            }
        }
    }

    // Move to an adjacent cave, but not the same cave as the "avoid" cave.
    static void moveToAdjacentCave(int[] object, int[] avoid) {
        boolean valid = false;
        while (!valid) {
            if (probability(0.5)) {
                if (probability(0.5))
                    object[0] = (object[0] + 1 + 5) % 5;
                else
                    object[0] = (object[0] - 1 + 5) % 5;
            } else {
                if (probability(0.5))
                    object[1] = (object[1] + 1 + 5) % 5;
                else
                    object[1] = (object[1] - 1 + 5) % 5;
            }
            if (!Arrays.equals(object, avoid))
                valid = true;
        }
    }

    // a quick dice roll
    static boolean probability(double threshold) {
        return Math.random() < threshold;
    }

    // check if target is adjacent to the player
    public static boolean isAdjacentToPlayer(int[] target, int[] player) {
        int horizd = Math.abs(player[0] - target[0]);
        int vertd = Math.abs(player[1] - target[1]);
        return (horizd <= 1 || horizd >= 4) && (vertd <= 1 || vertd >= 4);
    }

    // get random coordinates for the object within the 5x5 grid
    static int[] randomCoordinates() {
        int x = (int) (Math.random() * 5);
        int y = (int) (Math.random() * 5);
        return new int[] { x, y };
    }

    // get initial locations for all objects
    static int[][] initLocations() {
        int[][] locations = new int[5][2];
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
        }
        return locations;
    }
}

// Work Division
// Arisa and Shaan did most of this stuff
// Zk did most of the graphic stuff.
// integrating logic with ui was a group effort.