import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class onlineReservationSystem {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static Map<String, String> users;
    private static Map<String, Reservation> reservations;
    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JTextField pnrField;

    public static void main(String[] args) {
        frame = new JFrame("Online Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        users = new HashMap<>();
        users.put("Ayush Vishwakarma", "101982"); // Example user

        reservations = new HashMap<>();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createReservationPanel(), "Reservation");
        mainPanel.add(createCancellationPanel(), "Cancellation");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (validateLogin(username, password)) {
                cardLayout.show(mainPanel, "Reservation");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        });
        panel.add(loginButton);

        return panel;
    }

    private static JPanel createReservationPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("PNR Number:"));
        JTextField pnrField = new JTextField();
        panel.add(pnrField);

        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Train Number:"));
        JTextField trainNumberField = new JTextField();
        panel.add(trainNumberField);

        panel.add(new JLabel("Train Name:"));
        JComboBox<String> trainNameBox = new JComboBox<>(new String[] {"Vande Bharat", "Mahamana Express", "Rajdhani Express"});
        panel.add(trainNameBox);

        panel.add(new JLabel("Class Type:"));
        JComboBox<String> classTypeBox = new JComboBox<>(new String[] { "SL ","First AC", "Second AC", "Third AC"});
        panel.add(classTypeBox);

        panel.add(new JLabel("Date of Journey:"));
        JTextField dateField = new JTextField();
        panel.add(dateField);

        panel.add(new JLabel("From:"));
        JTextField fromField = new JTextField();
        panel.add(fromField);

        panel.add(new JLabel("To:"));
        JTextField toField = new JTextField();
        panel.add(toField);

        JButton reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(e -> {
            String pnr = pnrField.getText();
            if (pnr.isEmpty() || nameField.getText().isEmpty() || trainNumberField.getText().isEmpty() ||
                dateField.getText().isEmpty() || fromField.getText().isEmpty() || toField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled!");
                return;
            }
            Reservation reservation = new Reservation(
                    pnr, nameField.getText(), trainNumberField.getText(),
                    (String) trainNameBox.getSelectedItem(), (String) classTypeBox.getSelectedItem(),
                    dateField.getText(), fromField.getText(), toField.getText());
            reservations.put(pnr, reservation);
            JOptionPane.showMessageDialog(frame, "Reservation successful!");
        });
        panel.add(reserveButton);

        JButton cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.addActionListener(e -> cardLayout.show(mainPanel, "Cancellation"));
        panel.add(cancelReservationButton);

        return panel;
    }

    private static JPanel createCancellationPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("PNR Number:"));
        pnrField = new JTextField();
        panel.add(pnrField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String pnr = pnrField.getText();
            if (reservations.containsKey(pnr)) {
                Reservation reservation = reservations.get(pnr);
                int confirmation = JOptionPane.showConfirmDialog(frame, reservation.toString() + "\n\nDo you want to cancel this reservation?");
                if (confirmation == JOptionPane.YES_OPTION) {
                    reservations.remove(pnr);
                    JOptionPane.showMessageDialog(frame, "Reservation cancelled!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Reservation not found!");
            }
        });
        panel.add(searchButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Reservation"));
        panel.add(backButton);

        return panel;
    }

    private static boolean validateLogin(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    static class Reservation {
        String pnr;
        String name;
        String trainNumber;
        String trainName;
        String classType;
        String date;
        String from;
        String to;

        Reservation(String pnr, String name, String trainNumber, String trainName, String classType, String date, String from, String to) {
            this.pnr = pnr;
            this.name = name;
            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.classType = classType;
            this.date = date;
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "PNR: " + pnr + "\nName: " + name + "\nTrain Number: " + trainNumber + "\nTrain Name: " + trainName + 
                   "\nClass: " + classType + "\nDate: " + date + "\nFrom: " + from + "\nTo: " + to;
        }
    }
}