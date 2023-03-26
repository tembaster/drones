package ru.tembaster.dronedispatcher.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MedicationCreateView extends MedicationDto implements MedicationDto.Code,
																   MedicationDto.Name,
																   MedicationDto.Weight,
																   MedicationDto.Image {
	private String code;
	private String name;
	private Integer weight;
	private String image;
}