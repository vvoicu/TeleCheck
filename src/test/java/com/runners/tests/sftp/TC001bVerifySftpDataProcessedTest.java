package com.runners.tests.sftp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apps.tools.Constants;
import com.apps.tools.connectors.ftp.sftp.SftpConnection;
import com.apps.tools.file.TempProps;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class TC001bVerifySftpDataProcessedTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private String fileName = "..";
	private boolean isFilePresent = false;
	private int retries = 0;
	// 10 retries X 30 seconds = 5 minutes
	private static final int TEST_RETRY_COUNT = 10;

	@Before
	public void dataSetup() {
		logger.info("=================================================================");
		logger.info("================= " + this.getClass().getSimpleName());
		logger.info("=================================================================");
		Constants.REPORT_TEST_NAME = this.getClass().getSimpleName();
		
		// read sftp generated file name
		fileName = TempProps.readProperty("TC001aSftpFileUploadTest.properties", "fileName");
	}

	@Test
	public void tc001bVerifySftpDataProcessedTest() throws SftpException, JSchException, InterruptedException {
		String listItems = "";
		do {
			// write file to sftp
			// get sftp remote files list
			listItems = SftpConnection.getWorkingDirFileList();
			logger.info("NOTE: Looking for file: " + fileName);
			logger.info("NOTE: SFTP remote files: " + listItems);
			isFilePresent = !listItems.contains(fileName);

			if (!isFilePresent) {
				logger.info(retries + " retries. File is on the sftp inbound location, will retry in (ms) "
						+ Constants.TEST_RETRY_WAIT_TIME);
				Thread.sleep(Constants.TEST_RETRY_WAIT_TIME);
			}

			retries++;
		} while (!isFilePresent && retries < TEST_RETRY_COUNT);

		Assert.assertTrue("File should not be in the list. Expected: " + fileName + "\n Actual file list: " + listItems,
				isFilePresent);
		listItems = SftpConnection.getWorkingDirFileList();
		// we will check that something came through the sftp call to make sure the
		// connection happened
		Assert.assertTrue("List of files in the sftp have not been retrieved: " + listItems, listItems.contains("."));
	}

}
