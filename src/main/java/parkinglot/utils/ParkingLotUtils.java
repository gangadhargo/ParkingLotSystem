package parkinglot.utils;

import java.util.Date;

public class ParkingLotUtils {
	public static Integer getHoursBetweenTwoDates(Date startDateTime, Date endDateTime) {
		long diff = endDateTime.getTime() - startDateTime.getTime();
		Long diffHours = diff / (60 * 60 * 1000);
		return diffHours.intValue();
	}
}
