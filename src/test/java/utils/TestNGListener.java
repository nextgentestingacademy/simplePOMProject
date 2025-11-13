package utils;  // This class belongs to the 'utils' package which holds reusable utility components

// Importing necessary Java and Selenium libraries
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

// Importing ExtentReports classes to generate HTML reports
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import base.BaseTest;  // Importing BaseTest to access WebDriver instance

// This class implements TestNG’s ITestListener interface.
// Listeners let you perform custom actions (like logging or screenshots) 
// automatically at different stages of test execution.
public class TestNGListener extends BaseTest implements ITestListener {

    // ExtentReports instance to create and manage the HTML report
    ExtentReports extent;

    // ExtentTest represents each individual test in the report
    ExtentTest test;

    // ThreadLocal is used to handle parallel test execution safely.
    // It ensures each thread (test) gets its own instance of ExtentTest.
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // This method runs ONCE before any test starts in the entire suite
    @Override
    public void onStart(ITestContext context) {
        // Define report file location dynamically using project root directory
        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";

        // Create an ExtentSparkReporter which generates the HTML report
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Automation Test Results");   // Display name on the report
        reporter.config().setDocumentTitle("Test Execution Report");  // Browser tab title

        // Create ExtentReports instance and attach the reporter
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        // Add useful information about the test run
        extent.setSystemInfo("Tester", "Rahul Kamat");
        extent.setSystemInfo("Environment", "QA");
    }

    // This method runs before each @Test method starts
    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test entry in the report with the test method name
        test = extent.createTest(result.getMethod().getMethodName());

        // Store this test instance in ThreadLocal for thread safety
        extentTest.set(test);
    }

    // This method runs when a test case passes successfully
    @Override
    public void onTestSuccess(ITestResult result) {
        // Log the test status as PASSED in the report
        extentTest.get().log(Status.PASS, "Test passed successfully");
    }

    // This method runs when a test case fails
    @Override
    public void onTestFailure(ITestResult result) {
        // Retrieve the WebDriver instance from the failed test class
        Object currentClass = result.getInstance();
        WebDriver driver = ((BaseTest) currentClass).driver;

        // Log the error message or exception that caused the failure
        extentTest.get().fail(result.getThrowable());

        // Capture a screenshot and store it in the screenshots folder
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + result.getName() + ".png";

        try {
            // Copy the screenshot file to the target location
            FileUtils.copyFile(src, new File(screenshotPath));

            // Attach the screenshot to the Extent report
            extentTest.get().addScreenCaptureFromPath(screenshotPath, "Failed Test Screenshot");

            System.out.println("Screenshot saved for failed test: " + result.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method runs if a test was skipped (not executed)
    @Override
    public void onTestSkipped(ITestResult result) {
        // Log the skipped status in the report
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    // This method runs once after all tests in the suite finish
    @Override
    public void onFinish(ITestContext context) {
        // Flush writes all logs and results to the HTML report file
        extent.flush();
    }
}
