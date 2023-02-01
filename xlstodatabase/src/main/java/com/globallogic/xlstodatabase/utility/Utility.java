package com.globallogic.xlstodatabase.utility;

import java.sql.Date;

public class Utility {

	public static Date stringToDate(String date) throws Exception {
		return Date.valueOf(date);
	}
}
