package utils;  // This class is part of the 'utils' package which stores reusable utility files

// Import ExtentReports classes used to generate HTML reports
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    
    // Declare a static ExtentReports object. 
    // Making it static ensures only one instance of ExtentReports is created (Singleton pattern).
    private static ExtentReports extent;

    // This method returns the single ExtentReports instance.
    // If it's not already created, it creates one.
    public static ExtentReports getInstance() {
        
        // Check if the ExtentReports object is null
        // If yes, it means report is not yet initialized
        if (extent == null) {
            
            // Define the path where the HTML report file will be created
            String reportPath = "reports/ExtentReport.html";
            
            // Create an ExtentSparkReporter — this is the reporter responsible 
            // for generating a visually rich HTML report at the given location
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            
            // Configure the report — give it a title and a name for easy identification
            reporter.config().setDocumentTitle("Automation Test Report");   // Title shown on browser tab
            reporter.config().setReportName("Functional Test Results");     // Name displayed on report header

            // Create an instance of ExtentReports and attach the SparkReporter to it
            extent = new ExtentReports();
            extent.attachReporter(reporter);

            // Add some system or tester-specific information to the report
            extent.setSystemInfo("Tester", "QA Engineer");
        }
        
        // Return the ExtentReports instance — same instance is returned every time
        return extent;
    }
}
