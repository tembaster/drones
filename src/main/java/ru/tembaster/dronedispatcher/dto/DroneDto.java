package ru.tembaster.dronedispatcher.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@SuperBuilder(setterPrefix = "set")
public class DroneDto {


	interface Id {
		@Schema(example = "ae61e4be-8402-43dc-bcdb-5f12d0ddf339", type = "string", format = "uuid", required = true,
				description = "Unique identifier in format UUID 4", maxLength = 36)
		String getId();
	}

	interface SerialNumber {
		@Size(min = 1, message = "Serial number should have at least 1 character")
		@Size(max = 100, message = "Serial number should have 100 characters max")
		@Schema(example = "9HWovf35qu7wIbIcs", type = "string",
				description = "Drone serial number must not be more than 100 chars long", maxLength = 36)
		String getSerialNumber();
	}

	interface Model {
		@Schema(example = "HEAVYWEIGHT", required = true, description = "Drone model",
				implementation = ru.tembaster.dronedispatcher.model.Model.class)
		ru.tembaster.dronedispatcher.model.Model getModel();
	}

	interface Weight {
		@Max(value = 500, message = "weight should not exceed 500gr")
		@Schema(example = "400", required = true, description = "Drone weight")
		Integer getWeight();
	}

	interface BatteryCapacity {
		@Min(value = 0, message = "battery percentage cannot be lower than 0%")
		@Max(value = 100, message = "battery percentage cannot be more than 100%")
		@Schema(example = "100", required = true, description = "Drone battery capacity must not be more than 100%")
		Integer getBatteryCapacity();
	}

	interface State {
		@Schema(example = "IDLE", required = true, description = "Current drone state",
				implementation = ru.tembaster.dronedispatcher.model.State.class)
		ru.tembaster.dronedispatcher.model.State getState();
	}

	interface MedicationFullViewList {
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		@ArraySchema(schema = @Schema(implementation = MedicationFullView.class, description = "List of attached to drone medications"))
		List<MedicationFullView> getMedicationFullViewList();
	}
}
