package com.globallogic.xlstodatabase.utility;

import java.sql.Date;
import java.util.List;

public class Utility {
	public static Date stringToDate(String date) throws Exception {
		return Date.valueOf(date);
	}

	public static int stringToSecond(String duration)
	{
			if (duration.contains("hr") && duration.contains("min"))
			{
				String[] timeList = duration.split(" ");
				int hours = Integer.parseInt(timeList[0]);
				int minutes = Integer.parseInt(timeList[2]);
				// Convert the hours and minutes to seconds
				int hoursInSeconds = hours * 60 * 60;
				int minutesInSeconds = minutes * 60;
				// Add the seconds together
				return hoursInSeconds + minutesInSeconds;
			}
			else if(duration.contains("hr"))
			{
				String[] timeList = duration.split(" ");
				int hours = Integer.parseInt(timeList[0]);
				// Convert the hours to seconds
				return hours * 60 * 60;
			} else if (duration.contains("min")) {
				String[] timeList = duration.split(" ");
				int min = Integer.parseInt(timeList[0]);
				// Convert the minutes to seconds
				return min * 60 ;
			}
			else
			{
				String[] timeList = duration.split(" ");
				int sec = Integer.parseInt(timeList[0]);
				return sec ;
			}
	}
	public static String maxDuration(List<String> duration)
	{
		int max=duration.stream().map(Utility::stringToSecond).max(Integer::compareTo).get();
		int hours = max / 3600;
		int minutes = (max % 3600) / 60;
		// Format the result as a string
		String timeStr = String.format("%d hr %02d min", hours, minutes);
		return  timeStr;
	}
	public static String sumDuration(List<String> duration)
	{
		int sum=duration.stream().map(Utility::stringToSecond).mapToInt(Integer::intValue).sum();
		int hours = sum / 3600;
		int minutes = (sum % 3600) / 60;
		// Format the result as a string
		String timeStr = String.format("%d hr %02d min", hours, minutes);
		return  timeStr;
	}
}
