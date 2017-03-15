package org.jivesoftware.openfire.plugin.schedule;

import org.jivesoftware.openfire.plugin.provider.OpenfireProvider;

public class ScheduledTimedoutClearTask implements Runnable {

	@Override
	public void run() {
		OpenfireProvider.clearTimedoutRecords();
	}

}
