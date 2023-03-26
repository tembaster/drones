package ru.tembaster.dronedispatcher.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "obj_drone")
public class Drone {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "serial")
	private String serialNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "model")
	private Model model;

	@Column(name = "weight")
	private Integer weight;

	@Column(name = "battery_capacity")
	private Integer batteryCapacity;

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private State state;

	@OneToMany(mappedBy = "drone")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Medication> medications = new ArrayList<>();
}
