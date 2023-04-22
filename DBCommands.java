import java.util.ArrayList;
import java.util.Arrays;
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
				case "browsePolicy":
					browsePolicy(conn);
					break;
				case "searchCustomer":
					searchCustomer(conn, args);
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

	private static String formatPerson(ResultSet result) throws SQLException {
		return String.format(
			"SSN: %d\nName: %s %s\nContact: %s\nDOB: %s",
			result.getInt("ssn"), result.getString("firstName"), result.getString("lastName"),
			result.getString("contactInfo"), result.getDate("dateofbirth")
		);
	}

	public static List<String> listPeople(Connection conn) throws SQLException {
		List<String> people = new ArrayList<String>();
		String sql = "select * from customer";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			people.add(formatPerson(result));
		}
		return people;
	}

	public static void browsePolicy(Connection conn) throws SQLException {
		for (String policy : listPolicies(conn)) {
			System.out.println(policy);
			System.out.println();
		}
	}

	private static String formatPolicy(ResultSet result) throws SQLException {
		return String.format(
			"Policy id: %d\nOwner SSN:%d\nCovered from %s to %s\nCoverage: %s\nMonthly payment: $%d\nPolicy type: %s",
			result.getInt("policy_id"), result.getInt("owner"),
			result.getDate("start_date"), result.getDate("end_date"),
			result.getString("coverage"), result.getInt("monthly_payment"),
			getPolicyType(result)
		);
	}

	private static String getPolicyType(ResultSet result) throws SQLException {
		String[] parts = result.getString("policy_type").split("_");
		return parts[0];
	}

	public static List<String> listPolicies(Connection conn) throws SQLException {
		List<String> policies = new ArrayList<String>();
		
		String home_sql = "select * from policy natural join home_info";
		ResultSet result = conn.createStatement().executeQuery(home_sql);
		while (result.next()) {
			policies.add(formatPolicy(result) + String.format(
				"\nAddress: %s\nArea: %d square feet\nBeds: %d Baths: %d\nPrice: $%d",
				result.getString("address"), result.getInt("area"),
				result.getInt("bedcount"), result.getInt("bathcount"),
				result.getInt("price")
			));
		}
		
		String car_sql = "select * from policy natural join car_info";
		result = conn.createStatement().executeQuery(car_sql);
		while (result.next()) {
			policies.add(formatPolicy(result) + String.format(
				"\nVIN: %s\nMileage: %d per year\nYear, make, and model: %d %s %s",
				result.getString("VIN"), result.getInt("mileageperyear"),
				result.getInt("year"), result.getString("make"), result.getString("model")
			));
		}
		
		String life_sql = "select * from policy natural join life_info natural left join (select life_id, LISTAGG(existing_conditions, ', ') as existing_conditions from conditions group by life_id)";
		result = conn.createStatement().executeQuery(life_sql);
		while (result.next()) {
			policies.add(formatPolicy(result) + String.format(
				"\nBenefits: %d\nExisting conditions: %s",
				result.getInt("benefits"), result.getString("existing_conditions")
			));
		}
		return policies;
	}

	public static void searchCustomer(Connection conn, String[] args) throws SQLException {
		String sql = "select * from customer where ";// where SSN like '%?%' and firstname like '%?%' and lastname like '%?%' and contact like '%?%'";

		sql += String.join(" ", Arrays.asList(args).subList(1, args.length));
		ResultSet result = conn.createStatement().executeQuery(sql);
		while (result.next()) {
			System.out.println(formatPerson(result));
			System.out.println();
		}
	}
}


