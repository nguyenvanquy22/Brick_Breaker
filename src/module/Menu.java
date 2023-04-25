package module;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JPanel;

import connection.GameDao;

@SuppressWarnings("serial")
public class Menu extends JPanel implements KeyListener 
{	
	private int level = 0;
	private double valueFont[] = {1.5,1,1,1};
	private Image backGround = Toolkit.getDefaultToolkit().getImage("src/images/bgMenu.jpg");
	
	private int pageIndexCurrent = 1;
	private int pageIndexMax = 3;
	private int pageNumber;
	
	private boolean menuDisplay = true;
	private boolean historyDisplay = false;
	private boolean getDataOnece = true;
	
	private List<DataGame> data = null;
	
	public Menu() 
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public int getLevel() 
	{
		return level;
	}

	public boolean isMenuDisplay() 
	{
		return menuDisplay;
	}

	public void setMenuDisplay(boolean menuDisplay) {
		this.menuDisplay = menuDisplay;
	}

	public void paint(Graphics g) 
	{
		super.paint(g);
		
		if(!historyDisplay) {
			// background
//			g.setColor(Color.black);
//			g.fillRect(1, 1, 692, 592);
			g.drawImage(backGround, 0, 0, 700, 600, getFocusCycleRootAncestor());

			// title
			g.setColor(Color.red);
			g.setFont(new Font("Algerian",Font.CENTER_BASELINE, 50));
			g.drawString("BREAKOUT BALL", 150, 180);
			
			// level
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.CENTER_BASELINE, (int) (20*valueFont[0])));
			g.drawString("Easy (Press 0)", 250, 250);
			
			g.setFont(new Font("serif",Font.CENTER_BASELINE, (int) (20*valueFont[1])));
			g.drawString("Medium (Press 1)", 250, 300);
			
			g.setFont(new Font("serif",Font.CENTER_BASELINE, (int) (20*valueFont[2])));
			g.drawString("Hard (Press 2)", 250, 350);
			
			// display history
			g.setFont(new Font("serif",Font.CENTER_BASELINE, (int) (20*valueFont[3])));
			g.drawString("History Play ( H )", 250, 400);
		} 
		else 
		{
			// background
			g.setColor(Color.black);
			g.fillRect(1, 1, 692, 592);
			
			// header
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.CENTER_BASELINE, 30));
			g.drawString("No.", 50, 50);
			g.drawString("Date", 200, 50);
			g.drawString("Level", 370, 50);
			g.drawString("Score", 470, 50);
			g.drawString("Result", 570, 50);
			
			// history
			if(getDataOnece) {
				getDataOnece = false;
				data = new GameDao().getAllDataGame();
			}
			int n = data.size();
			pageNumber = n%10==0 ? n/10:n/10+1;
			pageNumber = pageNumber>pageIndexMax ? pageIndexMax:pageNumber;
			
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.CENTER_BASELINE, 20));
			int t = (pageIndexCurrent-1)*10;
			int No = 1;
			for(int i=n-t-1; i>=n-10-t && i>=0; i--) 
			{
				int y = 50+42*No;
				g.drawString((No+t)<10?"0"+(No+t):""+(No+t), 55, y);
				g.drawString(data.get(i).getTimePlayGame(), 150, y);
				g.drawString(data.get(i).getLevelGame()+"", 400, y);
				g.drawString(data.get(i).getScore()+"", 490, y);
				g.drawString(data.get(i).getResult()==1?"Win":"Lose", 590, y);
				No++;
			}
			
			// prev & next
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.CENTER_BASELINE, 30));
			g.drawString("<--", 500, 535);
			g.drawString("0"+pageIndexCurrent, 555, 535);
			g.drawString("-->", 600, 535);			
		}
		
		g.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub		
		if(!historyDisplay) 
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					valueFont[level--] = 1;
					level = level<0 ? 0:level;
					valueFont[level] = 1.5;
					break;
				case KeyEvent.VK_DOWN:
					valueFont[level++] = 1;
					level = level>3 ? 3:level;
					valueFont[level] = 1.5;
					break;
				case KeyEvent.VK_ENTER:
					if(level<3) menuDisplay = false;
					else {
						historyDisplay = true;
						getDataOnece = true;
					}
					break;
				case KeyEvent.VK_0:
					level = 0;
					menuDisplay = false;
					break;
				case KeyEvent.VK_1:
					level = 1;
					menuDisplay = false;
					break;
				case KeyEvent.VK_2:
					level = 2;
					menuDisplay = false;
					break;
				case KeyEvent.VK_H:
					historyDisplay = true;
					getDataOnece = true;
					break;
				default:
					break;
			}
		}
		else 
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_B:
					historyDisplay = false;
					break;
				case KeyEvent.VK_LEFT:
					pageIndexCurrent--;
					pageIndexCurrent = pageIndexCurrent<1 ? 1:pageIndexCurrent;
					break;
				case KeyEvent.VK_RIGHT:
					pageIndexCurrent++;
					pageIndexCurrent = pageIndexCurrent>pageNumber ? pageNumber:pageIndexCurrent;
					break;
				default:
					break;
			}
		}
		repaint();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}
