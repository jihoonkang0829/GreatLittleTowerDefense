import java.awt.*;
import javax.swing.*;
public class Knight extends Militia {
    public Knight (double xpos, double xvel, int attack, int defense, int radius) {
        this.xpos = xpos;
        this.xvel = xvel;
        this.ypos = 300;
        this.health = 100;
        this.attack = attack;
        this.defense = defense;
        this.radius = radius;
        head = new Color (172, 172, 172);
        body = new Color (0, 0, 139);
        timer = 0;
        damageTime = 0;
        inContact = false;
        initialVelocity = xvel;
        hitCastle = false;
    }
    
    public void paint (Graphics g, int camXSum) {
        //draws the head (helmet)
        g.setColor (head);
        g.fillRect ((int) (xpos - radius)-camXSum, (int)(ypos - radius), (int)(radius*2), (int)(radius*2));
        g.setColor (new Color (0,0,0));
        g.drawRect ((int) (xpos - radius)-camXSum, (int)(ypos - radius), (int)(radius*2), (int)(radius*2));
        g.fillRect ((int)(xpos - radius + 28)-camXSum, (int)(ypos - radius + 10), 12, 8);
        g.fillRect ((int)(xpos - radius + 36)-camXSum, (int)(ypos - radius + 10), 4, 20);
        
        //draws the body
        g.setColor (body);
        g.fillRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
        g.setColor (new Color (0,0,0));
        g.drawRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
        
        //makes the blood-shedding effect after collision
        if (inContact) {
            if (timer < 500) {
                g.setColor (new Color (255, 0, 0, 255-255*(int)timer/500));
                g.fillRect ((int) (xpos - radius)-camXSum, (int)(ypos - radius), (int)(radius*2), (int)(radius*2));
                g.fillRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
            }
        }
        
        //draws the health bar
        g.setColor (new Color (0, 0, 0));
        g.fillRect ((int)(xpos - radius + 5)-camXSum, (int)(ypos + radius - 60), 30, 10);
        g.setColor (new Color (127, 254, 0));
        g.fillRect ((int)(xpos - radius + 5)-camXSum, (int)(ypos + radius - 60), (int)(30*health/100.0), 10);
        g.setColor (new Color (0, 0, 0));
        g.drawRect ((int)(xpos - radius + 5)-camXSum, (int)(ypos + radius - 60), 30, 10);
        
    }
    
    //see what type of the army it is
    public int getType () {
        return 3;
    }
}