package com.apps.sm.devices.thosiba.input.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.apps.sm.devices.thosiba.input.model.submodels.CertificateSigningRequest;
import com.apps.sm.devices.thosiba.input.model.submodels.Manufacturer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({ "CertificateSigningRequestBatchId", "DocumentCreated", "NumberOfCSRsInBatch", "Manufacturer",
		"CertificateSigningRequest" })
public class CertificateSigningRequestBatch {


	@JsonProperty("NumberOfCSRsInBatch")
	private String numberOfCSRsInBatch;

	@JsonProperty("DocumentCreated")
	private String documentCreated;

	@JsonProperty("CertificateSigningRequestBatchId")
	private String certificateSigningRequestBatchId;

	@JsonProperty("CertificateSigningRequest")
	@JacksonXmlElementWrapper(useWrapping = false)
	private List<CertificateSigningRequest> certificateSigningRequest = new ArrayList<>();

	@JsonProperty("Manufacturer")
	private Manufacturer manufacturer;

	@JacksonXmlProperty(isAttribute = true)
	private String xmlns;

	@JsonProperty("NumberOfCSRsInBatch")
	public String getNumberOfCSRsInBatch() {
		return numberOfCSRsInBatch;
	}

	@JsonProperty("NumberOfCSRsInBatch")
	public void setNumberOfCSRsInBatch(String numberOfCSRsInBatch) {
		this.numberOfCSRsInBatch = numberOfCSRsInBatch;
	}

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	@JsonProperty("DocumentCreated")
	public String getDocumentCreated() {
		return documentCreated;
	}

	@JsonProperty("DocumentCreated")
	public void setDocumentCreated(String documentCreated) {
		this.documentCreated = documentCreated;
	}

	@JsonProperty("CertificateSigningRequestBatchId")
	public String getCertificateSigningRequestBatchId() {
		return certificateSigningRequestBatchId;
	}

	@JsonProperty("CertificateSigningRequestBatchId")
	public void setCertificateSigningRequestBatchId(String certificateSigningRequestBatchId) {
		this.certificateSigningRequestBatchId = certificateSigningRequestBatchId;
	}

	@JsonProperty("CertificateSigningRequest")
	public List<CertificateSigningRequest> getCertificateSigningRequest() {
		return certificateSigningRequest;
	}

	@JsonProperty("CertificateSigningRequest")
	public void setCertificateSigningRequest(List<CertificateSigningRequest> certificateSigningRequest) {
		this.certificateSigningRequest = certificateSigningRequest;
	}

	@JsonProperty("Manufacturer")
	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	@JsonProperty("Manufacturer")
	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("CertificateSigningRequestBatchId", certificateSigningRequestBatchId)
				.append("DocumentCreated", documentCreated).append("NumberOfCSRsInBatch", numberOfCSRsInBatch)
				.append("Manufacturer", manufacturer).append("CertificateSigningRequest", certificateSigningRequest)
				.toString();
	}
}
