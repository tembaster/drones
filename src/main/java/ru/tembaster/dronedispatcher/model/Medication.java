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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "obj_medication")
public class Medication {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "weight")
	private Integer weight;

	@Column(name = "code")
	private String code;

	@Column(name = "image")
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "drone_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Drone drone;
}
