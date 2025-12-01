package studentinfosyscrudapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;

    //UserDAO userDAO is the object that handles all database operations for user login, 
    //where DAO means Data Access Object—a class dedicated to communicating with the database.
    private final UserDAO userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();

        //initComponents() builds and initializes all the graphical elements of the login window.
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Student Information System");
        //makes the program fully exit when the login window is closed.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        //It adds empty spacing around the panel, and the four arguments
        //(20, 20, 20, 20) represent the size of the padding on top, left, bottom, and right respectively.
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JLabel lblHeader = new JLabel("Sign in", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblHeader.setForeground(new Color(25, 118, 210));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        formPanel.setBackground(Color.WHITE);

        JLabel lblUser = new JLabel("Username:");
        JLabel lblPass = new JLabel("Password:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(lblUser);
        formPanel.add(txtUsername);
        formPanel.add(lblPass);
        formPanel.add(txtPassword);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        btnLogin = new JButton("Login");
        btnCancel = new JButton("Cancel");
        Dimension btnSize = new Dimension(100, 35);
        btnLogin.setPreferredSize(btnSize);
        btnCancel.setPreferredSize(btnSize);

        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> loginUser());
        //System.exit(0) immediately closes the entire application, with 0 meaning it ended normally without errors.
        btnCancel.addActionListener(e -> System.exit(0));

        //makes the Login button activate automatically when the user presses ENTER, even without clicking it.
        getRootPane().setDefaultButton(btnLogin);

        //sets mainPanel as the main layout container of the window, meaning all UI components inside it become the window's visible content.
        setContentPane(mainPanel);

        //pack() sizes the window automatically
        pack();
        //centers the window on the screen.
        setLocationRelativeTo(null);
    }

    private void loginUser() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter username and password.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        //It calls the authenticate method in UserDAO to check the entered username and password in the database and returns a User object if the login is correct.
        User user = userDAO.authenticate(username, password);

        //if (user != null) checks whether the login was successful by confirming that a valid User object was returned from the database instead of null.
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                    "Welcome, " + user.getFullname() + "!",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            StudentManagementFrame dashboard = new StudentManagementFrame();
            dashboard.setVisible(true);
            //closes the current window and frees its resources.
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Incorrect username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
