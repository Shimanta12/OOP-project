import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentProfileWindow extends JFrame {
    private String studentEmail;

    public StudentProfileWindow(String studentEmail) {
        this.studentEmail = studentEmail;

        setTitle("Student Profile");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS
        add(panel);
        displayProfile(panel);

        setVisible(true);
    }

    private void displayProfile(JPanel panel) {
        try {
            Connection connection = DatabaseConnection.connect();
            String query = "SELECT * FROM Students WHERE email=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentEmail);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                addLabelWithNewline(panel, "Name: " + name);

                // Add labels for other student information with newlines
                addLabelWithNewline(panel, "ID: " + resultSet.getString("id"));
                addLabelWithNewline(panel, "Department: " + resultSet.getString("department"));
                addLabelWithNewline(panel, "Semester: " + resultSet.getString("semester"));
                addLabelWithNewline(panel, "Section: " + resultSet.getString("section"));
                addLabelWithNewline(panel, "Age: " + resultSet.getString("age"));
                addLabelWithNewline(panel, "DOB: " + resultSet.getString("dob"));
                addLabelWithNewline(panel, "Blood Group: " + resultSet.getString("blood_group"));

                JButton expenditureButton = new JButton("Daily Expenditure");
                expenditureButton.addActionListener(e -> new DailyExpenditureInterface(studentEmail));
                panel.add(expenditureButton);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addLabelWithNewline(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add a rigid area for spacing
    }
}
