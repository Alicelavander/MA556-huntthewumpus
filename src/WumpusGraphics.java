import java.awt.*;
import java.awt.event.KeyAdapter;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class WumpusGraphics extends Frame {
    public int[] playerPosition;
    public char previousUserInput = 'x';

    public Color warmYellow = new Color(143, 82, 1);
    public Color darkGrey = new Color(20, 20, 20);

    public char userInput;
    public boolean gameEnd;
    public boolean winLose;

    public WumpusGraphics() {
        setSize(751, 500);
        setVisible(true);
        setBackground(Color.BLACK);
    }

    public void drawCaves(Graphics g) {
        for (int i = 0; i <= 5; i++) { // draw horizontal lines
            g.setColor(darkGrey);
            g.drawLine(0, i * 100, 500, i * 100);
            g.drawLine(i * 100, 0, i * 100, 500);
        }
    }

    public void drawBorder(Graphics g) {
        g.setColor(Color.white);
        g.drawLine(500, 0, 750, 0);
        g.drawLine(500, 150, 750, 150);
        g.drawLine(500, 0, 500, 150);
        g.drawLine(750, 0, 750, 150);
    }

    public void drawInfoBox(Graphics g) {
        drawBorder(g);
    }

    public void drawPit(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(510, 52, 50, 30);
        Color myBlue = new Color(100, 100, 100);
        g.setColor(myBlue);
        g.fillOval(510, 50, 50, 30);
    }

    public void drawBat(Graphics g) {
        Color myBlue = new Color(0, 77, 84);
        g.setColor(myBlue);
        g.fillRect(510 + 50 + 25, 50, 50, 50);
    }

    public void drawWumpus(Graphics g) {
        Color myBlue = new Color(99, 0, 0);
        g.setColor(myBlue);
        g.fillRoundRect(510 + 50 + 25 + 50 + 25, 50, 50, 50, 50, 50);
    }

    public void shootArrow(Graphics g, char direction) {
        int movementDirectionX = 0;
        int movementDirectionY = 0;
        switch (direction) {
            case ('i') -> movementDirectionY = 1;
            case ('j') -> movementDirectionX = 1;
            case ('k') -> movementDirectionY = -1;
            case ('l') -> movementDirectionX = -1;
        }
        for (int i = 0; i < 75; i++) {
            int arrowLocationX = playerPosition[0] * 100 + 25 - 2 * i * movementDirectionX;
            int arrowLocationY = playerPosition[1] * 100 + 25 - 2 * i * movementDirectionY;
            g.fillRoundRect(arrowLocationX, arrowLocationY, 10, 10, 10, 10);
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            g.setColor(Color.black);
            g.fillRect(0, 0, 500, 500);
            drawCaves(g);
            drawPlayer(g);
            drawInfoBox(g);
        }
    }

    public void userInput(Graphics g) {
        addKeyListener(new KeyAdapter() { // have to use Adapter, Listener does not work
            public void keyPressed(java.awt.event.KeyEvent e) {
                userInput = e.getKeyChar();
            }
        });
        Main.setInputState(0, userInput);
        if (userInput == 'i' || userInput == 'j' || userInput == 'k' || userInput == 'l') {
            shootArrow(g, userInput);
        }
        userInput = 'x';
    }

    public void drawPlayer(Graphics g) {
        g.setColor(new Color(100, 100, 100));
        g.fillRoundRect(playerPosition[0] * 100 + 25, playerPosition[1] * 100 + 25, 50, 50, 50, 50);

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
        if (gameEnd == false) {
            playerPosition = Main.getLocation(0);
            drawCaves(g);
            drawBorder(g);

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
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
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
// currently the problem is, it runs the userInput code for a veryyy long time