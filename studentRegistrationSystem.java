import java.sql.*; 
import oracle.jdbc.*;
import java.math.*;
import java.io.*;
import java.awt.*;
import oracle.jdbc.pool.OracleDataSource;

public class StudentRegistrationSystem{

	public static BufferedReader br;

	public void displayInformation(){
			//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please select an option:\n" + 
								"a. To view Students information\n" +
								"b. To view Courses information\n" +
								"c. To view Pre-requisites information\n" +
								"d. To view Classes information\n" +
								"e. To view Enrollments information\n" +
								"f. To view all tables");
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
				case 'f':
					System.out.println("Students Information:");
					displayStudentsTable();
					System.out.println();
					System.out.println("Courses Information: \n");
					displayCoursesTable();
					System.out.println();
					System.out.println("Pre-requisites Information: \n");
					displayPreReqsTable();
					System.out.println();
					System.out.println("Classes Information: \n");
					displayClassesTable();
					System.out.println();
					System.out.println("Enrollments Information: \n");
					displayEnrollmentsTable();
					break;
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
			
		}

		public void displayStudentsTable(){
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

		public void displayCoursesTable(){
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

		public void displayPreReqsTable(){
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

		public void displayClassesTable(){
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

		public void displayEnrollmentsTable(){
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

		public void addStudent(){
			try{
				System.out.println("Please enter the sid for the Student");
				String sidIn = br.readLine();
				System.out.println("Please enter the student's first name");
				String firstNameIn = br.readLine();
				System.out.println("Please enter the student's last name");
				String lastNameIn = br.readLine();
				System.out.println("Please enter the current status of the student");
				String statusIn = br.readLine();
				System.out.println("Please enter the current gpa of the student");
				Double gpaIn = Double.parseDouble(br.readLine());
				System.out.println("Please enter the email address of the student");
				String emailIn = br.readLine();

				String message = null;

				OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
			    ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
			    Connection conn = ds.getConnection("epalani1", "Hydropump16");

			    Statement stmt = conn.createStatement ();
			    String dbcall = "{ call student_registration.add_student(?,?,?,?,?,?,?)}";
		      	CallableStatement cs = conn.prepareCall(dbcall);

			    cs.setString(1, sidIn);
			    cs.setString(2, firstNameIn);
			    cs.setString(3, lastNameIn);
			    cs.setString(4, statusIn);
			    cs.setDouble(5, gpaIn);
			    cs.setString(6, emailIn);
		        cs.registerOutParameter(7, OracleTypes.VARCHAR);
			    cs.executeUpdate();

			    message = cs.getString(7);
			    if(message != null){
			    	System.out.println(message);
			    }

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

		public void displayLogs(){
			try{
				System.out.println("Log information:\n");
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

		public void getStudentClassInformation(){
			try{	

				System.out.println("Please enter the student's id to be searched");
				String sidIn = br.readLine();

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

		public void getClassInformation(){
			try{	
					
				System.out.println("Please enter the class id to be searched");
				String classIdIn = br.readLine();
				
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

		public void getPreRequisiteInformation(){
			try{	
				System.out.println("Please enter the Department Code for the course to be searched");
				String deptCodeIn = br.readLine();
				System.out.println("Please enter the course number of the course to be searched");
				int courseNoIn = Integer.parseInt(br.readLine());
				
				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        Statement stmt = conn.createStatement();

		        String errorMsg = null;

		        CallableStatement cs1 = conn.prepareCall("TRUNCATE TABLE prereq_courses_temp_table");
		        cs1.execute();

		        String dbcall = "{call student_registration.get_prerequisites(?,?,?)}";
		        
		        CallableStatement cs2  = conn.prepareCall(dbcall);
		        cs2.setString(1,deptCodeIn);
		        cs2.setInt(2,courseNoIn);
		        cs2.registerOutParameter(3,OracleTypes.CURSOR);

		        cs2.execute();

		        System.out.println("Prerequisite Courses\n");
		        ResultSet rs = (ResultSet)cs2.getObject(3);
		        while (rs.next()) {
			        System.out.println(rs.getString(1));
		        }
		        rs.close();
		        
		        //close the result set, statement, and the connection
		        cs1.close();
		        cs2.close();
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

		public void studentEnrollment(){
			try{

				System.out.println("Please enter the student's id that has to be enrolled");
				String sidIn = br.readLine();
				System.out.println("Please enter the class id for which the student needs to be enrolled");
				String classIdIn = br.readLine();

				//Connection to Oracle server
		        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        Statement stmt = conn.createStatement();

		        String message = null;

		        CallableStatement cs1 = conn.prepareCall("TRUNCATE TABLE prereq_courses_temp_table");
		        cs1.execute();

		        String dbcall = "{call student_registration.enroll_student(?,?,?)}";

		        CallableStatement cs  = conn.prepareCall(dbcall);
		        cs.setString(1,sidIn);
		        cs.setString(2,classIdIn);
		        cs.registerOutParameter(3,OracleTypes.VARCHAR);

		        cs.execute();

		        message = cs.getString(3);

		        if(message != null){
		        	System.out.println(message);
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

		public void dropStudentEnrollment(){
			try{
				//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Please enter the student id for the student");
				String sid = br.readLine();
				System.out.println("Please enter the class id of the class from which the student needs to be dropped");
				String classid = br.readLine();
			
				OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        Statement stmt = conn.createStatement();

		        String message = null;

				String dbcall = "{call student_registration.drop_student_enrollment(?,?,?)}";

		        CallableStatement cs  = conn.prepareCall(dbcall);
		        cs.setString(1,sid);
		        cs.setString(2,classid);
		        cs.registerOutParameter(3,OracleTypes.VARCHAR);

		        cs.execute();

		        message = cs.getString(3);

		        if(message != null){
		        	System.out.println(message);
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

		public void deleteStudent(){
			try{
				//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Please enther the sid of the student that needs to be deleted");
				String sid = br.readLine();

				OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
		        ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111");
		        Connection conn = ds.getConnection("epalani1", "Hydropump16");

		        Statement stmt = conn.createStatement();

		        String message = null;

				String dbcall = "{call student_registration.delete_student(?,?)}";

		        CallableStatement cs  = conn.prepareCall(dbcall);
		        cs.setString(1,sid);
		        cs.registerOutParameter(2,OracleTypes.VARCHAR);

		        cs.execute();

		        message = cs.getString(2);

		        if(message != null){
		        	System.out.println(message);
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

	public static void main(String args[]) throws SQLException{

		br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		StudentRegistrationSystem srs = new StudentRegistrationSystem();

	try{
		Boolean userPref = true;
		while(userPref){
			System.out.println();
			System.out.println("********** Student Registration System **********");
			System.out.println("Please select an option:\n" +
							   "1.  To view information\n" +
							   "2.  To add a Student to the database\n" +
							   "3.  List the class information for a particular student\n" +
							   "4.  List the pre-requisites for a particular course\n" + 
							   "5.  List the class information\n" +
							   "6.  To enroll student in a class\n" +
							   "7.  To drop student from a class\n" +
							   "8.  To delete a student from the database\n" +
							   "9.  View Logs\n" +
							   "10. Exit");

			option = Integer.parseInt(br.readLine());
			switch(option){
				case 1:
					srs.displayInformation();
					break;
				case 2:
					srs.addStudent();
					break;
				case 3:
					srs.getStudentClassInformation();
					break;
				case 4:
					srs.getPreRequisiteInformation();
					break;
				case 5:
					srs.getClassInformation();
					break;
				case 6:
					srs.studentEnrollment();
					break;
				case 7:
					srs.dropStudentEnrollment();
					break;
				case 8:
					srs.deleteStudent();
					break;
				case 9:
					srs.displayLogs();
					break;
				case 10:
					userPref = false;
					System.out.println("Application terminating...\nThank you!");
					break;
			}	
		}
			
	}
	catch(Exception e){
			System.out.println(e);
	}
		
	}
}