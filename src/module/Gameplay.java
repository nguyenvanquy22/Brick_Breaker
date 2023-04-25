package module;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import java.awt.*;

import javax.swing.Timer;

import connection.UpdateStatement;

@SuppressWarnings("serial")
public class Gameplay extends JPanel implements KeyListener, ActionListener 
{
	private int level;
	private boolean gameDisplay = true;
	private boolean isPlay = false;
	private boolean isDisplayMessPause = false;
	private boolean isBricksMapDownRun = false;
	private boolean isBricksMapDownRun1 = true;
	private boolean dataInsertOnece = true;
	
	private int score;
	private int totalBricks;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX;
	private int ballposX;
	private int ballposY;
	private int ballXdir;
	private int ballYdir;
	
	private int brickValue;
	private int bricksMapDown;
	
	private BricksGenerator bricksMap;
	private BricksMapDownThread bricksMapDownThread;
	
	private DataGame dataGame;
	
	public Gameplay()
	{		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		dataGame = new DataGame();
	}
	
	public void setLevelGame(int level)
	{		
		this.level = level;
		gameDisplay = true;
		if (level > 0) brickValue = 3;
		else brickValue = 1;
		gameStart();
	}
	
	public void gameStart() {
		//isPlay = true;
		isDisplayMessPause = false;
		dataInsertOnece = true;
		score = 0;
		totalBricks = 32;
		playerX = 300;
		ballposX = 130;
		ballposY = 350;
		ballXdir = -2;
		ballYdir = -4;
		bricksMapDown = 1;
		
		if(level==2)
		{
			if(bricksMapDownThread != null)	bricksMapDownThread.stopThread();
			bricksMapDownThread = new BricksMapDownThread(bricksMapDown, 12000);
			isBricksMapDownRun = false;
			isBricksMapDownRun1 = true;
		}
		
		bricksMap = new BricksGenerator(brickValue, 4, 8);
		timer.start();
		
		repaint();
	}
	
	public void paint(Graphics g)
	{    	
		// Game Hard
		boolean gameLose = false;
		if(level==2) 
		{
			if(isBricksMapDownRun && isBricksMapDownRun1) 
			{
				isBricksMapDownRun1 = false;
				bricksMapDownThread.start();
			}
			bricksMapDown = bricksMapDownThread.getBricksMapDown();
			gameLose = isLose();
		}	
				
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		// drawing map bricks
		bricksMap.draw((Graphics2D) g, bricksMapDown);
		
		// borders
//		g.setColor(Color.yellow);
//		g.fillRect(1, 0, 3, 592);
//		g.fillRect(0, 1, 692, 3);
//		g.fillRect(683, 0, 3, 592);
		
		// the scores 		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590,40);
		
		// the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		// the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
				
		// when you lose the game			
		if(ballposY > 570 || gameLose)
        {
			 isPlay = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif", Font.BOLD, 35));
             g.drawString("Game Over, Scores: "+score, 180, 300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif", Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230, 350);   
             g.drawString("Press (B) to Back to Menu", 230, 400);
             
             if(level==2) bricksMapDownThread.stopThread();
             
             // write data
             if(dataInsertOnece) insertData(0);
        }		
		
		// when you won the game
		if(totalBricks <= 0)
		{
			 isPlay = false;
             ballXdir = 0;
     		 ballYdir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif", Font.BOLD, 35));
             g.drawString("You Won", 260, 200);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif", Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230, 250); 
             g.drawString("Press (B) to Back to Menu", 230, 300);
             
             if(level==2) bricksMapDownThread.stopThread();
             
             // write data
             if(dataInsertOnece) insertData(1);
		}
		
		// when you pause the game
		if(isDisplayMessPause)
		{
			g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 20));           
            g.drawString("Press (Space) to Continue", 230, 250); 
            g.drawString("Press (Enter) to Restart", 230, 300);
            g.drawString("Press (B) to Back to Menu", 230, 350);
		}
		
