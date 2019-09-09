import java.awt.*;
import javax.swing.*;
public class enemyMilitia{    
    public double xpos;
    public double ypos;
    public double xvel;
    public double health;
    public int attack;
    public int defense;
    public int radius;
    public Color head;
    public Color body;
    public long timer;
    public long damageTime;
    public boolean inContact;
    public double initialVelocity;
    public boolean hitCastle;
    public boolean hitArmy;
    public boolean arrowHitOnce;
    public long airTime;
    public enemyMilitia () {
    }
    public enemyMilitia (double xpos, double xvel, int attack, int defense, int radius) {
        this.xpos = xpos;
        this.xvel = xvel;
        this.ypos = 300;
        this.health = 100;
        this.attack = attack;
        this.defense = defense;
        this.radius = radius;
        head = new Color (255, 235, 205);
        body = new Color (220, 20, 60);
        damageTime = 0;
        inContact = false;
        initialVelocity = xvel;
        hitCastle = false;
        hitArmy = false;
    }
    
    //return true if the army contacted the enemy   
    public boolean collide (Militia b) {       
        if ((xpos - radius) < (b.xpos + radius)){
            inContact = true;
            return true;
        }
        return false;
    }
    
    public boolean contactCastle (Base b) {
        if (xpos - radius < 100 && b.durability > 0) {
            xpos = 100 + radius;
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
        g.fillOval ((int)(xpos - radius + 5)-camXSum, (int)(ypos - radius + 10), 10, 10);
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
    
    public void update (long dt, Militia[] army, int armyLast, Base Castle){
    }
    
    public void update (long dt){
        xpos += xvel*dt / 1000.0;
        ypos = 300;
        if (inContact) {
            timer += dt;
            if (timer < 500) {
                xvel = +200-timer/2.5;
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
                xvel = +200-damageTime/2.5;
            }
            else {
                xvel = initialVelocity;
                damageTime = 0;
                hitCastle = false;
            }
        }

    }
    //paint the head and the body
    
    
    
    //hit the opponent. In other words, decrease the health of the opponent
    public void hit (Militia b) {

        if (xvel < 0) {
            if (b.health > 0) {
                //The damage is affected by the defense of the opponent.
                b.health -= attack*(100-b.defense)/100.0;
                hitArmy = true;
                if (b.health < 0) {
                    //As there cannot be negative health, set health to zero if negative.
                    b.health = 0;
                    hitArmy = false;
                }
            }
        }
        hitArmy = false;
    }
    
     public void hitCastle (Base b) {
        System.out.println(b.durability);
        b.durability -= attack*(100-b.defense)/100.0;
        if (b.durability < 0) {
            b.durability = 0;
        }
    }
    
    public int getType () {
        return 1;
    }
}
