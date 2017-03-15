package com.example.xmpp.iq;

import org.jivesoftware.smack.packet.IQ;

import com.example.xmpp.OpenfireIQ;

/**
 * Created by Saveen Dhiman on 02-03-17
 * IQGetPositionCommon use to get all records from database
 */
public class IQGetPositionALL extends OpenfireIQ {
	public static String NAMESPACE = "openfire:iq:getallposition";

	String Id;

	public IQGetPositionALL(String from, String Id) {
		setType(IQ.Type.GET);
		setFrom(from);
		this.Id = Id;
	}

	public String getContent() {
		return "<item Id='" + this.Id + "'/>";
	}

	public String getNamespace() {
		return NAMESPACE;
	}
}