package com.revature.models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import com.revature.enumerations.Type;

public class ResourceObject {
	private int id;
	private Type type;
	private Building building;
	private int buildingId;
	private String name;
	private boolean disabled;
	private boolean inactive;
	private boolean retired;
	private LocalDateTime availableStartDate;
	private LocalDateTime reservableAfter;
	private LocalDateTime reservableBefore;
	private List<DayOfWeek> availableDays;
	private boolean hasEthernet;
	private boolean hasComputer;
	private int numberOfOutlets;
	private boolean hasMicrophone;
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
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	public int getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public LocalDateTime getAvailableStartDate() {
		return availableStartDate;
	}
	public void setAvailableStartDate(LocalDateTime availableStartDate) {
		this.availableStartDate = availableStartDate;
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
	public List<DayOfWeek> getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(List<DayOfWeek> availableDays) {
		this.availableDays = availableDays;
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
}
