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

	PROCEDURE process_prerequisites(dept_code_in IN prerequisites.dept_code%TYPE, course_no_in IN prerequisites.course_no%TYPE, error_msg OUT VARCHAR2, rc_cursor OUT SYS_REFCURSOR);

	PROCEDURE get_prerequisites(rc_cursor OUT SYS_REFCURSOR);

	PROCEDURE prerequisites_grade_check(dept_code_in IN prerequisites.dept_code%TYPE, course_no_in IN prerequisites.course_no%TYPE);

	PROCEDURE enroll_student(sid_in IN students.sid%TYPE, classid_in IN classes.classid%TYPE, error_msg OUT varchar2);

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

PROCEDURE process_prerequisites(dept_code_in IN prerequisites.dept_code%TYPE, course_no_in IN prerequisites.course_no%TYPE, error_msg OUT VARCHAR2, rc_cursor OUT SYS_REFCURSOR) IS
	CURSOR prereq_cursor IS
		SELECT pre_dept_code, pre_course_no FROM prerequisites
		WHERE dept_code = dept_code_in AND course_no = course_no_in;

	temp_cursor prereq_cursor%ROWTYPE;

	BEGIN
			INSERT INTO prereq_courses_temp_table SELECT pre_dept_code, pre_course_no FROM prerequisites WHERE dept_code = dept_code_in AND course_no = course_no_in;
			OPEN prereq_cursor;
			LOOP
				FETCH prereq_cursor INTO temp_cursor;
				EXIT WHEN prereq_cursor%NOTFOUND;
				process_prerequisites(temp_cursor.pre_dept_code,temp_cursor.pre_course_no,error_msg,rc_cursor);
			END LOOP;
			get_prerequisites(rc_cursor);
			CLOSE prereq_cursor;
	END;

PROCEDURE get_prerequisites(rc_cursor OUT SYS_REFCURSOR) IS
	BEGIN
		OPEN rc_cursor FOR SELECT CONCAT(dept_code,course_no) AS "prerequisite courses" FROM prereq_courses_temp_table;
	END;

PROCEDURE prerequisites_grade_check(dept_code_in IN prerequisites.dept_code%TYPE, course_no_in IN prerequisites.course_no%TYPE) IS
	CURSOR prereq_grade_cursor IS
		SELECT pre_dept_code, pre_course_no FROM prerequisites
		WHERE dept_code = dept_code_in AND course_no = course_no_in;

	temp_cursor prereq_grade_cursor%ROWTYPE;

	BEGIN
			INSERT INTO prereq_courses_temp_table SELECT pre_dept_code, pre_course_no FROM prerequisites WHERE dept_code = dept_code_in AND course_no = course_no_in;
			OPEN prereq_grade_cursor;
			LOOP
				FETCH prereq_grade_cursor INTO temp_cursor;
				EXIT WHEN prereq_grade_cursor%NOTFOUND;
				prerequisites_grade_check(temp_cursor.pre_dept_code,temp_cursor.pre_course_no);
			END LOOP;
			CLOSE prereq_grade_cursor;
	END;

PROCEDURE enroll_student(sid_in IN students.sid%TYPE, classid_in IN classes.classid%TYPE, error_msg OUT VARCHAR2) AS
student_exists number;
class_exists number;
student_enrolled NUMBER;
cl_size classes.class_size%TYPE;
cl_limit classes.class_size%TYPE;
cl_year classes.year%TYPE;
cl_semester classes.semester%TYPE;
cl_dept_code classes.dept_code%TYPE;
cl_course_no classes.course_no%TYPE;
courses_enrolled NUMBER;

BEGIN

	SELECT COUNT(*) INTO student_exists FROM students WHERE students.sid = sid_in;
	SELECT COUNT(*) INTO class_exists FROM classes WHERE classes.classid = classid_in;
	

	IF(student_exists = 0) THEN
		error_msg := 'The sid is invalid';
	ELSIF(class_exists = 0) THEN
		error_msg := 'The class is invalid';
	ELSIF(student_enrolled > 0) THEN
		error_msg := 'The student is already in this class';
	ELSIF(cl_size >= cl_limit) THEN
		error_msg := 'The class is full';
	END IF;

	IF(student_exists = 0) THEN
		error_msg := 'The sid is invalid';
	ELSE
		IF(class_exists = 0) THEN
			error_msg := 'The class is invalid';
		ELSE
			SELECT class_size, limit INTO cl_size, cl_limit FROM classes WHERE classid = classid_in;
			IF(cl_size + 1 > cl_limit) THEN
				error_msg := 'The class is full';
			ELSE
				SELECT COUNT(*) INTO student_enrolled FROM enrollments WHERE sid = sid_in AND classid = classid_in;
				IF(student_enrolled > 0) THEN
					error_msg := 'The student is already in this class';
				ELSE
					SELECT dept_code, course_no INTO cl_dept_code, cl_course_no FROM classes WHERE classid = classid_in;
					prerequisites_grade_check(cl_dept_code, cl_course_no);
					SELECT COUNT(*) INTO courses_enrolled FROM enrollments e WHERE e.sid = sid_in AND e.lgrade NOT IN ('D','F','I') AND e.lgrade IS NOT NULL AND e.classid IN (SELECT classid FROM classes WHERE (dept_code,course_no) IN(SELECT dept_code, course_no FROM prereq_courses_temp_table));
					IF(courses_enrolled > 0) THEN
						SELECT year, semester INTO cl_year, cl_semester FROM classes WHERE classid = classid_in;
						SELECT COUNT(*) INTO courses_enrolled FROM enrollments WHERE sid = sid_in AND classid IN(
						SELECT classid FROM classes WHERE year = cl_year AND semester = cl_semester AND classid <> classid_in
						);
						IF(courses_enrolled >= 4) THEN
							error_msg := 'Students cannot be enrolled in more than four classes in the same semester';
						ELSE
							IF(courses_enrolled = 3) THEN
								error_msg := 'You are overloaded';
								INSERT INTO enrollments VALUES(sid_in, classid_in, null);
							ELSE
								INSERT INTO enrollments VALUES(sid_in, classid_in, null);
							END IF;
						END IF;
					ELSE
						error_msg := 'Prerequisite not satisfied';
					END IF;	
				END IF;
			END IF;
		END IF;
	END IF;
END;

END;
/
show error;
