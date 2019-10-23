set serveroutput on;

CREATE OR REPLACE PACKAGE tables AS
	TYPE ref_cursor IS ref cursor; --the reference cursor so java can loop through and do print statements
	FUNCTION show_logs RETURN ref_cursor;
	FUNCTION show_products RETURN ref_cursor;
	FUNCTION show_purchases RETURN ref_cursor;
	FUNCTION show_employees RETURN ref_cursor;
	FUNCTION show_customers RETURN ref_cursor;
	FUNCTION show_suppliers RETURN ref_cursor;
	FUNCTION show_supply RETURN ref_cursor;

	PROCEDURE add_product(pid_in IN Products.pid%TYPE, pname_in IN Products.pname%TYPE, qoh_in IN Products.qoh%TYPE, qoht_in IN Products.qoh_threshold%TYPE, price_in IN Products.original_price%TYPE, discnt_in IN Products.discnt_rate%TYPE);

	PROCEDURE monthly_report(pname_in IN Products.pname%TYPE, rc_cursor OUT SYS_REFCURSOR);

	PROCEDURE add_purchase(pid_in IN Purchases.pid%TYPE, eid_in IN Purchases.eid%TYPE, cid_in IN Purchases.cid%TYPE, qty_in IN Purchases.qty%TYPE, isOrdered IN OUT NUMBER, newQuant IN OUT NUMBER);

	PROCEDURE update_purchase(pid_in IN Products.pid%TYPE, newQuant IN OUT NUMBER);
END;
/

