package com.apps.tools;

import java.io.File;

public class Constants {

	
	//Path Constants
	public static final String CONFIG_RESOURCES_PATH = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "config" + File.separator;
	public static final String TEMP_RESOURCES_PATH = "target" + File.separator;
	
	public static String REPORT_TEST_NAME = "ROOT";
	

	//API
	public static final long TEST_RETRY_WAIT_TIME_SECONDS = 100;
	public static final long TEST_RETRY_WAIT_TIME = 60000;
}
