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
					System.out.println(addPolicy(conn, Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]),
						startDate, endDate, Integer.valueOf(args[6]), args[7]) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addCarInfo":
					System.out.println(addCarInfo(conn, args[1], Integer.valueOf(args[2]), args[3], args[4],
						Integer.valueOf(args[5]), Integer.valueOf(args[6])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addHomeInfo":
					System.out.println(addHomeInfo(conn, Integer.valueOf(args[1]), args[2], Integer.valueOf(args[3]), Integer.valueOf(args[4]),
						Integer.valueOf(args[5]), Integer.valueOf(args[6]), Integer.valueOf(args[7])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addLifeInfo":
					System.out.println(addLifeInfo(conn, Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "addConditions":
					System.out.println(addConditions(conn, Integer.valueOf(args[1]), args[2]) ? "Command Completed Successfully" : "Command Failed");
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

	public static boolean addPolicy(Connection conn, int policyID, String coverage, int monthlyPayment, java.sql.Date startDate, java.sql.Date endDate, int owner, String policyType) throws SQLException {

		String sql = "INSERT INTO policy (policy_ID, coverage, monthly_payment, start_date, end_date, owner, policy_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, policyID);
		statement.setString(2, coverage);
		statement.setInt(3, monthlyPayment);
		statement.setDate(4, startDate);
		statement.setDate(5, endDate);
		statement.setInt(6, owner);
		statement.setString(7, policyType);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static boolean addCarInfo(Connection conn, String VIN, int mileagePerYear, String make, String model, int year, int policyID) throws SQLException {

		String sql = "INSERT INTO car_info (vin, mileagePerYear, make, model, year, policy_ID) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setString(1, VIN);
		statement.setInt(2, mileagePerYear);
		statement.setString(3, make);
		statement.setString(4, model);
		statement.setInt(5, year);
		statement.setInt(6, policyID);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static boolean addHomeInfo(Connection conn, int homeID, String address, int area, int bedCount, int bathCount, int price, int policyID) throws SQLException {

		String sql = "INSERT INTO home_info (home_ID, address, area, bedCount, bathCount, price, policy_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, homeID);
		statement.setString(2, address);
		statement.setInt(3, area);
		statement.setInt(4, bedCount);
		statement.setInt(5, bathCount);
		statement.setInt(6, price);
		statement.setInt(7, policyID);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static boolean addLifeInfo(Connection conn, int policyID, int lifeID, int benefits) throws SQLException {
		
		String sql = "INSERT INTO life_info (policy_ID, life_ID, benefits) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, policyID);
		statement.setInt(2, lifeID);
		statement.setInt(3, benefits);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}
	
	public static boolean addConditions(Connection conn, int lifeID, String condition) throws SQLException {

		String sql = "INSERT INTO conditions (life_ID, existing_conditions) VALUES (?, ?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		
		// Set the parameter values for the PreparedStatement object
		statement.setInt(1, lifeID);
		statement.setString(2, condition);
		
		// Execute the query
		int rowsInserted = statement.executeUpdate();
		System.out.println(rowsInserted);
		return true;
	}

	public static String listPeople(Connection conn) throws SQLException {
		String sql = "select * from customer";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		System.out.println(result);
		while (result.next()) {
			System.out.println(result.getInt("ssn"));
		}
		return "";
	}

	public static String listPolicies() {

		return "";
	}
}


