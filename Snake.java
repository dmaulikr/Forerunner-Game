import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This creates the attributes for the fore runner. This class sts attributes such
 * as the length as well as the type of forerunner.
 * 
 * @author Kevin Ge
 * @version May 12, 2016
 */
public class Snake extends Mover
{
    private int type, shots, startTime;
    private boolean killable;
    private Arena world;
    private Timer timer;
    private boolean timerReset;

    private GifImage myImage;
    private boolean onlyOnce = false;
    private boolean secondOnce = false;
    
    /**
     * Constructor for Runner (called Snake). Sets gif for animation, Gunner for 1st, Runner for
     * every 4th, and transparent (for spacing) for all else.
     *
     * @param type of Runner
     */
    public Snake(int type)
    {
        this.type = type;
        killable = true;
        timerReset = true;
        shots = 0;
        if (type == 0) //if first one
        {
            myImage = new GifImage("Gunner.gif");
        }
        else if (type % 4 == 0)
        {
            myImage = new GifImage("Runner.gif");
        }
        else
        {
            setImage("BlankSpace.png");
        }
    }

    /**
     * Runs animations. Shoots if space is held. Sets Runners' image to those of Gunners if TripleShot
     * is picked up. Sets killable to true if invincible for 4 seconds already.
     */
    public void act() 
    {
        if (type % 4 == 0)
        {
            setImage(myImage.getCurrentImage());
        }

        world = (Arena)getWorld();
        timer = world.getTimer();
        if (type == 0) //if warrior
        {
            shoot();
        }

        if (type % 4 == 0 && shots > 0 && onlyOnce)
        {
            myImage = new GifImage("Gunner.gif");
            onlyOnce = false; 
            secondOnce = true;
        }

        if (type != 0 && type % 4 == 0 && secondOnce && shots < 1)
        {
            myImage = new GifImage("Runner.gif");
            secondOnce = false;
        }

        //for invincibility to shut off after 4 seconds
        if (!killable && timerReset)
        {
            startTime = timer.getTime();
            timerReset = false;
        }

        if (timer.getTime() - startTime == 4)
        {
            killable = true;
            timerReset = true;
        }

    } 

    /**
     * Shoots one bullet forward. Shoots triple if power up tripleShots is picked up.
     * By Lawrence Cheng and David Magnusson
     */
    public void shoot() //fires bullets when the space bar is pressed
    {
        if("space".equals(Greenfoot.getKey()) )
        {
            getWorld().addObject(new Projectile(getRotation()), getX(), getY());
            Greenfoot.playSound("gun_fire.mp3");
            if(shots > 0)
            {
                getWorld().addObject(new Projectile(getRotation() + 90), getX(), getY()); //to right
                getWorld().addObject(new Projectile(getRotation() + 270), getX(), getY()); //to left
                shots--;
                Greenfoot.playSound("multishot.mp3");
                for(Snake snakes : world.getObjects(Snake.class))//so ALL Runners lose one shot
                {
                    snakes.setShots(shots);
                }
            }
        }
    }


    /**
     * Sets Runner's killable to false, which is checked by other killers (Brick and Enemy)
     */
    public void invincible()
    {
        killable = false;
    }

    /**
     * Returns if Runner is killable for other killers (Brick and Enemy)
     *
     * @return if Runner is killable
     */
    public boolean isKillable()
    {
        return killable;
    }

    /**
     * Triple shot powerup. Sets onlyOnce to true to Runner turns into 
     * Gunners once (stalls animation otherwise).
     */
    public void tripleShot()
    {
        shots = 10;
        onlyOnce = true;
    }
    
    /**
     * Sets the number of shots for this Runner
     * 
     * @param shot to be set to
     */
    public void setShots(int shot)
    {
        shots = shot;
    }
}
