import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * Mover is an actor that can move and turn (at 90 degree) angles
 * 
 * @author Kevin Ge
 * @version May 10, 2016
 */
public class Mover extends Actor
{
    /**
     * Method turn at a given angle.
     *
     * @param angle to turn
     */
    public void turn(int angle)
    {
        setRotation(getRotation() + angle);
    }

    /**
     * With the power of Pythogorean's Theorem, move!
     *
     * @param distance A parameter
     */
    public void move(double distance)
    {
        double angle = Math.toRadians( getRotation() );
        int x = (int) Math.round(getX() + Math.cos(angle) * distance);
        int y = (int) Math.round(getY() + Math.sin(angle) * distance);
        
        setLocation(x, y);
    }
}