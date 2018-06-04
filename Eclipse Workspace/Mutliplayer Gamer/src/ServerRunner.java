
public class ServerRunner {

	public static void main(String[] args) {
		String ip = "127.0.0.1";
		int port = 6969;
		
		ServerHub server = new ServerHub(ip,port);
		TCPClient client = new TCPClient(ip,port);
		
		server.start();
		client.connect();
		
		DatabaseManager db = new DatabaseManager();
		
		
		
	}

}
