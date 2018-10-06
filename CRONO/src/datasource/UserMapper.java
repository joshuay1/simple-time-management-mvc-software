package datasource;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Employee;
import domain.Roster;
import domain.User;

public class UserMapper {
	private final static String findStatementString =
	         "SELECT * " +
	         "  from APP.users "+
	         "  WHERE userID = !0!";
	
	 private static final String findAllTimesStatement =
	         "SELECT * from APP.users";
	
	public static void getAllUsers() throws SQLException {
			PreparedStatement sqlPrepared = DBConnection.prepare(findAllTimesStatement);
			ResultSet rs = sqlPrepared.executeQuery();
			List<Employee> result = new ArrayList<Employee>();
			while (rs.next()) {
				int id = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				String username = rs.getString(5);
				String password = rs.getString(6);
				int role = rs.getInt(7);
	
				Roster.addUsers(id, firstName, lastName, email, username, password,role);
			}

	}
	
	
	public static User getUser(int id) throws SQLException {
			String str = stringSplit(findStatementString, ""+id, 0);
			PreparedStatement sqlPrepared = DBConnection.prepare(str);
			ResultSet rs = sqlPrepared.executeQuery();
			rs.next();
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			String email = rs.getString(4);
			String userName = rs.getString(5);
			String password = rs.getString(6);
			int role = rs.getInt(7);
			User user =new Employee(id, firstName, lastName, email, userName, password,role);
			return user;
	}
	
	
	
	public static int getUserID(String userName) {
		String sql = "Select * "
				+ "From App.users "
				+ "WHERE username = '"+userName+"'";
		PreparedStatement sqlPrepared;
		try {
			sqlPrepared = DBConnection.prepare(sql);
			ResultSet rs = sqlPrepared.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("Errror with SQL");
			e.printStackTrace();
		}
		
		return -1;
	}
	
	
	
	
	public static void update(int userID, String firstName, String lastName, String email, String username, String password) throws SQLException {
		String sql = "UPDATE APP.users SET firstName = '"+ firstName+ "', lastName = '"+ lastName + "' , email = '" + email + "', username = '" + username + "' , password = '" + password + "' WHERE userID = "+ userID + "";
		PreparedStatement sqlPrepared = DBConnection.prepare(sql);
		int rs = sqlPrepared.executeUpdate();
	
	}
	
	public static int checkLogin(String username, String password) throws SQLException  {
		try {
			PreparedStatement stmt = DBConnection.prepare("SELECT userID " +
			         "  from APP.users " +
			         "  WHERE username ='"+username+"' AND password ='"+password+"'");
			ResultSet rs = stmt.executeQuery();
			rs.next();			
			return rs.getInt(1);
		}
		catch(SQLException e){
			return -1;
			
		}
	}
	
	private static String stringSplit(String stm, String input, int place) {
		String str = stm.replaceAll("!"+place+"!", input);
		return str;
	}
	public static void delete(int userID,String firstName, String lastName, String email, String username, String password) throws SQLException {
		String sql = "DELETE FROM APP.users WHERE userID = "+userID;
		PreparedStatement sqlPrepared = DBConnection.prepare(sql);
		int rs = sqlPrepared.executeUpdate();
	
	}
	
	public static void create(int userID,String firstName, String lastName, String email, String username, String password, int role) throws SQLException {
		System.out.println(" VALUES ("+userID + ",'"+firstName+"','"+lastName+"','"+email+"','"+username+"','"+password+"',"+role+")");
		String sql = "INSERT INTO APP.users (userID, firstName, lastName, email, username, password, role)" +
					" VALUES ("+userID + ",'"+firstName+"','"+lastName+"','"+email+"','"+username+"','"+password+"',"+role+")";
		PreparedStatement sqlPrepared = DBConnection.prepare(sql);
		int rs = sqlPrepared.executeUpdate();
		
	
	
	}
	
	public static String getUserPermission(String role) {
		String sql = "Select permission "
				+ "From App.roles_permission "
				+ "WHERE role = '"+role+"'";
		PreparedStatement sqlPrepared;
		try {
			sqlPrepared = DBConnection.prepare(sql);
			ResultSet rs = sqlPrepared.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch (SQLException e) {
			System.out.println("Errror with SQL");
			e.printStackTrace();
		}
		
		return "Error";
	}
	
	

}


