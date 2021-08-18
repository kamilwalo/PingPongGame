import javax.swing.*;
import java.awt.*;

public class PingPongFrame extends JFrame {
    PingPongFrame(){
        this.add(new PingPongPanel());
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
