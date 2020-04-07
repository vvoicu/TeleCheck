package com.apps.sm.devices.thosiba.input.model.submodels;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "PartnerName", "PartnerId", "PartnerContact" })
public class Manufacturer {

	@JsonProperty("PartnerId")
	private String partnerId;

	@JsonProperty("PartnerName")
	private String partnerName;

	@JsonProperty("PartnerContact")
	private PartnerContact partnerContact;

	@JsonProperty("PartnerId")
	public String getPartnerId() {
		return partnerId;
	}

	@JsonProperty("PartnerId")
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	@JsonProperty("PartnerName")
	public String getPartnerName() {
		return partnerName;
	}

	@JsonProperty("PartnerName")
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	@JsonProperty("PartnerContact")
	public PartnerContact getPartnerContact() {
		return partnerContact;
	}

	@JsonProperty("PartnerContact")
	public void setPartnerContact(PartnerContact partnerContact) {
		this.partnerContact = partnerContact;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("PartnerName", partnerName).append("PartnerId", partnerId)
				.append("PartnerContact", partnerContact).toString();
	}
}
