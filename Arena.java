import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;
/**
 * This creates the game Arena in which the game play occurs, along with the various obstacles
 * 
 * @author Kevin Ge
 *      Some methods were completed by other groups members and are mentioned under each respective methods
 *      
 * @version May 5, 2016
 */
public class Arena extends World
{
    //Creates the body for the Snake
    private Snake snakeBody[];
    private int direction;

    //World with the dimensions for the block
    public static final int BLOCK = 8;
    private int gameState;

    //Highscore & Timer
    public static final int SECOND = 65; //this is for estimating a second. There are around 65 acts in a second
    private Timer timer;
    private boolean printScores;
    private LinkedList<Integer> scores = new LinkedList<Integer>();

    //Enemies
    private int numEnemies;

    /**
     * Constructor for the Arena. Sets world size to 1200 by 1200. Direction of runner and gamestate set to 0.
     * 
     * Kevin Ge
     */
    public Arena()
    { 
        super(BLOCK * 150, BLOCK * 150, 1);
        direction = 0;
        gameState = 0;
    }

    /**
     * Game screens and main code.
     * Game state 0 - Title screen
     * Game state 1 - Game initialization. Runner "snake" is created. Bricks are spawned and timer started.
     * Game state -1 - Gameover. High scores recorded in LinkedList of integers. All objects are removed.
     * 
     * Otherwise continuously runs snake movement and enemy spawning.
     * 
     * Kevin Ge
     */
    public void act()
    {
        if (gameState == 0) //title screen
        {
            setBackground("start_screen.png");
            if (Greenfoot.isKeyDown("space")) //any key pressed
            {
                gameState = 1;
            }
            return;
        }
        else if (gameState == 1) //game mode
        {
            setBackground("firewatch.jpg");

            //Snake Initialization with length and direction
            direction = 3;
            snakeBody = new Snake[12]; //3 in line - 2 invis per one visible
            for(int i=0; i<snakeBody.length; i++)
            {
                snakeBody[i] = new Snake(i);
                addObject(snakeBody[i], getWidth() / 2 +(snakeBody.length-i) * BLOCK, getHeight() / 2); // adding snake actor to the world
            }

            //World Initialization
            spawnBricks();
            timer = new Timer();
            addObject(timer, this.getWidth() / 2, 20);

            //Enemy Initialization
            numEnemies = 0;

            gameState = 2; //prevents reinitialization
            printScores = true;
            return;
        }
        else if (gameState == -1) //game over
        {
            //removeObjects(getObjects(null)); //removes all objects from world
            List<Actor> actors = getObjects(null);  
            removeObjects(actors); //removes all objects from world
            setBackground("dead_screen.png");

            /**
             * By David
             */
            if(printScores)
            {
                scores();
                printScores = false;
            }

            //sets the labels for the different scores
            for(int j = 0; j < scores.size() && j < 5; j++)
            {
                //getBackground().drawString("" + scores.get(j), getWidth()/2, 500 + j * 100);
                addLabel(scores.get(j), 50 + getWidth() / 2, j * 125 + 500);
            }

            if (Greenfoot.isKeyDown("enter"))
            {
                direction = 0;
                gameState = 0;
                //removeObjects(getObjects(null)); //removes all objects from world
                List<Actor> actors2 = getObjects(null);  
                removeObjects(actors2); //removes all objects from world

            }
            return;
        }

        spawnEnemy();
        snakeMove();

    }

    /**
     *  Method to change game state. Mainly used to end the game from other classes (ex. brick and enemy)
     *  
     *  Kevin Ge
     */
    public void setGameState(int state)
    {
        gameState = state;
    }

    /**
     * Called by enemy when enemy dies. Decreases numEnemies to allow for more spawning.
     * 
     * Kevin Ge
     */
    public void killedEnemy()
    {
        numEnemies--;
        Greenfoot.playSound("thud.mp3");
    }

    /**
     * Returns the timer for other classes to use world's time.
     *
     * @return world's timer
     * 
     * Kevin Ge
     */
    public Timer getTimer()
    {
        return timer;
    }

    /**
     * Calculates scores with insertion sort 
     * By David Magnusson
     */
    public void scores()
    {
        scores.add(timer.getPoints());
        if(scores.size() > 1)
        {
            for ( int j = 1; j < scores.size(); j++)
            {
                int temp = (int)scores.get(j);
                int k;
                for (k = j-1; k>= 0 && temp > (int)scores.get(k); k--)
                {
                    scores.set(k+1,scores.get(k));
                }
                scores.set(k+1,temp);
            }
        }
    }

