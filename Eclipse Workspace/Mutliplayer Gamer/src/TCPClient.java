import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient implements Runnable{
	
	private Thread serverThread;
	private String ip;
	private int port;
	private boolean isConnected;
	private Socket serverSocket;
	private PrintWriter output = null;
	private BufferedReader input = null;
	
	public TCPClient(String ip, int port){
		this.ip = ip;
		this.port = port;
		
	}
	
	
	private void receivePackets(){
		Thread packetThread = new Thread("Packet Listener"){
			//output.writeChars(message);
			public void run(){
				String message;
				
				while(isConnected()){
					try {
						message = input.readLine();
						System.out.println("From Server: " + message);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		packetThread.start();
	}
	
	public void run(){
		while(isConnected()){
			output.write("test");
		}
	}
	
	public boolean connect() {
		if(isConnected()) {
			return false;
		}
		
		try {
			serverSocket = new Socket(ip,port);
			
			output = new PrintWriter(serverSocket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		isConnected = true;
		serverThread = new Thread(this);
		serverThread.start();
		receivePackets();
		
		
		
		return true;
	}
	
	public boolean disconnect() throws InterruptedException, IOException {
		if(!isConnected()) {
			return false;
		}
		serverSocket.close();
		isConnected = false;
		serverThread.join();
		
		input.close();
		output.close();
		
		return true;
	}
	
	private synchronized boolean isConnected(){
		return this.isConnected;
	}
}
