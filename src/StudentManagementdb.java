import java.sql.*;
import java.util.Scanner;

public class StudentManagementdb {

    // Database connection parameters
    private static final String URL = "jdbc:postgresql://localhost:5432/studentsdb"; // link the pgadmin database through JDBC here
    private static final String USER = "postgres";  
    private static final String PASSWORD = "postgres"; 

    // Establishes connection to PostgreSQL database
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Retrieves and displays all records from the students table
    public static void getAllStudents() {
        String SQL = "SELECT * FROM students ORDER BY student_id";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            System.out.println("\n=== ALL STUDENTS ===");
            System.out.printf("%-12s %-15s %-15s %-30s %-15s%n",
                    "Student ID", "First Name", "Last Name", "Email", "Enrollment Date");
            System.out.println("-----------------------------------------------------------------------------------");

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Date enrollmentDate = rs.getDate("enrollment_date");

                System.out.printf("%-12d %-15s %-15s %-30s %-15s%n",
                        studentId, firstName, lastName, email, enrollmentDate);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }
    }

    // Inserts a new student record into the students table
    public static void addStudent(String firstName, String lastName, String email, String enrollmentDate) {
        String SQL = "INSERT INTO students(first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(enrollmentDate));

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    // Updates the email address for a student with the specified student_id
    public static void updateStudentEmail(int studentId, String newEmail) {
        String SQL = "UPDATE students SET email = ? WHERE student_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Email updated successfully!");
            } else {
                System.out.println("No student found with ID: " + studentId);
            }

        } catch (SQLException e) {
            System.out.println("Error updating email: " + e.getMessage());
        }
    }

    // Deletes the record of the student with the specified student_id
    public static void deleteStudent(int studentId) {
        String SQL = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, studentId);

            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with ID: " + studentId);
            }

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    
    // Main method with menu interface
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== STUDENT MANAGEMENT SYSTEM ===");

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. View all students");
            System.out.println("2. Add new student");
            System.out.println("3. Update student email");
            System.out.println("4. Delete student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    getAllStudents();
                    break;

                case 2:
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter enrollment date (YYYY-MM-DD): ");
                    String enrollmentDate = scanner.nextLine();

                    addStudent(firstName, lastName, email, enrollmentDate);
                    break;

                case 3:
                    System.out.print("Enter student ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();

                    updateStudentEmail(updateId, newEmail);
                    break;

                case 4:
                    System.out.print("Enter student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    deleteStudent(deleteId);
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}