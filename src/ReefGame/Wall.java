package ReefGame;

import java.awt.Image;
import java.util.Observable;

import myGames.GameEvents;
import myGames.Thing;
import myGames.Unit;

public class Wall extends Unit {

	public Wall(int x, int y, double direction, int speed, Image[] img,
			GameEvents events, int maxdamage, int damageto, int eps) {
		super(x, y, direction, speed, img, events, maxdamage, damageto, eps);
	}

	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hitMe(Thing caller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if(getDamage()>=getMax()){
			setRDone(true);
			setDone(true);
		}
		if(getMax()==300){
			changeDamage(-300);
		}
	}

	@Override
	public void dead() {
	
	}

}
