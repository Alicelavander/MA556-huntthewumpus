import java.awt.*;
import java.awt.event.KeyAdapter;

public class WumpusGraphics extends Frame {
    public int mapDimensions = 5;
    public int xLocation = 2;
    public int yLocation = 2;
    public int xPos;
    public int yPos;
    public char userMoveTemp;
    public int direction;
    public Color warmYellow = new Color(143, 82, 1);
    public Color darkGrey = new Color(20, 20, 20);

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

    public void movementMechanism(Graphics g) {
        addKeyListener(new KeyAdapter() { // have to use Adapter, Listener does not work
            public void keyPressed(java.awt.event.KeyEvent e) {
                userMoveTemp = e.getKeyChar();
            }
        });
        yPos = yLocation * 100 + 25;
        xPos = xLocation * 100 + 25;
        if (userMoveTemp == 'w') {
            yLocation -= 1;
        } else if (userMoveTemp == 's') {
            yLocation += 1;
        } else if (userMoveTemp == 'a') {
            xLocation -= 1;
        } else if (userMoveTemp == 'd') {
            xLocation += 1;
        }
        Color myBlue = new Color(100, 100, 100);
        g.setColor(myBlue);
        g.fillRoundRect(xPos, yPos, 50, 50, 50, 50);
        drawGlowMechanism(g);
        repaint();
        userMoveTemp = 'x';
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

    public void drawGlowMechanism(Graphics g) {
        g.setColor(warmYellow);
        g.drawLine(xLocation * 100, yLocation * 100 - 50, xLocation * 100, yLocation * 100 + 150);
        g.drawLine(xLocation * 100 + 100, yLocation * 100 - 50, xLocation * 100 + 100, yLocation * 100 + 150);
        g.drawLine(xLocation * 100 - 50, yLocation * 100, xLocation * 100 + 150, yLocation * 100);
        g.drawLine(xLocation * 100 - 50, yLocation * 100 + 100, xLocation * 100 + 150, yLocation * 100 + 100);
    }

    public void paint(Graphics g) {
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
        movementMechanism(g);
    }

    public static void main(String[] args) {
        new WumpusGraphics();
    }
}

// the options are pit, bats, wumpus
// pit means windy, bats you hear fluttering, wumpus, it smells.

// add a shoot arrow mechanism, displays an arrow getting shot forward
// add a glowing mechanism, where you light up the cave or smth.