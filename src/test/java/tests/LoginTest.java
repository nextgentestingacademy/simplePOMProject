package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
		
//		SoftAssert soft = new SoftAssert();
		login.enterUsername("Admin");
		login.enterPassword("admin1234");
		login.clickLogin();
		
		String dashboardUrl = driver.getCurrentUrl();
		Assert.assertTrue(dashboardUrl.contains("dashboard"));
		
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
