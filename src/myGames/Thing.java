/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myGames;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.ArrayList;

/**
 *
 * @author Lowell Milliken
 * This class is the root class of every game object
 */
abstract public class Thing
{
    //x and y coordinate from the top right corner
    private double x;
	double y;
    //facing in radians with 0 pointing down, increasing counterclockwise
    private double direction;
    //speed in pixels per frame
    private int speed;
    //the image of the object
    protected Image[] img;
    //determines if the object should execute its dead method
    protected boolean done;
    //marks the object for deletion
    private boolean reallyDone;
    //an Observable object that every game object needs to access
    private GameEvents events;
    //determines which image is currently showing (for animation)
    private int imgTick;

    
    public Thing(double d, double e, double direction, int speed, Image[] img,
            GameEvents events)
    {
        this.x = d;
        this.y = e;
        this.direction = direction;
        this.speed = speed;
        this.img = img;
        this.events = events;
        done = false;
        reallyDone = false;
        imgTick = img.length-1;
    }

    public Thing(int x, int y, double direction, int speed, Image[] img)
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
        this.img = img;
        done = false;
        reallyDone = false;
        imgTick = img.length-1;
    }
    //draws the object with the given Graphics2D performimg rotation and
    //translation to get the proper facing and postion
    public void draw(Graphics2D g2, ImageObserver obs)
    {
        int w = img[imgTick].getWidth(obs);
        int h = img[imgTick].getHeight(obs);
        AffineTransform trans = new AffineTransform();

        trans.translate(x - w / 2, y - h / 2);
        trans.rotate(direction, w / 2, h / 2);

        if (!done)
        {
            g2.drawImage(img[imgTick], trans, obs);
        }
        
        imgTick++;
        if (imgTick >= img.length)
        {
            imgTick = 0;
        }
    }

    //called every frame, calls dead or action and move
    //before calling move, it checks if the object is out of bounds and sets it
    //to be deleted or other dealt with
    public void update(int w, int h)
    {
        if (done)
        {
            dead();
        } else
        {
            action();
            if ((x < -50) || (x > w + 50) || (y < -50) || (y > h + 50))
            {
                reallyDone = true;
                done = true;
            }
            move();
        }
    }

    //movement type actions
    abstract public void move();

    //action taken when dead
    abstract public void dead();
    
    //what to do when the object hits another object
    abstract public void itHit(Unit u);

    //dummy for action type things (so it does not need to be overridden when
    //not used
    public void action()
    {
    }

    //various get and set type methods for parameters after this point
    public void setX(double d)
    {
        this.x = d;
    }

    public double getX()
    {
        return x;
    }

    public void changeX(double left)
    {
        this.x += left;
    }

    public void setY(double d)
    {
        this.y = d;
    }

    public double getY()
    {
        return y;
    }

    public void changeY(double dy)
    {
        this.y += dy;
    }

    public void setDirection(double d)
    {
        this.direction = d%360;
    }

    public double getDirection()
    {
        return direction;
    }

    public void changeDirection(double change)
    {
        this.direction += change;
        this.direction%=360;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void changeSpeed(int change)
    {
        this.speed += change;
    }
    
    public Image getImage()
    {
        return img[imgTick];
    }

    public void setDone(boolean done)
    {
        this.done = done;
    }
    
    public boolean getDone()
    {
        return done;
    }
    
    public void setRDone(boolean rdone)
    {
        reallyDone = rdone;
    }
    
    public boolean getRDone()
    {
        return reallyDone;
    }
    
    public int getHeight()
    {
        return img[imgTick].getHeight(null);
    }
    
    public int getWidth()
    {
        return img[imgTick].getWidth(null);
    }
    
    public GameEvents getEvents()
    {
        return events;
    }
}