CRUD Application for Students database

***DESCRIPTION***

This Java application is designed to manage a PostgreSQL database "Students" for student records. It provides basic CRUD (Create, Read, Update, Delete) 
functionalities, allowing users to interact with the database to perform operations such as adding new student records, updating existing ones, 
deleting students, and viewing all student records.

***PREREQUISITES***

- Java Development Kit (JDK) 8 or later
- PostgreSQL database server

Create a database in postgres called "Students" and create a new table using the following SQL query:

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
in postgres directly without copy pasting into the quesry tool*

***HOW TO RUN**

This application can either be 

