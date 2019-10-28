import java.sql.*; 
import oracle.jdbc.*;
import java.math.*;
import java.io.*;
import java.awt.*;
import oracle.jdbc.pool.OracleDataSource;

public class studentRegistrationSystem{

	public static void main(String args[]) throws SQLException{

		// OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
  //    	ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
  //    	Connection conn = ds.getConnection("epalani1", "Hydropump16");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		String userPref = "";

		try{
			do{
			System.out.println("Please select an option:\n" +
							   "1. To view information\n" +
							   "2. To add a Student to the database\n" +
							   "3. List the class information for a particular student\n" +
							   "5. List the class information\n" +
							   "10. View Logs");
			option = Integer.parseInt(br.readLine());
			switch(option){
				case 1:
					displayInformation();
					break;
				case 2:
					System.out.println("Please enter the sid for the Student:");
					String sid_2 = br.readLine();
					System.out.println("Please enter the student's first name");
					String firstName = br.readLine();
					System.out.println("Please enter the student's last name");
					String lastName = br.readLine();
					System.out.println("Please enter the current status of the student");
					String status = br.readLine();
					System.out.println("Please enter the current gpa of the student");
					Double gpa = Double.parseDouble(br.readLine());
					System.out.println("Please enter the email address of the student");
					String email = br.readLine();
					addStudent(sid_2, firstName, lastName, status, gpa, email);
					break;
				case 3:
					System.out.println("Please enter the student's id to be searched");
					String sid_3 = br.readLine();
					getStudentClassInformation(sid_3);
					break;
				case 5:
					System.out.println("Please enter the class id to be searched");
					String classId = br.readLine();
					getClassInformation(classId);
					break;
				case 10:
					displayLogs();
					break;
			}
			System.out.println("Do you wish to continue?\n" +
							    "Enter Yes or No");
			userPref = br.readLine();		
			}while((userPref.toLowerCase()).equals("yes"));
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}

	public static void displayInformation(){
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please select an option:\n" + 
								"a. To view Students information\n" +
								"b. To view Courses information\n" +
								"c. To view Pre-requisites information\n" +
								"d. To view Classes information\n" +
								"e. To view Enrollments information");
			try{
				char dispOption = br.readLine().charAt(0);
				switch(dispOption){
				case 'a':
					System.out.println("Students Information:");
					displayStudentsTable();
					break;
				case 'b':
					System.out.println("Courses Information: \n");
					displayCoursesTable();
					break;
				case 'c':
					System.out.println("Pre-requisites Information: \n");
					displayPreReqsTable();
					break;
				case 'd':
					System.out.println("Classes Information: \n");
					displayClassesTable();
					break;
				case 'e':
					System.out.println("Enrollments Information: \n");
					displayEnrollmentsTable();
					break;

				}
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		}

		public static void displayStudentsTable(){
			try{
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        //Prepare to call stored procedure:
		        CallableStatement cs = conn.prepareCall("begin ? := student_registration.show_students(); end;");
		        //register the out parameter (the first parameter)
		        cs.registerOutParameter(1, OracleTypes.CURSOR);
		        
		        
		        // execute and retrieve the result set
		        cs.execute();
		        ResultSet rs = (ResultSet)cs.getObject(1);

		        // print the results
		        while (rs.next()) {
		            System.out.println(rs.getString(1) + "\t" +
		            rs.getString(2) + "\t" + rs.getString(3) + "\t" +
		            rs.getString(4) + "\t" + rs.getDouble(5) + "\t" +
		            rs.getString(6));
		        }

		        //close the result set, statement, and the connection
		        cs.close();
		        conn.close();
			}
			catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n");
   			}
		}

