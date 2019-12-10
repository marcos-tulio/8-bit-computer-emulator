package Screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author MrCapybara
 */
public final class Oscillator extends JLabel {

    //private final Color COLOR_GREEN = new Color(80, 220, 100);
    private final Dimension SIZE = new Dimension(264, 45);

    private final int BLOCK_SIZE = 35;
    private final int MARGIN = 8;

    private final List<Boolean> level = new ArrayList<>(
            Arrays.asList(false, false, false, false, false, false, false)
    );

    public Oscillator() {
        setSize(SIZE);
        setMinimumSize(SIZE);
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(new EtchedBorder());
    }

    public void setValue(boolean value) {
        level.add(0, value);
        level.remove(level.size() - 1);

        //System.out.println(level);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(0, MARGIN, SIZE.width, MARGIN);
        g2.drawLine(0, BLOCK_SIZE, SIZE.width, BLOCK_SIZE);

        for (int i = 0; i < level.size(); i++) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine((BLOCK_SIZE * i) + MARGIN, 0, (BLOCK_SIZE * i) + MARGIN, SIZE.height);

            g2.setColor(Color.BLACK);

            if (level.get(i))
                g2.drawLine((BLOCK_SIZE * i) + MARGIN, MARGIN, (BLOCK_SIZE * (i + 1)) + MARGIN, MARGIN);
            else
                g2.drawLine((BLOCK_SIZE * i) + MARGIN, BLOCK_SIZE, (BLOCK_SIZE * (i + 1)) + MARGIN, BLOCK_SIZE);

            if (i > 0) {
                if (!Objects.equals(level.get(i), level.get(i - 1)))
                    g2.drawLine((BLOCK_SIZE * i) + MARGIN, MARGIN, (BLOCK_SIZE * i) + MARGIN, BLOCK_SIZE);
            }
        }

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine((BLOCK_SIZE * level.size()) + MARGIN, 0, (BLOCK_SIZE * level.size()) + MARGIN, SIZE.height);
    }
}
