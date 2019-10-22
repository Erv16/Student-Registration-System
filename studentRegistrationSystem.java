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
							   "1. To view information");
			option = Integer.parseInt(br.readLine());
			switch(option){
				case 1:
					viewInformation();
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

	public static void viewInformation(){
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
		                rs.getString(2) + "\t" + rs.getString(3) + 
		                rs.getString(4) + 
		                "\t" + rs.getDouble(5) + "\t" +
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
		                rs.getString(2) + "\t" + rs.getString(3) + 
		                rs.getString(4) + 
		                "\t" + rs.getDouble(5) + "\t" +
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
		                rs.getString(2) + "\t" + rs.getString(3) + 
		                rs.getString(4) + 
		                "\t" + rs.getDouble(5) + "\t" +
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
		                rs.getString(2) + "\t" + rs.getString(3) + 
		                rs.getString(4) + 
		                "\t" + rs.getDouble(5) + "\t" +
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
		                rs.getString(2) + "\t" + rs.getString(3) + 
		                rs.getString(4) + 
		                "\t" + rs.getDouble(5) + "\t" +
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
}