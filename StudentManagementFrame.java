/*
 * Simple Student Information System CRUD App
 * Fullscreen JFrame
 */
package studentinfosyscrudapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagementFrame extends JFrame {

    private final StudentDAO studentDAO;

    private JTable tblStudents;
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtCourse;
    private JComboBox<String> cmbYear;
    private JTextField txtAge;  // NEW FIELD ADDED

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnRefresh;

    public StudentManagementFrame() {
        this.studentDAO = new StudentDAO();

        //setUndecorated(true);                 // removes title bar
        initComponents();                     // builds UI 
        loadStudentsIntoTable();              // load data
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // maximize window
    }

    private void initComponents() {
        setTitle("Student Information System - Java CRUD (OOP)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        //It adds a 15-pixel empty space on all sides of the panel to create clean and comfortable spacing around the UI.
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // ====== Title Panel ======
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(30, 136, 229)); // blue

        JLabel lblTitle = new JLabel("Student Information System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(Color.WHITE);

        //It adds empty padding around the title, with 20 pixels on the top and bottom and 10 pixels on the left and right, 
        //to make the label look more balanced and visually appealing.
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        titlePanel.add(lblTitle, BorderLayout.CENTER);

        // ====== Center Panel: left form + right table ======
        //It creates a panel using BorderLayout with 15-pixel horizontal and vertical gaps between components to give them clear spacing.
        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.setBackground(Color.WHITE);

        // ---- Left Form ----
        JPanel formCard = new JPanel(new BorderLayout(10, 10));
        formCard.setBackground(Color.WHITE);
        //It adds a visible titled border around the panel with the label "Student Details", making the section clearly identified and organized.
        formCard.setBorder(BorderFactory.createTitledBorder("Student Details"));

        //It creates a panel arranged in 5 rows and 1 column, and the last two arguments (8, 8) 
        //set the horizontal and vertical spacing between each component in the grid.
        JPanel labelsPanel = new JPanel(new GridLayout(5, 1, 8, 8));  // CHANGED FROM 4 TO 5
        labelsPanel.setBackground(Color.WHITE);
        JPanel fieldsPanel = new JPanel(new GridLayout(5, 1, 8, 8));  // CHANGED FROM 4 TO 5
        fieldsPanel.setBackground(Color.WHITE);

        JLabel lblId = new JLabel("ID:");
        JLabel lblName = new JLabel("Name:");
        JLabel lblCourse = new JLabel("Course:");
        JLabel lblYear = new JLabel("Year Level:");
        JLabel lblAge = new JLabel("Age:");  // NEW LABEL

        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCourse.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblYear.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblAge.setFont(new Font("Segoe UI", Font.PLAIN, 16));  // NEW

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(245, 245, 245));
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        txtCourse = new JTextField();
        txtCourse.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        cmbYear = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        cmbYear.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cmbYear.setPreferredSize(new Dimension(100, 40));

        txtAge = new JTextField();  // NEW FIELD
        txtAge.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        labelsPanel.add(lblId);
        labelsPanel.add(lblName);
        labelsPanel.add(lblCourse);
        labelsPanel.add(lblYear);
        labelsPanel.add(lblAge);  // ADDED

        fieldsPanel.add(txtId);
        fieldsPanel.add(txtName);
        fieldsPanel.add(txtCourse);
        fieldsPanel.add(cmbYear);
        fieldsPanel.add(txtAge);  // ADDED

        formCard.add(labelsPanel, BorderLayout.WEST);
        formCard.add(fieldsPanel, BorderLayout.CENTER);
        formCard.setPreferredSize(new Dimension(400, 200));

        // ---- Right Table ----
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createTitledBorder("Students List"));

        /*
        This code creates a new JTable and assigns it a DefaultTableModel to define how the table stores and displays data.
        The model starts with an empty 2D array (Object[][]{}) and sets the table headers to "ID", "Name", "Course", "Year", and "Age."
        Inside the model, isCellEditable() is overridden to always return false, which prevents the user from editing any table cell.
        This makes the table fully read-only so all changes must be done through the buttons, not directly inside the table.
        */
        tblStudents = new JTable();
        tblStudents.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Name", "Course", "Year", "Age"}  // ADDED "Age"
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // table is read-only
            }
        });

        tblStudents.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblStudents.setRowHeight(24);

        //This sets the table so the user can select only one row at a time. It prevents multiple selections, 
        //ensuring the user edits or deletes only one student record.
        tblStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // When user clicks a row, show in form
        /*This code listens for a mouse click on the table. When the user clicks a row, it gets the index of the selected row using getSelectedRow()
        If a valid row is clicked, the code reads the values from that row and places them into the text fields and dropdown menu, allowing the user 
        to easily load a student's information for viewing, updating, or deleting. MouseListener detects mouse events, while MouseAdapter is its simpler 
        version that lets you override only the method you need.
        */
        tblStudents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblStudents.getSelectedRow();
                if (row >= 0) {
                    txtId.setText(tblStudents.getValueAt(row, 0).toString());
                    txtName.setText(tblStudents.getValueAt(row, 1).toString());
                    txtCourse.setText(tblStudents.getValueAt(row, 2).toString());
                    cmbYear.setSelectedItem(tblStudents.getValueAt(row, 3).toString());
                    txtAge.setText(tblStudents.getValueAt(row, 4).toString());  // ADDED
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblStudents);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // Put form left, table right
        /*
        This code creates a JSplitPane that places the form on the left and the table on the right in a horizontal split. 
        The line setResizeWeight(0.3) makes the left side take 30% of the space and the right side 70%. setOneTouchExpandable(true) 
        adds small arrow buttons that let the user quickly expand or collapse either side. This layout makes the screen organized by 
        separating the input form and the student table into two adjustable sections.
        */
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formCard, tableCard);
        splitPane.setResizeWeight(0.3); // 30% left, 70% right
        splitPane.setOneTouchExpandable(true);

        centerPanel.add(splitPane, BorderLayout.CENTER);

        // ====== Bottom Buttons Panel ======
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnRefresh = new JButton("Refresh");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
        btnAdd.setFont(btnFont);
        btnUpdate.setFont(btnFont);
        btnDelete.setFont(btnFont);
        btnClear.setFont(btnFont);
        btnRefresh.setFont(btnFont);

        Dimension btnSize = new Dimension(120, 40);
        btnAdd.setPreferredSize(btnSize);
        btnUpdate.setPreferredSize(btnSize);
        btnDelete.setPreferredSize(btnSize);
        btnClear.setPreferredSize(btnSize);
        btnRefresh.setPreferredSize(btnSize);

        // Add actions
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadStudentsIntoTable());

        // Align to the right
        //adds an invisible, stretchable space that pushes the buttons to the right side of the panel, helping align them neatly.
        bottomPanel.add(Box.createHorizontalGlue());

        bottomPanel.add(btnAdd);
        //inserts a fixed 10-pixel horizontal space between buttons to keep them evenly separated.
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(btnUpdate);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(btnDelete);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(btnClear);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(btnRefresh);

        // ==== Assemble ====
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        //sets mainPanel as the main container of the window, making all components inside it the visible content of the frame.
        setContentPane(mainPanel);
        pack(); // layout components; undecorated was already set before this
    }

    // ====== CRUD Operations from UI ======

    private void loadStudentsIntoTable() {
        //This line calls getAllStudents() from StudentDAO to retrieve all student records from the database and store them in a list of Student objects.
        List<Student> students = studentDAO.getAllStudents();
        DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
        model.setRowCount(0);

        for (Student s : students) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    s.getCourse(),
                    s.getYear(),
                    s.getAge()  // ADDED AGE
            });
        }
    }

    private void addStudent() {
        String name = txtName.getText().trim();
        String course = txtCourse.getText().trim();
        String yearStr = (String) cmbYear.getSelectedItem();
        String ageStr = txtAge.getText().trim();  // ADDED

        if (name.isEmpty() || course.isEmpty() || ageStr.isEmpty()) {  // UPDATED VALIDATION
            JOptionPane.showMessageDialog(this,
                    "Please enter Name, Course, and Age.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
            int age = Integer.parseInt(ageStr);  // ADDED
            Student s = new Student(name, course, year, age);  // UPDATED CONSTRUCTOR

            boolean success = studentDAO.insertStudent(s);
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Student added successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadStudentsIntoTable();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to add student.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        String idText = txtId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student from the table to update.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = txtName.getText().trim();
        String course = txtCourse.getText().trim();
        String yearStr = (String) cmbYear.getSelectedItem();
        String ageStr = txtAge.getText().trim();  // ADDED

        if (name.isEmpty() || course.isEmpty() || ageStr.isEmpty()) {  // UPDATED VALIDATION
            JOptionPane.showMessageDialog(this,
                    "Please enter Name, Course, and Age.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            int year = Integer.parseInt(yearStr);
            int age = Integer.parseInt(ageStr);  // ADDED

            Student s = new Student(id, name, course, year, age);  // UPDATED CONSTRUCTOR

            boolean success = studentDAO.updateStudent(s);
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Student updated successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadStudentsIntoTable();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to update student.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String idText = txtId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student from the table to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        //if the user clicks YES, confirm becomes JOptionPane.YES_OPTION (which is 0).
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int id = Integer.parseInt(idText);
        boolean success = studentDAO.deleteStudent(id);

        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Student deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadStudentsIntoTable();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to delete student.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtCourse.setText("");
        txtAge.setText("");  // ADDED
        cmbYear.setSelectedIndex(0);
        tblStudents.clearSelection();
    }

    /*
    Java Swing has several built-in UI themes (called Look and Feel).
    Examples:
    Metal (default), Nimbus, Motif, Windows, GTK

    UIManager.getInstalledLookAndFeels() - Returns all themes that Java supports on your computer.
    The app uses a beautiful UI theme instead of the old gray Swing look.

    Always create Swing UI components on the Event Dispatch Thread (EDT).
    This is the thread where GUI events happen. If you don't, your UI could freeze or behave incorrectly.
    */

    // ====== Main method to run the app ======
    public static void main(String[] args) {
        // Optional: nicer look & feel (Nimbus)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // ignore, use default
        }

        SwingUtilities.invokeLater(() -> {
            new StudentManagementFrame().setVisible(true);
        });
    }
}

