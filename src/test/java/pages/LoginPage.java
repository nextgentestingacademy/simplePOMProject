package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class LoginPage extends BasePage{


	private final By edtUsername = By.name("username1");
	private final By edtPassword = By.name("password");
	private final By btnLogin = By.tagName("button");
	
	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public boolean enterPassword(String pwd) {
		enterText(edtPassword,pwd);
		return true;
	}

	public boolean clickLogin() {
		click(btnLogin);
		return true;
	}

	
	public boolean enterUsername(String userid) {
		enterText(edtUsername,userid);
		return true;
	}
	
	
}
