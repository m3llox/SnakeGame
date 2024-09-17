import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameFrame(){
        //Setting up fullscreen JFrame
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.setUndecorated(true);
        gd.setFullScreenWindow(this);
        this.setVisible(true);
        ImageIcon icon = new ImageIcon("D:\\pics\\e2446927-8549-488b-b10c-0bc1bae73369.jpg");
        this.setIconImage(icon.getImage());
    }

}