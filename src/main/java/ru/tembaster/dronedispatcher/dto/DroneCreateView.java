package ru.tembaster.dronedispatcher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DroneCreateView extends DroneDto implements DroneDto.Model,
														 DroneDto.Weight,
														 DroneDto.SerialNumber,
														 DroneDto.BatteryCapacity {
	private ru.tembaster.dronedispatcher.model.Model model;
	private Integer weight;
	private String serialNumber;
	private Integer batteryCapacity;
}
