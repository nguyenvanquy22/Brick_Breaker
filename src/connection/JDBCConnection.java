package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    static final String url = "jdbc:mysql://localhost:3306/brickbreakergame?useSSL=false";
    static final String user = "root";
    static final String password = "nvq211241738";
    
    public static Connection getJDBCConnection(){
            try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				return DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        return null;
    }
}
