package com.example.xmpp.iq;

import org.jivesoftware.smack.packet.IQ;

import com.example.xmpp.OpenfireIQ;

/**
 * Created by Saveen Dhiman on 02-03-17
 * IQRemovePosition use to remove records from database
 */

public class IQRemovePosition extends OpenfireIQ {

	public static String NAMESPACE = "openfire:iq:positionremove";
	String Id;

	public IQRemovePosition(String from, String Id) {
		setType(IQ.Type.GET);
		setFrom(from);
		this.Id = Id;
	}

	public String getContent() {

		return "<item delete='true' Id='" + this.Id + "'/>";
	}

	public String getNamespace() {
		return NAMESPACE;
	}
}
