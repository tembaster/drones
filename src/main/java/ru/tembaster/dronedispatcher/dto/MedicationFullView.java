package ru.tembaster.dronedispatcher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicationFullView extends MedicationCreateView implements MedicationDto.Id {
	private String id;
}
