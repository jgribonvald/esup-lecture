package org.esupportail.lecture.domain.model;


import java.util.Hashtable;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esupportail.lecture.domain.ExternalService;



/**
 * Class where are defined user profile (and customizations ...)
 * @author gbouteil
 *
 */
public class UserProfile {
	
	/*
	 ************************** PROPERTIES *********************************/	
	
	/**
	 * Log instance
	 */
	protected static final Log log = LogFactory.getLog(UserProfile.class);
	
	/**
	 * Id of the user, get from portlet request by USER_ID, defined in the channel config
	 * @see DomainTools#USER_ID
	 * @see ChannelConfig#loadUserId()
	 */
	private String userId;
	
	/**
	 * Hashtable of CustomContexts defined for the user, indexed by contexID.
	 */
	private Map<String,CustomContext> customContexts;

	/**
	 * Hashtable of CustomManagedCategory defined for the user, indexed by ManagedCategoryProfilID.
	 */
	// TODO why not customCategories ?
	private Map<String,CustomManagedCategory> customManagedCategories;

	/**
	 * Hashtable of CustomSource defined for the user, indexed by SourceProfilID.
	 */
	private Map<String,CustomSource> customSources;

	
	/*
	 ************************** Initialization ************************************/
	
	/**
	 * Constructor
	 */
	public UserProfile(){
		customContexts = new Hashtable<String,CustomContext>();
		customManagedCategories = new Hashtable<String, CustomManagedCategory>();
	}
	
	/**
	 * Constructor
	 * @param userId
	 */
	public UserProfile(String userId){
		customContexts = new Hashtable<String,CustomContext>();
		customManagedCategories = new Hashtable<String, CustomManagedCategory>();
		customSources = new Hashtable<String,CustomSource>();
		this.setUserId(userId);
	}
	/*
	 *************************** METHODS ************************************/

	
	/**
	 * Return the customContext identified by the context id" 
	 * if exists, else create it.
	 * @param contextId identifier of the context refered by the customContext
	 * @return customContext (or null)
	 */
	public CustomContext getCustomContext(String contextId){
		CustomContext customContext = 
				customContexts.get(contextId);
		if (customContext == null){
			customContext = new CustomContext(contextId,this);
			addCustomContext(customContext);
		}
		
		return customContext;
	}
	
	/**
	 * Return the customCategory identifed by the category id
	 * if exist,else,create it.
	 * @param categoryId identifier of the category refered by the customCategory
	 * @return customCategory (or null)
	 */
	public CustomManagedCategory getCustomManagedCategory(String categoryId){
		// TODO revoir avec customCategory et customManagedCategory
		CustomManagedCategory customCategory = 
			customManagedCategories.get(categoryId);
		if(customCategory == null){
			customCategory = new CustomManagedCategory(categoryId,this );
			addCustomCategory(customCategory);
		}
		return customCategory;
	}
	
	public CustomSource getCustomSource(String sourceId) {
		CustomSource customSource = 
			customSources.get(sourceId);
		
		return customSource;
	}

	
	/**
	 * @param customContext
	 */
	public void addCustomContext(CustomContext customContext){
		customContexts.put(customContext.getContextId(),customContext);
	}
	
	/**
	 * @param customCategory
	 */
	public void addCustomCategory(CustomManagedCategory customCategory){
		customManagedCategories.put(customCategory.getCategoryProfileID(),customCategory);
	}
	
	/**
	 * @param customCategory
	 */
	public void addCustomSource(CustomSource customSource){
		customSources.put(customSource.getSourceProfileId(),customSource);
	}
		
	/* ************************** ACCESSORS ********************************* */

//	/**
//	 * @return Returns the customContexts.
//	 */
//	public Hashtable<String, CustomContext> getCustomContexts() {
//		return customContexts;
//	}
//
//	/**
//	 * @param customContexts The customContexts to set.
//	 */
//	public void setCustomContexts(Hashtable<String, CustomContext> customContexts) {
//		this.customContexts = customContexts;
//	}

	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return customContexts
	 */
	public Map<String, CustomContext> getCustomContexts() {
		return customContexts;
	}

	/**
	 * @param customContexts
	 */
	public void setCustomContexts(Map<String, CustomContext> customContexts) {
		this.customContexts = customContexts;
	}

	/**
	 * @return customManagedCategories
	 */
	public Map<String, CustomManagedCategory> getCustomManagedCategories() {
		return customManagedCategories;
	}

	/**
	 * @param customManagedCategories
	 */
	public void setCustomManagedCategories(
			Map<String, CustomManagedCategory> customManagedCategories) {
		this.customManagedCategories = customManagedCategories;
	}

	
}
