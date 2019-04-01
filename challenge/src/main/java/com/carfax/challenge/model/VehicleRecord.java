package com.carfax.challenge.model;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRecord {
	private String vin;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@JsonProperty("data_provider_id")
	private int dataProviderId;

	@JsonProperty("odometer_reading")
	private int odometerReading;

	@JsonProperty("service_details")
	private Collection<String> serviceDetails;
}
