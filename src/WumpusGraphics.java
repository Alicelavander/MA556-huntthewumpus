import java.awt.*;
import java.awt.event.KeyAdapter;
import java.util.Arrays; // used when testing if the arrays and our manipulation of locations were working correctly.

import javax.swing.JOptionPane; //for displaying an alert after winning or losing

public class WumpusGraphics extends Frame { // graphics componenet
    public int[] playerPosition;

    // colors for styling purposes
    public Color warmYellow = new Color(143, 82, 1);
    public Color darkGrey = new Color(20, 20, 20);

    public char userInput; // which key the user pressed.
    public boolean gameEnd; // if game end or not
    public boolean winLose; // win if true, lose if false

    public WumpusGraphics() { // initializes the frame, so we can paint on it
        setSize(751, 500);
        setVisible(true);
        setBackground(Color.BLACK);
    }

    public void drawCaves(Graphics g) { // creates a 5x5 grid to represent the cave system
        for (int i = 0; i <= 5; i++) {
            g.setColor(darkGrey);
            g.drawLine(0, i * 100, 500, i * 100);
            g.drawLine(i * 100, 0, i * 100, 500);
        }
    }

    public void drawBorder(Graphics g) { // draws a border around info regarding wumpus, pit and bats
        g.setColor(Color.white);
        g.drawLine(500, 0, 750, 0);
        g.drawLine(500, 150, 750, 150);
        g.drawLine(500, 0, 500, 150);
        g.drawLine(750, 0, 750, 150);
    }

    public void drawPit(Graphics g) { // paints an oval to represent a pit nearby
        g.setColor(Color.red);
        g.fillOval(510, 52, 50, 30);
        Color myBlue = new Color(100, 100, 100);
        g.setColor(myBlue);
        g.fillOval(510, 50, 50, 30);
    }

    public void drawBat(Graphics g) { // paints a blue circle to represent bats nearby
        Color myBlue = new Color(0, 77, 84);
        g.setColor(myBlue);
        g.fillRect(510 + 50 + 25, 50, 50, 50);
    }

    public void drawWumpus(Graphics g) { // paints a dark red square to represent wumpus nearby
        Color myBlue = new Color(99, 0, 0);
        g.setColor(myBlue);
        g.fillRoundRect(510 + 50 + 25 + 50 + 25, 50, 50, 50, 50, 50);
    }

    public void shootArrow(Graphics g, char direction) { // covers the graphics of shooting the arrow
        int movementDirectionX = 0;
        int movementDirectionY = 0;
        switch (direction) {
            case ('i') -> movementDirectionY = 1;
            case ('j') -> movementDirectionX = 1;
            case ('k') -> movementDirectionY = -1;
            case ('l') -> movementDirectionX = -1;
        }
        for (int i = 0; i < 75; i++) { // only displays graphics, functinoality of what happens after shooting is
                                       // handled in Main.java
            int arrowLocationX = playerPosition[0] * 100 + 25 - 2 * i * movementDirectionX;
            int arrowLocationY = playerPosition[1] * 100 + 25 - 2 * i * movementDirectionY;
            g.fillRoundRect(arrowLocationX, arrowLocationY, 10, 10, 10, 10);
            try { // delay so can see the arrow
                Thread.sleep(8);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // paints the background/player over the previous arrow
            g.setColor(Color.black);
            g.fillRect(0, 0, 500, 500);
            drawCaves(g);
            drawPlayer(g);
            drawBorder(g);
        }
    }

    public void userInput(Graphics g) {
        addKeyListener(new KeyAdapter() { // have to use Adapter, Listener does not work
            public void keyPressed(java.awt.event.KeyEvent e) {
                userInput = e.getKeyChar();
            }
        });
        // important to keep these lines outside of the keyListener,
        // this way it only runs once, not many times
        if (userInput == 'w' || userInput == 'a' || userInput == 's' || userInput == 'd' || userInput == 'i'
                || userInput == 'j' || userInput == 'k' || userInput == 'l')
            Main.setInputState(0, userInput); // sends arrow direction or movement so main can handle the game backend
        if (userInput == 'i' || userInput == 'j' || userInput == 'k' || userInput == 'l') {
            shootArrow(g, userInput);
        }
        userInput = 'x'; // need to default to something thats not a user input so the above if
                         // statements don't run continously
    }

    public void drawPlayer(Graphics g) { // draws the player
        // converts grid coords into actual locations on the Frame.
        g.setColor(new Color(100, 100, 100));
        g.fillRoundRect(playerPosition[0] * 100 + 25, playerPosition[1] * 100 + 25, 50, 50, 50, 50);

        // draws warm yellow lines to provide illusion of light illuminating parts of
        // the cave
        g.setColor(warmYellow);
        g.drawLine(playerPosition[0] * 100, playerPosition[1] * 100 - 50, playerPosition[0] * 100,
                playerPosition[1] * 100 + 150);
        g.drawLine(playerPosition[0] * 100 + 100, playerPosition[1] * 100 - 50, playerPosition[0] * 100 + 100,
                playerPosition[1] * 100 + 150);
        g.drawLine(playerPosition[0] * 100 - 50, playerPosition[1] * 100, playerPosition[0] * 100 + 150,
                playerPosition[1] * 100);
        g.drawLine(playerPosition[0] * 100 - 50, playerPosition[1] * 100 + 100, playerPosition[0] * 100 + 150,
                playerPosition[1] * 100 + 100);
    }

    public void paint(Graphics g) {
        gameEnd = Main.getGameEnd();
        if (gameEnd == false) { // only paints if the game hasn't ended(duh)
            playerPosition = Main.getLocation(0); // gets playerPosition, in terms of grid location(5x5) from Main
            drawCaves(g);
            drawBorder(g);

            // conditionally render the pit,bat,and wumpus symbols according to game rules
            // handled in main
            if (Main.isAdjacentToPlayer(Main.getLocation(1), Main.getLocation(0))) {
                drawWumpus(g);
            }
            if (Main.isAdjacentToPlayer(Main.getLocation(3), Main.getLocation(0))) {
                drawBat(g);
            }
            if (Main.isAdjacentToPlayer(Main.getLocation(4), Main.getLocation(0))) {
                drawPit(g);
            }

            drawPlayer(g);

            userInput(g);
            repaint(); // continously reruns the paint function
            // delay so that the program doesn't bug out.
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else { // text display after winning or losing
            winLose = Main.getWinLose();
            System.out.println("enter a pit");
            if (winLose == false) { // if lost
                // Code To popup an WARNING_MESSAGE Dialog.
                JOptionPane.showMessageDialog(this, "You Shud Get Better at the Game, You Lost",
                        "WARNING", JOptionPane.WARNING_MESSAGE);
            } else if (winLose == true) { // if won
                JOptionPane.showMessageDialog(this, "I Guess Your Decent?? You Won...",
                        "Question", JOptionPane.QUESTION_MESSAGE);
            }
        }
    }
}

// Citations
// used RGB color picker for the colors, used stack overflow for some debugging
// help, although it was mainly just testing around
// used Geeks for Geek to figure out how to display alerts
// https://www.geeksforgeeks.org/message-dialogs-java-gui/

// Side note
// I(zk) also tried to use chatgpt to better understand the code,
// but it didn't help at all, meaning it had no impact on the code above
// Just thought I would mention that part tho.