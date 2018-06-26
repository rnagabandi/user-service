package com.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.h2.tools.DeleteDbFiles;

import com.service.pojo.Address;
import com.service.pojo.UserDetails;

/**
 * @author raghavender.n
 *
 */
public class DatabaseManager {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/test";
	private static final String DB_USER = "sa";
	private static final String DB_PASSWORD = "sa";
	private static final String TABLE_NAME="user_details";
	private static final String ADDRESS_TABLE_NAME="user_address";

	/*
	 * Below code will be executed on page class load into JVM
	 */
	static {

		String dropUserAddressSchema="drop table "+ADDRESS_TABLE_NAME;
		String dropUserSchema="drop table "+TABLE_NAME;
		String userSchema = "create table "+TABLE_NAME+" ( ID long, firstName varchar(255) NOT NULL, lastName varchar(255), "
				+ "	email varchar(255) , createdOn timestamp default now(), PRIMARY KEY (email ) );";
		
		String addressSchema = "create table "+ADDRESS_TABLE_NAME+" ( ID long, city varchar(255) NOT NULL, country varchar(255), "
				+ "	type varchar(255), zipcode Varchar(5000), createdOn timestamp default now());";

		try {
			//createSchema(dropUserAddressSchema,dropUserSchema);
			//Thread.sleep(2000);
			createSchema(userSchema);
			createSchema(addressSchema);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * @param schemas
	 * @throws SQLException
	 * It is used to craete the tables on server starts if it does not exist
	 */
	public static void createSchema(String... schemas) throws SQLException {

		Connection connection = null;
		try {
			PreparedStatement createPreparedStatement = null;
			Class.forName(DB_DRIVER);

			connection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			for (int i = 0; i < schemas.length; i++) {
				createPreparedStatement = connection.prepareStatement(schemas[i]);
				createPreparedStatement.addBatch();
			}
			
			createPreparedStatement.executeBatch();
			createPreparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

	}

	/**
	 * @param user
	 * @return
	 * @throws SQLException
	 * It is used to insert the user and address in corresponding tables
	 */
	public boolean insertUser(UserDetails user) throws SQLException {

		Connection connection = new DatabaseManager().getDBConnection();
		PreparedStatement insertPreparedStatement = null;
		long id=System.currentTimeMillis();

		String insertUserQuery = "INSERT INTO "+TABLE_NAME
				+ "(ID, firstName, lastName, email) values" + "("
				+ id + ",?,?,?)";
		String insertUserAddressQuery = "INSERT INTO "+ADDRESS_TABLE_NAME
				+ "(ID, city, country, type, zipcode) values" + "("
				+ id + ",?,?,?,?)";
		try {
			connection.setAutoCommit(true);

			insertPreparedStatement = connection.prepareStatement(insertUserQuery);
			insertPreparedStatement.setString(1, user.getFirstName());
			insertPreparedStatement.setString(2, user.getLastName());
			insertPreparedStatement.setString(3, user.getEmail());
			
			insertPreparedStatement.execute();
			
			
			for (int i = 0; i < user.getAddress().size(); i++) {
				insertPreparedStatement = connection.prepareStatement(insertUserAddressQuery);
				insertPreparedStatement.setString(1, user.getAddress().get(i).getCity());
				insertPreparedStatement.setString(2, user.getAddress().get(i).getCountry());
				insertPreparedStatement.setString(3, user.getAddress().get(i).getType());
				insertPreparedStatement.setString(4, user.getAddress().get(i).getZipcode());
				
				insertPreparedStatement.execute();
			}
			
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
	
	/**
	 * @param user
	 * @return
	 * @throws SQLException
	 * It is used to update the user
	 */
	public boolean updateUser(UserDetails user) throws SQLException {

		Connection connection = new DatabaseManager().getDBConnection();
		PreparedStatement insertPreparedStatement = null;

		String InsertQuery = "UPDATE "+TABLE_NAME
				+ " SET firstName = ? , lastName = ?  where email = ?";
		try {
			connection.setAutoCommit(true);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, user.getFirstName());
			insertPreparedStatement.setString(2, user.getLastName());
			insertPreparedStatement.setString(3, user.getEmail());
			
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

	/**
	 * @param firstName
	 * @return
	 * @throws SQLException
	 * It is used to get the user
	 */
	public UserDetails getUser(String firstName) throws SQLException {

		UserDetails user = new UserDetails();
		Connection connection = new DatabaseManager().getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String selectUserQuery = "select * from "+TABLE_NAME+"  where email = '"
				+ firstName +"'";
		String selectAddressQuery = "select * from "+ADDRESS_TABLE_NAME+"  where id = ";
				
		try {
			connection.setAutoCommit(false);
			
			long id=0;
			selectPreparedStatement = connection.prepareStatement(selectUserQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			System.out
					.println("H2 Database inserted through PreparedStatement");
			while (rs.next()) {
				id = rs.getLong("id");
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setEmail(rs.getString("email"));
			}
			
			selectPreparedStatement = connection.prepareStatement(selectAddressQuery+id);
			ResultSet rs1 = selectPreparedStatement.executeQuery();
			System.out
					.println("H2 Database inserted through PreparedStatement");
			ArrayList<Address> addresList = new ArrayList<Address>();
			while (rs1.next()) {
				Address address = new Address();
				address.setCity(rs1.getString("city"));
				address.setCountry(rs1.getString("country"));
				address.setType(rs1.getString("type"));
				address.setZipcode(rs1.getString("zipcode"));
				addresList.add(address);
			}
			user.setAddress(addresList);
			return user;
			
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
	
	/**
	 * @param email
	 * @return
	 * @throws SQLException
	 * It is used to delet teh user
	 */
	public boolean deleteUser(String email) throws SQLException {

		UserDetails user = new UserDetails();
		Connection connection = new DatabaseManager().getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		String SelectQuery = "delete from "+TABLE_NAME+"  where email = '"
				+ email +"'";
			
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
