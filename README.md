# **Student-Registration-System**
This project is to use Oracle's PL/SQL and JDBC to create an application to support typical student registration tasks at a university.

### **Steps to execute the project on the command line/terminal:**

Login to Oracle sqlplus
Type in the following commands
```
start proj2data2019
start sequence_triggers
start package_tables
```
3.	Exit from Oracle sqlplus
4.	Compile Java program using 
```
javac StudentRegistrationSystem.java
```
5.	Run the Java program using 
```
java StudentRegistrationSystem
```

In this project, the following tables will be used to from the Student Registration System : 

Students(sid, firstname, lastname, status, gpa, email)
Courses(dept_code, course_no, title)
Prerequisites(dept_code, course_no, pre_dept_code, pre_course_no)Classes(classid, dept_code, course_no, sect_no, year, semester, limit, class_size)
Enrollments(sid, classid, lgrade)
Logs(logid, who, time, table_name, operation, key_value)

Each tuple in the logs table describes who (the login name of a database user) has performed what operation (insert, delete, update) on which table (give the table name) and which tuple (as indicated by the value of the primary key of the tuple) at what time. Attribute logid is the primary key of the table

## **Functionalities Supported**
1. A sequence is used to generate a 5 digit logid automatically when new log records are inserted into the logs table.
2. Students can be added to the Students table.
3. For a given student (with sid ie. Student Id provided as a parameter), returns the sid, lastname, and status of the student as well as all the classes the student has taken or is taking are listed. For each class, the classid, dept_code, course_no, title, year, and semester is displayed. (dept_code and course_no will be displayed together, e.g., CS532.) If the student is not in the students table, a message stating “The sid is invalid.” is reported. If the student has not taken any course, a message stating “The student has not taken any course.” is reported.
4. For a given course (with dept_code and course_no as parameters), returns all its prerequisite courses (show dept_code and course_no together as in CS532), including both direct and indirect prerequisite courses. If course C1 has course C2 as a prerequisite, C2 is a direct prerequisite. In addition, if C2 has course C3 has a prerequisite, then C3 is an indirect prerequisite for C1. Note: It also identifies and displays indirect prerequisites which could be more than two levels away.
5. For a given class (with classid provided as a parameter), returns the classid, course title, semester, and year of the class as well as all the students (show sid and lastname) who have taken or are taking the class are listed. If the class is not in the classes table, a message stating “The cid is invalid.” is reported. If no student has taken or is taking the class, a message stating, “No student is enrolled in the class.” is reported.
6. A student can also be enrolled into a class. The sid of the student and the classid of the class are provided as parameters. If the student is not in the students table, a message stating, “The sid is invalid.” is reported. If the class is not in the classes table, a message stating, “The classid is invalid.” is reported. If the enrollment of the student into a class would cause “class_size > limit”, reject the enrollment and report “The class is full.” If the student is already in the class, a message stating “The student is already in this class.” is reported. If the student is already enrolled in three other classes in the same semester and the same year, a warning message stating “You are overloaded.” is reported and the student to be enrolled. If the student is already enrolled in four other classes in the same semester and the same year, a message stating, “Students cannot be enrolled in more than four classes in the same semester.” is reported and the enrollment is rejected. If the student has not completed the required prerequisite courses with minimum grade “C-”, the enrollment is rejected and a message stating, “Prerequisite courses have not been completed.” is reported For all the other cases, the requested enrollment is performed. We make sure that all data are consistent after each enrollment. For example, after you successfully enrolled a student into a class, the size of the corresponding class is updated accordingly. Ttrigger(s) are used to implement the updates of values caused by successfully enrolling a student into a class. 
7. A student can be dropped from a class (i.e. delete the tuple from the enrollments table). The sid of the student and the classid of the class are provided as parameters. If the student is not in the students table, a message stating, “sid not found.” is reported. If the classid is not in the classes table, a message stating, “classid not found.” is reported. If the student is not enrolled in the class, a message stating, “The student is not enrolled in the class.” is reported. If dropping the student from the class would cause a violation of the prerequisite requirement for another class, then the drop attempt is rejected and a message stating, “The drop is not permitted because another class uses it as a prerequisite.” is reported. In all the other cases, the student is dropped from the class. If the class is the last class for the student, then a message stating, “This student is enrolled in no class.”  is reported. If the student is the last student in the class, then a message stating, “The class now has no students.” is reported. We make sure that all data are consistent after the drop and all updates caused by the drop are implemented using trigger(s).
8. A student can be deleted from the students table based on a given sid (as a parameter). If the student is not in the students table, a message stating, “sid not found.” is reported. When a student is deleted, all tuples in the enrollments table involving the student are also deleted (a trigger is used to implement this) and this will trigger a number of actions as described in #7.
9. Triggers are implemented to add tuples to the logs table automatically whenever a student is added to or deleted from the students table, or when a student is successfully enrolled into or dropped from a class (i.e., when a tuple is inserted into or deleted from the enrollments table).
10. The users also have an option to view one among the multiple tables tuples or display all of the table's contents.

## **Interface**
Implemented an interactive and menu-driven interface using Java and JDBC . More details are given below:

It is a text-based menu-driven interface. It first displays menu options for a user to select (e.g., 1 for displaying a table, 2 for enrolling a student into a class, …). The primary options have sub-options to select from. Once a final option is selected, the interface prompts the user to enter parameter values. As an example, for enrolling a student into a class, the parameter values include sid and classid. Then an operation corresponding to the selected option will be performed with the appropriate messages displayed. 



