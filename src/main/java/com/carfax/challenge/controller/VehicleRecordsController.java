package com.carfax.challenge.controller;

import static com.carfax.challenge.utils.VehicleRecordsUtils.addOdometerRollBackEvents;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carfax.challenge.model.VehicleRecords;
import com.carfax.challenge.sevice.VehicleRecordsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/vehicle-records")
public class VehicleRecordsController {

	private final VehicleRecordsService service;

	public VehicleRecordsController(VehicleRecordsService service) {
		this.service = service;
	}

	@GetMapping("/{vin}/odometer-rollback")
	public ResponseEntity<VehicleRecords> vehicleRecordsWithOdometerRollback(@PathVariable String vin) {
		log.info("Getting vehicle records with odometer rollback event for VIN == {}", vin);
		
		try {
			VehicleRecords vehicleRecords = service.findVehicleRecordsByVin(vin);
			addOdometerRollBackEvents(vehicleRecords.getRecords());
			return new ResponseEntity<>(vehicleRecords, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error getting vehicle records with odometer rollback event for VIN == {}", vin, ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
