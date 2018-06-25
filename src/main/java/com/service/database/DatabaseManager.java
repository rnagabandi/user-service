package com.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.DeleteDbFiles;

import com.service.pojo.Address;
import com.service.pojo.UserDetails;

public class DatabaseManager {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/test";
	private static final String DB_USER = "sa";
	private static final String DB_PASSWORD = "sa";
	private static final String TABLE_NAME="UserInfo";

	static {

		String userSchema = "create table "+TABLE_NAME+" ( ID long, firstName varchar(255) NOT NULL, lastName varchar(255), "
				+ "	email varchar(255), address Varchar(5000), createdOn timestamp default now(), PRIMARY KEY (email ) );";

		try {
			createSchema(userSchema);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createSchema(String schema) throws SQLException {

		Connection connection = null;
		try {
			PreparedStatement createPreparedStatement = null;
			Class.forName(DB_DRIVER);

			connection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			createPreparedStatement = connection.prepareStatement(schema);
			createPreparedStatement.execute();
			createPreparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

	}

	public boolean insertUser(UserDetails user) throws SQLException {

		Connection connection = new DatabaseManager().getDBConnection();
		PreparedStatement insertPreparedStatement = null;

		String InsertQuery = "INSERT INTO "+TABLE_NAME
				+ "(ID, firstName, lastName, email, address) values" + "("
				+ System.currentTimeMillis() + ",?,?,?,?)";
		try {
			connection.setAutoCommit(true);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, user.getFirstName());
			insertPreparedStatement.setString(2, user.getLastName());
			insertPreparedStatement.setString(3, user.getEmail());
			insertPreparedStatement.setString(4, user.getAddress().toString());

			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}

		return true;

	}
	
	public boolean updateUser(UserDetails user) throws SQLException {

		Connection connection = new DatabaseManager().getDBConnection();
		PreparedStatement insertPreparedStatement = null;

		String InsertQuery = "UPDATE "+TABLE_NAME
				+ " SET firstName = ? , lastName = ? , address = ? where email = ?";
		try {
			connection.setAutoCommit(true);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, user.getFirstName());
			insertPreparedStatement.setString(2, user.getLastName());
			insertPreparedStatement.setString(3, user.getAddress().toString());
			insertPreparedStatement.setString(4, user.getEmail());

			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			connection.close();
		}

		return true;

	}

	public UserDetails getUser(String firstName) throws SQLException {

		UserDetails user = new UserDetails();
		Connection connection = new DatabaseManager().getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = "select * from "+TABLE_NAME+"  where email = '"
				+ firstName +"'";
		
		/*String SelectQuery = "select * from user";*/
		
		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			System.out
					.println("H2 Database inserted through PreparedStatement");
			while (rs.next()) {
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setEmail(rs.getString("email"));
				user.setAddress(null);
				return user;
			}
			
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			selectPreparedStatement.close();
			connection.commit();
			connection.close();
		}
		
		return user;

	}
	
	public boolean deleteUser(String email) throws SQLException {

		UserDetails user = new UserDetails();
		Connection connection = new DatabaseManager().getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = "delete from "+TABLE_NAME+"  where email = '"
				+ email +"'";
		
		/*String SelectQuery = "select * from user";*/
		
		try {
			connection.setAutoCommit(true);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			return selectPreparedStatement.executeUpdate() > 0 ? true: false;
			
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			selectPreparedStatement.close();
			connection.commit();
			connection.close();
		}
		
		return false;

	}

	private Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

}
