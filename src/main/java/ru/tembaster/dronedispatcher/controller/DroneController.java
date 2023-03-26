package ru.tembaster.dronedispatcher.controller;

import ru.tembaster.dronedispatcher.dto.DroneBatteryView;
import ru.tembaster.dronedispatcher.dto.DroneCreateView;
import ru.tembaster.dronedispatcher.dto.DroneFullView;
import ru.tembaster.dronedispatcher.dto.MedicationCreateView;
import ru.tembaster.dronedispatcher.dto.MedicationFullView;
import ru.tembaster.dronedispatcher.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/drones")
@RequiredArgsConstructor
@RestController
@Validated
public class DroneController {

	private final DroneService droneService;

	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<DroneFullView>> getAll() {
		return new ResponseEntity<>(droneService.getAvailableDrones(), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DroneFullView> registerNewDrone(@RequestBody @Valid DroneCreateView drone) {
		return new ResponseEntity<>(droneService.registerNewDrone(drone), HttpStatus.CREATED);
	}

	@GetMapping(value = "/{droneId}/battery", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DroneBatteryView> checkCurrentBatteryCapacity(@PathVariable String droneId) {
		return new ResponseEntity<>(droneService.getBatteryCapacity(droneId), HttpStatus.OK);
	}

	@GetMapping(value = "/{droneId}/medications", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MedicationFullView>> getLoadedMedications(@PathVariable String droneId) {
		return new ResponseEntity<>(droneService.getLoadedMedications(droneId), HttpStatus.OK);
	}

	@PostMapping(value = "/{droneId}/medications", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DroneFullView> loadMedicationsToDrone(@PathVariable String droneId,
																		   @RequestBody @Valid MedicationCreateView medications) {
		return new ResponseEntity<>(droneService.assignMedicationToDrone(droneId, medications), HttpStatus.OK);
	}

}
