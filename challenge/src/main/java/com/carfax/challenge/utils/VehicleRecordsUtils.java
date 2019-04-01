package com.carfax.challenge.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import com.carfax.challenge.model.VehicleRecord;

public final class VehicleRecordsUtils {
	public static final String ODOMETER_ROLLBACK_EVENT = "Odometer Rollback";

	private VehicleRecordsUtils() {

	}

	/**
	 * Add Odometer Rollback events for a given vehicle records
	 * {@link VehicleRecordsUtils#ODOMETER_ROLLBACK_EVENT} to field
	 * {@link VehicleRecord#serviceDetails}.
	 * 
	 * @param vehicleRecords
	 */
	public static void addOdometerRollBackEvents(Collection<VehicleRecord> vehicleRecords) {
		Objects.requireNonNull(vehicleRecords, "vehicle records must not be null");

		Iterator<VehicleRecord> iterator = vehicleRecords.iterator();
		VehicleRecord previous = null;

		if (iterator.hasNext()) {
			previous = iterator.next();
		}

		while (iterator.hasNext()) {
			VehicleRecord current = iterator.next();

			if (previous.getOdometerReading() > current.getOdometerReading()) {
				Collection<String> serviceDetails = new ArrayList<>(current.getServiceDetails());
				current.setServiceDetails(serviceDetails);
				serviceDetails.add(ODOMETER_ROLLBACK_EVENT);
			}

			previous = current;
		}
	}
}
