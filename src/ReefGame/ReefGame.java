/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReefGame;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.URL;
import java.text.AttributedString;
import java.util.*;

import javax.sound.midi.*;
import javax.swing.*;

import org.omg.CORBA.portable.InputStream;

import myGames.*;

/**
 *  The goal of the game is to destroy enemy boss.
 * For one player the controls are: arrow keys control movement, enter is fire
 * For the second player: wasd for movement and space to fire
 * You get 2 extra lives shared between the players
 * Player 1 spawns on the right and Player 2 on the left
 * Player 1 has the bottom life bar in the lower left corner and Player 2 has
 * the top life bar.
 * I got an error trying to run this in Chrome but it runs in Internet Explorer
 * and as an independent program.
 * @author Lowell Milliken
 */
public class ReefGame extends Game
{
    private static GameSpace screen,screen_Title;
	private static GameLabel start;
	private static GameLabel load;
	private static GameLabel help;
	private ScoreTable scoreTable;
	private static GameLabel scores,front;
	private static GameLabel quit;
	 static Star star_reef;
	 static ReefPlayer player;
    private Random random = new Random();
    private ArrayList<ArrayList> everything;
    private ArrayList<Thing> things,mainButton;
    static ArrayList<Wall> wall,Biglegs;
    static ArrayList<Star> star;
    private ArrayList<PlayerParent> players;
    private ReefEvents events;
    private int score;
    private Image[][] block,pop;
	private static Image[] button[];
	private Image[] Congratulation;
	private Image[][] Katch;
	static Image[][]Star;
	private Image[][] Bigleg;
	private Image[][] Bigleg_small;
	private Image[][] block_life;
	private Image[][] block_solid;
	private Image[] block_split;
	private Image[] block_double;
	private Image[] Title[];
    private GameController gcontroller;
  
    private ArrayList<ScoreType> sTable;
    static boolean gameover;
    private boolean destroy = false;
    private boolean twoplayers = false;
    private boolean exit = false;
    private int lives;
    private Image lifeImg;
    private URL[] Sound;
	private Graphics2D g2;

    
    public class ReefEvents extends GameEvents
    {

        public void setBoss(int type)
        {
            setType(type);
            setChanged();

            notifyObservers(this);
        }
    }

    public void paint(Graphics g)
    {
        Dimension d = getSize();
        Graphics2D g2 = createGraphics2D(d.width, d.height);
        drawAll(d.width, d.height, g2);
        if(start.getPressed()==true){

        g2.draw(new Rectangle2D.Double((int)player.getX()-40,(int)player.getY()-15,player.getWidth(),10));
        g2.draw(new Rectangle2D.Double(star_reef.getX()-17,star_reef.getY()-17,35,6));
        g2.draw(new Rectangle2D.Double(star_reef.getX()-17,(int)star_reef.getY()+12,35,6));
        g2.draw(new Rectangle2D.Double(star_reef.getX()-17,(int)star_reef.getY()-17,6,35));
        g2.draw(new Rectangle2D.Double(star_reef.getX()+12,(int)star_reef.getY()-17,6,35));
        for(int i=0;i<wall.size();i++){
        Wall temp_wall=wall.get(i);
        g2.draw(new Rectangle2D.Double((int)temp_wall.getX()-20,(int)temp_wall.getY()-10,temp_wall.getWidth(),5));
        g2.draw(new Rectangle2D.Double((int)temp_wall.getX()-20,(int)temp_wall.getY()+5,temp_wall.getWidth(),5));
        g2.draw(new Rectangle2D.Double((int)temp_wall.getX()-20,(int)temp_wall.getY()-10,5,temp_wall.getHeight()));
        g2.draw(new Rectangle2D.Double((int)temp_wall.getX()+14,(int)temp_wall.getY()-10,5,temp_wall.getHeight()));
        }
        //draw life
       int life=star_reef.getLife();
       for(int i=0;i<life;i++){
        g2.drawImage(Katch[1][0], 50+i*40, 400, null);
       }
        }
        g2.dispose();
        g.drawImage(super.getImage(), 0, 0, this);
    }
    
    
 
