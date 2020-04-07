package com.apps.sm.devices.thosiba.input.model.submodels;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "GPFGUID", "CertificatePurpose", "CSR" })
public class CertificateSigningRequest {

	@JsonProperty("CSR")
	private String csr;

	@JsonProperty("CertificatePurpose")
	private String certificatePurpose;

	@JsonProperty("GPFGUID")
	private String gPFGUID;

	@JsonProperty("CSR")
	public String getCSR() {
		return csr;
	}

	@JsonProperty("CSR")
	public void setCSR(String cSR) {
		this.csr = cSR;
	}

	@JsonProperty("CertificatePurpose")
	public String getCertificatePurpose() {
		return certificatePurpose;
	}

	@JsonProperty("CertificatePurpose")
	public void setCertificatePurpose(String certificatePurpose) {
		this.certificatePurpose = certificatePurpose;
	}

	@JsonProperty("GPFGUID")
	public String getGPFGUID() {
		return gPFGUID;
	}

	@JsonProperty("GPFGUID")
	public void setGPFGUID(String GPFGUID) {
		this.gPFGUID = GPFGUID;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("CSR", csr).append("CertificatePurpose", certificatePurpose)
				.append("GPFGUID", gPFGUID).toString();
	}
}