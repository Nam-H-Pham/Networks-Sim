import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{

    MyPanel panel;

    public MyFrame() {
        JFrame frame = new JFrame("Network Visualisation");
        panel = new MyPanel();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