    /**
     * Adds labels (text) for final high score screen
     *
     * @param score given score
     * @param x location x with x coordinates
     * @param y location y with y coordinates
     * 
     * David Magnusson
     */
    public void addLabel(int score, int x, int y)
    {
        Label label = new Label(score, 50);
        addObject(label, x, y);
    }

    /**
     * This method will changes the place where the enemy is spawned in the world.
     * 
     * David Magnusson
     */
    private void spawnEnemy()
    {
        int chance = Greenfoot.getRandomNumber(100);

        if (timer.getTime() < 0)
        {
            return;
        }
        else if (timer.getTime() <= 180 && numEnemies > 16)//Max 16 enemies before 3 mins
        {
            return;
        }
        else if (timer.getTime() > 180 && numEnemies > 32)//Max 32 enemies past 3 mins
        {
            return;
        }
        else if (chance > 0)//one out of 100 chance each act loop to spawn enemy
        {
            return;
        }

        int x=0, y=0;
        boolean overlap = true;

        //Prevents spawning on Runner
        while(overlap)
        {
            x = Greenfoot.getRandomNumber(getWidth());
            y = Greenfoot.getRandomNumber(getHeight());

            for(int i=0; i<snakeBody.length; i++)
            {
                // Condition to check enemy will not touch the snake
                if(x!=snakeBody[i].getX() || y!=snakeBody[i].getY())
                {
                    overlap = false;
                    break;
                }
            }
        }

        //Prevents spawning on bricks
        List<Brick> bricks = getObjects(Brick.class);
        for (Brick brick : bricks)
        {
            if (brick.getX() == x && brick.getY() == y)
            {
                return;
            }
        }

        //Adds the enemy into world
        Enemy enemy = new Enemy();
        addObject(enemy, x, y);
        numEnemies++;
    }

    /**
     * Spawns bricks into world in a patterned layout. This creates the set up for the obstacles
     * 
     * Lawrence Cheng
     */
    private void spawnBricks()
    {
        for(int i = 0; i<15; i++) //adds the bricks and obstacles to the arena
        {
            addBrick(150,200+(2*BLOCK*i));
            addBrick(800+(2*BLOCK*i),100);
            addBrick(300+(2*BLOCK*i),1100);
            if(i<7)
            {
                addBrick(400+(2*BLOCK*i),400+(2*BLOCK*i));
                addBrick(600+(2*BLOCK*i),800+(2*BLOCK*i));
                addBrick(1100-(2*BLOCK*i),500+(2*BLOCK*i));
            }
        }
    }

    /**
     * Helper method for spawnBricks(). Allows to create multiple bricks without naming them individually.
     *
     * @param x location x
     * @param y location y
     * 
     * Lawrence Cheng
     */
    public void addBrick(int x, int y)
    {
        Brick bricks = new Brick();
        addObject(bricks, x, y);
    }

    /**
     * Makes the snake move
     * First moves head, then moves body accordingly
     * Prevents "U-turns" like in a snake game
     * 
     * Kevin Ge
     */
    private void snakeMove()
    {
        if(Greenfoot.isKeyDown("right")) // checks if right key is pressed
        {
            if(direction==1 || direction==3)
                direction = 0;
        }
        else if(Greenfoot.isKeyDown("down")) // checks if down key is pressed
        {
            if(direction==0 || direction==2)
                direction = 1;
        }
        else if(Greenfoot.isKeyDown("left")) // checks if left key is pressed
        {
            if(direction==1 || direction==3)
                direction = 2;
        }
        else if(Greenfoot.isKeyDown("up")) // checks if up key is pressed
        {
            if(direction==0 || direction==2)
                direction = 3;
        }

        int rotation = snakeBody[0].getRotation();
        int previousLocationX = snakeBody[0].getX();
        int previousLocationY = snakeBody[0].getY();

        snakeBody[0].setRotation(direction*90);
        snakeBody[0].move(BLOCK); // moves one step

        if(snakeBody[0].getX() != previousLocationX || snakeBody[0].getY() != previousLocationY) // Checks if the snake hits walls
        {
            for(int i = 1; i < snakeBody.length; i++)
            {
                int tempRotation = snakeBody[i].getRotation();
                snakeBody[i].setRotation(rotation);

                previousLocationX = snakeBody[i].getX();
                previousLocationY = snakeBody[i].getY();
                snakeBody[i].move(BLOCK); // entire body moves one step
                rotation = tempRotation;
            }

            for(int i = 1; i < snakeBody.length; i++) // Checks if the snake eats itself
            {
                if(snakeBody[0].getX()==snakeBody[i].getX() && snakeBody[0].getY()==snakeBody[i].getY() && snakeBody[0].isKillable())
                {
                    gameState = -1;
                }
            }
        }
        else
        {
            gameState = -1;
        }
    }
}
