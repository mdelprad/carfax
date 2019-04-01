package com.carfax.challenge.controller;

import static com.carfax.challenge.utils.VehicleRecordsUtils.ODOMETER_ROLLBACK_EVENT;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.carfax.challenge.model.VehicleRecord;
import com.carfax.challenge.model.VehicleRecords;
import com.carfax.challenge.sevice.VehicleRecordsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VehicleRecordsControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private VehicleRecordsService service;

	@Test
	public void givenOdometerRollBack_whenVinIsValid_thenRollbackEventsAddedAnd200Status() throws Exception {
		final String vin = "VSSZZZ6JZ9R056308";
		final VehicleRecords vehicleRecords = new VehicleRecords();
		final Collection<VehicleRecord> vehicleRecordsList = Arrays.asList(
				new VehicleRecord(vin, new Date(), 10, 500, Arrays.asList("Air dam replaced")),
				new VehicleRecord(vin, new Date(), 10, 1000, Arrays.asList("Windshield replaced")),
				new VehicleRecord(vin, new Date(), 10, 800, Arrays.asList("Tires replaced")),
				new VehicleRecord(vin, new Date(), 10, 1500, Arrays.asList("Tires rotated")),
				new VehicleRecord(vin, new Date(), 10, 1200, Arrays.asList("Oil service")));

		vehicleRecords.setRecords(vehicleRecordsList);

		given(this.service.findVehicleRecordsByVin(vin)).willReturn(vehicleRecords);

		this.mvc.perform(get("/vehicle-records/{vim}/odometer-rollback", vin))
				.andExpect(status().isOk())
				.andExpect(jsonPath("records[0].service_details", not(containsInAnyOrder(ODOMETER_ROLLBACK_EVENT))))
				.andExpect(jsonPath("records[1].service_details", not(containsInAnyOrder(ODOMETER_ROLLBACK_EVENT))))
				.andExpect(jsonPath("records[2].service_details", containsInAnyOrder("Tires replaced", ODOMETER_ROLLBACK_EVENT)))
				.andExpect(jsonPath("records[3].service_details", not(containsInAnyOrder(ODOMETER_ROLLBACK_EVENT))))
				.andExpect(jsonPath("records[4].service_details", containsInAnyOrder("Oil service", ODOMETER_ROLLBACK_EVENT)));
	}

	@Test
	public void givenOdometerRollBack_whenVinIsInvalid_thenInternalServerError() throws Exception {
		final String vin = "VSSZZZ6JZ9R056308";

		given(this.service.findVehicleRecordsByVin(vin)).willThrow(new RuntimeException());

		this.mvc.perform(get("/vehicle-records/{vim}/odometer-rollback", vin))
				.andExpect(status().isInternalServerError());
	}

}