CREATE OR REPLACE PACKAGE BODY tables AS
	
	--show logs table
	FUNCTION show_products RETURN ref_cursor AS rc ref_cursor;
	BEGIN
		OPEN rc FOR 
			select * FROM Products;
		RETURN rc;
	END;

	FUNCTION show_purchases RETURN ref_cursor AS rc ref_cursor;
	BEGIN
		OPEN rc FOR 
			select * FROM Purchases;
		RETURN rc;
	END;

	FUNCTION show_employees RETURN ref_cursor AS rc ref_cursor;
	BEGIN
		OPEN rc FOR 
			select * FROM Employees;
		RETURN rc;
	END;

	FUNCTION show_customers RETURN ref_cursor AS rc ref_cursor;
	BEGIN
		OPEN rc FOR 
			select * FROM Customers;
		RETURN rc;
	END;

	FUNCTION show_suppliers RETURN ref_cursor AS rc ref_cursor;
	BEGIN
		OPEN rc FOR 
			select * FROM Suppliers;
		RETURN rc;
	END;

	FUNCTION show_supply RETURN ref_cursor AS rc ref_cursor;
	BEGIN
		OPEN rc FOR 
			select * FROM Supply;
		RETURN rc;
	END;

	FUNCTION show_logs RETURN ref_cursor AS rc ref_cursor;
	BEGIN
		OPEN rc FOR 
			select * FROM Logs;
		RETURN rc;
	END;

	PROCEDURE add_product(pid_in IN Products.pid%TYPE, pname_in IN Products.pname%TYPE, qoh_in IN Products.qoh%TYPE, qoht_in IN Products.qoh_threshold%TYPE, price_in IN Products.original_price%TYPE, discnt_in IN Products.discnt_rate%TYPE) AS
		qty_neg EXCEPTION;
		PRAGMA EXCEPTION_INIT(qty_neg, -20104);
		thr_neg EXCEPTION;
		PRAGMA EXCEPTION_INIT(thr_neg, -20105);
		greater_threshold EXCEPTION;
		PRAGMA EXCEPTION_INIT(greater_threshold, -20106);
		price_neg EXCEPTION;
		PRAGMA EXCEPTION_INIT(price_neg, -20107);
		discnt_invalid EXCEPTION;
		PRAGMA EXCEPTION_INIT(discnt_invalid, -20108);
	
	BEGIN
		IF(qoh_in < 0) THEN
			RAISE qty_neg;
		ELSIF (qoht_in < 0) THEN
			RAISE thr_neg;
		ELSIF (price_in < 0) THEN
			RAISE price_neg;
		ELSIF (discnt_in < 0 or discnt_in > 0.8) THEN
			RAISE discnt_invalid;
		ELSE
			INSERT INTO Products(pid, pname, qoh, qoh_threshold, original_price, discnt_rate) VALUES(pid_in, pname_in, qoh_in, qoht_in, price_in, discnt_in);
		END IF;

	EXCEPTION
	WHEN qty_neg THEN
		RAISE_APPLICATION_ERROR(-20104, 'NEGATIVE QUANTITY');
	WHEN thr_neg THEN
		RAISE_APPLICATION_ERROR(-20105, 'NEGATIVE THRESHOLD');
	WHEN greater_threshold THEN
		RAISE_APPLICATION_ERROR(-20106, 'GREATER THRESHOLD');
	WHEN price_neg THEN
		RAISE_APPLICATION_ERROR(-20107, 'NEGATIVE PRICE');
	WHEN discnt_invalid THEN
		RAISE_APPLICATION_ERROR(-20108, 'INVALID DISCOUNT');
	WHEN OTHERS THEN
		RAISE_APPLICATION_ERROR(SQLCODE,SQLERRM);
	END;

	PROCEDURE monthly_report(pname_in IN Products.pname%TYPE, rc_cursor OUT SYS_REFCURSOR) 
	IS
		pro_id Products.pid%TYPE;
	BEGIN
		SELECT pid INTO pro_id FROM Products WHERE pname=pname_in;

		DBMS_OUTPUT.PUT_LINE(pro_id);
		
		OPEN rc_cursor FOR
			SELECT to_char(ptime, 'Mon, YYYY'), SUM(qty), SUM(total_price)
			FROM Purchases
			WHERE Purchases.pid=pro_id
			GROUP BY pro_id, to_char(ptime, 'Mon, YYYY');

	EXCEPTION
	WHEN OTHERS THEN
		DBMS_OUTPUT.PUT_LINE(SQLERRM);
	END;

	PROCEDURE add_purchase(pid_in IN Purchases.pid%TYPE, eid_in IN Purchases.eid%TYPE, cid_in IN Purchases.cid%TYPE, qty_in IN Purchases.qty%TYPE, isOrdered IN OUT NUMBER, newQuant IN OUT NUMBER) AS
		curr_product Products%ROWTYPE;
		total_price Products.original_price%TYPE;
		insuff_qoh EXCEPTION;
		PRAGMA EXCEPTION_INIT(insuff_qoh, -20103);
		less_than_zero EXCEPTION;
		PRAGMA EXCEPTION_INIT(less_than_zero, -20102);

		newlyAdded Purchases.qty%TYPE;

	BEGIN
		SELECT * INTO curr_product FROM Products WHERE pid=pid_in;

		DBMS_OUTPUT.PUT_LINE(curr_product.qoh);

		IF (qty_in <= curr_product.qoh) THEN
			DBMS_OUTPUT.PUT_LINE('ELIGIBLE TO BUY');
			total_price := curr_product.original_price * (1 - curr_product.discnt_rate);
			INSERT INTO Purchases values(pur#.NEXTVAL, eid_in, pid_in, cid_in, qty_in, SYSDATE, total_price);
			UPDATE Products SET qoh = qoh - qty_in WHERE pid=pid_in;
			UPDATE Customers SET visits_made = visits_made + 1, last_visit_date = SYSDATE WHERE cid=cid_in;
			
			IF (curr_product.qoh-qty_in <= curr_product.qoh_threshold) THEN
				isOrdered:=1;
				newlyAdded:=newQuant;
				update_purchase(curr_product.pid, newlyAdded);
				newQuant:=newlyAdded;
			END IF;
		ELSIF (qty_in > curr_product.qoh) THEN
			RAISE insuff_qoh;
		ELSIF (qty_in < 0) THEN
			RAISE less_than_zero;
		END IF;
			
	EXCEPTION
	WHEN less_than_zero THEN
		RAISE_APPLICATION_ERROR(-20102, 'NEGATIVE NUMBER');
	WHEN insuff_qoh THEN
		RAISE_APPLICATION_ERROR(-20103, 'Insufficient quantity in stock');
	WHEN NO_DATA_FOUND THEN
		RAISE_APPLICATION_ERROR(SQLCODE, SQLERRM);
	WHEN OTHERS THEN
		RAISE_APPLICATION_ERROR(SQLCODE, SQLERRM);
	END;

	PROCEDURE update_purchase(pid_in IN Products.pid%TYPE, newQuant IN OUT NUMBER)
	IS
		curr_product Products%ROWTYPE;
		sup_id suppliers.sid%TYPE;
		updatedQuant Supply.quantity%TYPE;
	BEGIN
		SELECT * INTO curr_product FROM Products WHERE pid=pid_in;
		
		updatedQuant := curr_product.qoh + curr_product.qoh_threshold + 10;
		newQuant:=curr_product.qoh + curr_product.qoh_threshold + 10;
		UPDATE products SET qoh = updatedQuant where pid=pid_in; 
		
		SELECT sid INTO sup_id FROM (SELECT sid, SUM(quantity) FROM supply WHERE pid=pid_in GROUP BY sid ORDER BY SUM(quantity) asc) WHERE rownum = 1;

		INSERT into SUPPLY values(sup#.NEXTVAL,pid_in,sup_id,sysdate,updatedQuant);
	END;

END;
/
show error
