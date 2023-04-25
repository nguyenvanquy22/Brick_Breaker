package module;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Menu extends JPanel implements KeyListener 
{	
	private int level = 0;
	private double valueFont[] = {1.5,1,1,1};
	private Image backGround = Toolkit.getDefaultToolkit().getImage("src/images/bgMenu.jpg");
	
	private boolean menuDisplay = true;
	
	private History history = new History();
	
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
		
		if(!history.isHistoryDisplay()) {
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
			history.paintHistory(g);		
		}
		
		g.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub		
		if(!history.isHistoryDisplay()) 
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
						history.setHistoryDisplay(true);
						history.setDataGetOnece(true);
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
					history.setHistoryDisplay(true);
					history.setDataGetOnece(true);
					break;
				default:
					break;
			}
		}
		else 
		{
			history.handleKeyPress(e);
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
