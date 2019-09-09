import java.awt.*;
import javax.swing.*;
public class Archer extends Militia{
    private boolean collide; //see if it has collided with the enemy
    private long reload; //tracks the reloading time of the arrow
    private Arrow arrow; //makes an arrow
    private long airTime; //tracks the duration of flight of the arrow
    public Color bow; //color of the bow
    public boolean arrowHitOnce; //make the arrow only hit once
    
    public Archer (double xpos, double xvel, int attack, int defense, int radius) {
        this.xpos = xpos;
        this.xvel = xvel;
        this.ypos = 300;
        this.health = 100;
        this.attack = attack;
        this.defense = defense;
        this.radius = radius;
        head = new Color (255, 235, 205);
        body = new Color (0, 0, 139);
        inContact = false;
        hitCastle = false;
        initialVelocity = xvel;
        collide = false;
        reload = 3000;
        bow = new Color (205, 133, 63);
        airTime = 0;
        arrowHitOnce = false;
    }
    
    //see if the archer has collided with the enemy
    public boolean collide (enemyMilitia b) {
        //see if the enemy is within the range of the archer
        if ((b.xpos - b.radius) < (xpos + radius + 500)) {
            inContact = true;
        }
        
        //see if the archer has actually collided with the enemy
        if ((b.xpos - b.radius) < (xpos + radius)) {
            collide = true;
            return true;
        }
        return false;
    }
    
    //see if the enemy castle is within the range of the archer
    public boolean contactCastle (enemyBase b) {
        
        if (xpos + radius + 500 > 2900) {
            hitCastle = true;
            return true;
        }
        return false;
    }
    
    public void update (long dt, enemyMilitia [] enemy, int enemyLast, enemyBase enemyCastle){
        
        xpos += xvel*dt / 1000.0;
        ypos = 300;
        reload -= dt;
        //if the reloading time is negative, set to zero
        if (reload < 0) {
            reload = 0;
        }
        
        //if any object (enemy, castle) is within the range of the archer and the arrow is reloaded
        if ((inContact && reload == 0) || (hitCastle && reload == 0)) {
            xvel = 0;
            arrow = new Arrow (xpos, ypos, xpos+radius+500, ypos);            
            reload = 3000;
            airTime = 1000;
            arrowHitOnce = true;
        } 
        airTime -= dt;
        
        //updates arrow, gives damage to the enemy
        if (airTime > 0 && inContact) {
            arrow.update(dt);
            for (int i = 0; i < enemyLast; i++) {
                if (arrowHitOnce && arrow.collide(enemy[i])){
                    enemy[i].health -= 20*(100-enemy[i].defense)/100.0;
                    arrowHitOnce = false;
                    if (enemy[i].health < 0) {
                        //As there cannot be negative health, set health to zero if negative.
                        enemy[i].health = 0;
                    }
                }
            }
        }
        
        //updates arrow, gives damage to the enemy castle
        if (airTime > 0 && hitCastle && enemyCastle.durability != 0) {
            arrow.update(dt);
            if (arrowHitOnce){
                enemyCastle.durability -= 20*(100-enemyCastle.defense)/100.0;
                arrowHitOnce = false;
                if (enemyCastle.durability < 0) {
                    enemyCastle.durability = 0;
                }
                
            }
        }
        
        //if the enemy castle has crushed, set both booleans to false so that the archer can move
        if (enemyCastle.durability == 0) {
            inContact = false;
            hitCastle = false;
        }
        
        //if there is nothing within the range, keep moving
        if (!inContact && !hitCastle) {
            xvel = initialVelocity;
        }
        
        //make the moving back effect when collided with the enemy
        if (collide) {            
            timer += dt;
            if (timer < 500) {
                xvel = -200+timer/2.5;
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
        g.fillOval ((int)(xpos - radius + 28)-camXSum, (int)(ypos - radius + 10), 10, 10);
        //draws the body
        g.setColor (body);
        g.fillRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
        g.setColor (new Color (0,0,0));
        g.drawRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
        //draws the bow
        g.setColor (bow);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke (new BasicStroke (3));
        g.drawArc ((int)(xpos + radius*2 - 10)-camXSum, (int)ypos, 10, 50, 90, -180);
        g2.setStroke (new BasicStroke (1));
        
        //creates the blood-shed effect when collided with the enemy
        if (collide) {
            if (timer < 500) {
                g.setColor (new Color (255, 0, 0, 255-255*(int)timer/500));
                g.fillOval ((int) (xpos - radius)-camXSum, (int)(ypos - radius), (int)(radius*2), (int)(radius*2));
                g.fillRect ((int) (xpos - radius)-camXSum, (int)(ypos + radius), (int)(radius*2), (int)(radius*3));
            }
        }
        
        //paints the arrow
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
    
    //hits the enemy if it has collided with the enemy
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
    
    //see what type of the army it is
    public int getType () {
        return 2;
    }
}