package L3Capstone_Task8;

/**
 * Import relevant libraries
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.text.SimpleDateFormat;  
import java.util.Date;  

public class poisedPMSnew {//class open
	
	/**
	 * Main method takes in all user input and executes SQL commands accordingly
	 *
	 * SimpleDate Format class is instantiated and the desired date format is passed as  string to its constructor
	 * 
	 * The getConnection method of Java DriverManager class attempts to establish a connection to the database by using the given database url.
	 * 
	 * To create a direct line to the database for running queries- Allocate a Statement object inside the Connection using connection.createStatement().
	 * When you execute Statement objects, they generate ResultSet objects, which is a table of data representing a database result set.
	 * 
	 * The float.compare function call is used to compare two float values - compare total cost with amount paid to date
	 * 
	 * statement.close() and connection.close() - closes database connections explicitly to make sure that the code was able to close itself gracefully 
	 *  and to prevent any other objects from reusing the same connection after you are done with it.
	 *   @param args
			 */
			 
	public static void main(String[] args) {//main method open
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date date = new Date();  
		
		String currentDate = (formatter.format(date));
		
		System.out.println("\n****************Welcome to Poised Projects******************\n");
		
		//Call method to display menu to the user
		 // Each option will execute code based on the user input
		
		System.out.println(displayMenu());
		
		Scanner sc = new Scanner(System.in);
		String userInput = sc.nextLine();
		
		try
		{
			//Connect to the poisedPMS database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedPMS?useSSL=false", "userother", "swordFISH");
			
			// Create a direct line to the database for running queries 
			 Statement statement = connection.createStatement();
			 ResultSet results;
			 int rowsAffected;
			 
			//Codes to execute based on user selection
			
			 //If the user select "1" - prompt user for input to add a NEW Project to the database
		
			 
			 if(userInput.equals("1")) 
			 {
				 	Scanner in1 = new Scanner(System.in);
					System.out.println("Project number: ");
					String project_number = in1.nextLine();
					 
					Scanner in2 = new Scanner(System.in);
					System.out.println("Project name: ");
					String project_name = in2.nextLine();
					 
					Scanner in3 = new Scanner(System.in);
					System.out.println("Building type: ");
					String building_type = in3.nextLine();
					
					Scanner in4 = new Scanner(System.in);
					System.out.println("Physical address: ");
					String physical_address = in4.nextLine();
					
					Scanner in5 = new Scanner(System.in);
					System.out.println("erf Number: ");
					String erf_number = in4.nextLine();
					
					Scanner in6 = new Scanner(System.in);
					System.out.println("Total cost of the project: ");
					float total_cost = in6.nextFloat();
					
					Scanner in7 = new Scanner(System.in);
					System.out.println("Amount paid to date: ");
					float paid_to_date = in7.nextFloat();
					
					Scanner in8 = new Scanner(System.in);
					System.out.println("Project deadline: ");
					String project_deadline = in8.nextLine();
					
					Scanner in9 = new Scanner(System.in);
					System.out.println("Name of architect: ");
					String architect = in9.nextLine();
					
					Scanner in10 = new Scanner(System.in);
					System.out.println("Name of contractor: ");
					String contractor = in10.nextLine();
					
					Scanner in11 = new Scanner(System.in);
					System.out.println("Name of client: ");
					String client = in11.nextLine();
					
					Scanner in12 = new Scanner(System.in);
					System.out.println("Project status complete?: ");
					String status_complete = in12.nextLine();
					
					//Insert user input as values into project table using INSERT sql command
					rowsAffected = statement.executeUpdate("INSERT INTO project_details VALUES('"+project_number+"','"+project_name+"','"+building_type+"','"+physical_address+"','"+erf_number+"','"+total_cost+"','"+paid_to_date+"','"+project_deadline+"','"+architect+"','"+contractor+"','"+client+"','"+status_complete+"')");
					System.out.println("Query complete , " + rowsAffected + " rows added.");
					 
					printAllFromProjectsTable(statement);
					
			 }
			 //If the user selects "2" - To edit/update an existing project - Ask if they would like to finalize a project or extend the deadline date
			 else if(userInput.equals("2")) 
			 {
				 Scanner in13 = new Scanner(System.in);
				 System.out.println("Select one of the following options:\na - Finalize project and generate invoice\nb - Change project deadline date: ");
				 String userSelection = in13.nextLine();
				 
				 if(userSelection.equals("a")) {
					 Scanner in14 = new Scanner(System.in);
					 System.out.println("Enter project name: ");
					 String project_name = in14.nextLine();//Name of the project to be finalized
					 
					 //If user chooses to finalize an generate an invoice for the project-
					  //Ask user for the project name and use SELECT query to select the project from the project table
					 
					 String selectProjectName = "SELECT * FROM project_details WHERE project_name = '"+project_name+"'" ;
					 
				
					 // to execute query
					 results = statement.executeQuery(selectProjectName);
					 
					 if(results.next())
					 {
						 System.out.println("Project Number: "+results.getString("project_number")+ "\nProject Name: "+results.getString("project_name")+ "\nBuilding type: "+results.getString("building_type")+"\nPhysical Address: "+results.getString("physical_address")+"\nERF Number: "+results.getString("erf_number")+"\nProject Cost: "+results.getFloat("total_cost")+"\nAmount paid to date: "+results.getFloat("paid_to_date")+"\nProject Deadline: "+results.getString("project_deadline")+"\nArchitect: "+results.getString("architect")+"\nContractor: "+results.getString("contractor")+"\nClient: "+results.getString("client")+"\nProject complete?: "+results.getString("status_complete"));
						 
						 float projectTotal = results.getFloat("total_cost");//get project cost from project table
						 float projectPaidToDate = results.getFloat("paid_to_date");//get amount paid to date from projects table
						 
						  //If the result of the comparison = 0 then the client has no outstanding balance and an invoice will not be generated
					        if (Float.compare(projectPaidToDate, projectTotal) == 0) { 
					  
					            System.out.println("The client has no outstanding balance to pay (project is paid up)\nThis project will be marked as complete!\n");
					            rowsAffected = statement.executeUpdate("UPDATE project_details SET status_complete = yes WHERE project_name = '"+project_name+"'");//Change status_complete to yes
								System.out.println("Query complete, " + rowsAffected + " rows updated");
								  
								 printAllFromProjectsTable(statement);
					        } 
					        //If the result of the comparison < 0 then the client needs to be invoiced.
					        //Prompt user to enter the name of the client
					        //use SELECT query to select the client details from the person table
					       
					        else if (Float.compare(projectPaidToDate, projectTotal) < 0) { 
					  
					            System.out.println("\nThis project has not been paid in full");
					            
					            float clientBalance = projectTotal - projectPaidToDate;//Calculate outstanding balance
					            
					            System.out.println("\nGenerating invoice...\n");
					            Scanner InputClientName = new Scanner(System.in);
					            System.out.println("Enter client name: ");
								String person_name = InputClientName.nextLine();//Name of the client on the project
								String selectClient= "SELECT * FROM person_details WHERE person_name = '"+person_name+"'" ;
								results = statement.executeQuery(selectClient);
								if(results.next())
								 {
									 System.out.println("\nINVOICE TO:\n"+ "Type: "+results.getString("person_type")+ "\nName: "+results.getString("person_name")+ "\nEmail: "+results.getString("person_email")+"\nTel: "+results.getString("person_tel"));
								 }
					            
					            System.out.println("\nInvoice Date: " + currentDate + "\nPaid To Date: R" + projectPaidToDate + "\nAMOUNT DUE: R"+ clientBalance);
					            
					            System.out.println("\n*****Thank you for using Poised Projects*****");
					        } 
				 }
				 }
				 //If user chooses to change the deadline date of the project
				  //Ask for the new date and use UPDATE query to update the deadline date
	
				 else if(userSelection.equals("b")) {
					 
					 Scanner in15 = new Scanner(System.in);
					 System.out.println("Enter project name: ");
					 String project_name = in15.nextLine();
					 
					 
					 Scanner in16 = new Scanner(System.in);
					 System.out.println("Enter the new deadline date: ");
					 String project_deadline = in16.nextLine();
					 
					 rowsAffected = statement.executeUpdate("UPDATE project_details SET project_deadline = '"+project_deadline+"' WHERE project_name = '"+project_name+"'");
						System.out.println("Query complete, " + rowsAffected + " rows updated");
						  
						 printAllFromProjectsTable(statement);
				 }
					
	
						
				 }
			 //If the user select "3" - prompt user for input to add a NEW Person to the database
			 //Insert user input as values into person table using INSERT sql command
			 
			 else if(userInput.equals("3")) 
			 {
				 Scanner in16 = new Scanner(System.in);
					System.out.println("Type (architect, contractor or client?): ");
					String person_type = in16.nextLine();
					 
					Scanner in17 = new Scanner(System.in);
					System.out.println("Name: ");
					String person_name = in17.nextLine();
					 
					Scanner in18 = new Scanner(System.in);
					System.out.println("Email: ");
					String person_email = in18.nextLine();
					
					Scanner in19 = new Scanner(System.in);
					System.out.println("Contact number: ");
					String person_tel = in19.nextLine();
					
					rowsAffected = statement.executeUpdate("INSERT INTO person_details VALUES('"+person_type+"', '"+person_name+"','"+person_email+"', '"+person_tel+"')");
					System.out.println("Query complete , " + rowsAffected + " rows added.");
					 
					printAllFromPersonsTable(statement); 
			 }
			 //If the user selects "4" - To edit/update an existing person,
			 // Ask if which person details they would like to update (email or telephone)
			 //use UPDATE query to update the email or telephone details
			 
			 else if(userInput.equals("4"))
			 {
				 Scanner in20 = new Scanner(System.in);
				 System.out.println("Select one of the following options:\na - Change email address\nb - Change contact number: ");
				 String userSelection = in20.nextLine();
				 
				 if(userSelection.equals("a")) {
					 
					 Scanner in21 = new Scanner(System.in);
					 System.out.println("Enter person name: ");
					 String person_name = in21.nextLine();
					 
					 
					 Scanner in22 = new Scanner(System.in);
					 System.out.println("Enter the new Email: ");
					 String person_email = in22.nextLine();
					 
					 rowsAffected = statement.executeUpdate("UPDATE person_details SET person_email = '"+person_email+"' WHERE person_name = '"+person_name+"'");
						System.out.println("Query complete, " + rowsAffected + " rows updated");
						  
						 printAllFromPersonsTable(statement);
				 }
				 else if(userSelection.equals("b")) {
					 
					 Scanner in23 = new Scanner(System.in);
					 System.out.println("Enter person name: ");
					 String person_name = in23.nextLine();
					 
					 
					 Scanner in24 = new Scanner(System.in);
					 System.out.println("Enter the new contact number: ");
					 String person_tel = in24.nextLine();
					 
					 rowsAffected = statement.executeUpdate("UPDATE person_details SET person_tel = '"+person_tel+"' WHERE person_name = '"+person_name+"'");
						System.out.println("Query complete, " + rowsAffected + " rows updated");
						  
						 printAllFromPersonsTable(statement);
				 }
			 }
			 // If user selects "5" to view projects
			 //Ask if they want to view all projects/Select and view a specific project/view incomplete projects/view overdue projects
			 else if(userInput.equals("5"))
			 {
				 Scanner in25 = new Scanner(System.in);
				 System.out.println("Select one of the following options:\na - View all projects\nb - Select and view a project\nc - Check incomplete projects\nd - Check overdue projects: ");
				 String userSelection = in25.nextLine();
				 
				 if(userSelection.equals("a")) {
					 
					 //If user wants to view all projects - Call printAllFromProjectsTable method to view all projects in the database
					
					 System.out.println("All poisedPMS projects:\n");
					 printAllFromProjectsTable(statement);
				 }
				 
				 //If user chooses to view a specific project
				 //Request the project name
				 //use SELECT query to select the project details from the project table
				
				 else if(userSelection.equals("b")) {
					 
					 Scanner in26 = new Scanner(System.in);
					 System.out.println("Enter the project name: ");
					 String project_name = in26.nextLine();
					 
					 //query to select project name
					 String selectProjectName = "SELECT * FROM project_details WHERE project_name = '"+project_name+"'" ;
					 
					 // to execute query
					 results = statement.executeQuery(selectProjectName);
					 
					 if(results.next()){
						 
						 System.out.println("Project Number: "+results.getString("project_number")+"\nProject Name: "+results.getString("project_name")+"\nBuilding type: "+results.getString("building_type")+"\nPhysical Address: "+results.getString("physical_address")+"\nERF Number: "+results.getString("erf_number")+"\nTotal Cost: "+results.getInt("total_cost")+"\nAmount paid to date: "+results.getInt("paid_to_date")+"\nProject Deadline: "+results.getString("project_deadline")+"\nArchitect: "+results.getString("architect")+"\nContractor: "+results.getString("contractor")+"\nClient: "+results.getString("client")+"\nProject complete?: "+results.getString("status_complete"));
						 }
					 else {
					 System.out.println("Record not found");
				 }

			 }
				 
			else if(userSelection.equals("c")) {
				
				//If user chooses to view projects that are still incomplete,
				//Use SELECT and WHERE query to extract all projects with a 'no' value in the status_complete column
		
				System.out.println("Fetching projects that are still to be completed...\n");
				String incomplete = "SELECT * FROM project_details WHERE status_complete LIKE '%no%' ";
			    results = statement.executeQuery(incomplete);
			 
			}
			
			//If user chooses to view projects that are overdue,
			//Use SELECT and WHERE query to extract all projects with a deadline date value older than the current date value in the project_deadline column
			else if(userSelection.equals("d")) {
				System.out.println("Fetching projects that are overdue...\n");
				String overdue = "SELECT * FROM project_details WHERE project_deadline < '"+currentDate+"'";
			    results = statement.executeQuery(overdue);
			}
				 
				 
				 }
			 else if(userInput.equals("6"))
			 {
				 Scanner in27 = new Scanner(System.in);
				 System.out.println("Select one of the following options:\na - View all persons\nb - Select and view a person ");
				 String userSelection = in27.nextLine();
				 
				 if(userSelection.equals("a")) {
					 System.out.println("All persons saved in poisedPMS:\n");
					 printAllFromPersonsTable(statement);
				 }
				 else if(userSelection.equals("b")) {
					 Scanner in28 = new Scanner(System.in);
					 System.out.println("Enter the person name: ");
					 String person_name = in27.nextLine();
					 
					 //query to select project name
					 String selectPersonName = "SELECT * FROM person_details WHERE person_name = '"+person_name+"'" ;
					 
					 // to execute query
					 results = statement.executeQuery(selectPersonName);
					 
					 if(results.next()){
						 
						 System.out.println("\nType: "+results.getString("person_type")+"\nName: "+results.getString("person_name")+"\nEmail: "+results.getString("person_email")+ "\nTel: "+results.getString("person_tel"));
						 }
					 else {
					 System.out.println("Record not found");
				 }
				 }
			 }
			
		
			 statement.close();
			 connection.close();
			 
		}catch(SQLException e) {
					e.printStackTrace();
	}
	}
			 
	
	// Method() to display the menu to user
			public static String displayMenu() 
			{
				
				String menu = "Please select one of the options below, e.g.1,2,3,4,5,6: "
							+ "\n1 - Add/create a new project "
							+ "\n2 - Update/edit and existing project "
							+ "\n3 - Add/create a new person (eg architect, contractor or customer) "
							+ "\n4 - Update/edit an existing person's details "
							+ "\n5 - View project details "
							+ "\n6 - View person details ";
					
					return menu;
				}
			
			/**
			 * Method printing all values in all rows
			 * @param statement
			 * @throws SQLException
			 * Takes a statement to try and avoid spreading DB access too far
			 */
			 
			
			public static void printAllFromProjectsTable(Statement statement) throws SQLException{
				
				ResultSet results = statement.executeQuery("SELECT project_number, project_name, building_type, physical_address, erf_number, total_cost, paid_to_date, project_deadline, architect, contractor, client, status_complete FROM project_details");
				while (results.next()) {
					System.out.println(results.getString("project_number") + ", " 
				+ results.getString("project_name") 
				+ ", " + results.getString("building_type") 
				+ ", " + results.getString("physical_address")
				+ ", " + results.getString("erf_number")
				+ ", " + results.getString("total_cost")
				+ ", " + results.getString("paid_to_date")
				+ ", " + results.getString("project_deadline")
				+ ", " + results.getString("architect")
				+ ", " + results.getString("contractor")
				+ ", " + results.getString("client")
				+ ", " + results.getString("status_complete"));	

	}
			}
			/**
			 * Method printing all values in all rows
			 * @param statement
			 * @throws SQLException
			 * Takes a statement to try and avoid spreading DB access too far
			 */
			
			public static void printAllFromPersonsTable(Statement statement) throws SQLException{
				
				ResultSet results = statement.executeQuery("SELECT person_type, person_name, person_email, person_tel FROM person_details");
				while (results.next()) {
					System.out.println(results.getString("person_type") + ", " 
				+ results.getString("person_name") 
				+ ", " + results.getString("person_email") 
				+ ", " + results.getString("person_tel"));
					}
			}
			
			
			
}


	
