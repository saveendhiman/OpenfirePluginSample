package org.jivesoftware.openfire.plugin;

import java.io.File;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.plugin.handler.PositionsCommonIQHandler;
import org.jivesoftware.openfire.plugin.handler.PositionsDeleteIQHandler;
import org.jivesoftware.openfire.plugin.handler.PositionsGETIQHandler;
import org.jivesoftware.openfire.plugin.handler.PositionsIQHandler;
import org.jivesoftware.openfire.plugin.handler.PositionsUpdateIQHandler;
import org.jivesoftware.openfire.plugin.schedule.TaskScheduler;
import org.xmpp.component.Component;
import org.xmpp.component.ComponentException;
import org.xmpp.component.ComponentManager;
import org.xmpp.component.ComponentManagerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

/**
 * Created by Saveen Dhiman on 02-03-17
 * OpenfirePlugin 
 * This is the main class for all connection and request handles here.
 *
 */

public class OpenfirePlugin implements Plugin, Component {

	public static String FROM = "openfirepositions@openfire.st1ng.no-ip.biz";
	public static String HOST = "st1ng.no-ip.biz";
	
	ComponentManager cm;

	/**
	 * Initialize the plugin and its methods.
	 * 
	 */
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		System.out.println("purplkite plugin initialized!");
		cm = ComponentManagerFactory.getComponentManager();

		try {
			cm.addComponent("purplkite", this);
		} catch (ComponentException e) {
			e.printStackTrace();
		}
	
		XMPPServer.getInstance().getIQRouter().addHandler(new PositionsIQHandler(cm, this));
		XMPPServer.getInstance().getIQRouter().addHandler(new PositionsDeleteIQHandler(cm, this));
		XMPPServer.getInstance().getIQRouter().addHandler(new PositionsUpdateIQHandler(cm, this));
		XMPPServer.getInstance().getIQRouter().addHandler(new PositionsCommonIQHandler(cm, this));
		XMPPServer.getInstance().getIQRouter().addHandler(new PositionsGETIQHandler(cm, this));
		
	}

	public void destroyPlugin() {
		try {
			cm.removeComponent("openfire");
		} catch (ComponentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void initialize(JID jid, ComponentManager cm) throws ComponentException {

	}

	@Override
	public void processPacket(Packet arg0) {

	}

	@Override
	public void shutdown() {

	}

	@Override
	public void start() {

	}
}