		public static void displayCoursesTable(){
			try{
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        //Prepare to call stored procedure:
		        CallableStatement cs = conn.prepareCall("begin ? := student_registration.show_courses(); end;");
		        //register the out parameter (the first parameter)
		        cs.registerOutParameter(1, OracleTypes.CURSOR);
		        
		        
		        // execute and retrieve the result set
		        cs.execute();
		        ResultSet rs = (ResultSet)cs.getObject(1);

		        // print the results
		        while (rs.next()) {
		            System.out.println(rs.getString(1) + "\t" +
							           rs.getString(2) + "\t" + 
							           rs.getString(3));
		        }

		        //close the result set, statement, and the connection
		        cs.close();
		        conn.close();
			}
			catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n");
   			}
		}

		public static void displayPreReqsTable(){
			try{
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        //Prepare to call stored procedure:
		        CallableStatement cs = conn.prepareCall("begin ? := student_registration.show_pre_requisites(); end;");
		        //register the out parameter (the first parameter)
		        cs.registerOutParameter(1, OracleTypes.CURSOR);
		        
		        
		        // execute and retrieve the result set
		        cs.execute();
		        ResultSet rs = (ResultSet)cs.getObject(1);

		        // print the results
		        while (rs.next()) {
		            System.out.println(rs.getString(1) + "\t" +
		            					rs.getString(2) + "\t" + 
		            					rs.getString(3) + "\t" +
		            					rs.getString(4));
		        }

		        //close the result set, statement, and the connection
		        cs.close();
		        conn.close();
			}
			catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n");
   			}
		}

		public static void displayClassesTable(){
			try{
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        //Prepare to call stored procedure:
		        CallableStatement cs = conn.prepareCall("begin ? := student_registration.show_classes(); end;");
		        //register the out parameter (the first parameter)
		        cs.registerOutParameter(1, OracleTypes.CURSOR);
		        
		        
		        // execute and retrieve the result set
		        cs.execute();
		        ResultSet rs = (ResultSet)cs.getObject(1);

		        // print the results
		        while (rs.next()) {
		            System.out.println(rs.getString(1) + "\t" +
		            rs.getString(2) + "\t" + rs.getString(3) + "\t" +
		            rs.getString(4) + "\t" + rs.getString(5) + "\t" +
		            rs.getString(6) + "\t" + rs.getString(7) + "\t" +
		            rs.getString(8));
		        }

		        //close the result set, statement, and the connection
		        cs.close();
		        conn.close();
			}
			catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n");
   			}
		}

		public static void displayEnrollmentsTable(){
			try{
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        //Prepare to call stored procedure:
		        CallableStatement cs = conn.prepareCall("begin ? := student_registration.show_enrollments(); end;");
		        //register the out parameter (the first parameter)
		        cs.registerOutParameter(1, OracleTypes.CURSOR);
		        
		        
		        // execute and retrieve the result set
		        cs.execute();
		        ResultSet rs = (ResultSet)cs.getObject(1);

		        // print the results
		        while (rs.next()) {
		            System.out.println(rs.getString(1) + "\t" +
		            					rs.getString(2) + "\t" + 
		            					rs.getString(3));
		        }

		        //close the result set, statement, and the connection
		        rs.close();
		        cs.close();
		        conn.close();
			}
			catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n");
   			}
		}

		public static void addStudent(String sidIn, String firstNameIn, String lastNameIn, String statusIn, Double gpaIn, String emailIn){
			try{
				OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
			    ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
			    Connection conn = ds.getConnection("epalani1", "Hydropump16");

			    Statement stmt = conn.createStatement ();
			    String dbcall = "{ call student_registration.add_student(?,?,?,?,?,?)}";
		      	CallableStatement cs = conn.prepareCall(dbcall);

			    cs.setString(1, sidIn);
			    cs.setString(2, firstNameIn);
			    cs.setString(3, lastNameIn);
			    cs.setString(4, statusIn);
			    cs.setDouble(5, gpaIn);
			    cs.setString(6, emailIn);

			    cs.executeUpdate();

			    stmt.close();
			    conn.close();
			}
			catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n");
   			}
			
		}

		public static void displayLogs(){
			try{
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        //Prepare to call stored procedure:
		        CallableStatement cs = conn.prepareCall("begin ? := student_registration.show_logs(); end;");
		        //register the out parameter (the first parameter)
		        cs.registerOutParameter(1, OracleTypes.CURSOR);
		        
		        
		        // execute and retrieve the result set
		        cs.execute();
		        ResultSet rs = (ResultSet)cs.getObject(1);

		        // print the results
		        while (rs.next()) {
		            System.out.println(rs.getString(1) + "\t" +
		            rs.getString(2) + "\t" + rs.getString(3) + "\t" +
		            rs.getString(4) + "\t" + rs.getString(5) + "\t" +
		            rs.getString(6));
		        }

		        //close the result set, statement, and the connection
		        rs.close();
		        cs.close();
		        conn.close();
			}
			catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n");
   			}
		}

		public static void getStudentClassInformation(String sidIn){
			try{	
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        Statement stmt = conn.createStatement();

		        String errorMsg = null;

		        String dbcall = "{call student_registration.get_student_class_information(?,?,?)}";

		        CallableStatement cs = conn.prepareCall(dbcall);
		        cs.setString(1,sidIn);
		        cs.registerOutParameter(2,OracleTypes.VARCHAR);
		        cs.registerOutParameter(3,OracleTypes.CURSOR);

		        cs.execute();

		        errorMsg = cs.getString(2);

		        if(errorMsg != null){
		        	System.out.println(errorMsg);
		        }
		        else{
		        	ResultSet rs = (ResultSet)cs.getObject(3);
		        	while (rs.next()) {
			            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + 
			            					rs.getString(3) + "\t" + rs.getString(4) + "\t" +
			            					rs.getString(5) + "\t" + rs.getString(6) + "\t" +
			            					rs.getString(7) + "\t" + rs.getString(8)
			            );
		        	}
		        	rs.close();
		        }
		        
		        //close the result set, statement, and the connection
		        cs.close();
		        conn.close();
		    }
		    catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n" + e);
   			}

		}

		public static void getClassInformation(String classIdIn){
			try{	
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        Statement stmt = conn.createStatement();

		        String errorMsg = null;

		        String dbcall = "{call student_registration.get_class_information(?,?,?)}";

		        CallableStatement cs = conn.prepareCall(dbcall);
		        cs.setString(1,classIdIn);
		        cs.registerOutParameter(2,OracleTypes.VARCHAR);
		        cs.registerOutParameter(3,OracleTypes.CURSOR);

		        cs.execute();

		        errorMsg = cs.getString(2);

		        if(errorMsg != null){
		        	System.out.println(errorMsg);
		        }
		        else{
		        	ResultSet rs = (ResultSet)cs.getObject(3);
		        	while (rs.next()) {
			            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + 
			            					rs.getString(3) + "\t" + rs.getString(4) + "\t" +
			            					rs.getString(5) + "\t" + rs.getString(6)
			            );
		        	}
		        	rs.close();
		        }
		        
		        //close the result set, statement, and the connection
		        cs.close();
		        conn.close();
		    }
		    catch (SQLException ex) 
			{ 
				System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());
			}
   			catch (Exception e) 
   			{
   				System.out.println ("\n*** other Exception caught ***\n" + e);
   			}	
		}
}