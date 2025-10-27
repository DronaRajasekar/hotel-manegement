\
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:hotel.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initDatabase() {
        String createCustomer = "CREATE TABLE IF NOT EXISTS customer(id INTEGER PRIMARY KEY, name TEXT, phone TEXT, email TEXT)";
        String createRoom = "CREATE TABLE IF NOT EXISTS room(room_id INTEGER PRIMARY KEY, type TEXT, price REAL, available INTEGER)";
        String createBooking = "CREATE TABLE IF NOT EXISTS booking(booking_id INTEGER PRIMARY KEY, customer_id INTEGER, room_id INTEGER, date TEXT, payment REAL)";
        try (Connection conn = connect(); Statement st = conn.createStatement()) {
            st.execute(createCustomer);
            st.execute(createRoom);
            st.execute(createBooking);
        } catch (SQLException e) {
            System.out.println("DB Init Error: " + e.getMessage());
        }
    }

    public static void addCustomer(int id, String name, String phone, String email) {
        String sql = "INSERT INTO customer(id, name, phone, email) VALUES(?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, id);
            p.setString(2, name);
            p.setString(3, phone);
            p.setString(4, email);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add Customer Error: " + e.getMessage());
        }
    }

    public static List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT id, name, phone, email FROM customer";
        try (Connection conn = connect(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.out.println("Get Customers Error: " + e.getMessage());
        }
        return list;
    }

    public static void addRoom(int roomId, String type, double price, boolean available) {
        String sql = "INSERT OR REPLACE INTO room(room_id, type, price, available) VALUES(?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, roomId);
            p.setString(2, type);
            p.setDouble(3, price);
            p.setInt(4, available ? 1 : 0);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add Room Error: " + e.getMessage());
        }
    }

    public static List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT room_id, type, price, available FROM room";
        try (Connection conn = connect(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Room(rs.getInt("room_id"), rs.getString("type"), rs.getDouble("price"), rs.getInt("available")==1));
            }
        } catch (SQLException e) {
            System.out.println("Get Rooms Error: " + e.getMessage());
        }
        return list;
    }

    public static void addBooking(int bookingId, int customerId, int roomId, String date, double payment) {
        String sql = "INSERT INTO booking(booking_id, customer_id, room_id, date, payment) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, bookingId);
            p.setInt(2, customerId);
            p.setInt(3, roomId);
            p.setString(4, date);
            p.setDouble(5, payment);
            p.executeUpdate();
            // mark room occupied
            String upd = "UPDATE room SET available=0 WHERE room_id=?";
            try (PreparedStatement q = conn.prepareStatement(upd)) {
                q.setInt(1, roomId);
                q.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Add Booking Error: " + e.getMessage());
        }
    }

    public static List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT booking_id, customer_id, room_id, date, payment FROM booking";
        try (Connection conn = connect(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Booking(rs.getInt("booking_id"), rs.getInt("customer_id"), rs.getInt("room_id"), rs.getString("date"), rs.getDouble("payment")));
            }
        } catch (SQLException e) {
            System.out.println("Get Bookings Error: " + e.getMessage());
        }
        return list;
    }
}
