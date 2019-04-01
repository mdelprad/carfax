package com.carfax.challenge.sevice;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.carfax.challenge.Endpoints;
import com.carfax.challenge.model.VehicleRecord;
import com.carfax.challenge.model.VehicleRecords;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleRecordsServiceTest {
	@MockBean
	private RestTemplate restTemplate;

	@Autowired
	private VehicleRecordsService service;

	@Autowired
	private Endpoints endpoints;

	@Test
	public void whenVinIsValid_thenVehicleRecordsReturned() {
		final String vin = "VSSZZZ6JZ9R056308";
		final VehicleRecords vehicleRecords = new VehicleRecords();
		final Collection<VehicleRecord> vehicleRecordsList = Arrays.asList(
				new VehicleRecord(vin, new Date(), 10, 500, Arrays.asList("Air dam replaced")),
				new VehicleRecord(vin, new Date(), 10, 1000, Arrays.asList("Windshield replaced")));
		vehicleRecords.setRecords(vehicleRecordsList);

		Mockito.when(restTemplate.getForObject(endpoints.getVehicleRecords(), VehicleRecords.class, vin))
				.thenReturn(vehicleRecords);

		VehicleRecords vehicleRecordsReturned = service.findVehicleRecordsByVin(vin);
		Assert.assertEquals(vehicleRecordsReturned, vehicleRecords);
	}

	@Test
	public void whenVinIsValidAndNotFound_thenNull() {
		final String vin = "VSSZZZ6JZ9R056308";

		Mockito.when(restTemplate.getForEntity(endpoints.getVehicleRecords(), VehicleRecords.class, vin))
				.thenReturn(null);

		VehicleRecords vehicleRecordsReturned = service.findVehicleRecordsByVin(vin);
		Assert.assertEquals(vehicleRecordsReturned, null);
	}

	@Test(expected = NullPointerException.class)
	public void whenVinIsNull_thenException() {
		service.findVehicleRecordsByVin(null);
	}
}
