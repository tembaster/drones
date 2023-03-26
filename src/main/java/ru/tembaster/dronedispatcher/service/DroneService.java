package ru.tembaster.dronedispatcher.service;

import ru.tembaster.dronedispatcher.dto.DroneBatteryView;
import ru.tembaster.dronedispatcher.dto.DroneCreateView;
import ru.tembaster.dronedispatcher.dto.DroneFullView;
import ru.tembaster.dronedispatcher.dto.MedicationCreateView;
import ru.tembaster.dronedispatcher.dto.MedicationFullView;
import ru.tembaster.dronedispatcher.error.DroneLoadException;
import ru.tembaster.dronedispatcher.error.ResourceNotFoundException;
import ru.tembaster.dronedispatcher.model.Drone;
import ru.tembaster.dronedispatcher.model.Medication;
import ru.tembaster.dronedispatcher.model.State;
import ru.tembaster.dronedispatcher.repository.DroneRepository;
import ru.tembaster.dronedispatcher.repository.MedicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DroneService {

	@Value("${low_battery_indicator:25}")
	public Integer lowBatteryIndicator;
	private final DroneRepository droneRepository;
	private final MedicationRepository medicationRepository;

	private final ModelMapper modelMapper;

	@Autowired
	public DroneService(DroneRepository droneRepository, MedicationRepository medicationRepository, ModelMapper modelMapper) {
		this.droneRepository = droneRepository;
		this.medicationRepository = medicationRepository;
		this.modelMapper = modelMapper;
	}

	public List<DroneFullView> getAvailableDrones() {
		List<Drone> fetchedDrones = droneRepository.findAll();
		return fetchedDrones.stream()
							.filter(o -> getMedicationsWeight(o.getMedications()) < o.getWeight())
							.map(o -> modelMapper.map(o, DroneFullView.class))
							.collect(Collectors.toList());
	}

	public DroneFullView registerNewDrone(DroneCreateView drone) {
		Drone savedEntity = droneRepository.save(modelMapper.map(drone, Drone.class));
		return modelMapper.map(savedEntity, DroneFullView.class);
	}

	public DroneBatteryView getBatteryCapacity(String droneId) {
		Drone fetchedData =
				getDrone(droneId);
		return modelMapper.map(fetchedData, DroneBatteryView.class);
	}

	public List<MedicationFullView> getLoadedMedications(String droneId) {
		Drone fetchedData = getDrone(droneId);
		return fetchedData.getMedications()
						  .stream()
						  .map(o -> modelMapper.map(o, MedicationFullView.class))
						  .collect(Collectors.toList());
	}

	public DroneFullView assignMedicationToDrone(String droneId, MedicationCreateView medication) {
		Drone fetchedDrone = getDrone(droneId);
		if (fetchedDrone.getBatteryCapacity() <= lowBatteryIndicator) {
			throw new DroneLoadException("Not enough battery capacity for loading on drone with id: " + droneId);
		}
		if (getMedicationsWeight(fetchedDrone.getMedications()) + medication.getWeight() > fetchedDrone.getWeight()) {
			throw new DroneLoadException("Not enough load capacity on drone with id: " + droneId);
		}
		return modelMapper.map(updateDroneLoading(fetchedDrone, medication), DroneFullView.class);
	}

	private Drone updateDroneLoading(Drone fetchedDrone, MedicationCreateView medication) {
		Medication medicationPersistCandidate = modelMapper.map(medication, Medication.class);
		fetchedDrone.getMedications()
					.add(medicationPersistCandidate);
		fetchedDrone.setState(State.LOADING);
		medicationPersistCandidate.setDrone(fetchedDrone);
		medicationRepository.save(medicationPersistCandidate);
		return fetchedDrone;
	}

	private int getMedicationsWeight(List<Medication> meds) {
		return meds.stream()
				   .map(Medication::getWeight)
				   .reduce(0, Integer::sum);
	}

	private Drone getDrone(String droneId) {
		return droneRepository.findById(droneId)
							  .orElseThrow(() -> new ResourceNotFoundException(
									  String.format("Drone with id %s not found", droneId)));
	}

	@Scheduled(initialDelay = 0, fixedDelayString = "${journaling_delay:300000}")
	public void journalBatteries() {
		droneRepository.findAll()
					   .stream()
					   .map(fetchedDrone -> modelMapper.map(fetchedDrone, DroneBatteryView.class))
					   .forEach(o -> log.info(String.format("drone with id: %s has capacity: %s", o.getId(), o.getBatteryCapacity())));
	}
}
