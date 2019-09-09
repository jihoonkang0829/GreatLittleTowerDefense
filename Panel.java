import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{    
    public Militia [] army; //array of armies
    public enemyMilitia [] enemy; // array of enemies
    public int armyLast; //indicates the next position of the army to be added
    public int enemyLast; //indicates the next position of the enemy to be added
    private boolean mousePressed; //becomes true when mouse is pressed
    private double mx, my; //tracks the position of the mouse
    private double gold; //tracks the gold needed to create army
    private long timer; //tracks the time to automatically add an enemy
    public int camX; //variable that tracks the position of camera
    private int camXSum; //accumulation of camX
    private Base castle; //army castle
    private enemyBase enemyCastle; //enemy castle
    private boolean start; //see if the game is started or not
    Panel(){
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        army = new Militia [20];
        enemy = new enemyMilitia [20];
        armyLast = 0;
        enemyLast = 0;
        mousePressed = false;
        mx = 0;
        my = 0;
        gold = 0;
        timer = 0;
        camX = 0;
        camXSum = 0;
        castle = new Base ();
        enemyCastle = new enemyBase ();
        start = false;
    }

    //adds militia to the army
    private void addMilitia () {
        if (armyLast == army.length) {
            Militia [] bigger = new Militia[army.length*2];
            for (int i = 0; i < army.length; i++) {
                bigger[i] = army[i];
            }
            army = bigger;
        }
        army[armyLast] = new Militia (50, 100, 20, 30, 20);
        armyLast++;
    }
    
    //adds militia to the enemy army
    private void addEnemyMilitia () {
        if (enemyLast == enemy.length) {
            enemyMilitia [] temp = new enemyMilitia[enemy.length*2];
            for (int i = 0; i < enemy.length; i++) {
                temp[i] = enemy[i];
            }
            enemy = temp;
        }
        enemy[enemyLast] = new enemyMilitia (2950, -100, 30, 40, 20);
        enemyLast++;
    }
    
    //adds archer to the army
    public void addArcher () {
        if (armyLast == army.length) {
            Militia [] bigger = new Militia[army.length*2];
            for (int i = 0; i < army.length; i++) {
                bigger[i] = army[i];
            }
            army = bigger;
        }
        army[armyLast] = new Archer (50, 70, 10, 20, 20);
        armyLast++;
    }

    //adds archer to the enemy army
    public void addEnemyArcher () {
        if (enemyLast == enemy.length) {
            enemyMilitia [] temp = new enemyMilitia[enemy.length*2];
            for (int i = 0; i < enemy.length; i++) {
                temp[i] = enemy[i];
            }
            enemy = temp;
        }
        enemy[enemyLast] = new enemyArcher (2950, -70, 20, 30, 20);
        enemyLast++;
    }

    //adds knight to the army
    public void addKnight () {
        if (armyLast == army.length) {
            Militia [] bigger = new Militia[army.length*2];
            for (int i = 0; i < army.length; i++) {
                bigger[i] = army[i];
            }
            army = bigger;
        }
        army[armyLast] = new Knight (50, 50, 45, 45, 20);
        armyLast++;
    }

    //adds knight to the enemy army
    private void addEnemyKnight () {
        if (enemyLast == enemy.length) {
            enemyMilitia [] temp = new enemyMilitia[enemy.length*2];
            for (int i = 0; i < enemy.length; i++) {
                temp[i] = enemy[i];
            }
            enemy = temp;
        }
        enemy[enemyLast] = new enemyKnight (2950, -50, 60, 60, 20);
        enemyLast++;
    }

    public void paint(Graphics g){
        //draws the sky.        
        g.setColor (new Color (153, 204, 255));
        g.fillRect (0-camXSum/1000, 0, 3000, 382);

        //draws the army and the enemy
        for (int i = 0; i < armyLast; i++) {
            army[i].paint(g, camXSum/1000);
        }
        for (int i = 0; i < enemyLast; i++) {
            enemy[i].paint(g, camXSum/1000);
        }

        //draws the castle;
        castle.paint(g, camXSum/1000);

        //draws the enemy castle;
        enemyCastle.paint(g, camXSum/1000);

        //draws the land.
        g.setColor (new Color (218,165,32));
        g.fillRect (0-camXSum/1000, 380, 3000, 120);

        //draws the mini map
        g.setColor (new Color (153, 204, 255));
        g.fillRect (660, 420, 300, 38);
        g.setColor (new Color (218, 165, 32));
        g.fillRect (660, 458, 300, 12);
        g.setColor (new Color (0, 0, 139));
        for (int i = 0; i < armyLast; i++) {
            int xPos = 660+(int)((army[i].xpos)/3000*300);
            if (xPos > 950) {
                xPos = 950;
            }
            g.drawRect (xPos, 453, 2, 4);
        }        
        g.setColor (new Color (220, 20, 60));
        for (int i = 0; i < enemyLast; i++) {
            int xPos = 660+(int)((enemy[i].xpos)/3000*300);
            if (xPos < 662) {
                xPos = 662;
            }
            g.drawRect (xPos, 453, 2, 4);
        }
        g.setColor (new Color (192, 192, 192));
        g.fillRect (660, 430, 10, 28);
        g.setColor (new Color (105, 105, 105));
        g.fillRect (950, 430, 10, 28);
        g.setColor (new Color (0, 0, 0));
        g.drawRect (660, 420, 300, 50);
        g.setColor (new Color (255, 255, 255));
        g.drawRect (660+(camXSum/1000*300/3000), 420, 100, 50);

        //draws the control tab;
        g.setColor (new Color (0, 169, 169));
        g.fillRect (0, 500, 1000, 100);
        g.setColor (new Color (0, 0, 0));
        g.drawRect (0, 500, 1000, 100);

        //draws the Militia button;
        if (gold <= 300) {
            g.setColor (new Color (192,192,192, 150));
            g.fillRect (50, 515, 50, 50);
            g.setColor (new Color (255, 235, 205, 150));
            g.fillOval (65, 525, 20, 20);
            g.setColor (new Color (0, 0, 139, 150));
            g.fillRect (65, 545, 20, 20);
            g.setColor (new Color (0, 0, 0, 150));
            g.drawOval (65, 525, 20, 20);
            g.drawRect (65, 545, 20, 20);
            g.drawRect (50, 515, 50, 50);
        }
        else {
            g.setColor (new Color (192,192,192));
            g.fillRect (50, 515, 50, 50);
            g.setColor (new Color (255, 235, 205));
            g.fillOval (65, 525, 20, 20);
            g.setColor (new Color (0, 0, 139));
            g.fillRect (65, 545, 20, 20);
            g.setColor (new Color (0, 0, 0));
            g.drawOval (65, 525, 20, 20);
            g.drawRect (65, 545, 20, 20);
            g.drawRect (50, 515, 50, 50);
        }

        //draws the Archer button;
        if (gold <= 400) {
            g.setColor (new Color (192,192,192, 150));
            g.fillRect (123, 515, 50, 50);
            g.setColor (new Color (255, 235, 205, 150));
            g.fillOval (133, 525, 20, 20);
            g.setColor (new Color (0, 0, 139, 150));
            g.fillRect (133, 545, 20, 20);
            g.setColor (new Color (0, 0, 0, 150));
            g.drawOval (133, 525, 20, 20);
            g.drawRect (133, 545, 20, 20);
            g.drawRect (123, 515, 50, 50);
            g.setColor (new Color (205, 133, 63, 150));
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke (new BasicStroke (3));
            g.drawArc (155, 530, 10, 30, 90, -180);
            g2.setStroke (new BasicStroke (1));
        }
        else {
            g.setColor (new Color (192,192,192));
            g.fillRect (123, 515, 50, 50);
            g.setColor (new Color (255, 235, 205));
            g.fillOval (133, 525, 20, 20);
            g.setColor (new Color (0, 0, 139));
            g.fillRect (133, 545, 20, 20);
            g.setColor (new Color (0, 0, 0));
            g.drawOval (133, 525, 20, 20);
            g.drawRect (133, 545, 20, 20);
            g.drawRect (123, 515, 50, 50);
            g.setColor (new Color (205, 133, 63, 150));
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke (new BasicStroke (3));
            g.drawArc (155, 530, 10, 30, 90, -180);
            g2.setStroke (new BasicStroke (1));
        }

        //draws the Knight Button
        if (gold <= 500) {
            g.setColor (new Color (192,192,192, 150));
            g.fillRect (196, 515, 50, 50);
            g.setColor (new Color (172, 172, 172, 150));
            g.fillRect (211, 525, 20, 20);
            g.setColor (new Color (0, 0, 139, 150));
            g.fillRect (211, 545, 20, 20);
            g.setColor (new Color (0, 0, 0, 150));
            g.drawRect (211, 525, 20, 20);
            g.drawRect (211, 545, 20, 20);
            g.drawRect (196, 515, 50, 50);
        }
        else {
            g.setColor (new Color (192,192,192));
            g.fillRect (196, 515, 50, 50);
            g.setColor (new Color (172, 172, 172));
            g.fillRect (211, 525, 20, 20);
            g.setColor (new Color (0, 0, 139));
            g.fillRect (211, 545, 20, 20);
            g.setColor (new Color (0, 0, 0));
            g.drawRect (211, 525, 20, 20);
            g.drawRect (211, 545, 20, 20);
            g.drawRect (196, 515, 50, 50);
        }
        
        //shows the remaining gold;
        g.setColor (new Color (255, 215, 0));
        g.drawString ("Gold: " + String.valueOf((int)gold), 900, 550);

        //display game end (victory)
        if (enemyCastle.defeat()) {
            g.setColor (new Color (0, 0, 0, 100));
            g.fillRect (0, 0, 3000, 600);
            g.setColor (new Color (255, 215, 0));
            g.setFont (new Font ("SansSerif", Font.BOLD, 50));
            g.drawString ("You Won!!", 475, 275);
        }

        //display game end (defeat)
        if (castle.defeat()) {
            g.setColor (new Color (0, 0, 0, 100));
            g.fillRect (0, 0, 3000, 600);
            g.setColor (new Color (255, 0, 0));
            g.setFont (new Font ("SansSerif", Font.BOLD, 50));
            g.drawString ("You Lost!!", 475, 275);
        }
        
        //display the initial screen
        if (!start) {
            g.setColor (new Color (0, 0, 0, 100));
            g.fillRect (0, 0, 3000, 600);
            g.setColor (new Color (255, 0, 0));
            g.setFont (new Font ("SansSerif", Font.BOLD, 50));
            g.drawString ("Press Any Key To Start", 250, 275);
        }

    }

    public void update(long dt){
        this.requestFocus();
        if (start) {
            timer += dt;
            gold += dt/15.0;
            camXSum += camX;
            //set the range of the camera
            if (camXSum/1000 < 0) {
                camXSum = 0;
            }
            if (camXSum/1000 > 2000) {
                camXSum = 2000000;
            }
            
            //adds enemy in every certain amount of time
            if (timer > 5000 && !castle.defeat() && !enemyCastle.defeat()) {
                double random = Math.random();
                if (random < 0.4) {
                    addEnemyMilitia();
                }
                else if (random < 0.8){
                    addEnemyArcher();
                }
                else {
                    addEnemyKnight();
                }
                timer -= 5000;
                
            }
            
            
            for (int i = 0; i < armyLast; i++) {
                //update the army
                if (army[i].getType() == 1 || army[i].getType() == 3) {
                    army[i].update(dt);
                }
                else if (army[i].getType() == 2) {
                    army[i].update (dt, enemy, enemyLast, enemyCastle);
                }
                
                //see if the army has contacted the castle
                if (army[i].contactCastle(enemyCastle)) {
                    //type 2 (archer) does not need to hit castle because it is the arrow that is hitting the castle
                    if (army[i].getType() == 1 || army[i].getType() == 3) {
                        army[i].hitCastle (enemyCastle);
                    }

                }
                for (int j = 0; j < enemyLast; j++) {
                    //hit the enemy if collided with the enemy
                    if (army[i].collide(enemy[j])) {
                        army[i].hit(enemy[j]);
                    }
                    //delete the dead enemy, add gold
                    if (enemy[j].health == 0) {
                        if (enemy[j].getType() == 1) {
                            gold+=200;
                        }
                        if (enemy[j].getType() == 2) {
                            gold+=300;
                        }
                        if (enemy[j].getType() == 3) {
                            gold+=400;
                        }
                        enemy[j] = enemy[enemyLast-1];
                        enemyLast--;
                        j--;
                    }
                }            
            }

            for (int i = 0; i < enemyLast; i++) {
                //update the enemy
                if (enemy[i].getType() == 1||enemy[i].getType() == 3) {
                    enemy[i].update(dt);
                }
                else if (enemy[i].getType() == 2) {
                    enemy[i].update (dt, army, armyLast, castle);
                }
                
                //see if the enemy has contacted the castle
                if (enemy[i].contactCastle(castle)) {
                    if (enemy[i].getType() == 1||enemy[i].getType() == 3){
                        enemy[i].hitCastle (castle);
                    }
                }               
                for (int j = 0; j < armyLast; j++) {
                    //hit the army if collided with it
                    if (enemy[i].collide(army[j])) {
                        enemy[i].hit(army[j]);
                    }
                    //delete the dead army
                    if (army[j].health == 0) {
                        army[j] = army[armyLast-1];
                        armyLast--;
                        j--;
                    }
                }            
            }
        }

    }

    //modifies the position of the camera
    public void keyPressed(KeyEvent e){
        start = true;
        if (e.getKeyCode() == KeyEvent.VK_D){
            camX = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            camX = -3;
        }
    }

    public void keyReleased(KeyEvent e){
        camX = 0;
    }

    public void keyTyped(KeyEvent e){

    }

    public void mouseClicked(MouseEvent e){

    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }

    //add army, decrease gold
    public void mousePressed(MouseEvent e){
        mousePressed = true;
        if (mx > 50 && mx < 100 && my > 515 && my < 565 && gold > 300 && !castle.defeat() && !enemyCastle.defeat()) {
            addMilitia ();
            gold -= 300;
        }
        if (mx > 120 && mx < 170 && my > 515 && my < 565 && gold > 400 && !castle.defeat() && !enemyCastle.defeat()) {
            addArcher ();
            gold -= 400;
        }
        if (mx > 190 && mx < 240 && my > 515 && my < 565 && gold > 500 && !castle.defeat() && !enemyCastle.defeat()) {
            addKnight ();
            gold -= 500;
        }
    }

    public void mouseReleased(MouseEvent e){
        mousePressed = false;
    }

    public void mouseDragged(MouseEvent e){

    }

    //tracks the position of the mouse
    public void mouseMoved(MouseEvent e){
        mx = e.getX();
        my = e.getY();
    }
}