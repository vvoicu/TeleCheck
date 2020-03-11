package com.runners.tests.sftp;

import java.sql.SQLException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.apps.tools.Constants;
import com.apps.tools.FieldGenerators;
import com.apps.tools.FieldGenerators.Mode;
import com.apps.tools.FileUtils;
import com.apps.tools.SftpConnection;
import com.apps.tools.TempProps;
import com.ibm.icu.text.SimpleDateFormat;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

@RunWith(JUnit4.class)
public class TC001aSftpFileUploadTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

//	private File xmlFile;
	private String fileName = "";
	private String className = this.getClass().getSimpleName();

	private Document xmlDocumentContent;

	@Before
	public void setFileContent() throws ParserConfigurationException, TransformerException, SQLException {
		logger.info("=================================================================");
		logger.info("================= " + this.getClass().getSimpleName());
		logger.info("=================================================================");
		Constants.REPORT_TEST_NAME = this.getClass().getSimpleName();

		// temp file clean up for suite
		FileUtils.deleteFileIfExistsInTemp("TC001aSftpFileUploadTest.properties");
		FileUtils.deleteFileIfExistsInTemp("TC001aSftpFileUploadTest.properties");

		// generate xml payload
//		paramountXmlData = xmlDataPreparation();
//		Document xmlDocumentContent = 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		xmlDocumentContent = docBuilder.newDocument();

		// YYYMMDDRRR - generate sftp file name pattern
		String dateNow = new SimpleDateFormat("yyyMMdd").format(new Date());
		fileName = "FILE_KDM_" + dateNow + FieldGenerators.generateRandomString(3, Mode.NUMERIC) + ".xml";
		logger.info("FileName: " + fileName);

		// generate file with payload
//		xmlFile = FileUtils.writeXmlDocumentToFile(fileName, xmlDocumentContent);

	}

	@Test
	public void tc001ParamountSftpFileUploadTest() throws SftpException, JSchException {

		logger.info("NOTE: File name: " + fileName);

		// write file to sftp
//		SftpConnection.sendFileToMinioSftp(xmlFile.getAbsolutePath(), fileName);

		// get sftp remote files list\
		String listItems = SftpConnection.getStxWorkingDirFileList();
		logger.info("SFTP remote files: " + listItems);

		Assert.assertTrue("Uploaded xml file for Paramount is not found on SFTP. Actual file list: " + listItems
				+ ", \n Expected: " + fileName, listItems.contains(fileName));
	}

	@After
	public void cleanUpAndPersist() {
//		Map<String, Object> data = FileUtils.convertObjectDataToMap(paramountXmlData);
//		data.put("fileName", fileName);
		logger.debug(String.valueOf(xmlDocumentContent));
		TempProps.writeStringProperties(className + ".properties", xmlDocumentContent.toString());
	}

}
