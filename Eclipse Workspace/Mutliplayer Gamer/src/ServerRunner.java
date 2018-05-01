
public class ServerRunner {

	public static void main(String[] args) {
		
		ServerHub server = new ServerHub("127.0.0.1",9696);
		DatabaseManager db = new DatabaseManager();
		
		if(Settings.debug)
			System.out.println("Server Started: " + server.start());
		
	}

}
