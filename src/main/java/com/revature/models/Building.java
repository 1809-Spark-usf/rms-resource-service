package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Building.
 * Object that represents the buildings 
 * for the resource service.
 * The object include the id, the name of
 * the building, and the campus the building
 * is connected to.
 * 
 * @author 1811-Java-Nick | 12/27/2018
 */
@Entity
@Table(name="buildings")
public class Building {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	/** The name. */
	@NotNull
	@Column(nullable=false)
	private String name;
	
	/** The campus. */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="campus_id", nullable=false)
	private Campus campus;
	
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
	 * Gets the campus.
	 *
	 * @return the campus
	 */
	public Campus getCampus() {
		return campus;
	}
	
	/**
	 * Sets the campus.
	 *
	 * @param campus the new campus
	 */
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campus == null) ? 0 : campus.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	/**
	 * Overridden equals method method that compares
	 * this object with any other object to make sure
	 * it is the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Building other = (Building) obj;
		
		if (campus == null) {
			if (other.campus != null)
				return false;
		} else if (!campus.equals(other.campus))
			return false;
		
		if (id != other.id)
			return false;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}
	
	/**
	 * Instantiates a new building.
	 *
	 * @param id the id
	 * @param name the name
	 * @param campus the campus
	 */
	public Building(int id, @NotNull String name, Campus campus) {
		super();
		this.id = id;
		this.name = name;
		this.campus = campus;
	}
	
	/**
	 * Instantiates a new building.
	 */
	public Building() {
		super();
		this.name = " ";
	}
	
	/**
	 * to string of the building class.
	 */
	@Override
	public String toString() {
		return "Building [id=" + id + ", name=" + name + ", campus=" + campus + "]";
	}
	
}
