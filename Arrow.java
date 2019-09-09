import java.awt.*;
import javax.swing.*;
public class Arrow {
    public double xpos, ypos, xobj, yobj, xvel, yvel;
    public double airTime; //tracks the flight time of the arrow
    public double gravity; //gravity in the field
    public boolean collide; //see if the arrow has collided with the enemy
    Arrow () {
    }
    
    Arrow (double xpos, double ypos, double objectXPos, double objectYPos) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.xobj = objectXPos;
        this.yobj = objectYPos;
        gravity = 100.0;
        airTime = 1.0;
        xvel = Math.abs(xobj-xpos)/airTime;
        yvel = -gravity*airTime/2;
        collide = false;
    }
    
    public void update (long dt) {
        xpos += xvel*dt/1000.0;
        yvel += gravity*dt/1000.0;
        ypos += yvel*dt/1000.0;
    }
    
    public void paint (Graphics g, int camXSum) {
        //sets the thickness of the arrow
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke (new BasicStroke (2));
        if (!collide) {
            g.drawLine ((int)(xpos-30*xvel/Math.sqrt(xvel*xvel+yvel*yvel))-camXSum, (int)(ypos-30*yvel/Math.sqrt(xvel*xvel+yvel*yvel)), (int)xpos-camXSum, (int)ypos);
        }
        //if collided with the enemy, make the shape of the arrow constant
        if (collide) {
            g.drawLine ((int)(xpos-30*xvel/Math.sqrt(520*520+-11.2*-11.2))-camXSum, (int)(ypos-30*yvel/Math.sqrt(520*520+-11.2*-11.2)), (int)xpos-camXSum, (int)ypos);
        }
        //sets the thickness of the lines back to original
        g2.setStroke (new BasicStroke (1));
    }
    
    //sees if the arrow has collided with the enemy
    public boolean collide (enemyMilitia b) {
        if (xpos >= b.xpos && ypos <= b.ypos) {
            xpos = b.xpos;
            ypos = b.ypos;
            xvel = 0;
            yvel = 0;
            gravity = 0;
            collide = true;
            return true;
        }
        return false;
    }
    
}