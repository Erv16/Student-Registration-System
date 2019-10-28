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

   	PROCEDURE get_student_class_information(sid_in IN students.sid%TYPE, error_msg OUT varchar2, rc_cursor OUT SYS_REFCURSOR);

	PROCEDURE get_class_information(classid_in IN classes.classid%TYPE, error_msg OUT varchar2, rc_cursor OUT SYS_REFCURSOR);

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

PROCEDURE get_student_class_information(sid_in IN students.sid%TYPE, error_msg OUT varchar2, rc_cursor OUT SYS_REFCURSOR) IS
 	student_exists number;
 	student_enrolled number;
 BEGIN
 	SELECT COUNT(sid) INTO student_exists FROM students WHERE sid = sid_in;
 	SELECT COUNT(sid) INTO student_enrolled FROM enrollments WHERE sid = sid_in;

 	IF student_exists = 0 THEN
 		error_msg := 'The sid is invalid';
 	ELSIF student_enrolled = 0 THEN
 		error_msg := 'The student has not taken any course';
 	ELSE		

	    OPEN rc_cursor FOR
	    SELECT s.sid, s.lastname, s.status, cl.classid, CONCAT(cl.dept_code, cl.course_no) AS course, c.title, cl.year, cl.semester 
		FROM students s
		INNER JOIN enrollments e
		ON s.sid = e.sid
		INNER JOIN classes cl
		ON e.classid = cl.classid
		INNER JOIN courses c
		ON cl.dept_code = c.dept_code AND cl.course_no = c.course_no
		WHERE s.sid = sid_in;
	END IF;
END;

PROCEDURE get_class_information(classid_in IN classes.classid%TYPE, error_msg OUT varchar2, rc_cursor OUT SYS_REFCURSOR) IS
	class_exists number;
	students_enrolled number;
BEGIN
	SELECT COUNT(classid) INTO class_exists FROM classes WHERE classid = classid_in;
	SELECT COUNT(e.sid) INTO students_enrolled FROM classes cl, enrollments e WHERE cl.classid = classid_in AND cl.classid = e.classid;

	IF class_exists = 0 THEN
		error_msg := 'The class is invalid';
	ELSIF students_enrolled = 0 THEN
		error_msg := 'No student is enrolled in the class';
	ELSE
		OPEN rc_cursor FOR
		SELECT cl.classid, c.title, cl.semester, cl.year, s.sid, s.lastname
		FROM classes cl
		INNER JOIN courses c
		ON cl.dept_code = c.dept_code AND cl.course_no = c.course_no
		INNER JOIN enrollments e
		ON e.classid = cl.classid
		INNER JOIN students s
		ON e.sid = s.sid
		WHERE cl.classid = classid_in;
	END IF;
END;

PROCEDURE add_student(sid_in IN students.sid%TYPE, firstname_in IN students.firstname%TYPE, lastname_in IN students.lastname%TYPE, status_in IN students.status%TYPE, gpa_in IN students.gpa%TYPE, email_in IN students.email%TYPE) AS
BEGIN
 INSERT INTO students(sid, firstname, lastname, status, gpa, email) VALUES(sid_in, firstname_in, lastname_in, status_in, gpa_in, email_in);
END;

END;
/
show error;