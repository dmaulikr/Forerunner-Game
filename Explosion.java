import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Creates animated explosion that removes enemies caught in it.
 * 
 * @author David Magnusson
 * @version May 23, 2016
 */
public class Explosion extends Actor
{
    private GifImage myImage = new GifImage("explode.gif");
    private boolean onlyOnce = true;
    private int lifetime = 65; //65 acts is around one second
    /**
     * Animates. Explodes once when created, deleteing all nearby enemies. Removes itself after one second
     */
    public void act() 
    {
        setImage(myImage.getCurrentImage());
        
        if (onlyOnce)
        {
            Greenfoot.playSound("explode.mp3");
            onlyOnce = false;
        }
        
        //Destroys the various enemies that are around the explosion
        for (Enemy ene : getIntersectingObjects(Enemy.class))
        {
            Arena world = (Arena)getWorld();
            
            ene.onDeath();
            world.removeObject(ene);
        }
        
        if (lifetime == 0)
        {
            getWorld().removeObject(this);
        }
        lifetime--;
    }    
}
