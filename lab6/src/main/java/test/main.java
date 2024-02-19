package test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import Dao.CustomerDAO;
import model.Customer;
import model.Phonenumber;

public class main {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/lab6";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";
	static Connection dbConnection = null;
	static Statement statement = null;

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		 String insertTableSQL = "INSERT INTO CUSTOMER" + "(Name)" + "VALUES" +"('HelloHibernate')";
		dbConnection = getDBConnection();
		statement = (Statement) dbConnection.createStatement();
		 statement.executeUpdate(insertTableSQL);
		CustomerDAO c = new CustomerDAO() ;
		//c.addCustomer(new Customer("S1", new HashSet<Phonenumber>()));
		for(Customer cs : c.getAllCustomers())
		System.out.println(cs.getName());
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
}