	//creates and adds all the game panel to the applet
    //also sets up images, sounds, and creates and initializes state for most
    //variables and objects.
    @Override
    public void init()
    {      
        super.init();        
        screen = new GameSpace(getSprite("Resources/Background1.png"), new DrawAbs());
        start= new GameLabel(button[0][0],30,400,new DrawAbs());
        load= new GameLabel(button[1][0],150,400,new DrawAbs());
        help= new GameLabel(button[2][0],270,400,new DrawAbs());
        scores= new GameLabel(button[3][0],390,400,new DrawAbs());
        quit= new GameLabel(button[4][0],510,400,new DrawAbs());
        front=new GameLabel(Title[0][0],80,20,new DrawAbs());
        everything = new ArrayList<ArrayList>();
        things = new ArrayList<Thing>();
        //add mouse listener to screen
       
        addMouseListener(start);
        addMouseListener(load);
        addMouseListener(help);
        addMouseListener(scores);
        addMouseListener(quit);
        addMouseListener(front);
        players = new ArrayList<PlayerParent>();
        
        sTable=new ArrayList<ScoreType>();
        Biglegs=new ArrayList<Wall>();
        wall=new ArrayList<Wall>();
        star=new ArrayList<Star>();
        everything.add(star);
        everything.add(things);
        everything.add(wall);
        everything.add(Biglegs);
        everything.add(players);
       
         events=new ReefEvents(); 
         KeyControl keys = new KeyControl(events);
         addKeyListener(keys);
         
        gcontroller = new GameController();
        score = 0;
      
        gameover = false;
          
        sTable = new ArrayList<ScoreType>(10);
        for(int i = 0; i < 10; i++)
        {
            sTable.add(new ScoreType("<No One>", 0));
        }
      
    }
    
    //getting all image files
    @Override
    public void initImages()
    {
        try
        {
        	pop=new Image[1][45];
        	for(int i=0;i<45;i++){
        	pop[0][i]=getSprite("Resources/pop_"+i+".png");
        	}
        	Star=new Image[1][1];
        	Star[0][0]=getSprite("Resources/star.png");
          block=new Image[7][1];
          for(int i=0;i<7;i++){
        	  block[i][0]=getSprite("Resources/Block"+(i+1)+".png");
          }
          Bigleg_small=new Image[1][1];
          Bigleg_small[0][0]=getSprite("Resources/Bigleg_small.png");
          Katch=new Image[2][1];
          Katch[0][0]=getSprite("Resources/Katch.png");
          Katch[1][0]=getSprite("Resources/Katch_small.png");   
          block_life=new Image[1][1];
          block_life[0][0]=getSprite("Resources/Block_life.png");
          block_solid=new Image[1][1];
          block_solid[0][0]=getSprite("Resources/Block_solid.png");
          block_split=new Image[1];
          block_split[0]=getSprite("Resources/Block_split.png");
          button=new Image[5][1];
          button[0][0]=getSprite("Resources/Button_start.png");
          button[1][0]=getSprite("Resources/Button_load.png");
          button[2][0]=getSprite("Resources/Button_help.png");
          button[3][0]=getSprite("Resources/Button_scores.png");
          button[4][0]=getSprite("Resources/Button_quit.png");
          Title=new Image[2][1];
          Title[0][0]=getSprite("Resources/Title_burned.png");
          
        } catch (Exception e)
        {
            System.out.println("Error in getting images: " + e.getMessage());
        }
    }
    
