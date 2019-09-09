import java.awt.*;
import javax.swing.*;
public class Base{
    public int durability;
    public int defense;
    Base () {
        durability = 2000;
        defense = 20;
    }

    public void paint (Graphics g, int camXSum) {
        if (durability != 0) {
            g.setColor (new Color (192, 192, 192));
            g.fillRect (0-camXSum, 100, 100, 282);
            g.fillRect (0-camXSum, 80, 20, 20);
            g.fillRect (40-camXSum, 80, 20, 20);
            g.fillRect (80-camXSum, 80, 20, 20);
            g.setColor (new Color (0, 0, 0));
            g.drawRect (0-camXSum, 100, 100, 282);        
            g.drawRect (0-camXSum, 80, 20, 20);        
            g.drawRect (40-camXSum, 80, 20, 20);        
            g.drawRect (80-camXSum, 80, 20, 20);
            for (int i = 0; i <= 13; i++) {
                if (i % 3 == 0) {
                    g.drawRect (0-camXSum, 100+i*20, 30, 20);
                    g.drawRect (30-camXSum, 100+i*20, 30, 20);
                    g.drawRect (60-camXSum, 100+i*20, 30, 20);
                    g.drawRect (90-camXSum, 100+i*20, 10, 20);
                }
                if (i % 3 == 1) {
                    g.drawRect (0-camXSum, 100+i*20, 10, 20);
                    g.drawRect (10-camXSum, 100+i*20, 30, 20);
                    g.drawRect (40-camXSum, 100+i*20, 30, 20);
                    g.drawRect (70-camXSum, 100+i*20, 30, 20);
                }
                if (i % 3 == 2) {
                    g.drawRect (0-camXSum, 100+i*20, 20, 20);
                    g.drawRect (20-camXSum, 100+i*20, 30, 20);
                    g.drawRect (50-camXSum, 100+i*20, 30, 20);
                    g.drawRect (80-camXSum, 100+i*20, 20, 20);
                }
            }
            g.fillArc (50-camXSum, 250, 100, 100, 90, 90);
            g.fillRect (50-camXSum, 300, 50, 182);  
    
            //draws the health bar
            g.setColor (new Color (0, 0, 0));
            g.fillRect (10-camXSum, 50, 80, 10);
            g.setColor (new Color (127, 254, 0));
            g.fillRect (10-camXSum, 50, (int)(80*durability/2000.0), 10);
            g.setColor (new Color (0, 0, 0));
            g.drawRect (10-camXSum, 50, 80, 10);
        }
    }

    public boolean defeat () {
        return durability == 0;
    }
}