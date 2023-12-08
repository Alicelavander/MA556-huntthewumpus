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

        int arrowAmount = 5;
        int roomNumber;

        while(!gameEnd){
            roomNumber = 5 * player[1] + player[0] + 1;
            System.out.println("You are in Room" + roomNumber);
            /*
            TODO: check adjacent places and show the hint :)
             */
            System.out.print("Shoot or move (s/m)? ");
            String command = s.nextLine();
            while(!command.equals("s") && !command.equals("m")){
                System.out.print("Shoot or move (s/m)? ");
                command = s.nextLine();
            }

            System.out.print("Choose direction (n/e/s/w): ");
            String direction = s.nextLine();
            while(!direction.equals("n") && !direction.equals("e") && !direction.equals("s") && !direction.equals("w")){
                System.out.print("Choose direction (n/e/s/w): ");
                direction = s.nextLine();
            }
        }
    }

    public static int[] randomCoordinates() {
        int x = (int) (Math.random() * 5);
        int y = (int) (Math.random() * 5);
        return new int[]{x, y};
    }
}
