package com.runners.tests.sftp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import mps.app.backend.db.QueryCollection;
import mps.app.backend.model.xml.keydata.DCinemaSecurityMessage;
import mps.tools.Constants;
import mps.tools.CustomVerification;
import mps.tools.connectors.SqlDbConnection;
import mps.tools.utils.AzipUtils;
import mps.tools.utils.PrintUtils;
import mps.tools.utils.StringUtils;
import mps.tools.validations.KeydataValidations;
import rbs.tools.TempProps;
import rbs.tools.utils.FileUtils;

@RunWith(JUnit4.class)
public class TC001dParamountKeyStoreXmlGenerationTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private String previousTestOrderId = "1657026";
	private String expectedStartDate;
	private String expectedEndDate;

	@Before
	public void setupData() throws InterruptedException {
		logger.info("=================================================================");
		logger.info("================= " + this.getClass().getSimpleName());
		logger.info("=================================================================");
		Constants.REPORT_TEST_NAME = this.getClass().getSimpleName();
		
		// test when run as a suite is too fast!!!, need to slow it down for the db to
		// get populated
		TimeUnit.SECONDS.sleep(2);
		// TimeUnit.SECONDS.sleep(20);
		try {
			HashMap<String, String> expectedOrderDataMap = TempProps.readProperties("TC001cParamountVerifyOderTest.properties");
			previousTestOrderId = expectedOrderDataMap.get("order_id");
		} catch (Exception e) {
			logger.info("TC003VerifyParamountOderTest.properties FILE WAS NOT FOUND");
//			System.exit(1);
		}
	}

	@Test
	public void tc001dValidateKeyStoreXmlFieldsTest() throws IOException, SQLException {

		CustomVerification customVerification = new CustomVerification();

		SqlDbConnection dbConnection = new SqlDbConnection();
		List<HashMap<String, String>> orderSiteTable = dbConnection.createDbConnection(Constants.SQL_KEYGEN_ORDER_DB,
				QueryCollection.getOrderSiteQuery(previousTestOrderId));

		logger.info("---- PrintOrderSites ");
		PrintUtils.printSqlQueryData(orderSiteTable);

		String orderId = String.valueOf(orderSiteTable.get(0).get("order_id"));

		List<HashMap<String, String>> suborderTable = dbConnection.createDbConnection(Constants.SQL_KEYGEN_ORDER_DB,
				QueryCollection.getSuborderQuery(orderId));

		logger.info("---- PrintSubOrderSites ");
		PrintUtils.printSqlQueryData(suborderTable);

		// VERIFY ALL SUBORDER ENTRIES --- FOR LOOP
		for (HashMap<String, String> suborderDataRow : suborderTable) {

			// Suborder table row data used for validation and queries
			String suborderId = String.valueOf(suborderDataRow.get("id"));
			String screenId = String.valueOf(suborderDataRow.get("screen_id"));
			String suborderCplId = String.valueOf(suborderDataRow.get("cpl_id"));
			String suborderCplName = String.valueOf(suborderDataRow.get("cpl_name"));
			expectedStartDate = String.valueOf(suborderDataRow.get("not_valid_before"));
			expectedEndDate = String.valueOf(suborderDataRow.get("not_valid_after"));

			logger.info("=========== CHECKING SUBORDER DATA FOR =========== suborderId: " + suborderId);

			// Keystore keydata xml decompression and data modelling
			InputStream compressedKeyData = dbConnection.createDbConnectionDataAsInputStream(Constants.SQL_KEYGEN_KEYSTORE_DB,
					QueryCollection.getKeyStoreQuery(suborderId), "key_data");
			String decompressedXmlPayload = AzipUtils.decompressZlib(compressedKeyData);
			XmlMapper mapper = new XmlMapper();

			// write decompressed xmls to files
			FileUtils.writeToFile(Constants.TEMP_RESOURCES_PATH + suborderId + ".xml", decompressedXmlPayload);

			DCinemaSecurityMessage dataModel = mapper.readValue(decompressedXmlPayload, DCinemaSecurityMessage.class);

			// keystore xml data that will be validated
			String compositionPlaylistId = dataModel.authenticatedPublic.requiredExtensions.kdmRequiredExtensions.compositionPlaylistId;
			String xmlPayloadX509SubjectName = dataModel.authenticatedPublic.requiredExtensions.kdmRequiredExtensions.recipient.subjectName;
			// Validation 3 data preparation
			String xmlContentTitleText = dataModel.authenticatedPublic.requiredExtensions.kdmRequiredExtensions.contentTitleText;
			// Validation 4 data preparation
			String xmlStartDate = dataModel.authenticatedPublic.requiredExtensions.kdmRequiredExtensions.contentKeysNotValidBefore;
			String xmlEndDate = dataModel.authenticatedPublic.requiredExtensions.kdmRequiredExtensions.contentKeysNotValidAfter;

			// xml data clean up
			String subjectNameDnQualifier = StringUtils.extractDnQualifierFromString(xmlPayloadX509SubjectName);
			logger.info("subjectNameDnQualifier: " + subjectNameDnQualifier);

			// DB queries
			List<HashMap<String, String>> kdmDataTable = dbConnection.createDbConnection(Constants.SQL_KEYGEN_KEYSTORE_DB,
					QueryCollection.getCertStoreKdmQuery(subjectNameDnQualifier));
			List<HashMap<String, String>> cplDataTable = dbConnection.createDbConnection(Constants.SQL_STAGE_MPS_DB,
					QueryCollection.getMpsCplQuery(compositionPlaylistId));

			// DB data present
			customVerification.verifyTrue(screenId + " KDM -- KDM certs count expected '1' for SubjectName - dnQualifier: "
					+ subjectNameDnQualifier + " -- Actual: " + kdmDataTable.size(), kdmDataTable.size() == 1);
			customVerification.verifyTrue(screenId + " KDM --Expected 1 CPL entry in results for uuid = " + compositionPlaylistId
					+ " -- Actual size: " + cplDataTable.size(), cplDataTable.size() == 1);

			String cplId = String.valueOf(cplDataTable.get(0).get("id"));
			String kdmId = String.valueOf(kdmDataTable.get(0).get("id"));

			// format dates for validation
			expectedStartDate = expectedStartDate.replace(" ", "T").replace(".0", "+00:00");
			expectedEndDate = expectedEndDate.replace(" ", "T").replace(".0", "+00:00");

			// FOR DEBUGGING
			// Validation 1 data preparation
			logger.info("---- KDM data: ");
			PrintUtils.printSqlQueryData(kdmDataTable);
			// Validation 2 data preparation
			logger.info("compositionPlaylistId: " + compositionPlaylistId);
			logger.info("---- CPL data: ");
			PrintUtils.printSqlQueryData(cplDataTable);
			logger.info("---- Converted Expected Dates ----");
			logger.info(" Xml start: " + xmlStartDate + " -- and end: " + xmlEndDate);
			logger.info("Expected start: " + expectedStartDate + " -- and end: " + expectedEndDate);
			logger.info("ScreenId: " + screenId);
			logger.info(" KDM id: " + String.valueOf(kdmDataTable.get(0).get("id")));
			KeydataValidations keydataValidations = new KeydataValidations(customVerification);

			keydataValidations.verifyXmlKeydata(screenId, kdmId, suborderCplId, cplId, compositionPlaylistId, xmlContentTitleText,
					suborderCplName, xmlStartDate, xmlEndDate, xmlStartDate, xmlEndDate);
		}

		customVerification.verifyNoErrors();

	}

}
