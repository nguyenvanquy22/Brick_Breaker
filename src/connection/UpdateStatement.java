package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import module.DataGame;

public class UpdateStatement {
	public static void insertDataGame(DataGame dataGame) {
		Connection con = JDBCConnection.getJDBCConnection();
		
		String sql = "Insert into DataGame values(?,?,?,?)";
		
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, dataGame.getTimePlayGame());
			pst.setInt(2, dataGame.getLevelGame());
			pst.setInt(3, dataGame.getScore());			
			pst.setInt(4, dataGame.getResult());
			
			pst.executeUpdate();
			
			System.out.println("Insert Success");
			
		} catch (SQLException e) {
			System.out.println("Insert Fail");
		}	
	}
}
