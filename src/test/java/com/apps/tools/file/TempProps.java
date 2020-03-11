package com.apps.tools.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.apps.tools.Constants;

public class TempProps {

//	private static Logger logger = LoggerFactory.getLogger(TempProps.class);
//	private static Logger logger = LoggerFactory.getLogger(Constants.REPORT_TEST_NAME);
	private static Properties prop = new Properties();
	private static InputStream input = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, String> readProperties(String fileName) {
		HashMap<String, String> result = null;
		try {
			input = new FileInputStream(Constants.TEMP_RESOURCES_PATH + fileName);
			prop.load(input);
			result = new HashMap<String, String>((Map) prop);
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

	public static String readProperty(String fileName, String propertyKey) {
		String result = null;

		try {
			input = new FileInputStream(Constants.TEMP_RESOURCES_PATH + fileName);
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

	public static void writeListProperties(String fileName, String listKey, List<String> payload) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(Constants.TEMP_RESOURCES_PATH + fileName);
			prop.put(listKey, String.valueOf(payload));
			prop.store(out, "#File acts as a temporary DB");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static List<String> readPropertyAsList(String fileName, String propertyKey) {
		List<String> resultList = new ArrayList<String>();

		try {
			input = new FileInputStream(Constants.TEMP_RESOURCES_PATH + fileName);
			prop.load(input);
			String value = prop.getProperty(propertyKey);
			value = value.replace("[", "").replace("]", "");
			resultList = Arrays.asList(value.split(","));
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

		return resultList;
	}

	public static void writeMapProperties(String fileName, Map<String, Object> payload) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(Constants.TEMP_RESOURCES_PATH + fileName);
			for (String key : payload.keySet()) {
				prop.put(key, payload.get(key));
			}
			prop.store(out, "File acts as a temporary db");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	public static void writeStringProperties(String fileName, String payload) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(Constants.TEMP_RESOURCES_PATH + fileName);
			prop.put("collectedData", payload);
			prop.store(out, "File acts as a temporary db");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

}
