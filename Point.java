import java.awt.*;
import java.lang.Math;
import java.util.ArrayList;

public class Point {
    double [] coordinates = {0,0};
    double [] velocity = {0,0};
    int radius = 10;
    int mass = 1;
    boolean highlighted = false;

    public Point(double x, double y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    public void draw(Graphics2D g2D) {
        if (highlighted) {
            g2D.setColor(Color.red);
        } else {
            g2D.setColor(Color.black);
        }
        g2D.fillOval((int)coordinates[0], (int)coordinates[1], radius, radius);
        g2D.setColor(Color.black);
    }

    public void draw_connections(Graphics2D g2D, ArrayList<Point> points) {
        for (Point p : points) {
            g2D.drawLine((int)coordinates[0]+radius/2, (int)coordinates[1]+radius/2, (int)p.coordinates[0]+radius/2, (int)p.coordinates[1]+radius/2);
        }
    }

    public void update() {
        setCoordinates(coordinates[0] + velocity[0], coordinates[1] + velocity[1]);
    }

    public void setVelocity(double x, double y) {
        velocity[0] = x;
        velocity[1] = y;
    }

    public void setCoordinates(double x, double y) {
        coordinates[0] = x;
        coordinates[1] = y;
    }

    public double distanceTo(Point p) {
        double x = p.coordinates[0] - this.coordinates[0];
        double y = p.coordinates[1] - this.coordinates[1];
        double distance = Math.sqrt(x*x + y*y);
        return distance;
    }
}
