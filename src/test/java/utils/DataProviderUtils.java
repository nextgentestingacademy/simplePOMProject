package utils;

import org.testng.annotations.DataProvider;

public class DataProviderUtils {

	
	@DataProvider(name="loginDetails")
	public Object[][] getLoginData(){
		return new Object[][] {
			{"Admin","admin123"},
			{"admin","admin123"},
			{"Admin","admin"}
		};
	}
}
