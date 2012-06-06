package tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	
	public static void main(String[] args) throws ParseException {

		int today=20110711;
		System.err.println("today:"+today);
		
		System.err.println("next -19 date:"+getNDate(today, -19));
		
		System.err.println("next 20 day:"+getNDate(20));
		
		System.err.println("getNDateLastDayOfMonth():"+getNDateLastDayOfMonth(today));
		
		System.err.println("getNDateFirstDayOfMonth():"+getNDateFirstDayOfMonth(today));
		
		System.err.println("getNDateLastDayOfWeek():"+getNDateLastDayOfWeek(today));
		
		System.err.println("getNDateFirstDayOfWeek():"+getNDateFirstDayOfWeek(today));
		
	}
	
	public static int getNDate(int date,int n) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date currentDate=df.parse(String.valueOf(date));
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.DATE, n);
		return Integer.parseInt(df.format(cal.getTime()));
	}
	
	public static int getNDate(int n) throws ParseException{
		Date date=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date currentDate=date;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.DATE, n);
		return Integer.parseInt(df.format(cal.getTime()));
	}

	public static int getNDateLastDayOfMonth(int nDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = df.parse(String.valueOf(nDate));
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(currentDate);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return Integer.parseInt(df.format(lastDate.getTime()));
	}
	
	public static int getNDateFirstDayOfMonth(int nDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = df.parse(String.valueOf(nDate));
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(currentDate);
		final int lastDay = cDay1.getActualMinimum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return Integer.parseInt(df.format(lastDate.getTime()));
	}
	
	public static int getNDateLastDayOfWeek(int nDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = df.parse(String.valueOf(nDate));
		Calendar   c   =   Calendar.getInstance(); 
		c.setTime(currentDate);
		
		int   dayofweek   =   c.get(Calendar.DAY_OF_WEEK)   -   1; 
		if   (dayofweek   ==   0) dayofweek   =   7; 
		c.add(Calendar.DATE,   - dayofweek   +   7); 
		return Integer.parseInt(df.format(c.getTime()));
	}
	
	public static int getNDateFirstDayOfWeek(int nDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date currentDate = df.parse(String.valueOf(nDate));
		Calendar   c   =   Calendar.getInstance(); 
		c.setTime(currentDate);
		
		int   dayofweek   =   c.get(Calendar.DAY_OF_WEEK)   -   1; 
		if   (dayofweek   ==   0) dayofweek   =   7; 
		c.add(Calendar.DATE,   - dayofweek   +   1); 
		return Integer.parseInt(df.format(c.getTime()));
		
	}
	
	public static String getTimesformatMs(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		String temp= " "+days + " days " + hours + " hours " + minutes + " minutes "
				+ seconds + " seconds ";
		return temp.replaceAll("\\s0\\s\\w*","" );
	}

}