# Hotel Management System (Demo)

This is a demonstration Hotel Management System implemented in Java Swing with a lightweight SQLite backend (JDBC).
It includes simple modules for Customer, Room, and Booking management. This is intended as a teaching/demo project.

## Features
- Add customer records
- Add/update rooms
- Create bookings (marks room as occupied)
- View customers, rooms, and bookings

## Requirements
- Java JDK 8 or higher
- SQLite (JDBC driver included with modern JDKs or add the sqlite-jdbc jar to classpath)

## How to Compile and Run
1. Compile all java files:
   ```bash
   javac src/*.java
   ```
2. Run the GUI application:
   ```bash
   java -cp src HotelManagementFrontend
   ```

Note: The program creates a local SQLite database file `hotel.db` in the working directory.

