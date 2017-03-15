package org.jivesoftware.openfire.plugin.handler;

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
 * PositionsCommonIQHandler get all positions then notify correspondence user 
 *
 */

public class PositionsCommonIQHandler extends IQHandler {

	Component comp;
	ComponentManager cm;

	public PositionsCommonIQHandler(ComponentManager cm, Component comp) {
		super("Get contacts handler");
		this.cm = cm;
		this.comp = comp;
	}

	@Override
	public IQHandlerInfo getInfo() {
		return new IQHandlerInfo("query", "openfire:iq:positioncommon");
	}

	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		
		System.out.println("In Positions Common Handler");
		System.out.println(packet.toXML().toString());	

		Message message = new Message();
		message.setFrom(OpenfirePlugin.FROM);
		message.setTo(packet.getFrom());

		System.out.println("Commons :" + packet);
		String Id = packet.getChildElement().element("item").attribute("Id").getText();
		PositionItem groupInfo = OpenfireProvider.getGroup(Id);

		if (groupInfo == null) {
			
			Message message1 = new Message();
			message1.setFrom(OpenfirePlugin.FROM);
			message1.setTo(packet.getFrom());
			message1.setBody("CurrentPositions:" + "null" + "," + "null" + "," + "null" + "," + "null");
			try {
				cm.sendPacket(comp, message1);
			} catch (ComponentException e) {
				e.printStackTrace();
			}
		}

		message.setBody("CurrentPositions:" + groupInfo.Id + "," + groupInfo.Lat + "," + groupInfo.Lng
				+ "," + groupInfo.creationDate);

		try {
			cm.sendPacket(comp, message);
		} catch (ComponentException e) {
			e.printStackTrace();
		}

		IQ resultiq = IQ.createResultIQ(packet);
		return resultiq;
	}
}
