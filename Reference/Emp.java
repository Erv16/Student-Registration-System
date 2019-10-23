import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import java.sql.*;
import java.math.*;
import java.io.*;
import java.awt.*;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.pool.OracleDataSource;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.TableViewer;

import java.util.Date;

public class Emp {

	protected Shell shlBuRetailSystem;
	private Text textProductID;
	private Text textProductName;
	private Text textProductQty;
	private Text textProductThreshold;
	private Text textProductPrice;
	private Text textProductDiscount;
	private Text textPurchaseProductId;
	private Text textPurchaseEmployeeId;
	private Text textPurchaseCustomerId;
	private Text textPurchaseQty;
	private Label lblErrorMessage;
	private Label lblSuccessMessage;
	private Table table;
	private Combo comboProductList;
	private Label lblSupplyMessage;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Emp window = new Emp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlBuRetailSystem.open();
		shlBuRetailSystem.layout();
		while (!shlBuRetailSystem.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public void clearInputFields() {
		textPurchaseProductId.setText("");
		textPurchaseEmployeeId.setText("");
  	  	textPurchaseCustomerId.setText("");
  	  	textPurchaseQty.setText("");
  	  	
  	  	textProductID.setText("");
  	  	textProductName.setText("");
  	  	textProductQty.setText("");
  	  	textProductThreshold.setText("");
  	  	textProductPrice.setText("");
  	  	textProductDiscount.setText("");
	}
	
	public void hideMessageFields() {
		lblSuccessMessage.setVisible(false);
		lblErrorMessage.setVisible(false);
		lblSupplyMessage.setVisible(false);
	}
	
	public void fillProductDropdown() {
		System.out.println("fill product dropdown");
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      CallableStatement cs = conn.prepareCall("begin ? := tables.show_products(); end;");

		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(1);
			
	      while(rs.next()) {
	    	  comboProductList.add(rs.getString(2));
	      }
	      comboProductList.select(0);
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	public void displayProductsTable() {
		System.out.println("Display products table");
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      CallableStatement cs = conn.prepareCall("begin ? := tables.show_products(); end;");

		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(1);
	      
	      table.removeAll();
	      table.setRedraw(false);
	      
	      while ( table.getColumnCount() > 0 ) {
	    	  table.getColumns()[ 0 ].dispose();
	      }
	      
	      String[] titles = {"PID","PNAME", "QOH", "THRESHOLD", "PRICE", "DISCOUNT"};
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	  TableColumn column = new TableColumn(table, SWT.NULL);
	    	  column.setText(titles[loopIndex]);
	      }
			
	      while(rs.next()) {
	    	  TableItem item = new TableItem(table, SWT.NULL);
	          item.setText(0, rs.getString(1));
	          item.setText(1, rs.getString(2));
	          item.setText(2, rs.getString(3));
	          item.setText(3, rs.getString(4));
	          item.setText(4, rs.getString(5));
	          item.setText(5, rs.getString(6));
	      }
	      
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		      table.getColumn(loopIndex).pack();
		  }
	      
	      table.setRedraw(true);
	      table.setVisible(true);
	      
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	public void displayEmployeesTable() {
		System.out.println("Display employees table");
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      CallableStatement cs = conn.prepareCall("begin ? := tables.show_employees(); end;");

		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(1);
	      
	      table.removeAll();
	      table.setRedraw(false);
	      
	      while ( table.getColumnCount() > 0 ) {
	    	    table.getColumns()[ 0 ].dispose();
	    	}
	      
	      String[] titles = {"EID","ENAME", "TELEPHONE#"};
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	  TableColumn column = new TableColumn(table, SWT.NULL);
	    	  column.setText(titles[loopIndex]);
	      }
			
	      while(rs.next()) {
	    	  TableItem item = new TableItem(table, SWT.NULL);
	          item.setText(0, rs.getString(1));
	          item.setText(1, rs.getString(2));
	          item.setText(2, rs.getString(3));
	      }
	      
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		      table.getColumn(loopIndex).pack();
		  }
	      
	      table.setRedraw(true);
	      table.setVisible(true);
	      
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	public void displayCustomersTable() {
		System.out.println("Display customers table");
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      CallableStatement cs = conn.prepareCall("begin ? := tables.show_customers(); end;");

		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(1);
	      
	      table.removeAll();
	      table.setRedraw(false);
	      while ( table.getColumnCount() > 0 ) {
	    	    table.getColumns()[ 0 ].dispose();
	      }
	      
	      String[] titles = {"CID","CNAME", "TELEPHONE#", "VISITS MADE", "LAST VISIT DATE"};
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	  TableColumn column = new TableColumn(table, SWT.NULL);
	    	  column.setText(titles[loopIndex]);
	      }
			
