package org.esupportail.lecture.domain.service.impl;
/**
* ESUP-Portail Lecture - Copyright (c) 2006 ESUP-Portail consortium
* For any information please refer to http://esup-helpdesk.sourceforge.net
* You may obtain a copy of the licence at http://www.esup-portail.org/license/
*/
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esupportail.commons.utils.PortletRequestUtils;
import org.esupportail.lecture.domain.model.Category;
import org.esupportail.lecture.domain.model.Context;
import org.esupportail.lecture.domain.model.CustomContext;
import org.esupportail.lecture.domain.model.Source;
import org.esupportail.lecture.domain.model.Channel;
import org.esupportail.lecture.domain.model.UserProfile;
import org.esupportail.lecture.domain.model.UserAttributes;
import org.esupportail.lecture.domain.service.FacadeService;
import org.esupportail.lecture.domain.service.PortletService;
import org.esupportail.lecture.utils.exception.*;
import org.esupportail.lecture.web.UserWeb;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * Impl�mentation des services pour le test 
 * Utilis�e par Gw�na�lle
 * @author gbouteil
 *
 */
public class FacadeServiceImplGwe implements FacadeService {

	PortletService portletService;
	Channel myChannel; 
	
	/**
	 * Log instance 
	 */
	protected static final Log log = LogFactory.getLog(Channel.class); 
	
	

	
	
	/** 
	 * @see org.esupportail.lecture.domain.service.FacadeService#loadChannel()
	 */
	public void loadChannel() throws FatalException,MyException {
		myChannel.startup();
	}

//	/**
//	 * @see org.esupportail.lecture.domain.service.FacadeService#newUserSession()
//	 */
//	public void newUserSession() throws MyException,FatalException {
//		myChannel.startup();
//		// TODO le reste � propos du user
//	}

	
	/**
	 * @see org.esupportail.lecture.domain.service.FacadeService#reloadChannelConfig()
	 */
	public void reloadChannelConfig() throws FatalException{
		myChannel.loadConfig();
	}
	
	/**
	 * @see org.esupportail.lecture.domain.service.FacadeService#reloadMappingFile()
	 */
	public void reloadMappingFile()throws FatalException {
		myChannel.loadMappingFile();
	}
	
	/**
	 * @see org.esupportail.lecture.domain.service.FacadeService#channelToString()
	 */
	public String channelToString(){
		return myChannel.toString();
	}
	

	
	/**
	 * @deprecated
	 */
	public List<Category> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @deprecated
	 */
	public List<Source> getSources(Category cat) {
		// TODO Auto-generated method stub
		return null;
	}




	public Hashtable<String,Context> getContexts() {
		
		return myChannel.getContextsHash();
	}





	
	public CustomContext getCustomContext() {
		// TODO Auto-generated method stub
		
		return null;
	}


	public String getUserIdAttribute() {
		// TODO Auto-generated method stub
		return UserAttributes.USER_ID;
	}




	public UserWeb getUserWeb() {
		String userId = portletService.getUserAttribute(UserAttributes.USER_ID);
		UserProfile userProfile = myChannel.getUserProfile(userId);
		
		UserWeb userWeb = new UserWeb();
		userWeb.setId(userProfile.getUserId());
		return userWeb;
	}

	/**
	 * @return Returns the portletService.
	 */
	public PortletService getPortletService() {
		return portletService;
	}

	/**
	 * @param portletService The portletService to set.
	 */
	public void setPortletService(PortletService portletService) {
		this.portletService = portletService;
	}

	/**
	 * @return Returns the myChannel.
	 */
	public Channel getMyChannel() {
		return myChannel;
	}

	/**
	 * @param myChannel The myChannel to set.
	 */
	public void setMyChannel(Channel myChannel) {
		this.myChannel = myChannel;
	}



	

	



		

}
