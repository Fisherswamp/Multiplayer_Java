import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class clientInputWorker implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public clientInputWorker(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }
    
    public void run(){
        try {
        	if(Settings.debug)
        		System.out.println("ClientInputRunning");
        	
        	InputStream input  = clientSocket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
        	OutputStream output = clientSocket.getOutputStream();
        	PrintWriter out = new PrintWriter(output,true);
			
			String message = in.readLine();
			System.out.println("From client: " + message);
			out.write(message);
			
			output.close();
	        input.close();
	        in.close();
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}