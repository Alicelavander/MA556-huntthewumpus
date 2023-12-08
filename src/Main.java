import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        boolean gameEnd = false;
        int[] wumpus, bat, pit, player, arrow;
        /*
        TODO: use while loop & randomCoordinates() to place all 5 object in different places
         */
        player = randomCoordinates();
        wumpus = randomCoordinates();

        int arrowAmount = 5;
        int roomNumber;

        while(!gameEnd){
            roomNumber = 5 * player[1] + player[0] + 1;
            System.out.println("You are in Room" + roomNumber);
            /*
            TODO: check adjacent places and show the hint
             */
            System.out.print("Shoot or move (s/m)? ");
            char command = s.nextLine().toCharArray()[0];
            while(command != 's' && command != 'm'){
                System.out.print("Shoot or move (s/m)? ");
                command = s.nextLine().toCharArray()[0];
            }

            System.out.print("Choose direction (n/e/s/w): ");
            char direction = s.nextLine().toCharArray()[0];
            while(direction != 'n' && direction != 'e' && direction != 's' && direction != 'w'){
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
                if(target == wumpus) gameEnd = true;

            } else {

            }
        }
    }

    public static int[] randomCoordinates() {
        int x = (int) (Math.random() * 5);
        int y = (int) (Math.random() * 5);
        return new int[]{x, y};
    }
}
