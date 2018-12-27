package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class Campus.
 * Representation of the campus object used
 * for the resource service.
 * The object includes an id, the name of
 * the campus, and a list of buildings the 
 * campus is associated to.
 * 
 * @author 1811-Java-Nick | 12/27/2018
 */
@Entity
@Table(name="campuses")
public class Campus {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/** The name. */
	private String name;
	
	/** The buildings. */
	@OneToMany(mappedBy="campus", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Building> buildings;

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
	 * Gets the buildings.
	 *
	 * @return the buildings
	 */
	public List<Building> getBuildings() {
		return buildings;
	}

	/**
	 * Sets the buildings.
	 *
	 * @param buildings the new buildings
	 */
	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildings == null) ? 0 : buildings.hashCode());
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
		Campus other = (Campus) obj;
		if (buildings == null) {
			if (other.buildings != null)
				return false;
		} else if (!buildings.equals(other.buildings))
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
	 * to string of the campus class
	 */
	@Override
	public String toString() {
		return "Campus [id=" + id + ", name=" + name + ", buildings=" + buildings + "]";
	}

	/**
	 * Instantiates a new campus.
	 *
	 * @param id the id
	 * @param name the name
	 * @param buildings the buildings
	 */
	public Campus(int id, String name, List<Building> buildings) {
		super();
		this.id = id;
		this.name = name;
		this.buildings = buildings;
	}

	/**
	 * Instantiates a new campus.
	 */
	public Campus() {
		super();
	}
}
