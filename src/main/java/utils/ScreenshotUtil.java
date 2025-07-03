package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static void capture(WebDriver driver, String screenshotName) {
        try {
            System.out.println(" Capturing screenshot for: " + screenshotName);

            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            // Use absolute path
            File dir = new File(System.getProperty("user.dir") + "/screenshots");
            if (!dir.exists()) dir.mkdir();

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File dest = new File(dir, screenshotName + "_" + timestamp + ".png");

            Files.copy(src.toPath(), dest.toPath());

            System.out.println(" Screenshot saved to: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.err.println(" Screenshot capture failed: " + e.getMessage());
        }
    }
}
