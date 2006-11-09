<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:t="http://myfaces.apache.org/tomahawk">
	<jsp:directive.page language="java"
		contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" />
	<f:subview id="rightSubview">
		<!-- MENU with Source name, sort list and zoom -->
		<t:htmlTag value="div" id="menuRight" forceId="true">
			<t:htmlTag value="div" styleClass="menuTitle">
				<t:htmlTag value="span" styleClass="portlet-section-header">
					<h:outputText value="#{homeController.context.selectedCategory.name}/#{homeController.context.selectedCategory.selectedSource.name}" />
				</t:htmlTag>
			</t:htmlTag>
			<t:htmlTag value="div" styleClass="menuButton">
				<t:htmlTag value="ul">
					<t:htmlTag value="li">
						<h:outputText value="#{msgs['selectorLabel']}" />
						<h:selectOneMenu value="#{homeController.itemDisplayMode}">
							<f:selectItem itemValue="all" itemLabel="#{msgs['all']}" />
							<f:selectItem itemValue="notRead" itemLabel="#{msgs['notRead']}" />
							<f:selectItem itemValue="unreadFirst"
								itemLabel="#{msgs['unreadFirst']}" />
						</h:selectOneMenu>
						<h:commandButton id="submit" value="#{msgs['changeItemDisplayModeButtonLabel']}" action="#{homeController.changeItemDisplayMode}"/>
					</t:htmlTag>
					<t:htmlTag id="menuAndXML" value="li" rendered="#{!homeController.treeVisible}">
						<h:commandButton action="#{homeController.toggleTreeVisibility}"
							image="/media/menuAndXML.gif" alt="#{msgs['showTree']}" title="#{msgs['showTree']}"/>
					</t:htmlTag>
					<t:htmlTag id="XMLWithoutMenu" value="li" rendered="#{homeController.treeVisible}">
						<h:commandButton action="#{homeController.toggleTreeVisibility}"
							image="/media/XMLWithoutMenu.gif" alt="#{msgs['hideTree']}" title="#{msgs['hideTree']}"/>
					</t:htmlTag>
				</t:htmlTag>
			</t:htmlTag>
		</t:htmlTag>
		<!-- Items display -->
		<t:htmlTag value="div" id="right">
			<t:dataList value="#{homeController.items}" var="item" layout="simple">
				<!-- Read/Unread Button -->
				<t:htmlTag value="div" styleClass="toggleButton">
					<h:commandButton action="#{homeController.toggleItemReadState}"
						image="/media/unread.gif" alt="#{msgs['markAsRead']}"
						title="#{msgs['markAsRead']}" rendered="#{!item.read}">
						<t:updateActionListener property="#{homeController.item}"
							value="#{item}" />
					</h:commandButton>
					<h:commandButton action="#{homeController.toggleItemReadState}"
						image="/media/read.gif" alt="#{msgs['markAsUnread']}"
						title="#{msgs['markAsUnread']}" rendered="#{item.read}">
						<t:updateActionListener property="#{homeController.item}"
							value="#{item}" />
					</h:commandButton>
				</t:htmlTag>
				<!-- Item Display -->
				<t:htmlTag value="div"
					styleClass="#{item.read ? 'readArticle' : 'unreadArticle'}">
					<f:verbatim>
						<h:outputText value="#{item.htmlContent}" escape="false" />
					</f:verbatim>
				</t:htmlTag>
			</t:dataList>
		</t:htmlTag>
	</f:subview>
</jsp:root>
