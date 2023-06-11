package com.example.be;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static Date stringToDate(String dateString) {
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static java.util.Date sqlDate2utilDate(java.sql.Date sqlDate) {
		return Date.from(sqlDate.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static java.sql.Date utilDate2sqlDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}
	
	public static java.util.Date getExceptionReceivingDate() {
		Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 2);

        return calendar.getTime();
	}
}
