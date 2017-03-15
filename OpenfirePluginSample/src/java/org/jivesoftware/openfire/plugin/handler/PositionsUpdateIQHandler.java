package org.jivesoftware.openfire.plugin.handler;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.plugin.provider.OpenfireProvider;
import org.xmpp.component.Component;
import org.xmpp.component.ComponentException;
import org.xmpp.component.ComponentManager;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Message;
import org.xmpp.packet.Message.Type;


/**
 * Created by Saveen Dhiman on 02-03-17
 * PositionsUpdateIQHandler update record then notify correspondence user 
 *
 */

public class PositionsUpdateIQHandler extends IQHandler {

	Component comp;
	ComponentManager cm;

	public PositionsUpdateIQHandler(ComponentManager cm, Component comp) {
		super("Get position handler");
		this.cm = cm;
		this.comp = comp;
	}

	@Override
	public IQHandlerInfo getInfo() {
		return new IQHandlerInfo("query", "openfire:iq:positionupdate");
	}

	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		System.out.println("In Update");
		System.out.println(packet.toXML().toString());
	
		Element elem = packet.getChildElement().element("item");
		String Id = elem.attributeValue("Id");
		String Lat = elem.attributeValue("Lat");
		String Lng = elem.attributeValue("Lng");
		String creationDate = elem.attributeValue("creationDate");
	
		OpenfireProvider.updatePosition(Id, Lat, Lng, creationDate);
		
		Message message = new Message();
		message.setFrom(packet.getFrom());
		message.setType(Type.chat);
		message.setTo(packet.getFrom());
		message.setBody("Successfully updated position");
		try {
			cm.sendPacket(comp, message);
		} catch (ComponentException e) {
			e.printStackTrace();
		}

		IQ resultiq = IQ.createResultIQ(packet);
		return resultiq;
	}
}
