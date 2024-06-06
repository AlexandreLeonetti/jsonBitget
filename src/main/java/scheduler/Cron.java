/* scheduler */

package scheduler;


import sequenceCall.SequenceCall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cron {

    private static final Logger log = LoggerFactory.getLogger(Cron.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        Cron tasks = new Cron();
        tasks.startScheduler();
    }

    public void startScheduler() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                reportCurrentTime();
                System.out.println("Starting Cron... !");
                try {	
             	   		SequenceCall.sequentialCall();
                } catch (Exception e) {
                         log.error("Error running the Bitget EntryBitGet", e);
                }
            }
        };

        long initialDelay = getInitialDelay();
        scheduler.scheduleAtFixedRate(task, initialDelay, TimeUnit.DAYS.toMillis(1), TimeUnit.MILLISECONDS);
    }

    private long getInitialDelay() {
        Calendar now = Calendar.getInstance();
        Calendar nextRun = (Calendar) now.clone();
        nextRun.set(Calendar.HOUR_OF_DAY, 23);//23
        nextRun.set(Calendar.MINUTE, 59);//59
        nextRun.set(Calendar.SECOND, 20);//30
        nextRun.set(Calendar.MILLISECOND, 0);

        if (now.after(nextRun)) {
            nextRun.add(Calendar.DAY_OF_MONTH, 1);
        }

        return nextRun.getTimeInMillis() - now.getTimeInMillis();
    }

    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));

        // Measure RAM used
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory in bytes: " + memory);
        System.out.println("Used memory in megabytes: " + memory / (1024L * 1024L));
        System.out.println("reported time from cronBitGet");
    }
}