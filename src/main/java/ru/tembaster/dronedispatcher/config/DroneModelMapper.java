package ru.tembaster.dronedispatcher.config;

import ru.tembaster.dronedispatcher.dto.DroneCreateView;
import ru.tembaster.dronedispatcher.dto.DroneFullView;
import ru.tembaster.dronedispatcher.dto.MedicationFullView;
import ru.tembaster.dronedispatcher.model.Drone;
import ru.tembaster.dronedispatcher.model.State;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class DroneModelMapper {

	private ModelMapper modelMapper;

	@PostConstruct
	public void initModelMapper() {
		modelMapper.createTypeMap(Drone.class, DroneFullView.class)
				.addMappings(mp -> mp.using(droneMedicationDtoConverter)
									 .map(src -> src, DroneFullView::setMedicationFullViewList));
		modelMapper.createTypeMap(DroneCreateView.class, Drone.class)
				   .addMappings(mp -> mp.map(src -> State.IDLE, Drone::setState));
	}

	private final Converter<Drone, List<MedicationFullView>> droneMedicationDtoConverter = mappingContext ->
		mappingContext.getSource()
					  .getMedications()
					  .stream()
					  .map(o -> modelMapper.map(o, MedicationFullView.class))
					  .collect(Collectors.toList());

}
