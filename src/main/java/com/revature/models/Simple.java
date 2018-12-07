package com.revature.models;

public class Simple {
	private int id;
	private String namerino;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNamerino() {
		return namerino;
	}
	public void setNamerino(String namerino) {
		this.namerino = namerino;
	}
	public Simple(int id, String namerino) {
		super();
		this.id = id;
		this.namerino = namerino;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((namerino == null) ? 0 : namerino.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Simple other = (Simple) obj;
		if (id != other.id)
			return false;
		if (namerino == null) {
			if (other.namerino != null)
				return false;
		} else if (!namerino.equals(other.namerino))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Simple [id=" + id + ", namerino=" + namerino + "]";
	}
	public Simple() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
