public class enemyArrow extends Arrow{
    enemyArrow (double xpos, double ypos, double objectXPos, double objectYPos) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.xobj = objectXPos;
        this.yobj = objectYPos;
        gravity = 100.0;
        airTime = 1.0;
        xvel = -1*Math.abs(xobj-xpos)/airTime;
        yvel = -gravity*airTime/2;
        collide = false;
    }
    public boolean collide (Militia b) {
        if (xpos <= b.xpos && ypos <= b.ypos) {
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