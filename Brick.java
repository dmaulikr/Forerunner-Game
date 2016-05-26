import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Bricks destroys the Runner if run into.
 * 
 * @author David Magnusson
 * @version 5/15/16
 */
public class Brick extends Actor
{
    private Arena world;
    private Snake snake;

    /**
     * Removes Runner from world if it collides with me (Brick). Only if if is killable of course.
     */
    public void act() 
    {
        world = (Arena)getWorld();
        Snake snake = (Snake)getOneIntersectingObject(Snake.class);
        if (snake != null && snake.isKillable())
        {
            world.setGameState(-1); //Ends the game and displays Game Over
        }
    }    
}
