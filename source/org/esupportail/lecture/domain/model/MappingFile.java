package org.esupportail.lecture.domain.model;



public class MappingFile {

	/**
	 * location of the mapping file
	 */
	private String location = "";

	/**
	 * Getter of the property <tt>location</tt>
	 * @return  Returns the location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Setter of the property <tt>location</tt>
	 * @param location  The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString(){
		String string = "Location : "+ location ;
		System.out.println(string);
		return string ;
	}
	
	
	/**
	 */
	public void loadMappingFile(){
	
	}

}
