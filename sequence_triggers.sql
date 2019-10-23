DROP SEQUENCE logid;

CREATE SEQUENCE logid
START WITH 1000
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER logs_insert_student
AFTER INSERT ON students
FOR EACH ROW
BEGIN
INSERT INTO logs VALUES(logid.NEXTVAL, 'Erwin', SYSDATE, 'students', 'insert', 'sid');
END;
/

CREATE OR REPLACE TRIGGER logs_insert_enrollments
AFTER INSERT ON enrollments
FOR EACH ROW
BEGIN
INSERT INTO logs VALUES(logid.NEXTVAL, 'Erwin', SYSDATE, 'enrollments', 'insert', 'sid' || ' ' || 'classid');
END;
/

CREATE OR REPLACE TRIGGER logs_delete_student
AFTER DELETE ON students
FOR EACH ROW
BEGIN 
INSERT INTO logs VALUES(logid.NEXTVAL, 'Erwin', SYSDATE, 'students', 'delete', 'sid');
END;
/

CREATE OR REPLACE TRIGGER logs_delete_enrollments
AFTER DELETE ON enrollments
FOR EACH ROW
BEGIN
INSERT INTO logs VALUES(logid.NEXTVAL, 'Erwin', SYSDATE, 'enrollments', 'delete', 'sid' || ' ' || 'classid');
END;
/