    //getting all sound files
    @Override
    public void initSound()
    {
        try
        {
       Sequence music;
       Sequencer seq;
       URL musicu = ReefGame.class.getResource("Resources/Music.mid");
         Sound = new URL[6];
        Sound[0] = ReefGame.class.getResource("Resources/Sound_bigleg.wav");
        Sound[1] = ReefGame.class.getResource("Resources/Sound_block.wav");
        Sound[2] = ReefGame.class.getResource("Resources/Sound_click.wav");
        Sound[3] = ReefGame.class.getResource("Resources/Sound_katch.wav");
        Sound[4] = ReefGame.class.getResource("Resources/Sound_lost.wav");
        Sound[5] = ReefGame.class.getResource("Resources/Sound_wall.wav");
   
           music =  MidiSystem.getSequence(musicu);
           seq = MidiSystem.getSequencer();
           seq.open();
           seq.setSequence(music);
           seq.start();
           seq.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        }
        catch(Exception e)
        {
            System.out.println("Error in midi: " + e.getMessage());
        }
    }

    //this stores scores paired with names
    public class ScoreType
    {

        private String name=null;
        private int score;

        public ScoreType(String name, int score)
        {
            this.name = name;
            this.score = score;
        }

        public int getScore()
        {
            return score;
        }

        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
    }
    public class ScoreTable extends JFrame implements ActionListener
    {
        private JTextField enterName;
        private boolean high = false,edit=true;
        private int thisIndex;
        
        public ScoreTable(String title)
        {
            super(title);
            this.setLocation(400, 100);
            this.setLayout(new GridLayout(0,2));
           
            for(int i = 0; i < 10; i++)
            {
                if(score >= sTable.get(i).getScore()&&!high&&edit==true)
                {
                    enterName = new JTextField(10);
                    enterName.setEditable(true);
                    enterName.setFocusable(true);
                    this.add(enterName);
                    
                    JTextField scoreT = new JTextField(6);
                    scoreT.setEditable(false);
                    scoreT.setText("" + score);
                    this.add(scoreT);
                    
                    sTable.add(i, new ScoreType("dummy", score));
                    
                    high = true;
                    thisIndex = i;
                }
                else
                {
                    JTextField name = new JTextField(20);
                    name.setEditable(false);
                    if(sTable.get(i).getName()!=null){
                    name.setText(sTable.get(i).getName());
                    }else{
                    	name.setText("nobody");
                    }
                    this.add(name);
                    
                    JTextField scoreT = new JTextField(6);
                    scoreT.setEditable(false);
                    scoreT.setText("" + sTable.get(i).getScore());
                    this.add(scoreT);
                }
            }
            
            
        
            this.pack();
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if("one".equals(e.getActionCommand()))
            {
                twoplayers = false;
            }
            destroy = true;
            if(high)
            {
                sTable.get(thisIndex).setName(enterName.getText());
            }
            this.dispose();
        }

		public void setEdit(boolean b) {
			edit=b;
		}
    }
    

    //this creates Things when needed to make the gameplay pattern
    public class GameController
    {

        private int timer,level=0;
       private boolean addY=false;
        public GameController()
        {
            timer = 0;
        }

