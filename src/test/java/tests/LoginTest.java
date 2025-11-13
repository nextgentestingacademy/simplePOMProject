package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.Log;
import base.BaseTest;
import pages.LoginPage;
import utils.DataProviderUtils;

public class LoginTest extends BaseTest{
	private LoginPage login;
	
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod() {
		login = new LoginPage(driver);
	}
	
	
	@Parameters({"username","password"})
	@Test(priority=1,enabled=false,groups={"smoke","regression"})
	public void loginValidCredentials(String username, String password) throws InterruptedException {
		login.enterUsername(username);
		login.enterPassword(password);
		login.clickLogin();
		
		String dashboardUrl = driver.getCurrentUrl();
		Assert.assertFalse(dashboardUrl.contains("dashboard"));
	}
	
	@Test(enabled=true,groups="regression",priority=0)
	public void loginInvalidCredentials() throws InterruptedException {
		Log.info("Test case: loginInvalidCredentials execution started");
//		SoftAssert soft = new SoftAssert();
		login.enterUsername("Admin");
		Log.info("Entered username Admin");
		login.enterPassword("admin1234");
		Log.info("Entered password admin1234");
		login.clickLogin();
		Log.info("Clicked Login button");
		
		String dashboardUrl = driver.getCurrentUrl();
		Assert.assertTrue(dashboardUrl.contains("dashboard"));
		Log.info("Test case: loginInvalidCredentials execution completed");
//		soft.assertAll();
	}
	
	@Test(enabled=false,dataProvider = "loginDetails",dataProviderClass = DataProviderUtils.class, priority=1,groups={"smoke","regression"})
	public void loginMultipleCombo(String username, String password) throws InterruptedException {
		login.enterUsername(username);
		login.enterPassword(password);
		login.clickLogin();
		
		String dashboardUrl = driver.getCurrentUrl();
		Assert.assertTrue(dashboardUrl.contains("dashboard"));
	}

}
