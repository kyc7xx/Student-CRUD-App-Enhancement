package studentinfosyscrudapp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

/*
JWindow gives you exactly that — a clean, borderless floating window.
JWindow: Does not create heavy window decorations, Lighter weight, Shows up faster
This is why it's often used for: Splash screens, Startup logos, Loading animations
JWindow is perfect because it cannot be interacted with.

initComponents() is used to initialize and build all the graphical user interface elements of the window before it is displayed.
startLoading() begins the splash screen's timed progress animation and triggers the transition to the next window once loading reaches 100%.
BorderLayout() is a layout manager that arranges components in five regions—North, South, East, West, and Center—allowing organized placement of UI elements in a window.
GridLayout(3, 1) arranges components into a layout with 3 rows and 1 column, evenly dividing the space into three equal vertical sections.
*/

public class SplashScreen extends JWindow {

    private JProgressBar progressBar;
    private Timer timer;
    private int progress = 0;

    public SplashScreen() {
        initComponents();
        startLoading();
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Center content
        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.setBackground(Color.WHITE);

        JLabel lblAppName = new JLabel("Student Information System", SwingConstants.CENTER);
        lblAppName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblAppName.setForeground(new Color(25, 118, 210));

        JLabel lblSubtitle = new JLabel("Java CRUD • MySQL • OOP", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitle.setForeground(new Color(97, 97, 97));

        JLabel lblAuthor = new JLabel("Loading, please wait...", SwingConstants.CENTER);
        lblAuthor.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblAuthor.setForeground(new Color(120, 120, 120));

        centerPanel.add(lblAppName);
        centerPanel.add(lblSubtitle);
        centerPanel.add(lblAuthor);

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(progressBar, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setSize(600, 300);
        setLocationRelativeTo(null); // center
    }

    //The timer updates the progress bar every 40 milliseconds by increasing its value by 2%, 
    //creating a smooth loading effect until it reaches 100% and triggers the login screen.
    private void startLoading() {
        timer = new Timer(40, e -> {
            progress += 2;
            progressBar.setValue(progress);
            if (progress >= 100) {
                timer.stop();

                //dispose() is used to close and permanently remove the current window 
                //from memory without shutting down the entire application, allowing the next screen (like the login window) to open cleanly.
                dispose();
                showLogin();
            }
        });
        timer.start();
    }

    private void showLogin() {
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
    }
}
