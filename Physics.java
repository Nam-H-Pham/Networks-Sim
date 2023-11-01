import java.util.ArrayList;

public class Physics {
    double gravity;
    MyPanel panel;

    public Physics(MyPanel panel) {
        gravity = 0.2;
        this.panel = panel;
    }


    public void applyGravity(PointHandler ph) {
        ArrayList<Point> points = ph.points;
        for (Point p : points) {
            p.setVelocity(p.velocity[0], p.velocity[1] + gravity);
        }
    }

    public void applyDrag(PointHandler ph) {
        ArrayList<Point> points = ph.points;
        for (Point p : points) {
            p.setVelocity(p.velocity[0] * 0.99, p.velocity[1] * 0.99);
        }
    }

    public void applyBoundaryCollisions(PointHandler ph) {
        ArrayList<Point> points = ph.points;
        int width = panel.WIDTH;
        int height = panel.HEIGHT;
        for (Point p : points) {
            if (p.coordinates[0] < 0) {
                p.setCoordinates(0, p.coordinates[1]);
                p.setVelocity(p.velocity[0] * -1, p.velocity[1]);
            }
            if (p.coordinates[0] > width - p.radius) {
                p.setCoordinates(width - p.radius, p.coordinates[1]);
                p.setVelocity(p.velocity[0] * -1, p.velocity[1]);
            }
            if (p.coordinates[1] < 0) {
                p.setCoordinates(p.coordinates[0], 0);
                p.setVelocity(p.velocity[0], p.velocity[1] * -1);
            }
            if (p.coordinates[1] > height - p.radius) {
                p.setCoordinates(p.coordinates[0], height - p.radius);
                p.setVelocity(p.velocity[0], p.velocity[1] * -1);
            }
        }
    }

    public void applyPointAttraction(PointHandler ph, double fieldStrength, double fieldRange, boolean connected) {
        ArrayList<Point> points = ph.points;

        for (int n=0; n < points.size(); n++) {
            Point p = points.get(n);
            for (int m=0; m < points.size(); m++) {
                Point q = points.get(m);
                if (ph.isConnected(m, n) == connected) {
                    if (p != q) {
                        double distance = p.distanceTo(q);
                        if (distance < fieldRange) {
                            double x = q.coordinates[0] - p.coordinates[0];
                            double y = q.coordinates[1] - p.coordinates[1];
                            double angle = Math.atan2(y, x);
                            double force = fieldStrength * (fieldRange - distance);
                            double xForce = force * Math.cos(angle);
                            double yForce = force * Math.sin(angle);
                            p.setVelocity(p.velocity[0] + xForce, p.velocity[1] + yForce);
                        }
                    }
                }
            }
        }

    }

    public void applyPointBinding(PointHandler ph, double maxDistance, double springConstant, boolean connected) {
        ArrayList<Point> points = ph.points;
        for (int n=0; n < points.size(); n++) {
            Point p = points.get(n);
            for (int m=0; m < points.size(); m++) {
                Point q = points.get(m);
                if (ph.isConnected(m, n)  == connected) {
                    if (p != q) {
                        double distance = p.distanceTo(q);
                        if (distance > maxDistance) {
                            double x = q.coordinates[0] - p.coordinates[0];
                            double y = q.coordinates[1] - p.coordinates[1];
                            double angle = Math.atan2(y, x);
                            double force = springConstant * (distance - maxDistance);
                            double xForce = force * Math.cos(angle);
                            double yForce = force * Math.sin(angle);
                            p.setVelocity(p.velocity[0] + xForce, p.velocity[1] + yForce);
                        }
                    }
                }
            }
        }
    }
}
