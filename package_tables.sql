set serveroutput on;

CREATE OR REPLACE PACKAGE student_registration AS
	TYPE ref_cursor is ref cursor;
	FUNCTION show_students RETURN ref_cursor;
	FUNCTION show_courses RETURN ref_cursor; 
	FUNCTION show_pre_requisites RETURN ref_cursor; 
	FUNCTION show_classes RETURN ref_cursor; 
	FUNCTION show_enrollments RETURN ref_cursor; 
	FUNCTION show_logs RETURN ref_cursor; 

	PROCEDURE add_student(sid_in IN students.sid%TYPE, firstname_in IN students.firstname%TYPE, lastname_in IN students.lastname%TYPE, status_in IN students.status%TYPE, gpa_in IN students.gpa%TYPE, email_in IN students.email%TYPE);
END;
/

CREATE OR REPLACE PACKAGE BODY student_registration AS

FUNCTION show_students RETURN ref_cursor AS rc ref_cursor;
BEGIN
	OPEN rc FOR
		SELECT * FROM students;
	RETURN rc;
END;

FUNCTION show_courses RETURN ref_cursor AS rc ref_cursor;
BEGIN
	OPEN rc FOR
		SELECT * FROM courses;
	RETURN rc;
END;

FUNCTION show_pre_requisites RETURN ref_cursor AS rc ref_cursor;
BEGIN
	OPEN rc FOR
		SELECT * FROM prerequisites;
	RETURN rc;
END;

FUNCTION show_classes RETURN ref_cursor AS rc ref_cursor;
BEGIN
	OPEN rc FOR
		SELECT * FROM classes;
	RETURN rc;
END;

FUNCTION show_enrollments RETURN ref_cursor AS rc ref_cursor;
BEGIN
	OPEN rc FOR
		SELECT * FROM enrollments;
	RETURN rc;
END;

FUNCTION show_logs RETURN ref_cursor AS rc ref_cursor;
BEGIN
	OPEN rc FOR
		SELECT * FROM logs;
	RETURN rc;
END;

PROCEDURE add_student(sid_in IN students.sid%TYPE, firstname_in IN students.firstname%TYPE, lastname_in IN students.lastname%TYPE, status_in IN students.status%TYPE, gpa_in IN students.gpa%TYPE, email_in IN students.email%TYPE) AS
BEGIN
 INSERT INTO students(sid, firstname, lastname, status, gpa, email) VALUES(sid_in, firstname_in, lastname_in, status_in, gpa_in, email_in);
END;

END;
/
show error;