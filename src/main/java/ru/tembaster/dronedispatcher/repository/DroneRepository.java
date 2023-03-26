package ru.tembaster.dronedispatcher.repository;

import ru.tembaster.dronedispatcher.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, String> {
}
