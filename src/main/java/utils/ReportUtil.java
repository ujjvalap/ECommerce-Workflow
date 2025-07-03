package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for logging messages and generating a test report.
 */
public class ReportUtil {
    
    private static final List<String> logs = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs a message to the console and stores it for report generation.
     *
     * @param message the log message
     */
    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] " + message;
        logs.add(logEntry);
        System.out.println(logEntry);
    }

    /**
     * Generates a report using the default file name `report.txt`.
     */
    public static void generateReport() {
        generateReport("report.txt");
    }

    /**
     * Generates a report using a custom file name.
     *
     * @param fileName the name of the file to write the report to
     */
    public static void generateReport(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String entry : logs) {
                writer.write(entry);
                writer.newLine();
            }
            System.out.println(" Report generated successfully at: " + fileName);
        } catch (IOException e) {
            System.err.println(" Failed to generate report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Clears all previously stored log entries.
     */
    public static void clearLogs() {
        logs.clear();
    }
}


