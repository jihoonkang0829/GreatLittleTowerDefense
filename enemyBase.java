import java.awt.*;
import javax.swing.*;
public class enemyBase {
    public int durability;
    public int defense;
    enemyBase () {
        durability = 2000;
        defense = 20;
    }

    public void paint (Graphics g, int camXSum) {
        if (durability != 0) {
            g.setColor (new Color (105, 105, 105));
            g.fillRect (2900-camXSum, 100, 100, 282);
            g.fillRect (2900-camXSum, 80, 20, 20);
            g.fillRect (2940-camXSum, 80, 20, 20);
            g.fillRect (2980-camXSum, 80, 20, 20);
            g.setColor (new Color (0, 0, 0));
            g.drawRect (2900-camXSum, 100, 100, 282);
            g.drawRect (2900-camXSum, 80, 20, 20);
            g.drawRect (2940-camXSum, 80, 20, 20);
            g.drawRect (2980-camXSum, 80, 20, 20);
            for (int i = 0; i <= 13; i++) {
                if (i % 3 == 0) {
                    g.drawRect (2900-camXSum, 100+i*20, 30, 20);
                    g.drawRect (2930-camXSum, 100+i*20, 30, 20);
                    g.drawRect (2960-camXSum, 100+i*20, 30, 20);
                    g.drawRect (2990-camXSum, 100+i*20, 10, 20);
                }
                if (i % 3 == 1) {
                    g.drawRect (2900-camXSum, 100+i*20, 10, 20);
                    g.drawRect (2910-camXSum, 100+i*20, 30, 20);
                    g.drawRect (2940-camXSum, 100+i*20, 30, 20);
                    g.drawRect (2970-camXSum, 100+i*20, 30, 20);
                }
                if (i % 3 == 2) {
                    g.drawRect (2900-camXSum, 100+i*20, 20, 20);
                    g.drawRect (2920-camXSum, 100+i*20, 30, 20);
                    g.drawRect (2950-camXSum, 100+i*20, 30, 20);
                    g.drawRect (2980-camXSum, 100+i*20, 20, 20);
                }
            }
            g.fillArc (2850-camXSum, 250, 100, 100, 90, -90);
            g.fillRect (2900-camXSum, 300, 50, 182);
            //draws the health bar
            g.setColor (new Color (0, 0, 0));
            g.fillRect (2910-camXSum, 50, 80, 10);
            g.setColor (new Color (127, 254, 0));
            g.fillRect (2910-camXSum, 50, (int)(80*durability/2000.0), 10);
            g.setColor (new Color (0, 0, 0));
            g.drawRect (2910-camXSum, 50, 80, 10);
        }
    }
    
    public boolean defeat (){
        return durability == 0;
    }
}