package com.example.xmpp.xml;

import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.EmbeddedExtensionProvider;

import com.example.xmpp.dao.PositionItem;

import android.util.Log;

/**
 * Created by Saveen Dhiman on 02-03-17
 * GetGroupCommonExtension add the packet extension to server.
 */
public class GetGroupCommonExtension implements PacketExtension {
	public PositionItem group;

	public static EmbeddedExtensionProvider getGroupProvider() {
		return new EmbeddedExtensionProvider() {
			protected PacketExtension createReturnExtension(String paramAnonymousString1, String paramAnonymousString2,
					Map<String, String> paramAnonymousMap, List<? extends PacketExtension> paramAnonymousList) {
				return null;
			}
		};
	}

	/**
	 * get the extension provider from here.
	 *
	 * @return
	 */
	public static EmbeddedExtensionProvider getProvider() {
		return new EmbeddedExtensionProvider() {
			protected PacketExtension createReturnExtension(String paramAnonymousString1, String namespace,
					Map<String, String> args, List<? extends PacketExtension> exts) {
				GetGroupCommonExtension extension = new GetGroupCommonExtension();
				extension.group = new PositionItem();
				PositionItem group = extension.group;
				group.postionID = ((String) args.get("postionID"));
				Log.d("tag", "check values ::" + ((String) args.get("Id")));
				group.Id = ((String) args.get("Id"));
				group.Lat = ((String) args.get("Lat"));
				group.Lng = ((String) args.get("Lng"));
				group.creationDate = ((String) args.get("creationDate"));

				return extension;
			}
		};
	}

	public String getElementName() {
		return "group";
	}

	public String getNamespace() {
		return "openfire:iq:getallposition";
	}

	public String toXML() {
		return "";
	}
}