        public void game()
        {
        	  switch(timer){	
        case 0:
        	try{
          
    	java.io.InputStream source = getClass().getResourceAsStream("Resources/big.txt");
           if(level==1){
        	   source = getClass().getResourceAsStream("Resources/small.txt");
        	}
             int c=source.read();
             int x=20;
             int y=10;
             int count=0;		 
             while (c!= -1) {
            	
            	 if(c==49){      	
                	 wall.add(new Wall(x,y,0,0,block_solid[0],events,300,0,10));
                	 x+=40; 
                	count++;
                	
                 }
            	 
            	 if(c==51){
            		 wall.add(new Wall(x,y,0,0,block[0],events,100,0,10));
                	 x+=40; 
                	count++;
                	
            	 }
            	 
            	 if(c==52){
            		 wall.add(new Wall(x,y,0,0,block[1],events,100,0,10));
                	 x+=40; 
                	count++;
                	
            	 }
            	 
            	 if(c==53){
            		 wall.add(new Wall(x,y,0,0,block[2],events,100,0,10));
                	 x+=40; 
                	count++;
                	
            	 }
            	 
            	 if(c==54){
            		 wall.add(new Wall(x,y,0,0,block[3],events,100,0,10));
                	 x+=40; 
                	count++;
                	
            	 }
            	 if(c==55){
            		 wall.add(new Wall(x,y,0,0,block[4],events,100,0,10));
                	 x+=40; 
                	count++;
                	
            	 }
            	 if(c==56){
            		 wall.add(new Wall(x,y,0,0,block[5],events,100,0,10));
                	 x+=40; 
                	count++;
                	
            	 }
            	 if(c==57){
            		 wall.add(new Wall(x,y,0,0,block[6],events,100,0,10));
                	 x+=40; 
                	count++;
                	
            	 }
            	 
            	 if(c==97){
            		 wall.add(new life(x,y,0,0,block_life[0],events,100,0,10));
                	 x+=40; 
                	count++;
                	
            	 }
            	 
            	
            	 if(c==98){
            		 Biglegs.add(new Bigleg(x,y,0,5,Bigleg_small[0],events,100,0,10));
            		 x+=40;
            		
            		 count++;
            	 }
            		
            	
            	
            	 //if c==2 then increase x position
            	 if(c==50){
            	   x+=40;
            	 }
            	 if(x==660){
             		x=20;
             		y+=20;
             		count=0;
             	}
            	c=source.read();
            }
        	}
        	 catch(Exception e){
        		System.out.println(e);
        	  }
        	
        	 player=new ReefPlayer(320,430,0,10,Katch[0],events,5,50,5,KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,KeyEvent.VK_ENTER,5,5);
        	 players.add(player);
        	 star_reef=new Star(320,330,240,12,pop[0],events,2,everything,100,3);
        	 star.add(star_reef);
           }
        	  //if life is greater than 0 then continue the game
        	  if(star_reef.getDone()==true&&star_reef.getLife()>0){
        		  star_reef=new Star(320,330,240,12,pop[0],events,2,everything,100,star_reef.getLife());
        		  star.add(star_reef);
        	  }
        	  
        	  if(star_reef.getLife()<=0){
        		  wall.clear();
         		 players.clear();
         		 Biglegs.clear();
         		 star.clear();
         		 gameover=true;
        	  }
        	  
        	  
        	 
        	  if(Biglegs.size()==0&&star_reef.getLife()>0){
        		 timer=0;
        		 level++;
        		 wall.clear();
        		 players.clear();
        		 Biglegs.clear();
        		 star.clear();
        		 game();
        	  }
        	  if(level==2){
        		  gameover=true;
        	  }
        	  timer++;
        }
        
        private int randomX()
        {
            return random.nextInt(screen.getWidth());
        }
        
        private int randomY()
        {
            return random.nextInt(screen.getHeight());
        }

        public void resetTimer()
        {
            timer = 0;
        }

		public void main() {
			  start.drawImage(g2);
		        load.drawImage(g2);
		        help.drawImage(g2);
		        scores.drawImage(g2);
		        quit.drawImage(g2);
		        front.drawImage(g2);
			
		}

		public void setTimer(int i) {
			timer=i;
			
		}
    }

