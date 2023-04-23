package module;
import javax.swing.JFrame;

public class Main {
	public static void setFrame(JFrame obj) {
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Breakout Ball");
		obj.setLocationRelativeTo(null);
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        obj.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
	}
	public enum FrameState {
        MENU,
        GAMEPLAY
    }
	
	public static void main(String[] args) {
		JFrame obj1=new JFrame();
		JFrame obj2=new JFrame();
		setFrame(obj1);
		setFrame(obj2);

		Menu menu = new Menu();
		Gameplay gameplay = new Gameplay();
        FrameState currentState = FrameState.MENU;
		
		obj1.add(menu);
		
		while(true)
		{
			switch (currentState) {
            case MENU:
                menu.setMenuDisplay(true);
                obj1.setVisible(true);
                obj2.setVisible(false);

                if (!menu.isMenuDisplay()) {
                    currentState = FrameState.GAMEPLAY;
                    gameplay.setLevelGame(menu.getLevel());
                    gameplay.setGameDisplay(true);
                    obj1.setVisible(false);
                    obj2.add(gameplay);
                    obj2.setVisible(true);
                }
                break;
            
            case GAMEPLAY:
                if (gameplay.isGameDisplay()) {
                    obj1.setVisible(false);
                    obj2.setVisible(true);
                } else {
                    obj1.setVisible(true);
                    obj2.setVisible(false);
                    currentState = FrameState.MENU;
                }
                break;
			}
		}
	}
}
