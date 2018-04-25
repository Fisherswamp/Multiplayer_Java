import java.util.ArrayList;

public class ServerHub implements Runnable{
	static final long BILLION = 1_000_000_000;
	
	private boolean isRunning;
	private Thread serverThread;
	private ArrayList<Match> matchList;
	
	
	/**
	 * Starts the server
	 * @return boolean, true if the server was started, false if it already has been started
	 */
	public boolean start() {
		if(isRunning) {
			return false;
		}
		serverThread = new Thread(this);
		serverThread.start();
		isRunning = true;
		return true;
	}
	/**
	 * Stops the server
	 * @return boolean, true if the server was stopped, false if the server is not running
	 * @throws InterruptedException
	 */
	public boolean stop() throws InterruptedException {
		if(!isRunning) {
			return false;
		}
		isRunning = false;
		serverThread.join();
		return true;
	}
	
	@Override
	public void run() {
		long ticksPerSecond = 60;
		double nanoSecondsPerTick = BILLION/ticksPerSecond;
		long timeAtLastTick = System.nanoTime();
		long nanoSecondsElapsed = 0;
		
		int numTicksInSecond = 0;
		long timeSinceLastSecond = System.nanoTime();
		long now;
		while(isRunning) {
			now = System.nanoTime();
			nanoSecondsElapsed += now-timeAtLastTick;
			timeAtLastTick = now;
			while(nanoSecondsElapsed >= nanoSecondsPerTick){
				nanoSecondsElapsed -= nanoSecondsPerTick;
				tick();
				timeAtLastTick = System.nanoTime();
				numTicksInSecond++;
			}
			
			if(System.nanoTime() -  timeSinceLastSecond >= BILLION){
				timeSinceLastSecond = System.nanoTime();
				if(Settings.debug)
					System.out.println("Ticks Per Second: " + numTicksInSecond);
				numTicksInSecond = 0;
			}
			
		}
		
		
	}
	
	private void tick(){
		
	}
}
