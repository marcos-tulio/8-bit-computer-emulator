package Screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

/**
 *
 * @author MrCapybara
 */
public final class Cicle extends JLabel {
    
    private final Color COLOR_GREEN = new Color(80, 220, 100);
    
    private final Dimension SIZE = new Dimension(20, 20);

    private Color cicleColor = Color.BLACK;

    public Cicle() {
        setGray();
        setSize(SIZE);
        setMinimumSize(SIZE);
        setOpaque(true);
    }

    public void setGray() {
        cicleColor = Color.GRAY;
    }

    public void setRed() {
        cicleColor = Color.RED;
    }

    public void setGreen() {
        cicleColor = COLOR_GREEN;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(cicleColor);
        g2.fillOval(0, 0, SIZE.width, SIZE.height);
    }
}
