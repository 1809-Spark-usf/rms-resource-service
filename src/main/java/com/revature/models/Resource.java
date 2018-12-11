package com.revature.models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.enumerations.Type;

@Entity
@Table(name = "resources")
public class Resource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull(message = "Type is required.")
	private Type type;
	@JsonIgnore
	@Transient
	private Campus campus;
	@ManyToOne
	@JoinColumn(name="building_id", nullable=false)
	private Building building;
	@Transient
	private int buildingId;
	private String name;
	private boolean disabled;
	private boolean inactive;
	private boolean retired;
	@Column(name="available_start_date")
	private LocalDateTime availableStartDate;
	@Column(name="reservable_after")
	private LocalDateTime reservableAfter;
	@Column(name = "reservable_before")
	private LocalDateTime reservableBefore;
	@Column(name="available_days")
	@ElementCollection(targetClass=DayOfWeek.class)
	private List<DayOfWeek> availableDays;
	@Column(name="has_ethernet")
	private boolean hasEthernet;
	@Column(name = "has_computer")
	private boolean hasComputer;
	@Column(name = "number_of_outlets")
	private int numberOfOutlets;
	@Column(name = "has_microphone")
	private boolean hasMicrophone;

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
		this.buildingId = building.getId();
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public LocalDateTime getAvailableStartDate() {
		return availableStartDate;
	}

	public void setAvailableStartDate(LocalDateTime availableStartDate) {
		this.availableStartDate = availableStartDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	public boolean isRetired() {
		return retired;
	}

	public void setRetired(boolean retired) {
		this.retired = retired;
	}

	public boolean isHasEthernet() {
		return hasEthernet;
	}

	public void setHasEthernet(boolean hasEthernet) {
		this.hasEthernet = hasEthernet;
	}

	public boolean isHasComputer() {
		return hasComputer;
	}

	public void setHasComputer(boolean hasComputer) {
		this.hasComputer = hasComputer;
	}

	public int getNumberOfOutlets() {
		return numberOfOutlets;
	}

	public void setNumberOfOutlets(int numberOfOutlets) {
		this.numberOfOutlets = numberOfOutlets;
	}

	public boolean isHasMicrophone() {
		return hasMicrophone;
	}

	public void setHasMicrophone(boolean hasMicrophone) {
		this.hasMicrophone = hasMicrophone;
	}

	public LocalDateTime getReservableAfter() {
		return reservableAfter;
	}

	public void setReservableAfter(LocalDateTime reservableAfter) {
		this.reservableAfter = reservableAfter;
	}

	public LocalDateTime getReservableBefore() {
		return reservableBefore;
	}

	public void setReservableBefore(LocalDateTime reservableBefore) {
		this.reservableBefore = reservableBefore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public List<DayOfWeek> getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(List<DayOfWeek> availableDays) {
		this.availableDays = availableDays;
	}

	public Resource(int id, @NotNull(message = "Type is required.") Type type, Campus campus, Building building,
			int buildingId, String name, boolean disabled, boolean inactive, boolean retired,
			LocalDateTime availableStartDate, LocalDateTime reservableAfter, LocalDateTime reservableBefore,
			List<DayOfWeek> availableDays, boolean hasEthernet, boolean hasComputer, int numberOfOutlets,
			boolean hasMicrophone) {
		super();
		this.id = id;
		this.type = type;
		this.campus = campus;
		this.building = building;
		this.buildingId = buildingId;
		this.name = name;
		this.disabled = disabled;
		this.inactive = inactive;
		this.retired = retired;
		this.availableStartDate = availableStartDate;
		this.reservableAfter = reservableAfter;
		this.reservableBefore = reservableBefore;
		this.availableDays = availableDays;
		this.hasEthernet = hasEthernet;
		this.hasComputer = hasComputer;
		this.numberOfOutlets = numberOfOutlets;
		this.hasMicrophone = hasMicrophone;
	}

	public Resource() {
		super();
		this.name = " ";
	}

	public Resource(ResourceObject resource) {
		super();
		this.id = resource.getId();
		this.type = resource.getType();
		this.building = resource.getBuilding();
		this.buildingId = resource.getBuildingId();
		this.name = resource.getName();
		this.disabled = resource.isDisabled();
		this.inactive = resource.isInactive();
		this.retired = resource.isRetired();
		this.availableStartDate = resource.getAvailableStartDate();
		this.reservableAfter = resource.getReservableAfter();
		this.reservableBefore = resource.getReservableBefore();
		this.availableDays = resource.getAvailableDays();
		this.hasEthernet = resource.isHasEthernet();
		this.hasComputer = resource.isHasComputer();
		this.numberOfOutlets = resource.getNumberOfOutlets();
		this.hasMicrophone = resource.isHasMicrophone();
	}
	
	

}
