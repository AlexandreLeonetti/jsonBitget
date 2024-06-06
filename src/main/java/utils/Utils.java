//Utils.java
package utils;

import java.text.DecimalFormat;

public class Utils {
	public static String numberFormatter(int precision, float toBeFormatted) { //precision can be up to 6
        if (precision < 0 || precision > 6) {
            throw new IllegalArgumentException("Precision must be between 0 and 6");
        }

        StringBuilder pattern = new StringBuilder("#.");
        for (int i = 0; i < precision; i++) {
            pattern.append("#");
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());
        return decimalFormat.format(toBeFormatted);
    }
	
	
	public static void sleepMs(int ms) {// number of seconds
        try {
            Thread.sleep(1000*ms); // Delay of 2000 milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.err.println("Interrupted during sleep");
        }
	}
}