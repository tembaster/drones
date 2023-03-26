package ru.tembaster.dronedispatcher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DroneBatteryView extends DroneDto implements DroneDto.Id,
														  DroneDto.BatteryCapacity {
	private String id;
	private Integer batteryCapacity;
}
