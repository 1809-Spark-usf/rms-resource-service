package com.revature.models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import com.revature.enumerations.Type;

/**
 * The Class ResourceObject.
 * An object representing the resource
 * for the resource service.
 * 
 * The information the object contains include:
 * the objects id, the type of resource the user
 * requires (cubicle/office), the (campus + building),
 * boolean indicating the status of the resource
 * (disable/inactive/retired), time where the resource
 * is available (any available days), and boolean 
 * indicating if the resource contains any necessary 
 * materials the user might need (microphone/outlet/
 * computer/Ethernet).
 * 
 * SideNote: this is the object version of the Resource.java
 * it has no database implementation. only use locally
 * 
 *  @author 1811-Java-Nick | 12/27/2018
 */
public class ResourceObject {
	
	/** The id. */
	private int id;
	
	/** The type. */
	private Type type;
	
	/** The building. */
	private Building building;
	
	/** The building id. */
	private int buildingId;
	
	/** The name. */
	private String name;
	
	/** The disabled. */
	private boolean disabled;
	
	/** The inactive. */
	private boolean inactive;
	
	/** The retired. */
	private boolean retired;
	
	/** The available start date. */
	private LocalDateTime availableStartDate;
	
	/** The reservable after. */
	private LocalDateTime reservableAfter;
	
	/** The reservable before. */
	private LocalDateTime reservableBefore;
	
	/** The available days. */
	private List<DayOfWeek> availableDays;
	
	/** The has ethernet. */
	private boolean hasEthernet;
	
	/** The has computer. */
	private boolean hasComputer;
	
	/** The number of outlets. */
	private int numberOfOutlets;
	
	/** The has microphone. */
	private boolean hasMicrophone;
	
	public ResourceObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * Gets the building.
	 *
	 * @return the building
	 */
	public Building getBuilding() {
		return building;
	}
	
	/**
	 * Sets the building.
	 *
	 * @param building the new building
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}
	
	/**
	 * Gets the building id.
	 *
	 * @return the building id
	 */
	public int getBuildingId() {
		return buildingId;
	}
	
	/**
	 * Sets the building id.
	 *
	 * @param buildingId the new building id
	 */
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Checks if is disabled.
	 *
	 * @return true, if is disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}
	
	/**
	 * Sets the disabled.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	/**
	 * Checks if is inactive.
	 *
	 * @return true, if is inactive
	 */
	public boolean isInactive() {
		return inactive;
	}
	
	/**
	 * Sets the inactive.
	 *
	 * @param inactive the new inactive
	 */
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	
	/**
	 * Checks if is retired.
	 *
	 * @return true, if is retired
	 */
	public boolean isRetired() {
		return retired;
	}
	
	/**
	 * Sets the retired.
	 *
	 * @param retired the new retired
	 */
	public void setRetired(boolean retired) {
		this.retired = retired;
	}
	
	/**
	 * Gets the available start date.
	 *
	 * @return the available start date
	 */
	public LocalDateTime getAvailableStartDate() {
		return availableStartDate;
	}
	
	/**
	 * Sets the available start date.
	 *
	 * @param availableStartDate the new available start date
	 */
	public void setAvailableStartDate(LocalDateTime availableStartDate) {
		this.availableStartDate = availableStartDate;
	}
	
	/**
	 * Gets the reservable after.
	 *
	 * @return the reservable after
	 */
	public LocalDateTime getReservableAfter() {
		return reservableAfter;
	}
	
	/**
	 * Sets the reservable after.
	 *
	 * @param reservableAfter the new reservable after
	 */
	public void setReservableAfter(LocalDateTime reservableAfter) {
		this.reservableAfter = reservableAfter;
	}
	
	/**
	 * Gets the reservable before.
	 *
	 * @return the reservable before
	 */
	public LocalDateTime getReservableBefore() {
		return reservableBefore;
	}
	
	/**
	 * Sets the reservable before.
	 *
	 * @param reservableBefore the new reservable before
	 */
	public void setReservableBefore(LocalDateTime reservableBefore) {
		this.reservableBefore = reservableBefore;
	}
	
	/**
	 * Gets the available days.
	 *
	 * @return the available days
	 */
	public List<DayOfWeek> getAvailableDays() {
		return availableDays;
	}
	
	/**
	 * Sets the available days.
	 *
	 * @param availableDays the new available days
	 */
	public void setAvailableDays(List<DayOfWeek> availableDays) {
		this.availableDays = availableDays;
	}
	
	/**
	 * Checks if is checks for ethernet.
	 *
	 * @return true, if is checks for ethernet
	 */
	public boolean isHasEthernet() {
		return hasEthernet;
	}
	
	/**
	 * Sets the checks for ethernet.
	 *
	 * @param hasEthernet the new checks for ethernet
	 */
	public void setHasEthernet(boolean hasEthernet) {
		this.hasEthernet = hasEthernet;
	}
	
	/**
	 * Checks if is checks for computer.
	 *
	 * @return true, if is checks for computer
	 */
	public boolean isHasComputer() {
		return hasComputer;
	}
	
	/**
	 * Sets the checks for computer.
	 *
	 * @param hasComputer the new checks for computer
	 */
	public void setHasComputer(boolean hasComputer) {
		this.hasComputer = hasComputer;
	}
	
	/**
	 * Gets the number of outlets.
	 *
	 * @return the number of outlets
	 */
	public int getNumberOfOutlets() {
		return numberOfOutlets;
	}
	
	/**
	 * Sets the number of outlets.
	 *
	 * @param numberOfOutlets the new number of outlets
	 */
	public void setNumberOfOutlets(int numberOfOutlets) {
		this.numberOfOutlets = numberOfOutlets;
	}
	
	/**
	 * Checks if is checks for microphone.
	 *
	 * @return true, if is checks for microphone
	 */
	public boolean isHasMicrophone() {
		return hasMicrophone;
	}
	
	/**
	 * Sets the checks for microphone.
	 *
	 * @param hasMicrophone the new checks for microphone
	 */
	public void setHasMicrophone(boolean hasMicrophone) {
		this.hasMicrophone = hasMicrophone;
	}

	@Override
	public String toString() {
		return "ResourceObject [id=" + id + ", type=" + type + ", building=" + building + ", buildingId=" + buildingId
				+ ", name=" + name + ", disabled=" + disabled + ", inactive=" + inactive + ", retired=" + retired
				+ ", availableStartDate=" + availableStartDate + ", reservableAfter=" + reservableAfter
				+ ", reservableBefore=" + reservableBefore + ", availableDays=" + availableDays + ", hasEthernet="
				+ hasEthernet + ", hasComputer=" + hasComputer + ", numberOfOutlets=" + numberOfOutlets
				+ ", hasMicrophone=" + hasMicrophone + "]";
	}
	
	
}
