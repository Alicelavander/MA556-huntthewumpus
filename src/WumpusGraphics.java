import java.awt.*;
import java.awt.event.KeyAdapter;

public class WumpusGraphics extends Frame {
    public int mapDimensions = 5;
    public int[] playerPosition;

    public Color warmYellow = new Color(143, 82, 1);
    public Color darkGrey = new Color(20, 20, 20);

    char userInput;

    public WumpusGraphics() {
        setSize(751, 500);
        setVisible(true);
        setBackground(Color.BLACK);
    }

    public void drawCaves(Graphics g) {
        for (int i = 0; i <= mapDimensions; i++) { // draw horizontal lines
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

    public void userInput(Graphics g) {
        addKeyListener(new KeyAdapter() { // have to use Adapter, Listener does not work
            public void keyPressed(java.awt.event.KeyEvent e) {
                userInput = e.getKeyChar();
            }
        });

        switch (userInput) {
            case 'w' -> playerPosition[1] = (playerPosition[1] - 1 + 5) % 5;
            case 'd' -> playerPosition[0] = (playerPosition[0] + 1 + 5) % 5;
            case 's' -> playerPosition[1] = (playerPosition[1] + 1 + 5) % 5;
            case 'a' -> playerPosition[0] = (playerPosition[0] - 1 + 5) % 5;
            case ' ' -> shootArrow(g);
        }

        userInput = 'x';
    }

    public void drawPit(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(510, 52, 50, 30);
        Color myBlue = new Color(100, 100, 100);
        g.setColor(myBlue);
        g.fillOval(510, 50, 50, 30);
    }

    public void drawBat(Graphics g) {
        Color myBlue = new Color(100, 100, 100);
        g.setColor(myBlue);
        g.fillRect(510 + 50 + 25, 50, 50, 50);
    }

    public void drawWumpus(Graphics g) {
        Color myBlue = new Color(100, 100, 100);
        g.setColor(myBlue);
        g.fillRoundRect(510 + 50 + 25 + 50 + 25, 50, 50, 50, 50, 50);
    }

    public void shootArrow(Graphics g) {
        for (int i = 0; i < 75; i++) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            g.setColor(Color.black);
            g.fillRect(0, 0, 500, 500);
            drawCaves(g);
            drawPlayer(g);
            drawInfoBox(g);
            int arrowLocationX = playerPosition[0] * 100 + 25;
            int arrowLocationY = playerPosition[1] * 100 + 25 - 2 * i;
            paintArrow(g, arrowLocationX, arrowLocationY);
        }
    }

    public void paintArrow(Graphics g, int arrowLocationX, int arrowLocationY) {
        g.setColor(new Color(245, 24, 24));
        g.fillRoundRect(arrowLocationX, arrowLocationY, 12, 12, 15, 15);
        g.setColor(new Color(245, 54, 24));
        g.fillRoundRect(arrowLocationX, arrowLocationY + 8, 12, 12, 15, 15);
        g.setColor(new Color(245, 74, 24));
        g.fillRoundRect(arrowLocationX, arrowLocationY + 16, 12, 12, 15, 15);
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
        paintArrow(g, playerPosition[0] * 100 + 25, playerPosition[1] * 100 + 25);
    }

    public void paint(Graphics g) {
        playerPosition = Main.getLocation(0);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        drawCaves(g);
        drawBorder(g);
        drawPit(g);
        drawBat(g);
        drawWumpus(g);
        drawPlayer(g);

        userInput(g);

        repaint();
    }
}

// the options are pit, bats, wumpus
// pit means windy, bats you hear fluttering, wumpus, it smells.

// add a shoot arrow mechanism, displays an arrow getting shot forward
// add a glowing mechanism, where you light up the cave or smth.