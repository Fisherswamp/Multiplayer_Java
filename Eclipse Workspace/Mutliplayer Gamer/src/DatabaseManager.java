import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
	
	private Connection connection;
	
	public DatabaseManager(){
		try {
			connection = getConnection();
			createTable();
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
		if(Settings.debug)
			System.out.println("Connected");
		
		return conn;
	}
	
	private void createTable() throws SQLException{
		PreparedStatement create = connection.prepareStatement(
				"CREATE TABLE IF NOT EXISTS "
						+ "users("
							+ "id int NOT NULL AUTO_INCREMENT,"
							+ "username varchar(255),"
							+ "password varchar(255),"
							+ "salt varchar(255),"
							+ "start_date datetime,"
							+ "PRIMARY KEY(id)"
						+ ")"
		);
		create.executeUpdate();
		
		if(Settings.debug)
			System.out.println("Table created");
	}
}
