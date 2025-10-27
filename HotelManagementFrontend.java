\
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class HotelManagementFrontend extends JFrame {
    private JButton customerBtn, roomBtn, bookingBtn, billingBtn, viewBtn, exitBtn;

    public HotelManagementFrontend() {
        setTitle("Hotel Management System - Demo");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel title = new JLabel("Hotel Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        topPanel.setBackground(new Color(45, 62, 80));
        title.setForeground(Color.WHITE);
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        customerBtn = new JButton("Add Customer");
        roomBtn = new JButton("Add / Update Room");
        bookingBtn = new JButton("Book Room");
        billingBtn = new JButton("View Bookings");
        viewBtn = new JButton("View All Data");
        exitBtn = new JButton("Exit");

        JButton[] buttons = { customerBtn, roomBtn, bookingBtn, billingBtn, viewBtn, exitBtn };
        for (JButton b : buttons) {
            b.setBackground(new Color(70, 130, 180));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            btnPanel.add(b);
        }
        add(btnPanel, BorderLayout.CENTER);

        // Initialize DB
        DatabaseHelper.initDatabase();

        customerBtn.addActionListener(e -> addCustomerDialog());
        roomBtn.addActionListener(e -> addRoomDialog());
        bookingBtn.addActionListener(e -> bookRoomDialog());
        billingBtn.addActionListener(e -> viewBookingsDialog());
        viewBtn.addActionListener(e -> viewAllDataDialog());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void addCustomerDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        Object[] message = {
            "ID:", idField,
            "Name:", nameField,
            "Phone:", phoneField,
            "Email:", emailField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "Add Customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String email = emailField.getText().trim();
                DatabaseHelper.addCustomer(id, name, phone, email);
                JOptionPane.showMessageDialog(this, "Customer added.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        }
    }

    private void addRoomDialog() {
        JTextField roomField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField priceField = new JTextField();
        Object[] message = {
            "Room ID:", roomField,
            "Type:", typeField,
            "Price:", priceField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "Add / Update Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int roomId = Integer.parseInt(roomField.getText().trim());
                String type = typeField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                DatabaseHelper.addRoom(roomId, type, price, true);
                JOptionPane.showMessageDialog(this, "Room added/updated.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        }
    }

    private void bookRoomDialog() {
        JTextField bookingField = new JTextField();
        JTextField custField = new JTextField();
        JTextField roomField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField payField = new JTextField();
        Object[] message = {
            "Booking ID:", bookingField,
            "Customer ID:", custField,
            "Room ID:", roomField,
            "Date (YYYY-MM-DD):", dateField,
            "Payment:", payField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "Book Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int bid = Integer.parseInt(bookingField.getText().trim());
                int cid = Integer.parseInt(custField.getText().trim());
                int rid = Integer.parseInt(roomField.getText().trim());
                String date = dateField.getText().trim();
                double pay = Double.parseDouble(payField.getText().trim());
                DatabaseHelper.addBooking(bid, cid, rid, date, pay);
                JOptionPane.showMessageDialog(this, "Booking created.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        }
    }

    private void viewBookingsDialog() {
        List<Booking> list = DatabaseHelper.getAllBookings();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No bookings yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Booking b : list) sb.append(b).append("\\n");
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Bookings", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewAllDataDialog() {
        List<Customer> customers = DatabaseHelper.getAllCustomers();
        List<Room> rooms = DatabaseHelper.getAllRooms();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Customers ===\\n");
        for (Customer c : customers) sb.append(c).append("\\n");
        sb.append("\\n=== Rooms ===\\n");
        for (Room r : rooms) sb.append(r).append("\\n");
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Data", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HotelManagementFrontend frame = new HotelManagementFrontend();
            frame.getContentPane().setBackground(new Color(34, 47, 62));
            frame.setVisible(true);
        });
    }
}
