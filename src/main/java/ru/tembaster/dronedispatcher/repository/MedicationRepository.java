package ru.tembaster.dronedispatcher.repository;

import ru.tembaster.dronedispatcher.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, String> {
}
