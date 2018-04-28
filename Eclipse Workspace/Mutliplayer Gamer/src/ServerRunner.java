
public class ServerRunner {

	public static void main(String[] args) {
		
		ServerHub server = new ServerHub("127.0.0.1",9696);
		
		
		System.out.println("Server Started: " + server.start());
		
	}

}
