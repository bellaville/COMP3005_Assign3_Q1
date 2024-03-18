-- SQL code for Student database for COMP 3005 assignment 3
-- Name: Anabella Villeneuve
-- Student ID: 101182327

CREATE TABLE students
	(
		student_id SERIAL PRIMARY KEY,
		first_name TEXT NOT NULL,
		last_name TEXT NOT NULL,
		email TEXT NOT NULL UNIQUE,
		enrollment_date DATE
	);