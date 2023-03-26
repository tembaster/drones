package ru.tembaster.dronedispatcher.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;

public class MedicationDto {

	private static final String NAME_REGEXP = "^[a-zA-Z0-9_-]+$";
	private static final String CODE_REGEXP = "^[A-Z0-9_]+$";

	interface Id {
		@Schema(example = "ae61e4be-8402-43dc-bcdb-5f12d0ddf339", type = "string", format = "uuid", required = true,
				description = "Unique identifier in format UUID 4", maxLength = 36)
		String getId();

	}
	interface Name {
		@Pattern(regexp = NAME_REGEXP, message = "Medication name: allowed only letters, numbers, ‘-‘, ‘_’")
		@Schema(example = "medic_1", description = "Medication name must contain only letters, numbers, ‘-‘, ‘_’")
		String getName();
	}

	interface Weight {
		@Schema(example = "400", required = true, description = "Medication weight in grams")
		Integer getWeight();
	}

	interface Code {
		@Pattern(regexp = CODE_REGEXP, message = "Medication code: allowed only upper case letters, underscore and numbers")
		@Schema(example = "CJ0T832G1N_3XF7FKG0H",
				description = "Medication code must contain only upper case letters, underscore and numbers")
		String getCode();
	}

	interface Image {
		@Schema(example = "http://some-url/path/to/image", description = "Path to image (to simplify)")
		String getImage();
	}

	interface Drone {
		@JsonInclude(JsonInclude.Include.NON_NULL)
		@Schema(example = "ae61e4be-8402-43dc-bcdb-5f12d0ddf339", description = "Id of assigned drone")
		String getDrone();
	}

}
