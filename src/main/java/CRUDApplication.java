import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
Class CRUDApplication connects with a postgresql database called "Students" and provides CRUD operations for the
database that can display all student records in the database, add a student, remove a student, and update the email of
a specified student. This class is runnable has a main method which creates a new instance of CRUDApplication.

Upon creating an instance of CRUDApplication the user will be prompted to enter one of 5 commands including 'quit'
'get', 'add', 'updateEmail', and 'remove'.
 */

public class CRUDApplication {

    private static final String url = "jdbc:postgresql://localhost:5432/Students";
    private static final String user = "postgres";
    private static final String password = "Sitbacmm#D47aaa";
    private static Connection connection;

    /*
    Creates a CRUDApplication object which prompts the user for commands until 'quit' is entered. Commands include
    'get' which displays all records and attributes present in the database
    'add' which prompts the user for student info then adds a new student
    'updateEmail' which updates the email of a student with the specified student_id (only if the email is unique)
    'remove' which removes a student with the specified student_id.
    */
    public CRUDApplication(){ // constructor for the application
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password); // establish connection with postgresql
            if(connection != null){
                System.out.println("Connected to database successfully!\n");
            }else{
                System.out.println("Failed to connect to database");
            }
            Scanner scanner = new Scanner(System.in); // create scanner to read user input
            String input = "";
            while(!input.equals("quit")){ // display all possible commands to the user
                System.out.print("Here are the possible commands:" +
                        "\n'quit' to exit\n'get' to get all student records" +
                        "\n'add' to add a student\n'updateEmail' to update a student's email" +
                        "\n'remove' to delete a student\nEnter command: ");
                input = scanner.nextLine();
                if(input.equals("get")){ // if input is 'get' call getAllStudents() method
                    getAllStudents();
                }else if(input.equals("add")) { // if input is 'add' proceed to prompt user for student info

                    java.sql.Date enrollmentDate = null; // initialize the date to null

                    // ask user for name details and email (allow for empty values)
                    System.out.print("Enter first name: ");
                    String first = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String last = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String email = null;
                    while(email == null){
                        email = scanner.nextLine();
                        if(!isValidEmail(email)){
                            email = null;
                            System.out.print("non-unique email entered. Please try again!: ");
                        }
                    }

                    System.out.println("Enter enrollment date (YYYY-MM-DD): ");   // ask user for enrollment date

                    while(enrollmentDate == null) { // keep asking for input until date format is correct
                        String enrollmentDateStr = scanner.nextLine();
                        try {
                            enrollmentDate = java.sql.Date.valueOf(enrollmentDateStr); // check if date is the correct format

                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        }
                    }

                    addStudent(first, last, email, enrollmentDate); // call addStudent() method with collected parameters

                }else if(input.equals("updateEmail")){ // if input is 'updateEmail' prompt user for student id
                    int student_id = -1;

                    System.out.print("Enter student id whose email you wish to update (integer value): ");

                    while(student_id == -1){
                        String id = scanner.nextLine();
                    try{
                        student_id = Integer.parseInt(id);
                        if (!isValidID(student_id)) { // if the id is not valid, set it back to -1 and try again
                            System.out.print("ID not present in database. Please try again!: ");
                            student_id = -1; // Reset student_id
                        }
                    }catch(NumberFormatException e){
                        System.out.print("Not an integer. Please try again!: ");
                        }
                    }
                    System.out.print("Enter updated email address: ");
                    String email = null;
                    while(email == null){
                        email = scanner.nextLine();
                        if(!isValidEmail(email)){
                            email = null;
                            System.out.print("non-unique email entered. Please try again!: ");
                        }
                    }

                    updateStudentEmail(student_id, email);

                }else if(input.equals("remove")){ // if input is 'remove' prompt user for student id to delete
                    int student_id = -1;

                    System.out.print("Enter student id of student you wish to delete (integer value): ");

                    while(student_id == -1){
                        String id = scanner.nextLine();
                        try{
                            student_id = Integer.parseInt(id);
                            if (!isValidID(student_id)) {
                                System.out.print("ID not present in database. Please try again!: ");
                                student_id = -1; // Reset student_id
                            }
                        }catch(NumberFormatException e){
                            System.out.print("Not an integer. Please try again!: ");
                        }
                    }

                    deleteStudent(student_id);

                }else if(!input.equals("quit")){
                    System.out.println("invalid command: " + input + "\n"); // invalid command given
                }
            }
            connection.close(); // close the connection

        }
        catch(Exception e){
            System.out.println(e);
        }

    }
    /*
    Returns true if the argument 'id' is present in the database
    @param id: student id being checked for
    @return true if a record exists with student_id = id
    */
    private boolean isValidID(int id){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try{
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT student_id FROM students");
            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next()){
                ids.add(resultSet.getInt("student_id"));
            }

        }catch(Exception e){
            System.out.println(e);
        }
        return ids.contains(id);
    }
    /*
    Returns true if the argument 'email' is a unique email based on emails currently in the database
    @param email: email address being tested to see if it is unique
    @return true if email is unique and false otherwise
    */
    private boolean isValidEmail(String email){
        ArrayList<String> emails = new ArrayList<String>();
        try{
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT email FROM students"); // Only select emails
            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next()){
                emails.add(resultSet.getString("email"));
            }

        } catch(Exception e){
            System.out.println(e);
        }
        return !emails.contains(email); // Return true if the email is not present in the database
    }

    /*
    Prints out all attributes of all student records in the database.
     */
    public void getAllStudents(){
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("SELECT * FROM students ORDER BY student_id ASC");
            ResultSet resultSet = statement.getResultSet();

            System.out.println("student_id\tfirst_name\tlast_name\temail\tenrollment_date\n");

            while (resultSet.next()) {
                System.out.print(resultSet.getString("student_id") + "\t");
                System.out.print(resultSet.getString("first_name") + "\t");
                System.out.print(resultSet.getString("last_name") + "\t");
                System.out.print(resultSet.getString("email") + "\t");
                System.out.print(resultSet.getString("enrollment_date"));
                System.out.println("\n");
            }

        }catch(Exception e){
            System.out.println(e);
        }

    }
    /*
    Adds a student with the specified attribute values.
    @param first_name: the first name of the student
    @param last_name: the last name of the student
    @param email: email address of the student
    @param enrollment_date: the date of enrollment of the student
     */
    public void addStudent(String first_name, String last_name, String email, java.sql.Date enrollment_date){
        PreparedStatement preparedStatement = null;
        try{
            String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, enrollment_date);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("student added successfully!");
            }else{
                System.out.println("Error inserting student");
            }

        }catch(Exception e){
            System.out.println(e);
        }

    }
    /*
    Updates the email of the student with id student_id.
    @param student_id: id of student whose email will be updated
    @param new_email: the email to replace the old email
     */
    public void updateStudentEmail(int student_id, String new_email){
        PreparedStatement preparedStatement = null;
        try{
            String sql = "UPDATE students SET email = ? WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, new_email);
            preparedStatement.setInt(2, student_id);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("email updated successfully! student with id: " + student_id + "\n");
            }else{
                System.out.println("Error updating email");
            }

        }catch(Exception e){
            System.out.println(e);
        }

    }
    /*
    Deletes student with student_id student_id.
    @param student_id: id of student to be deleted
     */
    private void deleteStudent(int student_id){
        PreparedStatement preparedStatement = null;
        try{
            String sql = "DELETE FROM students WHERE student_id=?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, student_id);

            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("student with id: " + student_id + " deleted successfully!\n");
            }else{
                System.out.println("Error deleting student");
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
    /*
    Main method for creating an instance of CRUDApplication.
     */
    public static void main(String[] args) {
        CRUDApplication crud = new CRUDApplication();

    }
}
