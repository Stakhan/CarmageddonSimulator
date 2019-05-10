package stats;

import java.awt.*;
import javax.swing.*;

public class ComputeStatistics extends JFrame {
	/*
    private final int WIDTH = 330;
    private final int HEIGHT = 500;
    private Container drawable;
    private JPanel canvas;
    public SimpleGraph(double[] someData) {
        super("SimpleGraph");
        drawable = getContentPane();
        canvas = new GraphCanvas(someData);
        drawable.add(canvas);
        setSize(WIDTH, HEIGHT);
    }
    public class GraphCanvas extends JPanel {
        private double[] data;
        private int points;
        private double[] XData;
        private double[] YData;
        public GraphCanvas(double[] someData) {
            super();
            data = someData;
            points = data.length / 2;
            XData = new double[points];
            YData = new double[points];
            for(int i = 0; i < points; i++) {
                XData[i] = data[i * 2];
                YData[i] = data[i * 2 + 1];
            }
        }
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            for(int i = 0; i < points - 1; i++) {
                int x0 = (int) (XData[i] + 0.5);
                int x1 = (int) (XData[i + 1] + 0.5);
                int y0 = (int) (YData[i] +0.5);
                int y1 = (int) (YData[i + 1] + 0.5);
                g2.drawLine(x0, y0, x1, y1);
                if (i == 0)
                  g2.drawString(("" + x0 + ", " + y0), x0 - 20, y0 + 10);
                if (i == points - 2)
                  g2.drawString(("" + x1 + ", " + y1), x1, y1);
            }
        }
    }
    public static void main(String[] args) {
        double[] d = { 30.0, 150.0,
                       33.0, 145.0,
                       36.0, 162.0,
                       39.0, 128.0,
                       48.0, 114.0,
                       70.0, 240.0,
                       81.0, 400.0,
                      130.0, 450.0,
                      230.0,  85.0,
                      255.0,  30.0 };
        Frame f = new SimpleGraph(d);
        f.show();
    }
    */
}