package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import module.DataGame;

public class GameDao {
	public List<DataGame> getAllDataGame(){
		List<DataGame> data = new ArrayList<>();
		Connection con = JDBCConnection.getJDBCConnection();
		if (con != null) System.out.println("Connect to game successfull");
		
		String sql = "Select * from DataGame";
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				DataGame dataGame = new DataGame();
				
				dataGame.setTimePlayGame(rs.getString("TimePlayGame"));
				dataGame.setLevelGame(rs.getInt("LevelGame"));
				dataGame.setScore(rs.getInt("Score"));
				dataGame.setResult(rs.getInt("Result"));
				
				data.add(dataGame);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
