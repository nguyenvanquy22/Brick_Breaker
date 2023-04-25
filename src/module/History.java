package module;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import connection.GameDao;

public class History  {
	
	private int pageIndexCurrent = 1;
	private int pageIndexMax = 3;
	
	private int pageNumber;

	private boolean historyDisplay = false;
	private boolean dataGetOnece = true;
	
	private List<DataGame> data = null;
	
	public History() {
		
	}

	public boolean isHistoryDisplay() {
		return historyDisplay;
	}

	public void setHistoryDisplay(boolean historyDisplay) {
		this.historyDisplay = historyDisplay;
	}

	public boolean isDataGetOnece() {
		return dataGetOnece;
	}

	public void setDataGetOnece(boolean dataGetOnece) {
		this.dataGetOnece = dataGetOnece;
	}

	public void paintHistory(Graphics g)
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
		if(dataGetOnece) {
			dataGetOnece = false;
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
	
	public void handleKeyPress(KeyEvent e) 
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
}
