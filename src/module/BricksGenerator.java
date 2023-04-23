package module;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class BricksGenerator 
{
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	public int brickColorValue;
	
	public BricksGenerator (int brickValue, int row, int col)
	{
		map = new int[row][col];		
		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				if(brickValue == 1) map[i][j] = 1;
				else map[i][j] = new Random().nextInt(brickValue) + 1;
			}			
		}
		
		brickWidth = 540/col;
		brickHeight = 150/row;
		brickColorValue = 255/brickValue;
	}	
	
	public void draw(Graphics2D g, int bricksMapDown)
	{
		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				if(map[i][j] > 0)
				{	
					g.setColor(new Color(255, 255, 255, map[i][j]*brickColorValue));
					g.fillRect(j * brickWidth + 80, i * brickHeight + 60*bricksMapDown, brickWidth, brickHeight);
					
					// show separate brick
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 60*bricksMapDown, brickWidth, brickHeight);				
				}
			}
		}
	}
	
	public void setBrickValue(int brickValue, int row, int col)
	{
		map[row][col] = brickValue;
	}
}
