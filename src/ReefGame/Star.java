package ReefGame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;

import ReefGame.ReefGame.ScoreTable;
import ReefGame.ReefGame.ReefPlayer;
import myGames.Bullet;
import myGames.GameEvents;

public class Star extends Bullet{
   private int first=1;
   private int score=0;
   private boolean move=false;
   private double speed;
   private double dx;
   private int life=0;
   double dy;
	public Star(double d, double e, double direction, int speed, Image[] img,
			GameEvents events, int source, ArrayList ev, int damageto, int life) {
		super(d, e, direction, speed, img, events, source, ev, damageto);
		this.life=life;
	}

	
	public void move(){

		
		if(first==1){
		 dx=(getSpeed()*Math.cos(Math.toRadians(getDirection())));
		 dy= (getSpeed()*Math.sin(Math.toRadians(getDirection())));
		first--;
		}else{
			dy+=0.2;
			speed=Math.sqrt(dx*dx+dy*dy);
			
		}
		
		     changeY( dy);
		     changeX( dx);
		     
		    //check collide with bilegs
		 	Wall temp_bigleg=null;
		 	Iterator<Wall> it_bigleg=ReefGame.Biglegs.listIterator();
		 	while(it_bigleg.hasNext()){
		 		temp_bigleg=it_bigleg.next();
		 	Rectangle r1=new Rectangle((int)getX(),(int)getY(),getWidth(),getHeight());
			Rectangle r2=new Rectangle((int)temp_bigleg.getX(),(int)temp_bigleg.getY(),getWidth(),getHeight());
			   if(r1.intersects(r2)){
				this.itHit(temp_bigleg);
				 score+=100;
			    }
		 	}
		     
	        //check collide with wall
		    Wall temp_wall;
		     Iterator<Wall> it_wall = ReefGame.wall.listIterator();
		     Rectangle top=new Rectangle((int)getX()-17,(int)getY()-17,getWidth(),6);
	            Rectangle bot=new Rectangle((int)getX()-17,(int)getY()+12,getWidth(),6);
	            Rectangle left=new Rectangle((int)getX()-17,(int)getY()-17,6,getHeight());
	            Rectangle right=new Rectangle((int)getX()+12,(int)getY()-17,6,getHeight());
	
		        while (it_wall.hasNext())
		        {
		            temp_wall = it_wall.next();
		        
		            Rectangle wall_top=new Rectangle((int)temp_wall.getX()-20,(int)temp_wall.getY()-10,temp_wall.getWidth(),6);
		            Rectangle wall_bot=new Rectangle((int)temp_wall.getX()-20,(int)temp_wall.getY()+5,temp_wall.getWidth(),6);
		            Rectangle wall_left=new Rectangle((int)temp_wall.getX()-20,(int)temp_wall.getY()-10,6,temp_wall.getHeight());
		            Rectangle wall_right=new Rectangle((int)temp_wall.getX()+14,(int)temp_wall.getY()-10,6,temp_wall.getHeight());
		           
		        
		        if(bot.intersects(wall_top)!=true||top.intersects(wall_bot)!=true){
		            if(bot.intersects(wall_top)){
		            	itHit(temp_wall);
		            	 setY(getY()-dy);
		                  setX(getX()-dx);
		            	 setDirection(360-getDirection());
		                  dy=speed*Math.sin((Math.toRadians(getDirection())));
		                  dx=speed*Math.cos((Math.toRadians(getDirection())));
		                  score+=100;
		                  break;
		            }
		           if(top.intersects(wall_bot)&&right.intersects(wall_left)==false&&left.intersects(wall_right)==false){
		            	itHit(temp_wall);
			         
			            setY(getY()-dy);
		                setX(getX()-dx);
	            	 setDirection(360-getDirection());
	                  dy=speed*Math.sin((Math.toRadians(getDirection())));
	                  dx=speed*Math.cos((Math.toRadians(getDirection())));
	                  score+=100;
	                  break;
		            
		            }
		        }
		            if(right.intersects(wall_left)){
		            	itHit(temp_wall);
		            	
		            	setY(getY()-dy);
		                setX(getX()-dx);
		            	setDirection(180-getDirection());
		           
		                dy=speed*Math.sin((Math.toRadians(getDirection())));
		                dx=speed*Math.cos((Math.toRadians(getDirection())));
		                score+=100;
		                  break;
		            }
		            if(left.intersects(wall_right)){
		            	itHit(temp_wall);
		            	
		            	setY(getY()-dy);
		                setX(getX()-dx);
		            	setDirection(180-getDirection());
		           
		                dy=speed*Math.sin((Math.toRadians(getDirection())));
		                dx=speed*Math.cos((Math.toRadians(getDirection())));
		                score+=100;
		                  break;
		            }
	   
		        }
		      
		     	//check collide with bullet
		        ReefPlayer temp = ReefGame.player;

		        Rectangle reef_top=new Rectangle((int)temp.getX()-40,(int)temp.getY()-15,temp.getWidth(),10);

			      
		        if(bot.intersects(reef_top))
		          {        
		    	     setDirection(360-getDirection());	
		    	         first=1;
		    	         setY(getY()-dy);
		   	              setX(getX()-dx);	    	       
		    		       dx+=0.1;            
		            }    
		        
		        if(getX()<18||getX()>632){
		        	setX(getX()-dx);
		        	setDirection(180-getDirection());
		        	  dy=speed*Math.sin((Math.toRadians(getDirection())));
		                dx=speed*Math.cos((Math.toRadians(getDirection())));
		        }else if(getY()<18){
		        	setY(getY()-dy);
		        	setDirection(360-getDirection());
		        	  dy=speed*Math.sin((Math.toRadians(getDirection())));
		              dx=speed*Math.cos((Math.toRadians(getDirection())));
		        }else if(getY()>480){
		        	setDone(true);
		        	life--;
		        }
	}

	

	public void dead()
    {
        setRDone(true);
        if(getLife()<=0)
          ReefGame.gameover=true;
        
    }
            
	

	public void move(boolean b) {
		move=b;
		
	}
	
	int getScore(){
		return score;
	}
	
	int getLife(){
	 return life;
	}


	public void pluslife() {
	   life++;
		
	}
	
}
