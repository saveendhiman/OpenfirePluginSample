package com.example.xmpp;

import org.jivesoftware.smack.packet.IQ;

/**
 * Created by Saveen Dhiman on 02-03-17
 * Openfire IQ handler
 *
 */


public abstract class OpenfireIQ extends IQ {

	/**
	 * Get child element xml
	 *
	 * @return
     */
	public String getChildElementXML() {
		return "<query xmlns='" + getNamespace() + "'>" + getContent() + "</query>";
	}

	/**
	 * Get content
	 *
	 * @return
     */
	public String getContent() {
		return "";
	}

	public abstract String getNamespace();
}
