import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class DailyExpenditureInterface extends JFrame {
    private JTextField nameField, priceField;

    public DailyExpenditureInterface(String studentEmail) {
        setTitle("Daily Expenditure");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel, studentEmail);

        setVisible(true);
    }

    private void placeComponents(JPanel panel, String studentEmail) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(100, 20, 165, 25);
        panel.add(nameField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(10, 50, 80, 25);
        panel.add(priceLabel);

        priceField = new JTextField(20);
        priceField.setBounds(100, 50, 165, 25);
        panel.add(priceField);

        // Button to add data to the daily expenditure table
        JButton addButton = new JButton("Add Expenditure");
        addButton.setBounds(10, 80, 150, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpenditure(studentEmail);
            }
        });
        panel.add(addButton);

        // Button to get today's expenditures
        JButton getTodayExpenditureButton = new JButton("Get Today's Expenditures");
        getTodayExpenditureButton.setBounds(180, 80, 200, 25);
        getTodayExpenditureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTodayExpenditures(studentEmail);
            }
        });
        panel.add(getTodayExpenditureButton);
    }

    private void addExpenditure(String studentEmail) {
        // Implement code to add expenditure data to the database
        try {
            Connection connection = DatabaseConnection.connect();
            String query = "INSERT INTO daily_expenditure (student_email, name, price, date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentEmail);
            preparedStatement.setString(2, nameField.getText());
            preparedStatement.setDouble(3, Double.parseDouble(priceField.getText()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Expenditure added successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            preparedStatement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding expenditure", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getTodayExpenditures(String studentEmail) {
        // Implement code to fetch today's expenditures from the database and show the
        // total amount
        try {
            Connection connection = DatabaseConnection.connect();
            String query = "SELECT SUM(price) AS total FROM daily_expenditure WHERE student_email=? AND date=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentEmail);
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double totalExpenditure = resultSet.getDouble("total");
                JOptionPane.showMessageDialog(this, "Today's Total Expenditure: $" + totalExpenditure,
                        "Total Expenditure", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No expenditures for today", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching expenditures", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
