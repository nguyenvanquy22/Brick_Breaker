package module;

public class BricksMapDownThread extends Thread {
    private int bricksMapDown;
    private int delay;
    private volatile boolean isPlay = true;

    public BricksMapDownThread(int bricksMapDown, int delay) {
        this.bricksMapDown = bricksMapDown;
        this.delay = delay;
    }

    public void run() {
        try {
            while(isPlay) {
                Thread.sleep(delay);
                if(!isPlay) break;
                bricksMapDown++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
		isPlay = false;
	}

	public int getBricksMapDown() {
		return bricksMapDown;
	}    
}

