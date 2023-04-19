import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class DBCommands {
	
	public static void main(String[] args) throws Exception, SQLException {
		
		String dbUrl = "jdbc:oracle:thin:@h3oracle.ad.psu.edu:1521/orclpdb.ad.psu.edu"; 
		String username = "tcl5238"; //psu username, exclude the @psu.edu
		String password = "tcl5238"; //oracle database password
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(dbUrl, username, password);
			if(conn != null) System.out.println("You are connected!");

			conn.setAutoCommit(false);
   			
			switch (args[0]) {
				default:
					System.out.println("Invalid Command");
					break;
				case "addCustomer": 
					java.sql.Date dateOfBirth = java.sql.Date.valueOf(args[5]);
					System.out.println(addCustomer(conn, Integer.valueOf(args[1]), args[2], args[3], args[4], dateOfBirth) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addPolicy":
					java.sql.Date startDate = java.sql.Date.valueOf(args[4]);
					java.sql.Date endDate = java.sql.Date.valueOf(args[5]);
					System.out.println(addPolicy(Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]),
						startDate, endDate, args[6]) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addCarInfo":
					System.out.println(addCarInfo(Integer.valueOf(args[1]), Integer.valueOf(args[2]), args[3], args[4],
						Integer.valueOf(args[5]), Integer.valueOf(args[6])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addHomeInfo":
					System.out.println(addHomeInfo(Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]), Integer.valueOf(args[4]),
						Integer.valueOf(args[5]), Integer.valueOf(args[6]), Integer.valueOf(args[7])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addLifeInfo":
					System.out.println(addLifeInfo(Integer.valueOf(args[1]), Integer.valueOf(args[2]), args[3]) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addConditions":
					System.out.println(addConditions(Integer.valueOf(args[1]), args[2]) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "browseCustomer":
					browseCustomer(conn);
					break;
			}
			conn.close();
		}
		catch (SQLException error) {
			error.printStackTrace();
		}
	}

	public static boolean addCustomer(Connection conn, int SSN, String firstName, String lastName, String contactInfo, java.sql.Date dateOfBirth) throws SQLException {
		String sql = "INSERT INTO customer (ssn, firstName, lastName, contactInfo, dateOfBirth) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);

		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, SSN);
		statement.setString(2, firstName);
		statement.setString(3, lastName);
		statement.setString(4, contactInfo);
		statement.setDate(5, dateOfBirth);

		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static boolean addPolicy(int policyID, String coverage, int monthlyPayment, java.sql.Date startDate, java.sql.Date endDate, String owner) {

		return false;
	}

	public static boolean addCarInfo(int VIN, int mileagePerYear, String make, String model, int year, int policyID) {

		return false;
	}

	public static boolean addHomeInfo(int homeID, String address, int area, int bedCount, int bathCount, int price, int policyID) {

		return false;
	}

	public static boolean addLifeInfo(int policyID, int lifeID, String benefits) {

		return false;
	}
	
	public static boolean addConditions(int lifeID, String condition) {

		return false;
	}

	public static void browseCustomer(Connection conn) throws SQLException {
		for (String person : listPeople(conn)) {
			System.out.println(person);
			System.out.println();
		}
	}

	public static List<String> listPeople(Connection conn) throws SQLException {
		List<String> people = new ArrayList<String>();
		String sql = "select * from customer";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		System.out.println(result);
		while (result.next()) {
			people.add(String.format(
				"SSN: %d\nName: %s %s\nContact: %s\nDOB: %s",
				result.getInt("ssn"), result.getString("firstName"), result.getString("lastName"),
				result.getString("contactInfo"), result.getDate("dateofbirth").toString()
			));
		}
		return people;
	}

	public static String listPolicies() {

		return "";
	}
}


