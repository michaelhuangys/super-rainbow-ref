package ReefGame;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import myGames.DrawType;
import myGames.GameSpace;
import myGames.Thing;

public class GameLabel extends JLabel implements MouseListener{

	    private int backspeed;
	    private double backdirection;
	    private Image tile;
	    private int x,y;
	    private JLabel thumb;
	    private ImageIcon icon;
	    private DrawType drawer;
	    private boolean pressed=false;

	    public GameLabel(Image tile, DrawType drawer)
	    {
	        this.tile = tile;
	        this.drawer = drawer;
	    }

	    public GameLabel(Image tile, int x, int y, DrawType drawer) {
	    	  this.tile = tile;
	    	  this.x=x;
	    	  this.y=y;
	    	  this.drawer = drawer;
	          ImageIcon c=new ImageIcon(tile);
	          this.setIcon(c);
	          this.setLocation(x,y);
	    	  addMouseListener(this);
	   	  
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
	    	int x=e.getX();
	    	int y=e.getY();
	    
	    	int XC=getX();
	    	int width=tile.getWidth(null);
	    	int height=tile.getHeight(null);
	    	int YC=getY();
	    	
	    	if(x>XC&&x<(XC+width)&&y>YC&&y<(YC+height)){
		        pressed=true;
	    	}
		}
	    
	    public boolean getPressed(){
	    	return pressed;
	    }
	    
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			
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

		public void setPreesed(boolean b) {
			pressed=b;
			
		}
}
