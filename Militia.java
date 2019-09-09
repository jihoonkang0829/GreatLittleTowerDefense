import java.awt.*;
import javax.swing.*;
public class Militia{
    public double xpos; //tracks the x position of the militia
    public double ypos; //tracks the y position of the militia
    public double xvel; //tracks the x velocity of the militia
    public double health; //tracks the health of the militia
    public int attack; //attack ability of the militia
    public int defense; //defense ability of the militia
    public int radius; // head radius of the militia
    public Color head; // color of the head
    public Color body; //color of the body
    public long timer; //tracks the time after the collision
    public long damageTime; //tracks the time after it has collided with the castle
    public boolean inContact; //see if the militia has contacted the enemy;
    public double initialVelocity; //stores the initial velocity
    public boolean hitCastle; //see if the militia has collided with the castle
    public Militia(){
        
    }
    
    public Militia (double xpos, double xvel, int attack, int defense, int radius) {
        this.xpos = xpos;
        this.xvel = xvel;
        this.ypos = 300;
        this.health = 100;
        this.attack = attack;
        this.defense = defense;
        this.radius = radius;
        head = new Color (255, 235, 205);
        body = new Color (0, 0, 139);
        timer = 0;
        damageTime = 0;
        inContact = false;
        initialVelocity = xvel;
        hitCastle = false;
    }
    
    //return true if the army collided    
    public boolean collide (enemyMilitia b) {
        if ((b.xpos - radius) < (xpos + radius)){
            inContact = true;
            return true;
        }
        return false;
    }
    
    public boolean contactCastle (enemyBase b) {
        if (xpos + radius > 2900 && b.durability > 0) {
            xpos = 2900 - radius;
            hitCastle = true;
            return true;
        }
        return false;
    }
    
    public void paint (Graphics g, int camXSum) {
        //draws the head
        g.setColor (head);
        g.fillOval ((int) (xpos - radius)-camXSum, (int)(ypos - radius), (int)(radius*2), (int)(radius*2));
        g.setColor (new Color (0,0,0));
        g.drawOval ((int) (xpos - radius)-camXSum, (int)(ypos - radius), (int)(radius*2), (int)(radius*2));
        g.fillOval ((int)(xpos - radius + 28)-camXSum, (int)(ypos - radius + 10), 10, 10);
        //draws the body
        g.setColor (body);
        g.fillRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
        g.setColor (new Color (0,0,0));
        g.drawRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
        
        if (inContact) {
            if (timer < 500) {
                g.setColor (new Color (255, 0, 0, 255-255*(int)timer/500));
                g.fillOval ((int) (xpos - radius)-camXSum, (int)(ypos - radius), (int)(radius*2), (int)(radius*2));
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
    
    public void update (long dt){
        xpos += xvel*dt / 1000.0;
        ypos = 300;
        if (inContact) {           
            timer += dt;
            if (timer < 500) {
                xvel = -200+timer/2.5;
            }
            else {
                xvel = initialVelocity;
                timer = 0;
                inContact = false;
            }
        }
        
        if (hitCastle) {
            damageTime += dt;
            if (damageTime < 500) {
                xvel = -200+damageTime/2.5;
            }
            else {
                xvel = initialVelocity;
                damageTime = 0;
                hitCastle = false;
            }
        }
    }
    
    public void update (long dt, enemyMilitia[] enemy, int enemyLast, enemyBase enemyCastle){
    }
    
    
    
    //hit the opponent. In other words, decrease the health of the opponent
    public void hit (enemyMilitia b) {
        if (xvel > 0) {
            if (b.health > 0) {
                //The damage is affected by the defense of the opponent.
                b.health -= attack*(100-b.defense)/100.0;
                if (b.health < 0) {
                    //As there cannot be negative health, set health to zero if negative.
                    b.health = 0;
                }
            }
        }
        
    }
    
    public void hitCastle (enemyBase b) {
        b.durability -= attack*(100-b.defense)/100.0;
        if (b.durability < 0) {
            b.durability = 0;
        }
    }
    
    
    //see what type of the army it is
    public int getType () {
        return 1;
    }
}