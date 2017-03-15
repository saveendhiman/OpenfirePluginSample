package org.jivesoftware.openfire.plugin.provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.openfire.plugin.data.PositionItem;
import org.jivesoftware.openfire.plugin.data.TimeOutResult;

/**
 * Created by Saveen Dhiman on 02-03-17 OpenfireProvider All queries and methods
 * of queries define here.
 *
 */

public class OpenfireProvider {

	private static final String GET_POSITIONS = "SELECT * FROM ofPositions WHERE Id = ?";
	private static final String CREATE_POSITION = "INSERT INTO ofPositions (Id, Lat, Lng, creationDate) VALUES (?,?,?,?)";
	private static final String UPDATE_POSITION = "UPDATE ofPositions SET Id = ?, Lat = ?, Lng = ?, creationDate = ? WHERE Id = ?";
	private static final String DELETE_POSITION_BY_ID1 = "DELETE FROM ofPositions WHERE Id = ?";
	private static final String GET_POSITION_BY_ID = "SELECT * FROM ofPositions WHERE Id = ?";

	private static final String TIMEDOUT_QUERY1 = "CALL `ChangeStatus` ()";

	public static List<PositionItem> getGroups(String Id) {
		List<PositionItem> result = new ArrayList<PositionItem>();
		Connection db = null;
		try {
			db = DbConnectionManager.getConnection();
			PreparedStatement statement = db.prepareStatement(GET_POSITIONS, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, Id);
			ResultSet res = statement.executeQuery();
			while (res.next()) {
				PositionItem group = new PositionItem();
				group.postionID = res.getLong(1);
				group.Id = res.getString(2);
				group.Lat = res.getString(3);
				group.Lng = res.getString(4);
				group.creationDate = res.getString(5);
				result.add(group);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}

	public static void clearTimedoutRecords() {
		System.out.println("Clearing Activity");
		ChangeStatus();
	}

	/**
	 * Create a record position and save it to database
	 * 
	 * @param Id
	 * @param Lat
	 * @param Lng
	 * @param creationDate
	 * @return
	 */
	public static long createPosition(String Id, String Lat, String Lng, String creationDate) {
		Connection db = null;
		long result = -1;
		try {
			db = DbConnectionManager.getConnection();
			PreparedStatement statement = db.prepareStatement(CREATE_POSITION, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, Id);
			statement.setString(2, Lat);
			statement.setString(3, Lng);
			statement.setString(4, creationDate);
			statement.execute();
			ResultSet keys = statement.getGeneratedKeys();
			if (keys.first()) {
				result = keys.getLong(1);
			}

		} catch (SQLException e) {
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (SQLException e) {
				}
			}
		}
		return -1;
	}

	/**
	 * Update record in database.
	 * 
	 * @param Id
	 * @param Lat
	 * @param Lng
	 * @param creationDate
	 * @return
	 */

	public static long updatePosition(String Id, String Lat, String Lng, String creationDate) {
		Connection db = null;
		long result = -1;
		try {
			db = DbConnectionManager.getConnection();
			PreparedStatement statement = db.prepareStatement(UPDATE_POSITION);
			statement.setString(1, Id);
			statement.setString(2, Lat);
			statement.setString(3, Lng);
			statement.setString(4, creationDate);
			statement.setString(5, Id);
			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (SQLException e) {
				}
			}
		}
		return -1;
	}

	/**
	 * get one record from database.
	 * 
	 * @param Id
	 * @return
	 */
	public static PositionItem getGroup(String Id) {

		List<PositionItem> result = new ArrayList<PositionItem>();
		Connection db = null;
		try {
			db = DbConnectionManager.getConnection();
			PreparedStatement statement = db.prepareStatement(GET_POSITION_BY_ID);
			statement.setString(1, Id);
			ResultSet res = statement.executeQuery();

			while (res.next()) {
				PositionItem group = new PositionItem();
				group.Id = res.getString(1);
				group.Lat = res.getString(2);
				group.Lng = res.getString(3);
				group.creationDate = res.getString(4);
				result.add(group);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (SQLException e) {
				}
			}
		}
		return result.size() > 0 ? result.get(0) : null;
	}

	/**
	 * Delete the record from database
	 * 
	 * @param Id
	 */
	public static void deleteGroup1(String Id) {
		Connection db = null;
		try {
			db = DbConnectionManager.getConnection();
			PreparedStatement statement = db.prepareStatement(DELETE_POSITION_BY_ID1);
			statement.setString(1, Id);
			statement.execute();

		} catch (SQLException e) {
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	/**
	 * time out result
	 * 
	 * @return
	 */
	public static TimeOutResult ChangeStatus() {
		List<TimeOutResult> result = new ArrayList<TimeOutResult>();
		Connection db = null;
		try {
			db = DbConnectionManager.getConnection();
			PreparedStatement statement = db.prepareStatement(TIMEDOUT_QUERY1);
			ResultSet res = statement.executeQuery();

			while (res.next()) {
				TimeOutResult timeoutresult = new TimeOutResult();
				timeoutresult.RecordsUpdated = res.getString(1);
				System.out.println("Result test :" + res.getString(1));
				result.add(timeoutresult);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (SQLException e) {
				}
			}
		}
		return result.size() > 0 ? result.get(0) : null;
	}

}