		g.dispose();
	}
	
	public boolean isLose() 
	{
		for(int i = 0; i<bricksMap.map.length; i++)
		{
			for(int j = 0; j<bricksMap.map[0].length; j++)
			{
				int brickY = (i+2) * bricksMap.brickHeight + 60*bricksMapDown;
				if(bricksMap.map[i][j] > 0 && brickY > 570) 
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public void insertData(int result) 
	{
		dataInsertOnece = false;
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timePlayGame = now.format(formatter);
        dataGame.setTimePlayGame(timePlayGame);
        dataGame.setLevelGame(level);
        dataGame.setScore(score);
        dataGame.setResult(result);
        
        UpdateStatement.insertDataGame(dataGame);
	}

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{        
			if(playerX >= 600)
			{
				playerX = 600;
			}
			else
			{
				moveRight();
			}
        }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{          
			if(playerX < 10)
			{
				playerX = 10;
			}
			else
			{
				moveLeft();
			}
        }	

		if (e.getKeyCode() == KeyEvent.VK_SPACE) 
		{
			if(isPlay) {
				isDisplayMessPause = true;
				isPlay = false;
				timer.stop();
				if(level==2) bricksMapDownThread.stopThread();
			} else {
				isDisplayMessPause = false;
				isPlay = true;
				timer.start();
				if(level==2)
				{
					bricksMapDownThread = new BricksMapDownThread(bricksMapDown, 12000);
					bricksMapDownThread.start();
				}
			}
			repaint();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!isPlay)	gameStart();
        }
		
		if (e.getKeyCode() == KeyEvent.VK_B)
		{
			if(!isPlay && gameDisplay) gameDisplay = false;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void moveRight()
	{
		isBricksMapDownRun = true;
		isPlay = true;
		playerX+=30;	
	}
	
	public void moveLeft()
	{
		isBricksMapDownRun = true;
		isPlay = true;
		playerX-=30;	 	
	}
	
	public boolean isGameDisplay() {
		return gameDisplay;
	}

	public void setGameDisplay(boolean gameDisplay) {
		this.gameDisplay = gameDisplay;
	}

	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(isPlay)
		{			
			if(new Rectangle(ballposX, ballposY, 20, 20)
					.intersects(new Rectangle(playerX, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = -3;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20)
					.intersects(new Rectangle(playerX + 70, 550, 30, 8)))
			{
				ballYdir = -ballYdir;
				ballXdir = ballXdir + 1;
			}
			else if(new Rectangle(ballposX, ballposY, 20, 20)
					.intersects(new Rectangle(playerX + 30, 550, 40, 8)))
			{
				ballYdir = -ballYdir;
			}
			
			// check map collision with the ball		
			A: for(int i = 0; i<bricksMap.map.length; i++)
			{
				for(int j =0; j<bricksMap.map[0].length; j++)
				{				
					if(bricksMap.map[i][j] > 0)
					{
						//scores++;
						int brickX = j * bricksMap.brickWidth + 80;
						int brickY = i * bricksMap.brickHeight + 60*bricksMapDown;
						int brickWidth = bricksMap.brickWidth;
						int brickHeight = bricksMap.brickHeight;
						
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						
						if(ballRect.intersects(brickRect))
						{		
							bricksMap.setBrickValue(--bricksMap.map[i][j], i, j);
							if(bricksMap.map[i][j] == 0) {
								score += 5;
								totalBricks--;
							}
							
							// when ball hit left or right of brick
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)	
							{
								ballXdir = -ballXdir;
							}
							// when ball hits top or bottom of brick
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX < 0)
			{
				ballXdir = -ballXdir;
			}
			if(ballposY < 0)
			{
				ballYdir = -ballYdir;
			}
			if(ballposX > 670)
			{
				ballXdir = -ballXdir;
			}		
			
			repaint();		
		}
	}
}
