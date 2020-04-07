package com.apps.sm.devices.thosiba.input.model.submodels;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "PartnerName", "PartnerId", "PartnerContact" })
public class PartnerContact {

	@JsonProperty("PartnerContactName")
	private String partnerContactName;

	@JsonProperty("PartnerContactTelephone")
	private String partnerContactTelephone;

	@JsonProperty("PartnerContactEmail")
	private String partnerContactEmail;

	@JsonProperty("PartnerContactName")
	public String getPartnerContactName() {
		return partnerContactName;
	}

	@JsonProperty("PartnerContactName")
	public void setPartnerContactName(String partnerContactName) {
		this.partnerContactName = partnerContactName;
	}

	@JsonProperty("PartnerContactTelephone")
	public String getPartnerContactTelephone() {
		return partnerContactTelephone;
	}

	@JsonProperty("PartnerContactTelephone")
	public void setPartnerContactTelephone(String partnerContactTelephone) {
		this.partnerContactTelephone = partnerContactTelephone;
	}

	@JsonProperty("PartnerContactEmail")
	public String getPartnerContactEmail() {
		return partnerContactEmail;
	}

	@JsonProperty("PartnerContactEmail")
	public void setPartnerContactEmail(String partnerContactEmail) {
		this.partnerContactEmail = partnerContactEmail;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("PartnerContactName", partnerContactName)
				.append("PartnerContactTelephone", partnerContactTelephone)
				.append("PartnerContactEmail", partnerContactEmail).toString();
	}
}