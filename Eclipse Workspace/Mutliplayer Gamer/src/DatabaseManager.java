import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * 
 * @author Itai Rivkin-Fish
 * @version 5/2/2018
 */
public class DatabaseManager {
	
	private Connection connection;
	
	public DatabaseManager(){
		try {
			connection = getConnection();
			createUserTable();
			createMatchTable();
			String addUser = "test";
			while(userExists(addUser)){
				addUser += "x";
			}
			addUser(addUser,"1234","5687");
			changeProfilename(addUser,"testProfile");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setLoggedOn() throws SQLException{
		
	}
	
	/**
	 * 
	 * @param username Name of user to check if they are logged on
	 * @return Whether or not the user is logged on
	 * @throws SQLException
	 */
	public boolean isLoggedOn(String username) throws SQLException{
		PreparedStatement check = connection.prepareStatement(
				"SELECT * FROM users WHERE username = ? AND logged_on = 1"
		);

		check.setString(1, username);
		
		return check.executeQuery().next();
	}
	
	/**
	 * Get connection to mySQL server
	 * @return {@link Connection} object
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
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
			System.out.println("Connected to database");
		
		return conn;
	}
	/**
	 * Creates user database table, if it has not already been created
	 * @throws SQLException
	 */
	private void createUserTable() throws SQLException{
		PreparedStatement create = connection.prepareStatement(
				"CREATE TABLE IF NOT EXISTS "
						+ "users("
							+ "username varchar(255),"
							+ "profilename varchar(255),"
							+ "password varchar(255),"
							+ "salt varchar(255),"
							+ "start_date datetime DEFAULT CURRENT_TIMESTAMP,"
							+ "profile_level int DEFAULT 1,"
							+ "id int NOT NULL AUTO_INCREMENT,"
							+ "logged_on  boolean DEFAULT 0,"						
							+ "PRIMARY KEY(username),"
							+ "KEY(id)"
						+ ")"
		);
		create.executeUpdate();
		
		if(Settings.debug)
			System.out.println("Table created");
	}
	
	/**
	 * Creates match database table, if it has not already been created
	 * @throws SQLException
	 */
	private void createMatchTable() throws SQLException{
		PreparedStatement create = connection.prepareStatement(
				"CREATE TABLE IF NOT EXISTS "
						+ "matches("
							+ "id int NOT NULL AUTO_INCREMENT,"
							+ "PRIMARY KEY(id)"
						+ ")"
		);
		create.executeUpdate();
		
		if(Settings.debug)
			System.out.println("Table created");
	}
	
	/**
	 * 
	 * @param username Name to check
	 * @return whether or not the user exists in the database
	 * @throws SQLException
	 */
	private boolean userExists(String username) throws SQLException{
		PreparedStatement check = connection.prepareStatement(
				"SELECT * FROM users WHERE username = ?"
		);
		check.setString(1, username);
		
		return check.executeQuery().next();
	}
	/**
	 * 
	 * @param username The name of the user to add
	 * @param password The user's password (hashed)
	 * @param salt The salt used in the hash
	 * @throws SQLException
	 */
	private void addUser(String username, String password, String salt) throws SQLException{		
		PreparedStatement add = connection.prepareStatement(
				"INSERT INTO users(username, profilename, password,salt)"
				+ "VALUES (?, ?, ?, ?);"
		);
		add.setString(1, username);
		add.setString(2, username);
		add.setString(3, password);
		add.setString(4, salt);
		add.executeUpdate();
	}
	/**
	 * 
	 * @param username username of user to change profilename
	 * @param newProfilename new name to set
	 * @throws SQLException
	 */
	private void changeProfilename(String username, String newProfilename) throws SQLException{
		PreparedStatement change = connection.prepareStatement(
				"UPDATE users"
				+ " SET profilename = ?"
				+ " WHERE username = ?;"
		);
		change.setString(1, newProfilename);
		change.setString(2, username);
		change.executeUpdate();
	}
}
