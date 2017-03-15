package org.jivesoftware.openfire.plugin.handler;

import java.util.List;

import org.dom4j.Element;
import org.jivesoftware.openfire.IQHandlerInfo;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.handler.IQHandler;
import org.jivesoftware.openfire.plugin.OpenfirePlugin;

import org.jivesoftware.openfire.plugin.data.PositionItem;
import org.jivesoftware.openfire.plugin.provider.OpenfireProvider;
import org.xmpp.component.Component;
import org.xmpp.component.ComponentException;
import org.xmpp.component.ComponentManager;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Message;

/**
 * Created by Saveen Dhiman on 02-03-17
 * PositionsGETIQHandler get one record then send it to correspondence user 
 *
 */

public class PositionsGETIQHandler extends IQHandler {

	Component comp;
	ComponentManager cm;

	public PositionsGETIQHandler(ComponentManager cm, Component comp) {
		super("Get contacts handler");
		this.cm = cm;
		this.comp = comp;
	}

	@Override
	public IQHandlerInfo getInfo() {
		return new IQHandlerInfo("query", "openfire:iq:getallposition");
	}

	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		System.out.println("In Get IQ Handler");
		System.out.println(packet.toXML().toString());	
		
		Message message = new Message();
		message.setFrom(OpenfirePlugin.FROM);
		message.setTo(packet.getFrom());

		String Id = packet.getChildElement().element("item").attribute("Id").getText();
		List<PositionItem> groups = OpenfireProvider.getGroups(Id);

		for (PositionItem group : groups) {
			Element child = message.addChildElement("group", "openfire:iq:getallposition");
			child.addAttribute("postionID", String.valueOf(group.postionID));
			child.addAttribute("Id", group.Id);
			child.addAttribute("Lat", group.Lat);
			child.addAttribute("Lng", group.Lng);
			child.addAttribute("creationDate", group.creationDate);
		}

		try {
			cm.sendPacket(comp, message);
		} catch (ComponentException e) {
			e.printStackTrace();
		}

		IQ resultiq = IQ.createResultIQ(packet);
		return resultiq;
	}
}
