package org.esupportail.lecture.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.esupportail.lecture.domain.model.Accessibility;
import org.esupportail.lecture.domain.model.ManagedCategory;
import org.esupportail.lecture.domain.model.ManagedCategoryProfile;
import org.esupportail.lecture.domain.model.ManagedSourceProfile;
import org.esupportail.lecture.domain.model.SourceProfile;
import org.esupportail.lecture.domain.model.VisibilitySets;
import org.esupportail.lecture.exceptions.dao.XMLParseException;

/**
 * Get a Freash Managed Category from a distinct Thread.
 * @author bourges
 */
public class FreshManagedCategoryThread extends Thread {

	/**
	 * Log instance.
	 */
	private static final Log LOG = LogFactory.getLog(FreshManagedCategoryThread.class);
	/**
	 * ManagedCategory to return by this Thread.
	 */
	private ManagedCategory managedCategory;
	/**
	 * ManagedCategoryProfile used to return a ManagedCategory.
	 */
	private ManagedCategoryProfile managedCategoryProfile;
	/**
	 * Exception generated in this Thread.
	 */
	private Exception exception;
	/**
	 * user and password. 
	 * null for anonymous access.
	 */
	private UsernamePasswordCredentials creds;
	
	/**
	 * Constructor.
	 * @param profile used to return a ManagedCategory
	 * @param creds - user and password. null for anonymous access
	 */
	public FreshManagedCategoryThread(final ManagedCategoryProfile profile, 
			final UsernamePasswordCredentials creds) {
		this.managedCategoryProfile = profile;
		this.exception = null;
		this.creds = creds;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			this.managedCategory = getFreshManagedCategory(managedCategoryProfile, creds);
		} catch (XMLParseException e) {
			this.exception = e;
		}
	}

	/**
	 * get a managed category from the web without cache.
	 * @param profile ManagedCategoryProfile of Managed category to get
	 * @param creds - user and password. null for anonymous access
	 * @return Managed category
	 * @throws XMLParseException 
	 */
	@SuppressWarnings("unchecked")
	private synchronized ManagedCategory getFreshManagedCategory(final ManagedCategoryProfile profile,
			final UsernamePasswordCredentials creds) 
			throws XMLParseException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("in getFreshManagedCategory");
		}
		// TODO (RB <-- GB) gestion des attributs xml IMPLIED 
		ManagedCategory ret = new ManagedCategory();
		try {
			//get the XML
			String categoryURL = profile.getUrlCategory();
			Document document = null;
			if (creds != null) {
				HttpClient client = new HttpClient();
				if (creds != null) {
					client.getState().setCredentials(AuthScope.ANY, creds);				
				}
				GetMethod method = new GetMethod(categoryURL);
				try {
					client.executeMethod(method);
					InputStream responseStream = method.getResponseBodyAsStream();
					document = new SAXReader().read(responseStream);				
				} catch (HttpException e) {
					throw new RuntimeException("Error in getFreshSource", e);
				} catch (IOException e) {
					throw new RuntimeException("Error in getFreshSource", e);
				}
			} else {
			    document = new SAXReader().read(categoryURL);
			}
			Element root = document.getRootElement();
			// Category properties
			ret.setName(root.valueOf("@name"));
			ret.setDescription(root.valueOf("/category/description"));
			ret.setProfileId(profile.getId());
			// SourceProfiles loop
			Hashtable<String, SourceProfile> sourceProfiles = new Hashtable<String, SourceProfile>();
			List<Node> srcProfiles = root.selectNodes("/category/sourceProfiles/sourceProfile");
			for (Node srcProfile : srcProfiles) {
				ManagedSourceProfile sp = new ManagedSourceProfile(profile);
				sp.setFileId(srcProfile.valueOf("@id"));
				sp.setName(srcProfile.valueOf("@name"));
				sp.setSourceURL(srcProfile.valueOf("@url"));
				String timeout = srcProfile.valueOf("@timeout");
				if (!(timeout.equals(""))) {
					sp.setTimeOut(Integer.parseInt(timeout));
					if (LOG.isTraceEnabled()) {
						LOG.trace("1 getFreshManagedCategory : " 
								+ "first vcalue of timeout (string) : "
								+ timeout);
						LOG.trace("2 getFreshManagedCategory : " 
								+ "value of timeout in xml :"
								+ srcProfile.valueOf("@timeout"));
						LOG.trace("3 getFreshManagedCategory : " 
								+ "value of timeout in Integer :"
								+ Integer.parseInt(srcProfile.valueOf("@timeout")));
					}
				} else {
					LOG.trace("4 getFreshManagedCategory : timeout (string) is empty");
				}
				String specificUserContentValue = srcProfile.valueOf("@specificUserContent");
				if (specificUserContentValue.equals("yes")) {
					sp.setSpecificUserContent(true);
				} else {
					sp.setSpecificUserContent(false);
				}
				//TODO (RB) change xslt attributes here when this feature will be suported in sourceprofile
				sp.setXsltURL(srcProfile.valueOf("@xsltFile"));
				sp.setItemXPath(srcProfile.valueOf("@itemXPath"));
				String access = srcProfile.valueOf("@access");
				if (access.equalsIgnoreCase("public")) {
					sp.setAccess(Accessibility.PUBLIC);
				} else if (access.equalsIgnoreCase("cas")) {
					sp.setAccess(Accessibility.CAS);
				}
				// SourceProfile visibility
				VisibilitySets visibilitySets = new VisibilitySets();  
				// foreach (allowed / autoSubscribed / Obliged)
				visibilitySets.setAllowed(
						XMLUtil.loadDefAndContentSets(
								srcProfile.selectSingleNode("visibility/allowed")));
				visibilitySets.setObliged(
						XMLUtil.loadDefAndContentSets(
								srcProfile.selectSingleNode("visibility/obliged")));
				visibilitySets.setAutoSubscribed(
						XMLUtil.loadDefAndContentSets(
								srcProfile.selectSingleNode(
										"visibility/autoSubscribed")));
				sp.setVisibility(visibilitySets);
				sp.setTtl(profile.getTtl());
				sourceProfiles.put(sp.getId(), sp);				
			}
			ret.setSourceProfilesHash(sourceProfiles);
			// Category visibility
			VisibilitySets visibilitySets = new VisibilitySets();  
			// foreach (allowed / autoSubscribed / Obliged)
			visibilitySets.setAllowed(
					XMLUtil.loadDefAndContentSets(
							root.selectSingleNode("/category/visibility/allowed")));
			visibilitySets.setObliged(
					XMLUtil.loadDefAndContentSets(
							root.selectSingleNode("/category/visibility/obliged")));
			visibilitySets.setAutoSubscribed(
					XMLUtil.loadDefAndContentSets(
							root.selectSingleNode("/category/visibility/autoSubscribed")));
			ret.setVisibility(visibilitySets);
		} catch (DocumentException e) {
			String profileId = "null";
			if (profile != null) {
				profileId = profile.getId();
			}
			String msg = "getFreshManagedCategory(" + profileId + "). Can't read configuration file.";
			LOG.error(msg);
			throw new XMLParseException(msg , e);
		}
		return ret;
	}

	/**
	 * @return managedCategory
	 */
	public ManagedCategory getManagedCategory() {
		return managedCategory;
	}

	/**
	 * @return exception thowed during run
	 */
	public Exception getException() {
		return exception;
	}

}
