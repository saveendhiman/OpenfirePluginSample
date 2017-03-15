package org.jivesoftware.openfire.plugin.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {
	public static void scheduleClearingActivity(){		
		ScheduledTimedoutClearTask clearTask = new ScheduledTimedoutClearTask();
		ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(clearTask, 0, 30, TimeUnit.SECONDS);
	}
}
