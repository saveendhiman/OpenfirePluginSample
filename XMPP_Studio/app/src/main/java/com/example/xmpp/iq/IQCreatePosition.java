package com.example.xmpp.iq;

import org.jivesoftware.smack.packet.IQ;

import com.example.xmpp.OpenfireIQ;

/**
 * Created by Saveen Dhiman on 02-03-17
 * IQGetPositionCommon use to create a record in database
 */
public class IQCreatePosition extends OpenfireIQ {
	public static String NAMESPACE = "openfire:iq:position";
	private String creationDate;

	private String Id;
	private String Lat;
	private String Lng;

	public IQCreatePosition(String Id, String Lat, String Lng, String creationDate) {

		setType(IQ.Type.SET);
		this.Id = Id;
		this.Lat = Lat;
		this.Lng = Lng;
		this.creationDate = creationDate;

	}

	public String getContent() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<item Id='%s' Lat='%s' Lng='%s' creationDate='%s' />", Id,
				Lat, Lng, creationDate));

		return builder.toString();
	}

	public String getNamespace() {
		return NAMESPACE;
	}
}