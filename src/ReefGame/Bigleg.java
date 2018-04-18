package ReefGame;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Iterator;

import ReefGame.ReefGame.ReefPlayer;
import myGames.GameEvents;

public class Bigleg extends Wall {

	public Bigleg(int x, int y, double direction, int speed, Image[] img, GameEvents events, int maxdamage,
			int damageto, int eps) {
		super(x, y, direction, speed, img, events, maxdamage, damageto, eps);
		
	}
	

	public void move(){
		changeX(getSpeed());
		
		Wall temp = null;
		Iterator<Wall> it_wall = ReefGame.wall.listIterator();
		while(it_wall.hasNext()){
			 temp=it_wall.next();
			if(temp.collision(getX(), getY(), getWidth(), getHeight())){
				setSpeed(-getSpeed());
				
			}
		}
				
		if(getDamage()>getMax()){
			setDone(true);
			setRDone(true);
		}
	}
}
