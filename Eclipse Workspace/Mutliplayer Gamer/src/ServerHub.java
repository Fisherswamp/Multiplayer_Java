import java.util.ArrayList;

public class ServerHub implements Runnable{
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
		ServerRunner.test += 1;
		while(isRunning) {
			//Check for user input
		}
		
		
	}
}
