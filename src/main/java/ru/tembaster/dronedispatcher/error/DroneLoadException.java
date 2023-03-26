package ru.tembaster.dronedispatcher.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DroneLoadException extends RuntimeException {

	public DroneLoadException(String message) {
		super(message);
	}

}
