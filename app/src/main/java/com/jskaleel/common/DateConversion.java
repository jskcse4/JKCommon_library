package com.jskaleel.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class DateConversion {

	public static long getDateDifference(Calendar startDate,Calendar endDate)
	{   	
		startDate.set(Calendar.HOUR,0);
		startDate.set(Calendar.MINUTE,0);
		startDate.set(Calendar.SECOND,0);
		startDate.set(Calendar.AM_PM,0);

		endDate.set(Calendar.HOUR,0);
		endDate.set(Calendar.MINUTE,0);
		endDate.set(Calendar.SECOND,0);
		endDate.set(Calendar.AM_PM,0);

		long milis1 = startDate.getTimeInMillis();
		long milis2 = endDate.getTimeInMillis();        

		return getDateDifference(milis1, milis2);
	}

	public static long getDateDifference(long strtDate, long endDate){
		long diff = endDate - strtDate;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		return diffDays;
	}

	public static long getMonthDifference(Calendar startDate,Calendar endDate){
		long monthDiff = endDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH) + (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR)) * 12;		
		return -monthDiff; 
	}

	public static long getMinuteDifference(long startDate, long endDate){
		long diff = endDate - startDate;
		long minutes = (diff / (1000 * 60));
		return minutes;
	}

	public static Calendar addMonth(Calendar cal,int numberOfMonth){
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) + numberOfMonth);
		return cal;
	}

	public static String getFormatedDateAsString(String strDate,String parseFormat){
		return convertCalendarToString(convertStringToCalendar(strDate,"yyyy-MM-dd"),parseFormat);
	}

	@SuppressLint("SimpleDateFormat")
	public static String getFormatedDateAsStringwithTime(String strDate,String parseFormat){

		Date date = null;
		SimpleDateFormat simpleDateFormat;
		try {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			date = simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String newString = new SimpleDateFormat(parseFormat).format(date);

		if(newString.endsWith("AM"))
			newString =newString.substring(0, newString.length()-2)+"am";
		else if(newString.endsWith("PM"))
			newString =newString.substring(0, newString.length()-2)+"pm";

		return newString;

	}

	@SuppressLint("SimpleDateFormat")
	public static String getDate(String strDate){

		Date date = null;
		SimpleDateFormat simpleDateFormat;
		try {
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = simpleDateFormat.parse(strDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		//need to change convertCalendarToString () method
		return new SimpleDateFormat("MMMMM dd, yyyy").format(date).toString();

	}

	@SuppressWarnings("deprecation")
	@SuppressLint("SimpleDateFormat")
	public static Calendar convertStringToCalendar(String strDate,String parseFormat){
		DateFormat formatter ; 
		Date date = null ; 
		formatter = new SimpleDateFormat(parseFormat);		
		try {			
			date = (Date)formatter.parse(strDate);			
		} catch (Exception e) {	
			Log.d("DateConversion","Error parsing Date : " + strDate);
			date=new Date(strDate);
		} 		
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);		
		return cal;
	}	

	@SuppressLint("SimpleDateFormat")
	public static String convertCalendarToString(Calendar cal,String format){	
		SimpleDateFormat df = new SimpleDateFormat(format);		
		return df.format(cal.getTime());
	}

	
	public static String convertCalendarToString(Calendar cal){	
		SimpleDateFormat df = new SimpleDateFormat("MMMMM dd, yyyy");		
		return df.format(cal.getTime());
	}
	public static String getCurrentDateTime(){
		Calendar cal = Calendar.getInstance();
		
		int a = cal.get(Calendar.AM_PM);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
		String newString =df.format(cal.getTime());
		if(a == Calendar.AM)
		{
			newString = newString+" am";
		}
		else if(a ==Calendar.PM)
		{
			newString = newString+" pm";
		}
		return newString;
	}

	public static boolean compareWithCurrentDate(Calendar cal2){
		Calendar cal1 = Calendar.getInstance();
		String date1=convertCalendarToString(cal1,"yyyy-MM-dd");  
		String date2=convertCalendarToString(cal2,"yyyy-MM-dd");

		if(date1.equalsIgnoreCase(date2))
			return true;
		else
			return false;
	}

	@SuppressWarnings("deprecation")
	public static Date stringToDate(String strDate,String parseFormat){
		DateFormat formatter ; 
		Date date = null ; 
		formatter = new SimpleDateFormat(parseFormat);
		try {
			date = (Date)formatter.parse(strDate);
		} catch (ParseException e) {			
			date=new Date(strDate);
		} 		
		return date;
	}

	public static String calendarToStringwithslash(Calendar cal){	
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");		
		return df.format(cal.getTime());
	}

	public static Calendar getPreviousMonth(Calendar cal){
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) - 1);
		return cal;
	}

	public static Calendar getNextMonth(Calendar cal){
		cal.set(Calendar.MONTH,cal.get(Calendar.MONTH) + 1);
		return cal;
	}

	public static String getTimeFromLong(long time, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(time));
	}

	/*public static String getAKDTTimeFromDate(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
		return sdf.format(date);
	}

	public static String getISTTimeFromDate(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("IST"));
		return sdf.format(date);
	}*/

	public static String getTimeFromDate(Date date, String format){
		return getTimeFromDate(date, format, TimeZone.getDefault());
	}

	public static String getTimeFromDate(Date date, String format, TimeZone timeZone){

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if(timeZone != null){
			sdf.setTimeZone(timeZone);
		}
		return sdf.format(date);

	}


	/** The time(long) value is seconds not millis
	 * @param timeZone  String representation of time format
	 * @param time   time as long value in seconds
	 * @return time  time as long in seconds
	 */

	public static long getLocalizedTime(String timeZone, long time) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aaa");
		//sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		String dateS = sdf.format(new Date(time * 1000l)); // /1351330745
		return DateConversion.stringToDate(dateS, "dd-MM-yyyy hh:mm:ss aaa").getTime() / 1000l;

	}

	public static SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Date date1,date2;
	public static long diff; 
	public static String TAG ="DateConversion";
	public static boolean isAfter(String currentDate,String checkDate)
	{

		try {
			
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			date1	 = simpleDateFormat.parse(currentDate);
			date2	 = simpleDateFormat.parse(checkDate);

			//in milliseconds
			diff = date2.getTime() - date1.getTime();

//			PrintLog.debug(TAG, "Difference: "+diff);
			if(diff<=0)
			{
				return false;
			}
			else
			{
				return true;
			}
			
			
//			long diffSeconds = diff / 1000 % 60;
//			long diffMinutes = diff / (60 * 1000) % 60;
//			long diffHours = diff / (60 * 60 * 1000) % 24;
//			long diffDays = diff / (24 * 60 * 60 * 1000);
//			System.out.print(diffDays 	+ " days, ");
//			System.out.print(diffHours 	+ " hours, ");
//			System.out.print(diffMinutes+ " minutes, ");
//			System.out.print(diffSeconds+ " seconds.");

		} catch (Exception e) 
		{
			PrintLog.error(TAG, ""+e);
			return true;
		}
	}

}
