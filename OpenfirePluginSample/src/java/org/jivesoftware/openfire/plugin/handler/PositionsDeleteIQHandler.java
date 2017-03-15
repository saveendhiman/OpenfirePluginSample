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
import org.xmpp.packet.Message.Type;

/**
 * Created by Saveen Dhiman on 02-03-17
 * PositionsDeleteIQHandler delete the record then notify correspondence user 
 *
 */
public class PositionsDeleteIQHandler extends IQHandler {

	Component comp;
	ComponentManager cm;

	public PositionsDeleteIQHandler(ComponentManager cm, Component comp) {
		super("Get group handler");
		this.cm = cm;
		this.comp = comp;
	}

	@Override
	public IQHandlerInfo getInfo() {
		return new IQHandlerInfo("query", "openfire:iq:positionremove");
	}

	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {		
		System.out.println("In Delete IQ Handler");
		System.out.println(packet.toXML().toString());	

		Message message = new Message();
		message.setFrom(OpenfirePlugin.FROM);
		message.setTo(packet.getFrom());

		Element elem = packet.getChildElement();
		String Id = elem.element("item").attribute("Id").getText();

		OpenfireProvider.deleteGroup1(Id);

		message = new Message();
		message.setFrom(packet.getFrom());
		message.setType(Type.chat);
		message.setTo(packet.getFrom() + "@conference." + OpenfirePlugin.HOST);
		message.setBody("Admin deleted record");

		try {
			cm.sendPacket(comp, message);
		} catch (ComponentException e) {
			e.printStackTrace();
		}

		IQ resultiq = IQ.createResultIQ(packet);
		return resultiq;
	}
}
