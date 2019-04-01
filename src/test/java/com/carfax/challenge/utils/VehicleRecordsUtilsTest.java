package com.carfax.challenge.utils;

import static com.carfax.challenge.utils.VehicleRecordsUtils.ODOMETER_ROLLBACK_EVENT;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.carfax.challenge.model.VehicleRecord;

@RunWith(SpringRunner.class)
public final class VehicleRecordsUtilsTest {

	@Test
	public void whenVehicleRecordsIsValid_thenOdometerRollbackEventAdded() {
		final String vin = "VSSZZZ6JZ9R056308";
		final Collection<VehicleRecord> vehicleRecordsList = Arrays.asList(
				new VehicleRecord(vin, new Date(), 10, 500, Arrays.asList("Air dam replaced")),
				new VehicleRecord(vin, new Date(), 10, 1000, Arrays.asList("Windshield replaced")),
				new VehicleRecord(vin, new Date(), 10, 800, Arrays.asList("Tires replaced")),
				new VehicleRecord(vin, new Date(), 10, 1500, Arrays.asList("Tires rotated")),
				new VehicleRecord(vin, new Date(), 10, 1200, Arrays.asList("Oil service")));

		VehicleRecordsUtils.addOdometerRollBackEvents(vehicleRecordsList);

		VehicleRecord[] array = vehicleRecordsList.toArray(new VehicleRecord[vehicleRecordsList.size()]);
		assertThat(array[0].getServiceDetails(), not(containsInAnyOrder(ODOMETER_ROLLBACK_EVENT)));
		assertThat(array[1].getServiceDetails(), not(containsInAnyOrder(ODOMETER_ROLLBACK_EVENT)));
		assertThat(array[2].getServiceDetails(), containsInAnyOrder("Tires replaced", ODOMETER_ROLLBACK_EVENT));
		assertThat(array[3].getServiceDetails(), not(containsInAnyOrder(ODOMETER_ROLLBACK_EVENT)));
		assertThat(array[4].getServiceDetails(), containsInAnyOrder("Oil service", ODOMETER_ROLLBACK_EVENT));
	}

	@Test(expected = NullPointerException.class)
	public void whenVehicleRecordsIsNull_thenException() {
		VehicleRecordsUtils.addOdometerRollBackEvents(null);
	}

}
