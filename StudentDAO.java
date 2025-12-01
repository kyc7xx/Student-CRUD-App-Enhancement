/*
StudentDAO is a Data Access Object responsible for handling all database operations related to students.
Its purpose is to separate the student CRUD logic from the UI, making the code cleaner, 
organized, and easier to maintain.

List is just a blueprint (interface), but it cannot store data by itself.
ArrayList is the actual container that holds the data.
 */
package studentinfosyscrudapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public StudentDAO() {
        createTableIfNotExists();
    }

    // Create table if not exists (for convenience)
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS students ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "course VARCHAR(100) NOT NULL, "
                + "year INT NOT NULL, "
                + "age INT NOT NULL"  // ADDED AGE COLUMN
                + ")";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Table 'students' checked/created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT id, name, course, year, age FROM students ORDER BY id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getInt("year"),
                        rs.getInt("age")  // ADDED AGE
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertStudent(Student s) {
        String sql = "INSERT INTO students (name, course, year, age) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, s.getName());
            pstmt.setString(2, s.getCourse());
            pstmt.setInt(3, s.getYear());
            pstmt.setInt(4, s.getAge());  // ADDED AGE

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(Student s) {
        String sql = "UPDATE students SET name = ?, course = ?, year = ?, age = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, s.getName());
            pstmt.setString(2, s.getCourse());
            pstmt.setInt(3, s.getYear());
            pstmt.setInt(4, s.getAge());  // ADDED AGE
            pstmt.setInt(5, s.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
