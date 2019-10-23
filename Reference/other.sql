

--answer 1
CREATE SEQUENCE log#
START WITH 100
INCREMENT BY 1;


--answer 5

create or replace trigger update_class_size
after insert on enrollments
for each row 
declare
begin
update classes
set class_size = class_size + 1
where classid = :new.classid;
end;
/


--answer 6

create or replace trigger update_class_on_drop
after delete on enrollments
for each row 
declare
begin
update classes
set class_size = class_size - 1
where classid = :old.classid;
end;
/


--answer 7

create or replace trigger delete_enrollments
before delete on students
for each row 
begin
delete from enrollments where B# = :old.B#;
end;
/

--answer 8
create or replace trigger update_logs_on_delete_student
after delete on students
for each row
begin
insert into logs values(log#.nextval,'Yash',sysdate,'students','delete','B#');
end;
/

create or replace trigger logs_delete_enrollments
after delete on enrollments
for each row
begin
insert into logs values(log#.nextval,'Yash',sysdate,'enrollments','delete','B#'||','||'classid');
end;
/

create or replace trigger logs_insert_enrollments
after insert on enrollments
for each row
begin
insert into logs values(log#.nextval,'Yash',sysdate,'enrollments','insert','B#'||','||'classid');
end;
/


