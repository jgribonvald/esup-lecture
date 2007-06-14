/**
* ESUP-Portail Lecture - Copyright (c) 2006 ESUP-Portail consortium
* For any information please refer to http://esup-helpdesk.sourceforge.net
* You may obtain a copy of the licence at http://www.esup-portail.org/license/
*/
package org.esupportail.lecture.domain.beans;

import org.esupportail.lecture.domain.model.CategoryProfile;
import org.esupportail.lecture.domain.model.CustomCategory;
import org.esupportail.lecture.domain.model.CustomContext;
import org.esupportail.lecture.exceptions.domain.CategoryNotLoadedException;
import org.esupportail.lecture.exceptions.domain.CategoryProfileNotFoundException;
import org.esupportail.lecture.exceptions.domain.DomainServiceException;
import org.esupportail.lecture.exceptions.domain.ElementDummyBeanException;

/**
 * used to store category informations
 * @author bourges
 */
public class CategoryBean {
	
	/* 
	 *************************** PROPERTIES ******************************** */	
	
	/**
	 * id of categery
	 */
	private String id;
	/**
	 * name of category
	 */
	private String name;
	/**
	 * description of the category
	 */
	private String description;
	/**
	 * store if category is folded or not
	 */
	private boolean folded = true;
	
	/*
	 *************************** INIT ************************************** */	
			
	/**
	 * Default Constructor.
	 */
	public CategoryBean(){}
	
	/**
	 * Constructor initializing object
	 * @param customCategory
	 * @param customContext
	 * @throws CategoryProfileNotFoundException
	 * @throws CategoryNotLoadedException
	 */
	public CategoryBean(CustomCategory customCategory,CustomContext customContext) throws CategoryProfileNotFoundException, CategoryNotLoadedException{
		CategoryProfile profile = customCategory.getProfile();
		
		this.name = profile.getName();
		this.description = profile.getDescription();
		this.id = profile.getId();
		this.folded = customContext.isCategoryFolded(id);
	}
	
	/*
	 *************************** ACCESSORS ********************************* */	
	
	/**
	 * @return description of the category
	 * @throws ElementDummyBeanException 
	 */
	public String getDescription() throws ElementDummyBeanException {
		return description;
	}
	/**
	 * @param description description of the category
	 * @throws ElementDummyBeanException 
	 */
	public void setDescription(String description) throws ElementDummyBeanException {
		this.description = description;
	}
	/**
	 * @return if category is folded or not
	 * @throws ElementDummyBeanException 
	 */
	public boolean isFolded() throws ElementDummyBeanException {
		return folded;
	}
	/**
	 * @param folded
	 * @throws ElementDummyBeanException 
	 */
	public void setFolded(boolean folded) throws ElementDummyBeanException {
		this.folded = folded;
	}
	/**
	 * @return id of category
	 * @throws ElementDummyBeanException 
	 */
	public String getId() throws ElementDummyBeanException {
		return id;
	}
	/**
	 * @param id
	 * @throws ElementDummyBeanException 
	 */
	public void setId(String id) throws ElementDummyBeanException {
		this.id = id;
	}
	/**
	 * @return name of category
	 * @throws ElementDummyBeanException 
	 */
	public String getName() throws ElementDummyBeanException {
		return name;
	}
	/**
	 * @param name
	 * @throws ElementDummyBeanException 
	 */
	public void setName(String name) throws ElementDummyBeanException {
		this.name = name;
	}

	/*
	 *************************** METHODS *********************************** */	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String string = "";
		string += "     Id = " + id.toString() + "\n";
		string += "     Name = " + name.toString() + "\n";
		string += "     Desc = "; 
		if (description != null){
			string += description.toString() + "\n";
		}
		string += "     Folded = "+ folded + "\n";
		
		return string;
	}
	
}
