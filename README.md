# poisedPMS_Database
The code enables a connection to a databse containing all project details (Project names, project number, type of building being designed and constructed, project cost, architect details, contractor details, client details, project deadline etc)

#How the code works:
- Main method takes in all user input and executes SQL commands accordingly.
- The class contains a method to display menu to the user which is called in the main.
- The relevant sql commands are executed based on user input or selection from the main menu.
     - To insert new values into a table in the database:
     ```rowsAffected = statement.executeUpdate("INSERT INTO project_details VALUES('"+project_number+"','"+project_name+"','"+building_type+"','"+physical_address+"','"+erf_number+"','"+total_cost+"','"+paid_to_date+"','"+project_deadline+"','"+architect+"','"+contractor+"','"+client+"','"+status_complete+"')");
					System.out.println("Query complete , " + rowsAffected + " rows added.");
``

     - To update values in a table:
     ```rowsAffected = statement.executeUpdate("UPDATE project_details SET project_deadline = '"+project_deadline+"' WHERE project_name = '"+project_name+"'");
						System.out.println("Query complete, " + rowsAffected + " rows updated");
						  
``` 
- There are also methods to extract and display all rows in the tables. These methods take a statement to try and avoid spreading DB access too far: 
```
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
			}```
      
      ```public static void printAllFromPersonsTable(Statement statement) throws SQLException{
				
				ResultSet results = statement.executeQuery("SELECT person_type, person_name, person_email, person_tel FROM person_details");
				while (results.next()) {
					System.out.println(results.getString("person_type") + ", " 
				+ results.getString("person_name") 
				+ ", " + results.getString("person_email") 
				+ ", " + results.getString("person_tel"));
					}
			}```
      
      
