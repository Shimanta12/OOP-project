import javax.swing.*;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentLoginApp extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public StudentLoginApp() {
        setTitle("Student Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 20, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(100, 20, 165, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        panel.add(loginButton);
    }

    private void login() {
        String email = emailField.getText();
        String password = String.valueOf(passwordField.getPassword());

        // Implement JDBC code to connect to the database and validate login
        // Use PreparedStatement to avoid SQL injection

        // Sample JDBC code (you need to replace the placeholders with your actual
        // database details)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DatabaseConnection.connect();

            String query = "SELECT * FROM Students WHERE email=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Successful login, fetch student profile and show the profile window
                // showProfileWindow(resultSet);
                new StudentProfileWindow(email);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // private void showProfileWindow(ResultSet resultSet) {
    // // Implement code to display the student profile window
    // // Extract data from the ResultSet and create a new window
    // }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentLoginApp();
            }
        });
    }
}
