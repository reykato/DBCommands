import java.sql.*;

public class DBCommands {
	
	public static void main(String[] args) throws Exception {
		
		String dbUrl = "jdbc:oracle:thin:@h3oracle.ad.psu.edu:1521/orclpdb.ad.psu.edu"; 
		String username = "tcl5238"; //psu username, exclude the @psu.edu
		String password = "tcl5238"; //oracle database password
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(dbUrl, username, password);
			if(conn != null) System.out.println("You are connected!");
		}
		catch (SQLException error) {
			error.printStackTrace();
		}

		switch (args[0]) {
			case "test":
				System.out.println(test() ? "Command Completed Successfully" : "Command Failed");
				break;
			case "addPerson": 
				java.sql.Date dateOfBirth = java.sql.Date.valueOf(args[5]);
				System.out.println(addPerson(Integer.valueOf(args[1]), args[2], args[3], args[4], dateOfBirth) ? "Command Completed Successfully" : "Command Failed");
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
		}
	}

	public static boolean addPerson(int SSN, String firstName, String lastName, String contactInfo, java.sql.Date dateOfBirth) {

		return false;
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

	public static boolean test() {
		return true;
	}
}


