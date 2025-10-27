import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WeatherLogger {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = sc.next();
        System.out.print("Enter temperature (°C): ");
        double temp = sc.nextDouble();

        try (FileWriter fw = new FileWriter("weather.txt", true)) {
            fw.write(date + " - " + temp + "°C\n");
            System.out.println("✅ Weather data saved successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error writing file: " + e.getMessage());
        }

        sc.close();
    }
}
