package com.apps.tools.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.apps.tools.Constants;

public class ConfigUtils {

	private static Properties prop = new Properties();
	private static InputStream input = null;

	public static String getProperty(String propertyKey) {
		String result = "";
		String configFile = System.getProperty("configFile") == null ? "local" : System.getProperty("configFile");
		try {
			input = new FileInputStream(Constants.CONFIG_RESOURCES_PATH + configFile + "-config.properties");
			prop.load(input);
			result = prop.getProperty(propertyKey);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
