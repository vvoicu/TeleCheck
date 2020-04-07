package com.apps.sm.devices.thosiba.input.data;

import java.io.IOException;

import com.apps.sm.devices.thosiba.input.model.CertificateSigningRequestBatch;
import com.apps.sm.devices.thosiba.input.model.submodels.CertificateSigningRequest;
import com.apps.sm.devices.thosiba.input.model.submodels.Manufacturer;
import com.apps.sm.devices.thosiba.input.model.submodels.PartnerContact;
import com.apps.tools.FieldGenerators;
import com.apps.tools.FieldGenerators.Mode;
import com.apps.tools.file.FileUtils;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

public class GenerateThoshibaInputProdData {
	
	
	public static void main(String[] args) throws IOException {
		XmlMapper mapper = new XmlMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
		CertificateSigningRequestBatch data = generateCertificateSigningRequestBatch();
		String finalPayload = mapper.writeValueAsString(data);
		finalPayload = finalPayload.replace("<?xml version='1.0' encoding='UTF-8'?>", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		FileUtils.writeToFile("simble.xml", finalPayload);
		System.out.println(finalPayload);
	
	}

	/**
	 * Root
	 */
	public static CertificateSigningRequestBatch generateCertificateSigningRequestBatch() {

		CertificateSigningRequestBatch document = new CertificateSigningRequestBatch();
		document.setXmlns(ToshibaDefaultData.xmlns);
		document.setCertificateSigningRequestBatchId(ToshibaDefaultData.certificateSigningRequestBatchId);
		document.setDocumentCreated(ToshibaDefaultData.documentCreated);
		document.setNumberOfCSRsInBatch(ToshibaDefaultData.numberOfCSRsInBatch);
		document.setManufacturer(generateManufacturer());
		document.getCertificateSigningRequest().add(generateCertificateSigninRequest(ToshibaDefaultData.CSR_1, ToshibaDefaultData.GPFGUID_1, ToshibaDefaultData.certificatePurpose_1));
		document.getCertificateSigningRequest().add(generateCertificateSigninRequest(ToshibaDefaultData.CSR_2, ToshibaDefaultData.GPFGUID_2, ToshibaDefaultData.certificatePurpose_2));
		document.getCertificateSigningRequest().add(generateCertificateSigninRequest(ToshibaDefaultData.CSR_3, ToshibaDefaultData.GPFGUID_3, ToshibaDefaultData.certificatePurpose_3));
		document.getCertificateSigningRequest().add(generateCertificateSigninRequest(ToshibaDefaultData.CSR_4, ToshibaDefaultData.GPFGUID_4, ToshibaDefaultData.certificatePurpose_4));
	
		return document;
	}

	/**
	 * Used by CertificateSigningRequestBatch - is a child of
	 * 
	 * @return
	 */
	public static Manufacturer generateManufacturer() {
		Manufacturer document = new Manufacturer();
		document.setPartnerName(ToshibaDefaultData.partnerName);
		document.setPartnerId(ToshibaDefaultData.partnerId);
		document.setPartnerContact(generatePartnerContact());
		return document;
	}

	/**
	 * Used by Manufacturer - is a child of
	 * 
	 * @return
	 */
	public static PartnerContact generatePartnerContact() {
		PartnerContact document = new PartnerContact();
		document.setPartnerContactName(ToshibaDefaultData.partnerContactName);
		document.setPartnerContactTelephone(ToshibaDefaultData.partnerContactTelephone);
		document.setPartnerContactEmail(FieldGenerators.generateRandomString(20, Mode.ALPHANUMERIC));
//		document.setPartnerContactEmail(ToshibaDefaultData.partnerContactEmail);
		return document;
	}

	public static CertificateSigningRequest generateCertificateSigninRequest(String CSR, String GPFGUID, String certificatePurpose) {
		CertificateSigningRequest document = new CertificateSigningRequest();
		document.setCertificatePurpose(certificatePurpose);
		document.setCSR(CSR);
		document.setGPFGUID(GPFGUID);
		return document;
	}

}
