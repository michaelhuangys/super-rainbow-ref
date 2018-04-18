package ReefGame;

import java.awt.Image;

import myGames.GameEvents;

public class life extends Wall {

	public life(int x, int y, double direction, int speed, Image[] img, GameEvents events, int maxdamage, int damageto,
			int eps) {
		super(x, y, direction, speed, img, events, maxdamage, damageto, eps);
	}

	public void move(){
		if(getDamage()>=getMax()){
			setDone(true);
			setRDone(true);
			ReefGame.star_reef.pluslife();
		}
	}
}
