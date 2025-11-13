package utils;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import base.BaseTest;

public class TestNGListener extends BaseTest implements ITestListener{

	public void onTestFailure(ITestResult result) {
		Object currentClass = result.getInstance();
		WebDriver driver = ((BaseTest) currentClass).driver;
		
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("screenshots/" + result.getName() + ".png"));
			System.out.println("Screenshot saved for failed test: " + result.getName());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
