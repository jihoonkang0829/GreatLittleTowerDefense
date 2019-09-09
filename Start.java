import java.awt.*;
import javax.swing.*;

public class Start
{
   public static void main(String[] args){
       JFrame frame = new JFrame("Great Little Tower Defense");
       
       Panel p = new Panel();
       frame.add(p);

       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
       frame.setSize(1000, 600);
 
       long currentTime = System.currentTimeMillis();
       while (true){
            p.requestFocus();
            long frameStart = System.currentTimeMillis();
            p.update(frameStart - currentTime);
            frame.repaint();
            currentTime = frameStart;
       }
   }
}
