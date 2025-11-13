package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties properties;
	
	public static void loadProperties() {
		properties = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
			properties.load(fis);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		if(properties == null) {
			loadProperties();
		}
		return properties.getProperty(key);
	}
}
