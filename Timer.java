import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Creates a timer for the game and records the duration for which the game is played.
 * 
 * @author Lawrence Cheng
 * @version 5/12/16
 */
public class Timer extends Actor
{
    private Arena arena;
    private int points, seconds, count;
    /**
     * Constructor for timer. Sets variables for points and seconds to 0. Count to 65.
     */
    public Timer()
    {
        points = 0;
        seconds = 0;
        count = 65;
    }
    
    /**
     * Counts time using 65 acts = 1 second. Adds points about 2 every second. Displays info at top of screen.
     */
    public void act() 
    {
        arena = (Arena)getWorld();

        //time + points based from time
        if(counter())
        {
            seconds++;
            count = 65;
        }

        if (count == 2 || count == 34)
        {
            points++;
        }

        display();
    }    

    /**
     * Displays time and points during gameplay.
     */
    private void display()
    {
        setImage(new GreenfootImage("Time Elapsed:" + seconds + "  Points:" + points, 30, Color.WHITE, Color.BLACK));
    }

    /**
     * Checks if 65 acts has elasped.
     *
     * @return true if 65 acts has passed, false otherwise
     */
    private boolean counter()
    {
        if(count > 0)
        {
            count--;
        }
        return count == 0;
    }
    
    /**
     * Returns the current time in seconds.
     *
     * @return time elasped since initialization
     */
    public int getTime()
    {
        return seconds;    
    }

    /**
     * Returns current amount of points.
     *
     * @return points
     */
    public int getPoints()
    {
        return points;    
    }
    
    /**
     * Adds an amount to points.
     *
     * @param point amount to add
     */
    public void addPoints(int point)
    {
        points += point;
    }
}
