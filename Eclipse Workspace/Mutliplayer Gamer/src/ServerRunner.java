
public class ServerRunner {

	static int test = 0;
	public static void main(String[] args) {
		
		ServerHub server = new ServerHub();
		
		
		server.start();
		test += 1;
		try {
			server.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(test);
		
	}

}
