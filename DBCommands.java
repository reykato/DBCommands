import java.sql.*;

public class DBCommands {
	
	public static void main(String[] args) throws Exception, SQLException {
		
		String dbUrl = "jdbc:oracle:thin:@h3oracle.ad.psu.edu:1521/orclpdb.ad.psu.edu"; 
		String username = "tcl5238"; //psu username, exclude the @psu.edu
		String password = "tcl5238"; //oracle database password
		

		try {
			// Class.forName("oracle.jdbc.driver.OracleDriver");
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
				case "editCustomer":
					java.sql.Date newDateOfBirth = java.sql.Date.valueOf(args[6]);
					System.out.println(editCustomer(conn, Integer.valueOf(args[1]), Integer.valueOf(args[2]), args[3], args[4], args[5], newDateOfBirth) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "editPolicy":
					java.sql.Date newStartDate = java.sql.Date.valueOf(args[5]);
					java.sql.Date newEndDate = java.sql.Date.valueOf(args[6]);
					System.out.println(editPolicy(conn, Integer.valueOf(args[1]), Integer.valueOf(args[2]), args[3], Integer.valueOf(args[4]),
						newStartDate, newEndDate, args[7], args[8]) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "removeCustomer":
					System.out.println(removeCustomer(conn, Integer.valueOf(args[1])) ? "Command Completed Successfully" : "Command Failed");
					break;
				case "removePolicy":
					System.out.println(removePolicy(conn, Integer.valueOf(args[1])) ? "Command Completed Successfully" : "Command Failed");
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
		PreparedStatement stmt = conn.prepareStatement(sql);

		// Set the parameter values for the PreparedStatement object
		stmt.setInt(1, SSN);
		stmt.setString(2, firstName);
		stmt.setString(3, lastName);
		stmt.setString(4, contactInfo);
		stmt.setDate(5, dateOfBirth);

		// Execute the query
		int rowsInserted = stmt.executeUpdate();
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

	public static boolean editCustomer(Connection conn, int SSN, int newSSN, String firstName, String lastName, String contactInfo, java.sql.Date dateOfBirth) throws SQLException {
		String sql = "UPDATE customer SET ssn = ?, firstName = ?, lastName = ?, contactInfo = ?, dateOfBirth = ? WHERE ssn = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, newSSN);
		stmt.setString(2, firstName);
		stmt.setString(3, lastName);
		stmt.setString(4, contactInfo);
		stmt.setDate(5, dateOfBirth);
		stmt.setInt(6, SSN);
		return stmt.executeUpdate() != 0;
	}

	public static boolean editPolicy(Connection conn, int policyID, int newPolicyID, String coverage, int monthlyPayment, java.sql.Date startDate, java.sql.Date endDate, String owner, String policyType) throws SQLException {
		String sql = "UPDATE customer SET policy_ID = ?, coverage = ?, monthly_payment = ?, start_date = ?, end_date = ?, owner = ?, policy_type = ? WHERE policyID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, newPolicyID);
		stmt.setString(2, coverage);
		stmt.setInt(3, monthlyPayment);
		stmt.setDate(4, startDate);
		stmt.setDate(5, endDate);
		stmt.setString(6, owner);
		stmt.setString(7, policyType);
		stmt.setInt(8, policyID);
		return stmt.executeUpdate() != 0;
	}

	public static boolean removeCustomer(Connection conn, int SSN) throws SQLException {
		String sql = "DELETE FROM customer WHERE ssn = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, SSN);
		System.out.println("about to execute removeCustomer");
		return stmt.executeUpdate() != 0;
	}

	public static boolean removePolicy(Connection conn, int policyID) throws SQLException {
		String sql = "DELETE FROM policy WHERE policy_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, policyID);
		return stmt.executeUpdate() != 0;
	}

	public static String listPeople(Connection conn) throws SQLException {
		String sql = "select * from customer";
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery(sql);
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


