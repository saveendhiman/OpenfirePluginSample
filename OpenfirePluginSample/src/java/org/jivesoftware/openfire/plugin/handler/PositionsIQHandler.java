package org.jivesoftware.openfire.plugin.handler;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.plugin.OpenfirePlugin;
import org.jivesoftware.openfire.plugin.provider.OpenfireProvider;
import org.xmpp.component.Component;
import org.xmpp.component.ComponentException;
import org.xmpp.component.ComponentManager;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Message;

/**
 * Created by Saveen Dhiman on 02-03-17
 * PositionsIQHandler create record then notify correspondence user 
 *
 */


public class PositionsIQHandler extends IQHandler {

	Component comp;
	ComponentManager cm;

	public PositionsIQHandler(ComponentManager cm, Component comp) {
		super("Get contacts handler");
		this.cm = cm;
		this.comp = comp;
	}

	@Override
	public IQHandlerInfo getInfo() {
		return new IQHandlerInfo("query", "openfire:iq:position");
	}

	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		System.out.println("In Positions IQ Handler");
		System.out.println(packet.toXML().toString());		

		Element elem = packet.getChildElement().element("item");

		String Id = elem.attributeValue("Id");
		String Lat = elem.attributeValue("Lat");
		String Lng = elem.attributeValue("Lng");
		String creationDate = elem.attributeValue("creationDate");

		OpenfireProvider.createPosition(Id, Lat, Lng, creationDate);
	
		Message message = new Message();
		message.setFrom(OpenfirePlugin.FROM);
		message.setTo(packet.getFrom());
		message.setBody("Successfully created positions");
		
		
		try {
			cm.sendPacket(comp, message);
		} catch (ComponentException e) {
			e.printStackTrace();
		}
		
		IQ resultiq = IQ.createResultIQ(packet);
		return resultiq;
	}
}
