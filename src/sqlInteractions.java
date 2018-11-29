import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class sqlInteractions {

	public static void saveCombinationToRecipes(String name,String item1, String item2, String item3, String item4 ) throws ClassNotFoundException, SQLException {
		PreparedStatement stmt = DatabaseConnection.get_Connection().prepareStatement("INSERT INTO recipes (name, item1, item2, item3, item4) VALUES (?,?,?,?,?)");
		stmt.setString(1, name);
		stmt.setString(2, item1);
		stmt.setString(3, item2);
		stmt.setString(4, item3);
		stmt.setString(5, item4);
		stmt.execute();
		stmt.close();
		DatabaseConnection.get_Connection().commit();
	}
	public static List<String> getCombinationFromDB(String name) throws ClassNotFoundException, SQLException {
		List<String> checksums = new ArrayList<String>();
		Statement stmt;
		stmt = DatabaseConnection.get_Connection().createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT item1,item2,item3,item4 FROM recipes WHERE name =\""+name+"\"");
			ResultSet rs = stmt.executeQuery("SELECT * FROM recipes WHERE id=0");
			while(rs.next()) {
				for(int i=1;i<=4;i++) {
					checksums.add(rs.getString("item"+i).toString());
				}
			}
		return checksums;
	}
	
	
}
