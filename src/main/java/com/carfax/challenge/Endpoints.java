package com.carfax.challenge;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "endpoints")
public class Endpoints {
	private String vehicleRecords;
}