	      while(rs.next()) {
	    	  TableItem item = new TableItem(table, SWT.NULL);
	          item.setText(0, rs.getString(1));
	          item.setText(1, rs.getString(2));
	          item.setText(2, rs.getString(3));
	          item.setText(3, rs.getString(4));
	          item.setText(4, rs.getString(5));
	      }
	      
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		      table.getColumn(loopIndex).pack();
		  }
	      
	      table.setRedraw(true);
	      table.setVisible(true);
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	public void displayPurchasesTable() {
		System.out.println("Display purchases table");
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      CallableStatement cs = conn.prepareCall("begin ? := tables.show_purchases(); end;");

		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(1);
	      
	      table.removeAll();
	      table.setRedraw(false);
	      
	      while ( table.getColumnCount() > 0 ) {
	    	  table.getColumns()[ 0 ].dispose();
	      }
	      
	      String[] titles = {"PUR#","EID", "PID", "CID", "QTY", "PTIME", "TOTAL PRICE"};
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	  TableColumn column = new TableColumn(table, SWT.NULL);
	    	  column.setText(titles[loopIndex]);
	      }
			
	      while(rs.next()) {
	    	  TableItem item = new TableItem(table, SWT.NULL);
	          item.setText(0, rs.getString(1));
	          item.setText(1, rs.getString(2));
	          item.setText(2, rs.getString(3));
	          item.setText(3, rs.getString(4));
	          item.setText(4, rs.getString(5));
	          item.setText(5, rs.getString(6));
	          item.setText(6, rs.getString(7));
	      }
	      
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		      table.getColumn(loopIndex).pack();
		  }
	      
	      table.setRedraw(true);
	      table.setVisible(true);
	      
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	public void displaySuppliersTable() {
		System.out.println("Display suppliers table");
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      CallableStatement cs = conn.prepareCall("begin ? := tables.show_suppliers(); end;");

		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(1);
	      
	      table.removeAll();
	      table.setRedraw(false);
	      
	      while ( table.getColumnCount() > 0 ) {
	    	  table.getColumns()[ 0 ].dispose();
	      }
	      
	      String[] titles = {"SID","SNAME", "CITY", "TELEPHONE#"};
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	  TableColumn column = new TableColumn(table, SWT.NULL);
	    	  column.setText(titles[loopIndex]);
	      }
			
	      while(rs.next()) {
	    	  TableItem item = new TableItem(table, SWT.NULL);
	          item.setText(0, rs.getString(1));
	          item.setText(1, rs.getString(2));
	          item.setText(2, rs.getString(3));
	          item.setText(3, rs.getString(4));
	      }
	      
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		      table.getColumn(loopIndex).pack();
		  }
	      
	      table.setRedraw(true);
	      table.setVisible(true);
	      
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	public void displaySupplyTable() {
		System.out.println("Display supply table");
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      CallableStatement cs = conn.prepareCall("begin ? := tables.show_supply(); end;");

		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(1);
	      
	      table.removeAll();
	      table.setRedraw(false);
	      
	      while ( table.getColumnCount() > 0 ) {
	    	  table.getColumns()[ 0 ].dispose();
	      }
	      
	      String[] titles = {"SUP#","PID", "SID", "SDATE", "QTY"};
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	  TableColumn column = new TableColumn(table, SWT.NULL);
	    	  column.setText(titles[loopIndex]);
	      }
			
	      while(rs.next()) {
	    	  TableItem item = new TableItem(table, SWT.NULL);
	          item.setText(0, rs.getString(1));
	          item.setText(1, rs.getString(2));
	          item.setText(2, rs.getString(3));
	          item.setText(3, rs.getString(4));
	          item.setText(4, rs.getString(5));
	      }
	      
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		      table.getColumn(loopIndex).pack();
		  }
	      
	      table.setRedraw(true);
	      table.setVisible(true);
	      
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	public void displayLogTable() {
		System.out.println("Display log table");
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      CallableStatement cs = conn.prepareCall("begin ? := tables.show_logs(); end;");

		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(1);
	      
	      table.removeAll();
	      table.setRedraw(false);
	      
	      while ( table.getColumnCount() > 0 ) {
	    	  table.getColumns()[ 0 ].dispose();
	      }
	      
	      String[] titles = {"LOG#", "WHO", "OTIME", "TABLE NAME", "OPERATION"};
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	  TableColumn column = new TableColumn(table, SWT.NULL);
	    	  column.setText(titles[loopIndex]);
	      }
			
	      while(rs.next()) {
	    	  TableItem item = new TableItem(table, SWT.NULL);
	          item.setText(0, rs.getString(1));
	          item.setText(1, rs.getString(2));
	          item.setText(2, rs.getString(3));
	          item.setText(3, rs.getString(4));
	          item.setText(4, rs.getString(5));
	          item.setText(5, rs.getString(6));
	      }
	      
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		      table.getColumn(loopIndex).pack();
		  }
	      
	      table.setRedraw(true);
	      table.setVisible(true);
	      
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	public void findTableToView(String tableName) {
		if(tableName.equals("Products")) 
			displayProductsTable();
		else if (tableName.equals("Purchases")) 
			displayPurchasesTable();
		else if (tableName.equals("Employees"))
			displayEmployeesTable();
		else if (tableName.equals("Customers")) 
			displayCustomersTable();
		else if (tableName.equals("Suppliers")) 
			displaySuppliersTable();
		else if (tableName.equals("Supply")) 
			displaySupplyTable();
		else if (tableName.equals("Log")) 
			displayLogTable();
	}
	
	public void getMonthlyReport(String product) { //, String month, String year
		System.out.println("Generate report for: " + product);
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      String dbCall = "{ call tables.monthly_report(?,?) }";
	      CallableStatement cs = conn.prepareCall(dbCall);
	      cs.setString(1, product);
		  //register the out parameter (the first parameter)
	      cs.registerOutParameter(2, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      cs.execute();
	      ResultSet rs = (ResultSet)cs.getObject(2);
	      
	      table.removeAll();
	      table.setRedraw(false);
	      
	      while ( table.getColumnCount() > 0 ) {
	    	  table.getColumns()[ 0 ].dispose();
	      }
	      
	      String[] titles = {"Month, Year", "Quantity", "Total Price", "Average Sale Price"};
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
	    	  TableColumn column = new TableColumn(table, SWT.NULL);
	    	  column.setText(titles[loopIndex]);
	      }
		
	      while(rs.next()) {
	    	  TableItem item = new TableItem(table, SWT.NULL);
	          item.setText(0, rs.getString(1));
	          item.setText(1, rs.getString(2));
	          item.setText(2, rs.getString(3));
	          Double avg = Math.floor(rs.getDouble (3)/rs.getDouble(2)*100)/100;
	          item.setText(3, avg.toString());
	      }
	      
	      while (rs.next ()) {
	          System.out.print (rs.getString (1)+"  ");
	          System.out.print (rs.getString (2)+"  ");
	          System.out.print (rs.getString (3)+"  ");
	          System.out.print ( Math.floor(rs.getDouble (3)/rs.getDouble(2)*100)/100 +"  ");
	          System.out.println ("");
	       }
	      
	      for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
		      table.getColumn(loopIndex).pack();
		  }
	      
	      table.setRedraw(true);
	      table.setVisible(true);
	      
	      
	      rs.close();
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n");
	     System.out.println (ex.toString());
	     lblErrorMessage.setText(ex.toString());}
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
		
	}
	
	public void addNewProduct(String pid, String pname, Integer qty, Integer threshold, Double price, Double discnt) {
		System.out.println("Create new product:");
		//System.out.println("pid: "+ pid+ "\nPname: " + pname + "\nqty: " + qty + "\nthreshold: " + threshold + "\nprice: " + price + "\ndiscnt: " + discnt);
		hideMessageFields();
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      String dbcall = "{ call tables.add_product(?,?,?,?,?,?)}";
	      CallableStatement cs = conn.prepareCall(dbcall);

		  //register the out parameter (the first parameter)
	      cs.setString(1, pid);
	      cs.setString(2, pname);
	      cs.setInt(3, qty);
	      cs.setInt(4, threshold);
	      cs.setDouble(5, price);
	      cs.setDouble(6, discnt);
	      //cs.registerOutParameter(1, OracleTypes.CURSOR);

	      // execute and retrieve the result set
	      int count = cs.executeUpdate();
	      System.out.println(count);
	      
	      //Successful call
	      if(count > 0) {
	    	  clearInputFields();
	    	  
	    	  lblSuccessMessage.setText("Product added successfully");
	    	  lblSuccessMessage.setVisible(true);
	    	  
	    	  comboProductList.add(pname);
	      }
	      
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { 
	    	 System.out.println ("\n*** SQLException caught ***\n");
	    	 Integer purErr = ex.getErrorCode();
	    	 System.out.println("Caught123: " + purErr);
	    	 switch(purErr) {
	    	 	case 20104: 
	    	 		lblErrorMessage.setText("Quantity entered has to be positive");
	    	 		lblErrorMessage.setVisible(true);
	    	 		break;
	    	 	case 20105: 
	    	 		lblErrorMessage.setText("Threshold entered has to be positive");
	    	 		lblErrorMessage.setVisible(true);
	    	 		break;
	    	 	case 20106: 
	    	 		lblErrorMessage.setText("Threshold has to be less than quantity");
	    	 		lblErrorMessage.setVisible(true);
	    	 		break;
	    	 	case 20107: 
	    	 		lblErrorMessage.setText("Price should be greater than 0");
	    	 		lblErrorMessage.setVisible(true);
	    	 		break;
	    	 	case 20108: 
	    	 		lblErrorMessage.setText("Discount should be between 0-0.8");
	    	 		lblErrorMessage.setVisible(true);
	    	 		break;
	    	 	default: 
	    	 		lblErrorMessage.setText("Invalid data input");
	    	 		lblErrorMessage.setVisible(true);
	    	 }
	     }
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}

	public void addNewPurchase(String pid, String eid, String cid, Integer qty) {
		System.out.println("Create new purchase: " + pid +" "+ cid +" "+ eid +" "+ qty );
		hideMessageFields();
		
		try
	    {

	      //Connection to Oracle server. Need to replace username and
	      //password by your username and your password. For security
	      //consideration, it's better to read them in from keyboard.
	      OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
	      ds.setURL("jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:acad111");
	      Connection conn = ds.getConnection("sbaptis2", "Shanbapt8");

	      // Query
	      Statement stmt = conn.createStatement ();

	      //Prepare to call stored procedure:
	      String dbcall = "{ call tables.add_purchase(?,?,?,?,?,?)}";
	      CallableStatement cs = conn.prepareCall(dbcall);

		  //register the out parameter (the first parameter)
	      Integer isOrdered = 0;
	      Integer ordQuant = 0;
	      cs.setString(1, pid);
	      cs.setString(2, eid);
	      cs.setString(3, cid);
	      cs.setInt(4, qty);
	      cs.setInt(5, isOrdered);
	      cs.setInt(6, ordQuant);
	      
	      //Test
	      cs.registerOutParameter(5, OracleTypes.NUMBER);
	      cs.registerOutParameter(6, OracleTypes.NUMBER);
	      // execute and retrieve the result set
	      int count = cs.executeUpdate();
	      System.out.println(count);
	      
	      //Successful call
	      if(count > 0) {
	    	  System.out.println(cs.toString());
	    	  System.out.println("=====");
	    	  System.out.println("Ord: " + cs.getInt(5));
	    	  System.out.println("NEW: " + cs.getInt(6));
	    	  
	    	  if(cs.getInt(5) == 1) {
	    		  lblSupplyMessage.setText("A new order for products has been placed\nThe current amount of products available is now: "+ cs.getInt(6));
	    		  lblSupplyMessage.setVisible(true);
	    	  }
	    	  clearInputFields();
	    	  
	    	  lblSuccessMessage.setText("Purchase added successfully");
	    	  lblSuccessMessage.setVisible(true);
	      }
	      
	      stmt.close();
	      conn.close();
		}
	     catch (SQLException ex) { 
	    	 Integer errCode = ex.getErrorCode();
	    	 System.out.println("Caught: " + errCode);
	    	 switch(errCode) {
	    	 	case 20102: 
	    	 		lblErrorMessage.setText("Quantity entered has to be positive");
	    	 		lblErrorMessage.setVisible(true);
	    	 		break;
	    	 	case 20103: 
	    	 		lblErrorMessage.setText("Insufficient quantity in stock");
	    	 		lblErrorMessage.setVisible(true);
	    	 		break;
	    	 	case 21000: 
	    	 		lblErrorMessage.setText("Invalid data input (P###, E##, C###)");
	    	 		lblErrorMessage.setVisible(true);
	    	 		break;
	    	 	default: 
	    	 		lblErrorMessage.setText("Invalid data input");
	    	 		lblErrorMessage.setVisible(true);
	    	 }
	     }
	     catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBuRetailSystem = new Shell();
		shlBuRetailSystem.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		shlBuRetailSystem.setSize(545, 615);
		shlBuRetailSystem.setText("BU Retail System");
		
		Combo comboTableList = new Combo(shlBuRetailSystem, SWT.NONE);
		comboTableList.setItems(new String[] {"Purchases", "Products", "Customers", "Employees", "Suppliers", "Supply", "Log"});
		comboTableList.setBounds(145, 6, 94, 22);
		comboTableList.select(0);
		
		Button btnDisplayTable = new Button(shlBuRetailSystem, SWT.NONE);
		btnDisplayTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				findTableToView(comboTableList.getText());
			}
		});
		btnDisplayTable.setBounds(245, 4, 94, 27);
		btnDisplayTable.setText("Display");
		
		Label lblSelectTableTo = new Label(shlBuRetailSystem, SWT.NONE);
		lblSelectTableTo.setBounds(10, 10, 129, 14);
		lblSelectTableTo.setText("Select table to display:");
		
		/*Combo comboMonthList = new Combo(shlBuRetailSystem, SWT.NONE);
		comboMonthList.setItems(new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
		comboMonthList.setBounds(245, 40, 94, 27);
		comboMonthList.select(3);*/
		
		Label lblViewPurchaseReport = new Label(shlBuRetailSystem, SWT.NONE);
		lblViewPurchaseReport.setBounds(10, 44, 129, 14);
		lblViewPurchaseReport.setText("View Purchase report:");
		
		comboProductList = new Combo(shlBuRetailSystem, SWT.NONE);
		//comboProductList.setItems(new String[] {"stapler", "TV", "camera", "pencil", "chair", "lamp", "tablet", "computer", "powerbank"});
		comboProductList.setItems(new String[] {});
		comboProductList.setBounds(145, 40, 94, 27);
		comboProductList.select(0);
		fillProductDropdown();
		
		/*Combo comboYears = new Combo(shlBuRetailSystem, SWT.NONE);
		comboYears.setItems(new String[] {"2018", "2019", "2020", "2021", "2022"});
		comboYears.setBounds(345, 40, 73, 27);
		comboYears.select(1);*/
		
		Button btnDisplayReport = new Button(shlBuRetailSystem, SWT.NONE);
		btnDisplayReport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//String monthVar = hmap.get(comboMonthList.getText());
				getMonthlyReport(comboProductList.getText());//, monthVar, comboYears.getText()
			}
		});
		btnDisplayReport.setBounds(245, 38, 94, 27);
		btnDisplayReport.setText("Display");
		
		Label lblAddProduct = new Label(shlBuRetailSystem, SWT.NONE);
		lblAddProduct.setBounds(33, 113, 82, 14);
		lblAddProduct.setText("Add product:");
		
		Label lblProductid = new Label(shlBuRetailSystem, SWT.NONE);
		lblProductid.setBounds(33, 136, 57, 14);
		lblProductid.setText("ProductID:");
		
		Label lblName = new Label(shlBuRetailSystem, SWT.NONE);
		lblName.setBounds(33, 161, 59, 14);
		lblName.setText("Name:");
		
		Label lblNewLabel = new Label(shlBuRetailSystem, SWT.NONE);
		lblNewLabel.setBounds(33, 186, 59, 14);
		lblNewLabel.setText("Quantity:");
		
		Label lblNewLabel_1 = new Label(shlBuRetailSystem, SWT.NONE);
		lblNewLabel_1.setBounds(33, 211, 59, 14);
		lblNewLabel_1.setText("Threshold:");
		
		Label lblNewLabel_2 = new Label(shlBuRetailSystem, SWT.NONE);
		lblNewLabel_2.setBounds(33, 236, 59, 14);
		lblNewLabel_2.setText("Price:");
		
		Label lblDiscount = new Label(shlBuRetailSystem, SWT.NONE);
		lblDiscount.setBounds(33, 256, 94, 14);
		lblDiscount.setText("Discount(0-0.8):");
		
		Button btnAddProduct = new Button(shlBuRetailSystem, SWT.NONE);
		btnAddProduct.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addNewProduct(
						textProductID.getText(),
						textProductName.getText(),
						Integer.parseInt(textProductQty.getText()),
						Integer.parseInt(textProductThreshold.getText()),
						Double.parseDouble(textProductPrice.getText()),
						Double.parseDouble(textProductDiscount.getText())
						);
			}
		});
		btnAddProduct.setBounds(33, 283, 160, 27);
		btnAddProduct.setText("Add Product");
		
		Composite composite = new Composite(shlBuRetailSystem, SWT.NONE);
		composite.setBounds(23, 111, 206, 199);
		
		textProductID = new Text(composite, SWT.BORDER);
		textProductID.setBounds(117, 22, 64, 19);
		
		textProductName = new Text(composite, SWT.BORDER);
		textProductName.setBounds(117, 47, 64, 19);
		
		textProductQty = new Text(composite, SWT.BORDER);
		textProductQty.setBounds(117, 72, 64, 19);
		
		textProductThreshold = new Text(composite, SWT.BORDER);
		textProductThreshold.setBounds(117, 97, 64, 19);
		
		textProductPrice = new Text(composite, SWT.BORDER);
		textProductPrice.setBounds(117, 122, 64, 19);
		
		textProductDiscount = new Text(composite, SWT.BORDER);
		textProductDiscount.setBounds(117, 147, 64, 19);
		
		Label lblAddPurchase = new Label(shlBuRetailSystem, SWT.NONE);
		lblAddPurchase.setText("Add purchase:");
		lblAddPurchase.setBounds(287, 113, 94, 14);
		
		Label label_1 = new Label(shlBuRetailSystem, SWT.NONE);
		label_1.setText("ProductID:");
		label_1.setBounds(287, 136, 57, 14);
		
		textPurchaseProductId = new Text(shlBuRetailSystem, SWT.BORDER);
		textPurchaseProductId.setBounds(383, 133, 64, 19);
		
		Label lblEmployeeid = new Label(shlBuRetailSystem, SWT.NONE);
		lblEmployeeid.setText("EmployeeID:");
		lblEmployeeid.setBounds(287, 161, 73, 14);
		
		textPurchaseEmployeeId = new Text(shlBuRetailSystem, SWT.BORDER);
		textPurchaseEmployeeId.setBounds(383, 158, 64, 19);
		
		textPurchaseCustomerId = new Text(shlBuRetailSystem, SWT.BORDER);
		textPurchaseCustomerId.setBounds(383, 183, 64, 19);
		
		textPurchaseQty = new Text(shlBuRetailSystem, SWT.BORDER);
		textPurchaseQty.setBounds(383, 208, 64, 19);
		
		Label lblCustomerid = new Label(shlBuRetailSystem, SWT.NONE);
		lblCustomerid.setText("CustomerID:");
		lblCustomerid.setBounds(287, 186, 73, 14);
		
		Label lblQuantity = new Label(shlBuRetailSystem, SWT.NONE);
		lblQuantity.setText("Quantity:");
		lblQuantity.setBounds(287, 211, 59, 14);
		
		Composite composite_1 = new Composite(shlBuRetailSystem, SWT.NONE);
		composite_1.setBounds(277, 111, 206, 150);
		
		Button btnEnterPurchase = new Button(composite_1, SWT.NONE);
		btnEnterPurchase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addNewPurchase(
						textPurchaseProductId.getText(), 
						textPurchaseEmployeeId.getText(), 
						textPurchaseCustomerId.getText(), 
						Integer.parseInt(textPurchaseQty.getText()));
			}
		});
		btnEnterPurchase.setBounds(10, 123, 160, 27);
		btnEnterPurchase.setText("Enter Purchase");
		
		Label label = new Label(shlBuRetailSystem, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		label.setBounds(-21, 81, 576, 2);
		
		lblErrorMessage = new Label(shlBuRetailSystem, SWT.CENTER);
		lblErrorMessage.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblErrorMessage.setBounds(23, 326, 460, 14);
		lblErrorMessage.setText("Error message");
		lblErrorMessage.setVisible(false);
		
		table = new Table(shlBuRetailSystem, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setBounds(10, 391, 525, 177);
		table.setLinesVisible(true);
		table.setVisible(false);
		
		lblSuccessMessage = new Label(shlBuRetailSystem, SWT.CENTER);
		lblSuccessMessage.setText("Success message");
		lblSuccessMessage.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblSuccessMessage.setBounds(23, 346, 460, 14);
		lblSuccessMessage.setVisible(false);
		
		lblSupplyMessage = new Label(shlBuRetailSystem, SWT.WRAP);
		lblSupplyMessage.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lblSupplyMessage.setBounds(277, 267, 206, 59);
		lblSupplyMessage.setText("Supply message");
		lblSupplyMessage.setVisible(false);
		
		

	}
}
