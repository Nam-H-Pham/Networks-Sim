import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;

public class PointHandler {
    ArrayList<Point> points = new ArrayList<Point>(); 
    int [][] connection_matrix = {{0}};

    public PointHandler() {
        createPoints();
    }

    private void createPoints() {

        int num_points = this.connection_matrix.length;
        
        for (int i = 0; i < num_points; i++) {
            Point p = new Point(Math.random() * 500, Math.random() * 500);
            addPoint(p);
        }
    }

    public void addPoint(Point p) {
        this.points.add(p);
        

        ArrayList<Integer> connected_points = new ArrayList<Integer>();
        for (int i=0; i < this.points.size(); i++) {
            Point q = this.points.get(i);
            if (q.highlighted) {
                connected_points.add(i);
            }
        }

        int [][] new_connection_matrix = new int[this.connection_matrix.length + 1][this.connection_matrix.length + 1];
        for (int i=0; i < this.connection_matrix.length; i++) {
            for (int j=0; j < this.connection_matrix.length; j++) {
                new_connection_matrix[i][j] = this.connection_matrix[i][j];
            }
        }
        
        for (int i=0; i < connected_points.size(); i++) {
            int index = connected_points.get(i);
            new_connection_matrix[this.connection_matrix.length-1][index] = 1;
            new_connection_matrix[index][this.connection_matrix.length-1] = 1;
        }

        System.out.println(connected_points);

        this.connection_matrix = new_connection_matrix;
    }

    public void removePoint(Point p) {
        int index = this.points.indexOf(p);

        int [][] new_connection_matrix = new int[this.connection_matrix.length - 1][this.connection_matrix.length - 1];
        for (int i=0; i < this.connection_matrix.length; i++) {
            for (int j=0; j < this.connection_matrix.length; j++) {
                if (i < index && j < index) {
                    new_connection_matrix[i][j] = this.connection_matrix[i][j];
                }
                if (i < index && j > index) {
                    new_connection_matrix[i][j-1] = this.connection_matrix[i][j];
                }
                if (i > index && j < index) {
                    new_connection_matrix[i-1][j] = this.connection_matrix[i][j];
                }
                if (i > index && j > index) {
                    new_connection_matrix[i-1][j-1] = this.connection_matrix[i][j];
                }
            }
        }
        this.connection_matrix = new_connection_matrix;
        this.points.remove(index);
    }

    public void drawPoints(Graphics2D g2D) {
        for (Point p : this.points) {
            p.draw(g2D);
        }
    }

    public boolean isConnected(int p, int q) {
        if (this.connection_matrix[p][q] == 1) {
            return true;
        }
        return false;
    }

    public void drawPointConnections(Graphics2D g2D) {
        for (int p=0; p < this.points.size(); p++) {
            ArrayList<Point> connections = new ArrayList<Point>();
            for (int q=0; q < this.points.size(); q++) {
                if (this.connection_matrix[p][q] == 1) {
                    connections.add(this.points.get(q));
                }
            }
            this.points.get(p).draw_connections(g2D, connections);
        }
    }

    public void updatePoints() {
        for (Point p : this.points) {
            p.update();
        }
    }

    public Point getClosestPoint(int x, int y) {
        Point closest_point = this.points.get(0);
        double closest_distance = closest_point.distanceTo(new Point(x, y));
        for (Point p : this.points) {
            double distance = p.distanceTo(new Point(x, y));
            if (distance < closest_distance) {
                closest_distance = distance;
                closest_point = p;
            }
        }
        return closest_point;
    }
}
