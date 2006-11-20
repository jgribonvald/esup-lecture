/**
* ESUP-Portail Lecture - Copyright (c) 2006 ESUP-Portail consortium
* For any information please refer to http://esup-helpdesk.sourceforge.net
* You may obtain a copy of the licence at http://www.esup-portail.org/license/
*/
package org.esupportail.lecture.domain.model;

/**
 * Source profile element : a source profile can be a managed or personal one.
 * @author gbouteil
 *
 */
public abstract class SourceProfile {

/* ************************** PROPERTIES ******************************** */	

	private String id;

	private String name = "";

	private String sourceURL = "";

	private Source source;


/* ************************** METHODS ******************************** */	

/* ************************** ACCESSORS ******************************** */	

	/**
	 * Returns the source profile Id
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the source profile Id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the source profile name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the source profile name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the source URL of the source profile
	 * @return sourceURL
	 */
	public String getSourceURL() {
		return sourceURL;
	}
	
	/**
	 * Sets the source URL of the source profile
	 * @param sourceURL
	 */
	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public abstract String getContent(); 

	/**
	 * Returns source of this managed source profile (if loaded)
	 * @return source
	 */
	protected Source getSource() {
		return source;
	}
	
	/**
	 * Sets source on the profile
	 * @param source
	 */
	protected void setSource(Source source) {
		this.source = source;
	}

}