    //Updates all Things and then draws everything
    //when the game is resetting, this method will also 
    @Override
    public void drawAll(int w, int h, Graphics2D g2)
    {
       
        Thing temp;
         this.g2=g2;
        screen.drawBackground(g2);
           
    if(start.getPressed()){
    	exit=false;
        Iterator<ArrayList> it = everything.listIterator();
        
        while (it.hasNext())
        {
            Iterator<Thing> it2 = it.next().listIterator();
            while (it2.hasNext())
            {
                if (gameover)
                {
                    break;
                }
                temp = it2.next();
                temp.update(w, h);
            
                if (temp.getRDone())
                {
                    it2.remove();
                }
            }
            if (gameover)
            {
                break;
            }
        }
        screen.drawHere(everything, g2);
        if(destroy)
        {
            it = everything.listIterator();
            while(it.hasNext())
            {
                Iterator<Thing> it2 = it.next().listIterator();
                while(it2.hasNext())
                {
                    it2.next();
                    it2.remove();
                }
            }
            gcontroller = new GameController();
            destroy = false;
            gameover = false;
            exit=false;
            score = 0;
            this.requestFocus();
        }
        gcontroller.game();
        
   }else if(help.getPressed()){
	   JFrame f = new JFrame("Help");
	   JPanel panel=new JPanel();
	   panel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
	   JLabel jlabel1 = new JLabel("<html><h1>Controls:</h1> <br>While you are on the opening screen: <br>"+"Use the left mouse button to click on any of the buttons<br> <br> While playing the game: <br>"+"<Left>  moves Katch to the left <br>"+"<Right> moves Katch to the right</html>");
	   panel.add(jlabel1);
	   f.add(panel);
	   f.setSize(new Dimension(640, 480));
       f.setResizable(false);
       f.setLocation(300, 80);
       f.setVisible(true);
       help.setPreesed(false);
   }else if(scores.getPressed()){
	   scoreTable = new ScoreTable("High Scores");
 	   scoreTable.setEdit(false);
       scoreTable.setVisible(true);
        scores.setPreesed(false);
        start.setPreesed(false);
        gameover=false;
   }else if(quit.getPressed()){
	   System.exit(0);
   }
    else{
	   gcontroller.main();
   }
     
	    if(gameover==true && exit==false){
	    	score=star_reef.getScore();
	    	  scoreTable = new ScoreTable("High Scores");
	          scoreTable.setVisible(true);
	          exit=true;
	          start.setPreesed(false);
	          gcontroller = new GameController();
	          gameover=false;
	    }
	     
    }
    public class ReefPlayer extends PlayerParent {
    int count=1;
    	public ReefPlayer(int x, int y, double direction, int speed, Image[] img,
    			GameEvents events, int maxdamage, int damageto, int eps, int left,
    			int right,  int fire, int spfire, int deadTime) {
    		super(x, y, direction, speed, img, events, maxdamage, damageto, eps, left,
    				right, fire);
    	
    	}

    	@Override
    	public void hitMe(Thing caller) {
    		

    	}
    	   public void action()
           {
           	
               if(getIsFiring())
               {
                   if(getShotDelay() == 0)
                   {  
                	   if(count>0){
                       star_reef.changeSpeed(12);
                       star_reef.move(true);
                       count--;
                	   }
                   }
                }
             }
    	@Override
    	public void move() {
    		if(getMvRight()){
    			if(getX()+getSpeed()<640){
    			  this.changeX(getSpeed());
    			}
    		}
    		
    		if(getMvLeft()){
    			if(getX()-getSpeed()>0){
    			  this.changeX(-getSpeed());
    			}
    		}

    		Wall temp;
    	    Iterator<Wall> it = ReefGame.wall.listIterator();
    	    while(it.hasNext()){
    	    	temp=it.next();

    	    	if(temp.collision(getX(), getY(), getWidth(), getHeight())){
    	    		if(getMvLeft()){
    	    		setX(getX()+getSpeed());
    	    		}else{
    	    			setX(getX()-getSpeed());
    	    		}
    	    	}
    	    }
    	}

    	@Override
    	public void dead() {
    		
    	}

		public void getIsFiring(boolean b) {
			super.isFiring=b;
			
		}

    }

    
    public static void main(String[] args)
    {
        final ReefGame game = new ReefGame();
        game.init();
        final JFrame f = new JFrame("Super Rainbow Reef");
        f.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                f.dispose();
                System.exit(0);
            }
        });
       
 
        f.getContentPane().add("Center", game);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setResizable(false);
        f.setLocation(400, 100);
        game.start();
        f.setVisible(true);
    }
}