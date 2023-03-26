package ru.tembaster.dronedispatcher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DroneFullView extends DroneCreateView implements DroneDto.Id,
															  DroneDto.State,
															  DroneDto.MedicationFullViewList {
	private String id;
	private ru.tembaster.dronedispatcher.model.State state;
	private List<MedicationFullView> medicationFullViewList;
}
