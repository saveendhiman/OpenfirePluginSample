package org.jivesoftware.openfire.plugin.data;

import java.io.Serializable;

/**
 * Created by Saveen Dhiman on 02-03-17
 * UserPositionItem here is the getter and setter
 *
 */

public class UserPositionItem implements Serializable {
	
	
	private static final long serialVersionUID = 3012363003681389719L;
	private long positionID;
	private String userId;
	private String userLat;
	private String userLng;
	private String creationDate;
	
	
	public long getPositionID() {
		return positionID;
	}
	public void setPositionID(long postionID) {
		this.positionID = postionID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserLat() {
		return userLat;
	}
	public void setUserLat(String userLat) {
		this.userLat = userLat;
	}
	public String getUserLng() {
		return userLng;
	}
	public void setUserLng(String userLng) {
		this.userLng = userLng;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
}
