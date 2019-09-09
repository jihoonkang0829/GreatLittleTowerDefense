import java.awt.*;
import javax.swing.*;
public class enemyArcher extends enemyMilitia{
    private boolean collide;
    private long reload;
    private enemyArrow arrow;
    private long airTime;
    public Color bow;
    public boolean arrowHitOnce;
    public boolean hitCastle;
    public enemyArcher (double xpos, double xvel, int attack, int defense, int radius) {
        this.xpos = xpos;
        this.xvel = xvel;
        this.ypos = 300;
        this.health = 100;
        this.attack = attack;
        this.defense = defense;
        this.radius = radius;
        head = new Color (255, 235, 205);
        body = new Color (220, 20, 60);
        inContact = false;
        initialVelocity = xvel;
        collide = false;
        reload = 3000;
        bow = new Color (205, 133, 63);
        airTime = 0;
        arrowHitOnce = false;
        hitCastle = false;
    }
    
    
    public boolean collide (Militia b) {
        if ((b.xpos + b.radius) > (xpos - radius - 500)) {
            inContact = true;
        }
        
        if ((b.xpos + b.radius) > (xpos - radius)) {
            collide = true;
            return true;
        }
        return false;
    }
    
    public boolean contactCastle (Base b) {
        if (xpos - radius - 500 < 100) {
            hitCastle = true;
            return true;
        }
        return false;
    }
    
    public void update (long dt, Militia [] army, int armyLast, Base Castle){
        
        xpos += xvel*dt / 1000.0;
        ypos = 300;
        reload -= dt;
        if (reload < 0) {
            reload = 0;
        }
        
        if ((inContact && reload == 0) || (hitCastle && reload == 0)) {
            xvel = 0;
            arrow = new enemyArrow (xpos, ypos, xpos-radius-500, ypos);            
            reload = 3000;
            airTime = 1000;
            arrowHitOnce = true;
        } 
        airTime -= dt;
        if (airTime > 0 && inContact) {
            arrow.update(dt);
            for (int i = 0; i < armyLast; i++) {
                if (arrowHitOnce && arrow.collide(army[i])){
                    army[i].health -= 20*(100-army[i].defense)/100.0;
                    arrowHitOnce = false;
                    if (army[i].health < 0) {
                        //As there cannot be negative health, set health to zero if negative.
                        army[i].health = 0;
                    }
                }
            }
        }
        
        if (airTime > 0 && hitCastle && Castle.durability != 0) {
            arrow.update(dt);
            if (arrowHitOnce) {
                Castle.durability -= 20*(100-Castle.defense)/100.0;
                arrowHitOnce = false;
                if (Castle.durability < 0) {
                    Castle.durability = 0;
                }
            }
            
        }
        
        if (Castle.durability == 0) {
            inContact = false;
            hitCastle = false;
        }
        
        if (!inContact && !hitCastle) {
            xvel = initialVelocity;
        }
        
        if (collide) {            
            timer += dt;
            if (timer < 500) {
                xvel = +200-timer/2.5;
            }
            else {
                xvel = initialVelocity;
                timer = 0;
                collide = false;
            }
        }
        
        
        inContact = false;
        hitCastle = false;
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
        //draws the bow
        g.setColor (bow);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke (new BasicStroke (3));
        g.drawArc ((int)(xpos - radius*2 + 10)-camXSum, (int)ypos, 10, 50, 90, 180);
        g2.setStroke (new BasicStroke (1));
        if (collide) {
            if (timer < 500) {
                g.setColor (new Color (255, 0, 0, 255-255*(int)timer/500));
                g.fillOval ((int) (xpos - radius)-camXSum, (int)(ypos - radius), (int)(radius*2), (int)(radius*2));
                g.fillRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
            }
        }
        
        if ((inContact && airTime > 0) || (hitCastle && airTime > 0)) {
            arrow.paint(g, camXSum);
        }
        
        //draws the health bar
        g.setColor (new Color (0, 0, 0));
        g.fillRect ((int)(xpos - radius + 5)-camXSum, (int)(ypos + radius - 60), 30, 10);
        g.setColor (new Color (127, 254, 0));
        g.fillRect ((int)(xpos - radius + 5)-camXSum, (int)(ypos + radius - 60), (int)(30*health/100.0), 10);
        g.setColor (new Color (0, 0, 0));
        g.drawRect ((int)(xpos - radius + 5)-camXSum, (int)(ypos + radius - 60), 30, 10);
        
    }
    
    public void hit (Militia b) {
        if (xvel < 0) {

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
    
    public int getType () {
        return 2;
    }
}
