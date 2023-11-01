import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MyPanel extends JPanel implements ActionListener, MouseListener, KeyListener {
    final int WIDTH = 900;
    final int HEIGHT = 900;

    Timer timer;
    PointHandler Points = new PointHandler();
    Physics Physics;

    public MyPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.white);
        timer = new Timer(1, this);
        timer.start();

        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus(); 

        this.Physics = new Physics(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.Points.drawPoints((Graphics2D)g);
        this.Points.drawPointConnections((Graphics2D)g);
    }

    public void actionPerformed(ActionEvent e) {

        //this.Physics.applyGravity(this.Points);
        this.Physics.applyDrag(this.Points);

        this.Physics.applyPointAttraction(this.Points, -0.001, 1000, true);
        this.Physics.applyPointAttraction(this.Points, -0.001, 1000, false);

        this.Physics.applyPointBinding(this.Points, 100, 0.01, true);
        this.Physics.applyBoundaryCollisions(this.Points);
        this.Points.updatePoints();

        repaint();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX();
            int y = e.getY();
            Point p = new Point(x, y);
            this.Points.addPoint(p);
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            int x = e.getX();
            int y = e.getY();

            Point closest_point = this.Points.getClosestPoint(x, y);
            closest_point.highlighted = (closest_point.highlighted) ? false : true;
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            int x = e.getX();
            int y = e.getY();

            Point closest_point = this.Points.getClosestPoint(x, y);
            this.Points.removePoint(closest_point);
        }
    }

    public void mouseEntered(MouseEvent e){
    }

    public void mouseExited(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}