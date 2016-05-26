import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This will create the attributes for the different power ups within the game
 * This class will set the types for the powerups and their effects.
 * 
 * @author Kevin Ge
 * @author David Magnusson
 * 
 * @version May 12, 2016
 */
public class PowerUp extends Actor
{
    private int type, state, startTime;
    
    /**
     * Constructor for PowerUp class. Initialized the type and image when created.
     * 
     * Kevin Ge
     */
    public PowerUp()
    {
        type = Greenfoot.getRandomNumber(5) + 1;//random num from 1 - 5
        //type = 5; //TEST PURPOSES
        if (type == 1)//points get!
        {
            setImage("gem.png");
        }
        else if (type == 2)//invincible get!
        {
            setImage("invincible.png");
        }
        else if (type == 3)//explosion get!
        {
            setImage("bomb.png");
        }
        else if (type == 4)//slow enemies get!
        {
            setImage("flake.png");
        }
        else if (type == 5)//triple shots
        {
            setImage("quadshot.png");
        }
    }

    /**
     * Continuously checks for Runners to be picked up, else despawns after 3 seconds.
     * 
     * David Magnusson
     */
    public void act() 
    {
        Arena world = (Arena)getWorld();
        Snake snake = (Snake)getOneIntersectingObject(Snake.class);
        if ( state == 0 ) //initialize start time
        {
            startTime =  world.getTimer().getTime();
            state = 1; //prevents reinitialization
        }

        if ( getOneIntersectingObject(Snake.class) != null || world.getTimer().getTime() - startTime == 3 ) //3 seconds before powerup disappears
        {
            activatePowerUps();
            world.removeObject(this);
            return;
        }
    }
    
    /**
     * Activates the various abilities of power ups.
     * 
     * Kevin Ge: Gem, Invincible, Slow Enemies
     * David Magnusson: Explosion, Triple Shots
     */
    public void activatePowerUps()
    {
        Arena world = (Arena)getWorld();
        if (type == 1)//points get!
        {
            Greenfoot.playSound("gem.mp3");
            world.getTimer().addPoints(30);
        }
        else if (type == 2)//invincible get!
        {
            Greenfoot.playSound("invincible.mp3");
            for(Snake snakes : world.getObjects(Snake.class))
            {
                snakes.invincible();
            }
        }
        else if (type == 3)//explosion get!
        {
            getWorld().addObject(new Explosion(), getX(), getY());
        }
        else if (type == 4)//slow enemies get!
        {
            Greenfoot.playSound("freeze.mp3");
            for(Enemy enemy : world.getObjects(Enemy.class))
            {
                enemy.slowSpeed();
            }
        }
        else if (type == 5)//triple shots get!
        {
            Greenfoot.playSound("equip.mp3");
            for(Snake snakes : world.getObjects(Snake.class))
            {
                snakes.tripleShot();
            }
        }
    }
}
