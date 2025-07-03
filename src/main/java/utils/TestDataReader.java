package utils;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading test data from a CSV file.
 */
public class TestDataReader {

    /**
     * Reads test data from the first data row of a CSV file.
     *
     * @param filePath path to the CSV file
     * @return Map containing key-value pairs of test data
     */
    public static Map<String, String> getTestData(String filePath) {
        Map<String, String> data = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            if (records.size() < 2) {
                System.err.println(" CSV file must contain at least one data row.");
                return data;
            }

            String[] headers = records.get(0);
            String[] values = records.get(1);

            for (int i = 0; i < headers.length && i < values.length; i++) {
                data.put(headers[i].trim(), values[i].trim());
            }

        } catch (IOException | CsvException e) {
            System.err.println(" Failed to read CSV data: " + e.getMessage());
        }
        return data;
    }

    /**
     * Wrapper method to get test data based on a test case ID (you can later use it to filter by ID).
     *
     * @param testCaseId not used right now, just for interface compatibility
     * @return Map of test data from default CSV file
     */
    public static Map<String, String> readTestData(String testCaseId) {
        // You can add filtering logic by testCaseId later
        return getTestData("src/test/resources/testdata.csv");
    }
}
