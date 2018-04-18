/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myGames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

/**
 * This is the space in which the game object live.
 * It draws the background and all objects.
 * This exact way in which the objects are drawn is determined by the DrawType
 * class which
 * @author Lowell
 */
public class GameSpace extends JPanel implements MouseListener
{

    private int backspeed;
    private double backdirection;
    private Image tile;
    private int x,y;
    private DrawType drawer;
    private boolean pressed=false;

    public GameSpace(Image tile, DrawType drawer)
    {
        super();
        this.tile = tile;
        backspeed = 0;
        backdirection = 0.;
        this.drawer = drawer;
       
       
    }

	public void drawBackground(Graphics2D g2)
    {
        int h = this.getHeight();
        int w = this.getWidth();
        int TileWidth = tile.getWidth(this);
        int TileHeight = tile.getHeight(this);
  
      g2.drawImage(tile, 0,0, TileWidth,TileHeight, this);
     
    }

    //goes through and draws everything
    public void drawHere(ArrayList<ArrayList> everything, Graphics2D g2)
    {
        Thing temp;
        Iterator<ArrayList> it = everything.listIterator();
        while (it.hasNext())
        {
            Iterator<Thing> it2 = it.next().listIterator();
            while (it2.hasNext())
            {
                temp = it2.next();
                drawer.drawThis(temp, g2, this);
            }
        }
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
            g.drawImage(tile, 0, 0, null);
    }
    
    public void setTile(Image tile)
    {
        this.tile = tile;
    }
    
    public Image getTile()
    {
        return tile;
    }

    public void setBackDirection(double direction)
    {
        backdirection = direction;
    }
    
    public double getBackDirection()
    {
        return backdirection;
    }

    public void setBackSpeed(int speed)
    {
        backspeed = speed;
    }
    
    public int getBackSpeed()
    {
        return backspeed;
    }
    
    public void setDrawType(DrawType drawer)
    {
        this.drawer = drawer;
    }
    
    public DrawType getDrawType()
    {
        return drawer;
    }

	public void drawImage(Graphics2D g2) {
		g2.drawImage(tile,x,y,this);
	}

    public void mousePressed(MouseEvent e) {
	    pressed=true;
	  
	}
    
    public boolean getPressed(){
    	return pressed;
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
	
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
