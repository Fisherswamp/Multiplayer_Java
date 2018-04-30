import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	
	private Connection connection;
	
	public DatabaseManager(){
		try {
			connection = getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String ip = "localhost";
		String port  = "3306";
		String dataBaseName = "test";
		String url = "jdbc:mysql://" + ip + ":" + port + "/" + dataBaseName;
		String username = "testusr";
		String password = "testpss";
		Class.forName(driver);
		
		Connection conn = DriverManager.getConnection(url,username,password);
		System.out.println("Connected");
		
		return conn;
	}
}
