package org.esupportail.lecture.domain.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.InitializingBean;


/**
 * 
 * @author gbouteil
 * 
 *
 */

public class Context  {
/* ************************** PROPERTIES ******************************** */	
	protected static final Log log = LogFactory.getLog(Channel.class); 
	/**
	 *  The context name 
	 */
	private String name = "";
	
	/**
	 * The context description
	 */
	private String description = "";
	
	/**
	 * The context id
	 */
	private String id;

	/**
	 * The context edit mode : not used for the moment
	 */
	private Editability edit;
	
	/**
	 * Managed category profiles available in this context.
	 */
	private Set<ManagedCategoryProfile> managedCategoryProfilesSet = new HashSet<ManagedCategoryProfile>();
	/**
	 * reference id on managed category profile
	 */
	private Set<String>refIdManagedCategoryProfilesSet = new HashSet<String>();
	
	
/* ************************** ACCESSORS ******************************** */	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Editability getEdit() {
		return edit;
	}

	public void setEdit(Editability edit) {
		this.edit = edit;
	}

	public Set<ManagedCategoryProfile> getManagedCategoryProfilesSet() {
		return managedCategoryProfilesSet;
	}
	public void setManagedCategoryProfilesSet(Set<ManagedCategoryProfile> managedCategoryProfilesSet) {
		this.managedCategoryProfilesSet = managedCategoryProfilesSet;
	}

	public void setRefIdManagedCategoryProfile(String s){
		refIdManagedCategoryProfilesSet.add(s);
	}
	
	public void setSetRefIdManagedCategoryProfiles(Set<String> s){
		refIdManagedCategoryProfilesSet = s;
	}
/* ************************** Initialization *********************************** */
//	public void afterPropertiesSet (){
//		/* Connecting Managed category profiles and contexts */
///*		Iterator iterator=managedCategoryProfilesSet.iterator();
//
//		while (iterator.hasNext()) {
//	        ManagedCategoryProfile m = (ManagedCategoryProfile)iterator.next();
//	        m.addContext(this);    
//		}
//*/	}
	

	public void initManagedCategoryProfiles (Channel channel){
		/* Connecting Managed category profiles and contexts */
		Iterator iterator=refIdManagedCategoryProfilesSet.iterator();

		while (iterator.hasNext()) {
			String id = (String)iterator.next();
			ManagedCategoryProfile mcp = channel.getManagedCategoryProfile(id);
			managedCategoryProfilesSet.add(mcp);
	        mcp.setContext(this);    
		}
	}
	
/* ************************** METHODS ******************************** */
	
	public String toString(){
		
		String string = "";
	
	/* The context name */
		string += "	name : "+ name +"\n";
		
    /* The context description */
		string += "	description : "+ description +"\n";
	
	/* The context id */
		string += "	id : "+ id +"\n";

	/* The Id Category profiles Set */
		string += "	managedCategoryProfile ids : \n";
		string += "           "+refIdManagedCategoryProfilesSet.toString();
		string += "\n";
	 	
		
	/* The context edit mode : not used for the moment */
	// 	string += "	edit : "+ edit +"\n";;
	
	/* Managed category profiles available in this context */
		string += "	managedCategoryProfilesSet : \n";
		Iterator iterator = managedCategoryProfilesSet.iterator();
		for (ManagedCategoryProfile m = null; iterator.hasNext();) {
			m = (ManagedCategoryProfile)iterator.next();
			string += "         ("+ m.getId() + "," + m.getName()+")\n";
		}
		
		return string;
	}

}
