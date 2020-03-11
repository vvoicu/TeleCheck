package com.runners.tests.sftp;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mps.app.backend.db.MpsDbCalls;
import mps.app.backend.db.QueryCollection;
import mps.tools.Constants;
import mps.tools.CustomVerification;
import mps.tools.connectors.SqlDbConnection;
import mps.tools.utils.PrintUtils;
import mps.tools.validations.DbValidations;
import mps.tools.validations.model.DbValidationFieldsModel;
import mps.tools.validations.model.DbValidationTablesModel;
import rbs.tools.TempProps;

@RunWith(JUnit4.class)
public class TC001cParamountVerifyOderTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final int TEST_RETRY_COUNT = 5;
	private String className = this.getClass().getSimpleName();

	private DbValidationFieldsModel expectedFieldsCollections = new DbValidationFieldsModel();
	private HashMap<String, String> expectedDataMap;
	private String formatedExpectedEndDate;
	private String formatedExpectedStartDate;
	private String expectedKeyOrderId;
	private String theatreId;
	private String expectedSuborderStateId = "22";
	private String expectedContentId;
	private String expectedTitle;
	private String expectedDigitalKeyOrderId;

	private String paramountApiId = "14";
	private String expectedOrderGenerationStateId = "7";
	private String expectedOrderDeliveryStateId = "16";
	// private String expectedOrderGenerationStateId = "5";
	// private String expectedOrderDeliveryStateId = "14";
	int expectedPendingOrderCount = 1;
	private String pendingOrderTableApiOrderId;

	// DB table data
	private List<HashMap<String, String>> timezoneDataTable;

	private DbValidationTablesModel dbTables;

	@Before
	public void dataSetup() throws SQLException, InterruptedException {
		logger.info("=================================================================");
		logger.info("================= " + this.getClass().getSimpleName());
		logger.info("=================================================================");
		Constants.REPORT_TEST_NAME = this.getClass().getSimpleName();

		// read sftp generated file name - EXPECTED DATA
		expectedDataMap = TempProps.readProperties("TC001aParamountSftpFileUploadTest.properties");
		expectedDataMap.remove("fileName");
		logger.info("---- Expected Data Map: \n" + expectedDataMap);

		expectedKeyOrderId = expectedDataMap.get("digital_key_order_id");
		theatreId = expectedDataMap.get("theatre_id");
		expectedContentId = expectedDataMap.get("content_id");
		expectedTitle = expectedDataMap.get("title");
		expectedDigitalKeyOrderId = expectedDataMap.get("digital_key_order_id");

		// DB data grabber
		MpsDbCalls mpsDbCalls = new MpsDbCalls();
		dbTables = mpsDbCalls.grabOrderDbTablesByThirdPartyOrderId(expectedDigitalKeyOrderId, expectedOrderGenerationStateId, TEST_RETRY_COUNT);

		String extractedPendingOrderID = dbTables.pendingOrdersTable.get(0).get("id").toString();
		
		String extractTimezoneQuery = QueryCollection.getTimezoneQuery(paramountApiId, extractedPendingOrderID);
		SqlDbConnection sqlStuff = new SqlDbConnection();
		timezoneDataTable = sqlStuff.createDbConnection(Constants.SQL_STAGE_MPS_DB, extractTimezoneQuery);
		
		pendingOrderTableApiOrderId = String.valueOf(dbTables.pendingSubOrdersTable.get(0).get("pending_order_id"));

//		String expectedTimezoneEndDate = StringUtils.convertLocalTimeToUtc(expectedDataMap.get("license_end_date"),
//				"yyyy-MM-dd'T'HH:mm:ss.00", String.valueOf(timezoneDataTable.get(0).get("timezone")));
//		String expectedTimezoneStartDate = StringUtils.convertLocalTimeToUtc(expectedDataMap.get("license_begin_date"),
//				"yyyy-MM-dd'T'HH:mm:ss.00", String.valueOf(timezoneDataTable.get(0).get("timezone")));
//
//		// format expected fields to match DB field format
//		formatedExpectedEndDate = expectedTimezoneEndDate.replace("T", " ").split("\\+")[0];
//		formatedExpectedStartDate = expectedTimezoneStartDate.replace("T", " ").split("\\+")[0];
		
		formatedExpectedEndDate = String.valueOf(expectedDataMap.get("license_end_date")).replace(".00", "").replace("T", " ").split("\\+")[0];
		formatedExpectedStartDate = String.valueOf(expectedDataMap.get("license_begin_date")).replace(".00", "").replace("T", " ").split("\\+")[0];

		logger.info("---- Timezone data: ");
		PrintUtils.printSqlQueryData(timezoneDataTable);
	}

	/**
	 * Test validates 20 entries in 2 DataBases and 3 tables
	 * (keygen_api.pendingOrder, keygen_api.pendingSuborder, keygen_order.order)
	 * 
	 * @throws SQLException
	 */
	@Test
	public void tc001cVerifyParamountOderTest() throws SQLException {

		CustomVerification customVerification = new CustomVerification();
		DbValidations dbValidations = new DbValidations(customVerification);

		// verifications between the generated xml file fields and the db data
		
		// Set Expected data
		expectedFieldsCollections.setPendingOrderFields(expectedPendingOrderCount, formatedExpectedEndDate,
				formatedExpectedStartDate, expectedKeyOrderId, expectedKeyOrderId);
		expectedFieldsCollections.setPendingSuboderFields(expectedTitle, formatedExpectedEndDate,
				formatedExpectedStartDate, theatreId, expectedSuborderStateId, expectedContentId);
		expectedFieldsCollections.setOrderFields(expectedDigitalKeyOrderId, pendingOrderTableApiOrderId,
				formatedExpectedStartDate, formatedExpectedEndDate, expectedOrderDeliveryStateId, expectedOrderGenerationStateId);
		

		// Verify all data - see description
		dbValidations.verifyDatabaseOrderTables(dbTables, expectedFieldsCollections);

//		// Verify Pending Orders Data
//		dbValidations.verifyPendingOrderTableFields(dbTables.pendingOrdersTable, expectedPendingOrderCount, formatedExpectedEndDate,
//				formatedExpectedStartDate, expectedKeyOrderId, expectedKeyOrderId);
//
//		// Verify Pending Suborders Data
//		dbValidations.verifyPendingSuborderTableFields(dbTables.pendingSubOrdersTable, expectedTitle, formatedExpectedEndDate,
//				formatedExpectedStartDate, theatreId, expectedSuborderStateId, expectedContentId);
//
//		// Verify order Data
//		dbValidations.verifyOrderTableFields(dbTables.orderTable, expectedDigitalKeyOrderId, pendingOrderTableApiOrderId,
//				formatedExpectedStartDate, formatedExpectedEndDate, expectedOrderDeliveryStateId, expectedOrderGenerationStateId);

		// Extract timezone
		customVerification.verifyTrue("Timezone entry count missmatch, expected 1 Actual: " + timezoneDataTable.size(),
				timezoneDataTable.size() == 1);

		// String timezoneLocation =
		// String.valueOf(timezoneDataTable.get(0).get("timezone"));

		customVerification.verifyNoErrors();
	}

	@After
	public void persistTestData() throws SQLException, InterruptedException {
		// Constants.SQL_KEYGEN_ORDER_DB order table 'id' field
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("order_id", String.valueOf(dbTables.orderTable.get(0).get("id")));
		logger.info("Persisted OrderId: " + data);
		TempProps.writeMapProperties(className + ".properties", data);
	}

}