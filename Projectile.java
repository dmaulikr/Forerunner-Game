import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * This generates projectiles from the character that will destroy enemies within the game
 * 
 * @author Lawrence Cheng 
 * @version May 10, 2016
 */
public class Projectile extends Actor
{
    private int direction, speed, enemyDeaths;
    
    /**
     * Constructor for Projectile class. Sets the direction to given dir. Speed is 30.
     *
     * @param dir A parameter
     */
    public Projectile(int dir)
    {
        direction = dir;
        speed = 30;
    }
    
    /**
     * Moves forward, destroying enemeis in front. Destroys itself if runs into a brick or edge of world.
     */
    public void act() 
    {
        setRotation(direction);
        move(speed);
        destroy();

        //Removes the projectile if it collides with the brick in the world
        if( isAtEdge() || getOneIntersectingObject(Brick.class) != null)
        {
            getWorld().removeObject(this);
            return;
        }
    }     

    /**
     * Removes enemies that it runs into.
     */
    public void destroy()
    {
        Enemy enemy = (Enemy)getOneObjectAtOffset(0,0, Enemy.class);
        if (enemy != null)
        {
            Arena world = (Arena)getWorld();
            enemy.onDeath();
            world.removeObject(enemy);
        }     
    }

    
}

