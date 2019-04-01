package com.carfax.challenge.sevice;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.carfax.challenge.Endpoints;
import com.carfax.challenge.model.VehicleRecords;

@Service
public class VehicleRecordsServiceImpl implements VehicleRecordsService {

	private final RestTemplate rest;
	private final Endpoints endpoints;

	public VehicleRecordsServiceImpl(RestTemplate rest, Endpoints properties) {
		this.endpoints = properties;
		this.rest = rest;
	}

	@Override
	public VehicleRecords findVehicleRecordsByVin(String vin) {
		Objects.requireNonNull(vin, "vehicle identification number must not be null");

		return rest.getForObject(endpoints.getVehicleRecords(), VehicleRecords.class, vin);
	}

}
