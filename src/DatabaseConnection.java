import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static Connection connection;

	
	private DatabaseConnection()
	{
	}
	
	public static Connection get_Connection() throws ClassNotFoundException {
		if (connection == null){
			try {
				Class.forName("org.sqlite.JDBC");
				System.out.println(Settings.databasePath);
				connection = DriverManager.getConnection("jdbc:sqlite:"+Settings.databasePath);
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public static void close_Connection(){
		try {
			connection.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

