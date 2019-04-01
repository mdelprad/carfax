package com.carfax.challenge.sevice;

import com.carfax.challenge.model.VehicleRecords;

public interface VehicleRecordsService {
	/**
	 * Find all records for a vehicle given a VIN
	 * 
	 * @return {@link VehicleRecords}.
	 */
	VehicleRecords findVehicleRecordsByVin(String vin);
}
