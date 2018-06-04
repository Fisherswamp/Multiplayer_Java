import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 
 * @author Itai Rivkin-Fish
 * @version 5/2/2018
 */
public class ServerHub implements Runnable{
	static final long BILLION = 1_000_000_000;
	static final int packetRecieveSize = 2048;
	
	private List<Input> inputList = Collections.synchronizedList(new ArrayList<Input>());
	
	private boolean isRunning;
	private Thread serverThread;
	private ArrayList<Match> matchList;
	
	private String ip;
	private int port;
	private ServerSocket serverSocket;
	
	
	public ServerHub(String ip, int port){
		this.ip = ip;
		this.port = port;
		
	}
	
	/**
	 * Receive packets as the server on a seperate thread
	 */
	private void receivePackets(){
		Thread packetThread = new Thread("Packet Listener"){
			public void run(){
				System.out.println("Is running: " + isRunning());
				while(isRunning()){
					try {	
						Socket clientSocket = serverSocket.accept();
						new Thread(
								new clientInputWorker(clientSocket, "Server Worker Thread")
						).start();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		packetThread.start();
	}
	
	
	/**
	 * Starts the server
	 * @return boolean, true if the server was started, false if it already has been started
	 */
	public boolean start() {
		if(isRunning()) {
			return false;
		}
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setRunning(true);
		serverThread = new Thread(this);
		serverThread.start();
		receivePackets();	
		return true;
	}
	/**
	 * Stops the server
	 * @return boolean, true if the server was stopped, false if the server is not running
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public boolean stop() throws InterruptedException, IOException {
		if(!isRunning()) {
			return false;
		}
		serverSocket.close();
		setRunning(false);
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
		while(isRunning()) {
			now = System.nanoTime();
			nanoSecondsElapsed += now-timeAtLastTick;
			timeAtLastTick = now;
			while(nanoSecondsElapsed >= nanoSecondsPerTick){
				nanoSecondsElapsed -= nanoSecondsPerTick;
				tick();
				//timeAtLastTick = System.nanoTime();
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
	
	/**
	 * 
	 * @return boolean Whether or not the server is running
	 */
	private synchronized boolean isRunning(){
		return this.isRunning;
	}
	
	private synchronized void setRunning(Boolean running){
		isRunning = running;
	}
}
