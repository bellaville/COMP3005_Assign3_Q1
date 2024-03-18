***CRUD Application for Students database***

***DESCRIPTION***

This Java application is designed to manage a PostgreSQL database "Students" for student records. It provides basic CRUD (Create, Read, Update, Delete) 
functionalities, allowing users to interact with the database to perform operations such as adding new student records, updating existing ones, 
deleting students, and viewing all student records.

***PREREQUISITES***

- Java Development Kit (JDK) 8 or later
- PostgreSQL database server

Create a database in postgres called "Students" and create a new table called "students" using the following SQL query:

CREATE TABLE students
	(
		student_id SERIAL PRIMARY KEY,
		first_name TEXT NOT NULL,
		last_name TEXT NOT NULL,
		email TEXT NOT NULL UNIQUE,
		enrollment_date DATE
	);

 Populate the database with this initial data using the following SQL query:

 INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');

*These commands are also included as .sql files in the Students_DB_SQL_files folder in this repository if you wish to download and run them 
in postgres directly without copy pasting them into the query tool*

***HOW TO RUN***

This application can either be imported into Intellij to be run, or it can be run from command line using the included .jar file "COMP3005_Assign3_Q1.jar"

To run this project in intellij:

- open intellij and select the option "Get from VCS"
- select git as the version control and paste the following url into the URL field:
  https://github.com/bellaville/COMP3005_Assign3_Q1.git
- Select the folder you would like the project to be in
- build the project and run it

To run this project using the .jar file

- download the .jar file "COMP3005_Assign3_Q1.jar" from the repository
- open command prompt by pressing windows key and typing "cmd"
- navigate to the directory where the jar file is located
- type the following command to run the .jar file:

  java -jar COMP3005_Assign3_Q1.jar

- you can now interact with the application from command prompt

***HOW TO USE***

After starting the application, you will be prompted to enter your PostgreSQL database URL, username, and password. 
Once connected, you can use the following commands:

- get: Displays all student records.
- add: Adds a new student. You will be prompted to enter the student's first name, last name, email, and enrollment date.
- updateEmail: Updates the email address of a student. You will need to provide the student ID and the new email address.
- remove: Deletes a student record. You will need to provide the student ID of the record you wish to delete.
- quit: Exits the application.

***IMPORTANT NOTES***

Ensure your PostgreSQL service is running before starting the application.

The application assumes the existence of a students table within the Students database. 
Please adjust the SQL queries accordingly if your table structure differs.




	

