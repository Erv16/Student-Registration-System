create or replace package project2_pkg as
-- answer 2
  type ref_cursor is ref cursor;
  function show_students return ref_cursor;
  function show_TAs return ref_cursor;
  function show_courses return ref_cursor;
  function show_classes return ref_cursor;
  function show_enrollments return ref_cursor;
  function show_prerequisites return ref_cursor;
  function show_logs return ref_cursor;
-- answer 3
   type TA_ref_cursor is ref cursor;
   function Show_TA(choosed_class_id in classes.classid%type) 
   return TA_ref_cursor;
-- answer 4
   type prerequisite_record_type is record
  (
    dept_code prerequisites.pre_dept_code%type,
    course# prerequisites.pre_course#%type,
    des VARCHAR2(30)
  );
  type prerequisite_table_type is table of prerequisite_record_type;
  function getprerequisites(dept_code_in in prerequisites.dept_code%type, course#_in in prerequisites.course#%type)
  return prerequisite_table_type PIPELINED;
-- answer 5
  procedure enroll_student(sid in students.B#%type,cid in classes.classid%type);
-- answer 6
  procedure drop_class(sid in students.B#%type,cid in classes.classid%type);
-- answer 7
  procedure delete_students(s_B# in students.B#%type);
end;
/
show errors;

create or replace package body project2_pkg as
-- answer 2
--students
function show_students
  return ref_cursor is rc ref_cursor;
begin
  open rc for
  select * from students;
  return rc;
end;
--TAs
function show_TAs
   return ref_cursor is rc ref_cursor;
begin
  open rc for
  select * from TAs;
  return rc;
end;
--Courses
function show_courses
   return ref_cursor is rc ref_cursor;
begin
  open rc for
  select * from Courses;
  return rc;
end;
--Classes
function show_classes
   return ref_cursor is rc ref_cursor;
begin
  open rc for
  select * from Classes;
  return rc;
end;
--Enrollments
function show_enrollments
   return ref_cursor is rc ref_cursor;
begin
  open rc for
  select * from Enrollments;
  return rc;
end;
--Prerequisites
function show_prerequisites
   return ref_cursor is rc ref_cursor;
begin
  open rc for
  select * from Prerequisites;
  return rc;
end;
--Logs
function show_logs
   return ref_cursor is rc ref_cursor;
begin
  open rc for
  select * from Logs;
  return rc;
end;
-- answer 3
  function Show_TA(choosed_class_id in classes.classid%type) 
   return TA_ref_cursor 
   is
   trc TA_ref_cursor;
   choosed_B# Students.B#%TYPE;
 begin
    select TA_B# into choosed_B#  from Classes where classid = choosed_class_id;
  
    if(choosed_B# is not null) then
      dbms_output.put_line('');
    else
      dbms_output.put_line('The class has no TA');
    end if;
    
    open trc for select B#, first_name, last_name from Students s where B# = choosed_B#;
    return trc;
 end;
-- answer 4
  procedure getprerequities_inner
 (dept_code_in prerequisites.dept_code%type, course#_in prerequisites.course#%type, 
  depth_in number,prerequisite_table in out prerequisite_table_type)
 is
  CURSOR cur_pre( dept_code_in prerequisites.dept_code%type, course#_in prerequisites.course#%type) IS 
  SELECT PRE_DEPT_CODE, PRE_COURSE# FROM prerequisites 
  WHERE dept_code = dept_code_in and course# = course#_in;
  dep number;
 begin
   dep := depth_in + 1;
   FOR l_pre IN cur_pre(dept_code_in,course#_in)
   LOOP
    prerequisite_table.extend(1);
    IF(dep = 1)
      THEN
        prerequisite_table(prerequisite_table.count).des := 'direct';
        --DBMS_OUTPUT.PUT_LINE(l_pre.PRE_DEPT_CODE||' '||l_pre.PRE_COURSE#||' '||'direct'); 
      ELSE 
        prerequisite_table(prerequisite_table.count).des := 'indirect';
        --DBMS_OUTPUT.PUT_LINE(l_pre.PRE_DEPT_CODE||' '||l_pre.PRE_COURSE#||' '||'indirect'); 
    END IF;
    prerequisite_table(prerequisite_table.count).dept_code := l_pre.PRE_DEPT_CODE;
    prerequisite_table(prerequisite_table.count).course# := l_pre.PRE_COURSE#;
    project2_pkg.getprerequities_inner(l_pre.PRE_DEPT_CODE,l_pre.PRE_COURSE#, dep, prerequisite_table);
   END LOOP;
  end;

  function getprerequisites(dept_code_in in prerequisites.dept_code%type, course#_in in prerequisites.course#%type) 
  return prerequisite_table_type PIPELINED
  AS
    prerequisite_table prerequisite_table_type := prerequisite_table_type();
    cur_rec prerequisite_record_type;
  begin
    project2_pkg.getprerequities_inner(dept_code_in, course#_in, 0, prerequisite_table); 
      for i in prerequisite_table.first .. prerequisite_table.last
    loop
      DBMS_OUTPUT.PUT_LINE(
      prerequisite_table(i).dept_code || ' ' || prerequisite_table(i).course# ||' '||prerequisite_table(i).des);
      cur_rec :=  prerequisite_table(i);
      PIPE ROW(cur_rec);
    end loop;
    --return prerequisite_table;
  end;
-- answer 5

procedure enroll_student(sid in students.B#%type,cid in classes.classid%type) as
s_id students.B#%type;
c_id classes.classid%type;
sem classes.semester%type;
y classes.year%type;
c_size classes.class_size%type;
c_limit classes.limit%type;
c number;
p_dept_code classes.dept_code%TYPE;
p_course# classes.course#%TYPE;
invalid_year exception;
student_present exception;
class_full exception;
overload exception;
prerequisite exception;
more_than_five exception;

begin

begin 
select B# into s_id from students where B# = sid;
exception
When no_data_found then
dbms_output.put_line('The B# is invalid');
return;
end;

begin
select classid into c_id from classes where classid = cid;
exception
When no_data_found then
dbms_output.put_line('The Classid is invalid');
return;
end;

begin
select classid,semester,year into c_id,sem,y from classes where classid = cid;
if(sem != 'Fall' or y != '2018') then raise invalid_year;
end if;
exception
when invalid_year then 
dbms_output.put_line('Cannot enroll into a class from a previous semester.');
return;
end;

begin
select count(*) into c from enrollments where B# = sid and classid = cid;
if(c > 0) then raise student_present;
end if;
exception
when student_present then
dbms_output.put_line('The Student is already in the class');
return;
end;

begin
select class_size,limit into c_size,c_limit from classes where classid = cid;
if(c_size >= c_limit) then raise class_full;
end if;
exception
when class_full then
dbms_output.put_line('The class is already full.');
return;
end;

begin
select count(B#) into c from enrollments where B# = sid;
if(c = 4) then raise overload;
end if;
exception
when overload then
dbms_output.put_line('The student will be overloaded with the new enrollment.');
insert into enrollments values(sid,cid,null);
return;
end;


begin
select count(B#) into c from enrollments where B# = sid;
if(c >= 5) then raise more_than_five;
end if;
exception
when more_than_five then
dbms_output.put_line('Students cannot be enrolled in more than five classes in the same semester.');
return;
end;


begin
SELECT count(*) INTO c FROM Enrollments WHERE lgrade IN ('A', 'A-', 'B','B+', 'B-', 'C') and classid in (SELECT classid FROM Classes WHERE (dept_code, course#) in (SELECT pre_dept_code, pre_course# FROM Prerequisites WHERE dept_code = p_dept_code and course# = p_course#));
IF c <> 0 THEN raise prerequisite;
end if;
exception
when prerequisite then 
dbms_output.put_line('Prerequisite not satisfied'); 
return;
end;


insert into enrollments values(sid,cid,null);
dbms_output.put_line('This student successfully enrolled in the given class');
end;



-- answer 6
procedure drop_class(sid in students.B#%type,cid in classes.classid%type) as
s_id students.B#%type;
c_id classes.classid%type;
sem classes.semester%type;
y classes.year%type;
c_size classes.class_size%type;
c_course# classes.course#%type;
c number;
invalid_year exception;
e1 exception;
e2 exception;
student_not_enrolled exception;
prerequisite exception;
begin

begin 
select B# into s_id from students where B# = sid;
exception
When no_data_found then
dbms_output.put_line('The B# is invalid');
return;
end;

begin
select classid into c_id from classes where classid = cid;
exception
When no_data_found then
dbms_output.put_line('The Classid is invalid');
return;
end;

begin
select count(*) into c from enrollments where B# = sid and classid = cid;
if(c = 0) then raise student_not_enrolled;
end if;
exception
when student_not_enrolled then
dbms_output.put_line('The student is not enrolled in the class.');
return;
end;

begin
select classid,semester,year into c_id,sem,y from classes where classid = cid;
if(sem != 'Fall' or y != '2018') then raise invalid_year;
end if;
exception
when invalid_year then
dbms_output.put_line('Only enrollment in the current semester can be dropped.');
return;
end;

begin
select course# into c_course# from classes where classid= cid;
begin 
select count(*) into c from courses where course# in (select p1.course# from prerequisites p1,prerequisites p2 where p2.course# = p1.pre_course#
      start with p1.pre_course# = c_course#
      connect by prior p1.course# = p1.pre_course#);
                                                                             
if(c > 0) then raise prerequisite;
end if;
exception
when prerequisite then 
dbms_output.put_line('The drop is not permitted because another class the student registered uses it as a prerequisite.');
return;
end;
end;

delete from enrollments where B# = sid and classid = cid;
dbms_output.put_line('This student dropped successfully from the given class');
 
begin
select count(B#) into c from enrollments where B# = sid;
if(c = 0) then raise e1;
end if;
exception
when e1 then
dbms_output.put_line('This student is not enrolled in any classes.');
end;


begin
select class_size into c_size from classes where classid = cid;
if (c_size = 0) then raise e2;
end if;
exception
when e2 then
dbms_output.put_line('The class now has no students.');
end;


end;

-- answer 7
procedure delete_students(s_B# in students.B#%type) as
invalid_B# exception;
begin
delete from students where B# = s_B#;
if sql%notfound then
raise invalid_B#;
end if;
exception 
when invalid_B# 
then 
dbms_output.put_line('B# is invalid');
end;

end;
/
show errors
