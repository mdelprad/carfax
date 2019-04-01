package com.carfax.challenge.model;

import java.util.Collection;

import lombok.Data;

@Data
public class VehicleRecords {
	private Collection<VehicleRecord> records;
}
