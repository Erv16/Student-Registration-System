DROP SEQUENCE logid;

CREATE SEQUENCE logid
START WITH 1000
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER logs_insert_student
AFTER INSERT ON students
FOR EACH ROW
BEGIN
INSERT INTO logs VALUES(logid.NEXTVAL, 'Erwin', SYSDATE, 'students', 'insert', :NEW.sid);
END;
/

CREATE OR REPLACE TRIGGER logs_insert_enrollments
AFTER INSERT ON enrollments
FOR EACH ROW
BEGIN
INSERT INTO logs VALUES(logid.NEXTVAL, 'Erwin', SYSDATE, 'enrollments', 'insert', :NEW.sid || '	' || :NEW.classid);
END;
/

CREATE OR REPLACE TRIGGER logs_delete_student
AFTER DELETE ON students
FOR EACH ROW
BEGIN 
INSERT INTO logs VALUES(logid.NEXTVAL, 'Erwin', SYSDATE, 'students', 'delete', :OLD.sid);
END;
/

CREATE OR REPLACE TRIGGER logs_delete_enrollments
AFTER DELETE ON enrollments
FOR EACH ROW
BEGIN
INSERT INTO logs VALUES(logid.NEXTVAL, 'Erwin', SYSDATE, 'enrollments', 'delete', :OLD.sid || ' ' || :OLD.classid);
END;
/

CREATE OR REPLACE TRIGGER student_enrollment_in_class
AFTER INSERT ON enrollments
FOR EACH ROW
BEGIN
UPDATE classes SET class_size = class_size + 1 WHERE classes.classid = :NEW.classid;
END;
/

CREATE OR REPLACE TRIGGER drop_student_class
AFTER DELETE ON enrollments
FOR EACH ROW
BEGIN 
UPDATE classes SET class_size = class_size - 1 WHERE classes.classid = :OLD.classid;
END;
/

CREATE OR REPLACE TRIGGER drop_student_enrollment
BEFORE DELETE ON students
FOR EACH ROW
BEGIN
DELETE FROM enrollments WHERE sid = :OLD.sid;
END;
/ 

