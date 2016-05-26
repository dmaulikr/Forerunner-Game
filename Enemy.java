import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class will create the attributes for the enemies. For instance, it creates the
 * movement for the enemy and the spawning patterns.
 * 
 * @author Lawrence Cheng
 *          Methods onDeath() and powerup (slow speed) were completed by Kevin
 * @version 5/5/16
 */
public class Enemy extends Mover
{
    private double speed = 1.5;
    private GifImage myImage = new GifImage("Zombie.gif");
    
    /**
     * Randomly moves within world, avoiding walls and world edges.
     * 
     * Lawrence Cheng
     */
    public void act() 
    {
        setImage(myImage.getCurrentImage()); //For animation
        move(speed * 2);
        
        //Turns the enemy in random angles so the moveement isn't predictable
        if(Greenfoot.getRandomNumber(100)<10)
        {
            turn(Greenfoot.getRandomNumber(90) - 45);
            move(speed);
        }
        
        //Turns the enemy around when it reaches the world's edge in the x-direction
        if(getX() <= 5 || getX() >= getWorld().getWidth()-5)
        {
            turn(150);
            move(speed);
        }
        
      //Turns the enemy around when it reaches the world's edge in the y-direction
        if(getY() <= 5 || getY() >= getWorld().getHeight()-5)
        {
            turn(150);
            move(speed);
        }
        
        //Turns the enemy around when it runs into a brick 
        if(getOneIntersectingObject(Brick.class) != null)
        {
            turn(170);
            while (getOneIntersectingObject(Brick.class) != null)
            {
                move(speed * 3);
            }
        }

        eat();
    }    

    /**
     * Eats Runner if it collides with them. Always checking for Runners.
     * 
     * Lawrence Cheng
     */
    public void eat()
    {
        Snake snake = (Snake)getOneObjectAtOffset(0,0, Snake.class);
        
        if (snake != null && snake.isKillable())
        {
            Arena arena = (Arena)getWorld();
            arena.setGameState(-1);
        }
    }

    /**
     * Method used by PowerUp to slow the speed of enemies. Sets speed to 0.6.
     * 
     * Kevin Ge
     */
    public void slowSpeed()
    {
        speed = 0.6;
    }

    /**
     * Called when this enemy dies. 40% chance to drop powerup. Adds 30 points to score. Reduces numEnemies in world by one.
     * 
     * Kevin Ge
     */
    public void onDeath()
    {
        int chanceToDrop = Greenfoot.getRandomNumber(10);
        
        if (chanceToDrop < 5)//set to 5 = 40% chance
        {
            Arena arena = (Arena)getWorld();
            arena.addObject(new PowerUp(), this.getX(), this.getY());
        }
        
        Arena world = (Arena)getWorld();
        world.getTimer().addPoints(30); //30 pts for killing
        world.killedEnemy(); //numEnemies-- for spawning purposes
    }
}