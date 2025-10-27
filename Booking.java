public class Booking {
    private int bookingId;
    private int customerId;
    private int roomId;
    private String date;
    private double payment;

    public Booking(int bookingId, int customerId, int roomId, String date, double payment) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.date = date;
        this.payment = payment;
    }

    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public int getRoomId() { return roomId; }
    public String getDate() { return date; }
    public double getPayment() { return payment; }

    @Override
    public String toString() {
        return bookingId + " | Cust:" + customerId + " | Room:" + roomId + " | " + date + " | Payment:" + payment;
    }
}
