package alb.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Dates {

	public static Date fromString(String string) {
		String dateString[] = string.split("/");
		if (dateString.length != 3) {
			throw new NumberFormatException("Invalid fields for a date");
		}
		return Dates.fromDdMmYyyy(
				Integer.parseInt(dateString[0]), 
				Integer.parseInt(dateString[1]),
				Integer.parseInt(dateString[2])
			);
	}

	public static Date fromDdMmYyyy(int dd, int mm, int yyyy) {
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.DAY_OF_MONTH, dd);
		c.set(Calendar.MONTH, mm - 1); // base 0
		c.set(Calendar.YEAR, yyyy);
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * @return Yesterday at 00:00:00.000
	 */
	public static Date yesterday() {
		return addDays( trunc(new Date()), -1);
	}

	/**
	 * @return Today at 00:00:00.000
	 */
	public static Date today() {
		return trunc( new Date() );
	}
	
	/**
	 * @return Tomorrow at 00:00:00.000
	 */
	public static Date tomorrow() {
		return addDays( trunc(new Date()), 1);
	}

	/**
	 * @return Today at hh:mm:ss.mmm, for example 12/12/2012 12:10:23.021
	 */
	public static Date now() {
		return new Date();
	}
	
	/**
	 * @param date
	 * @return true if the date is day 1 of the corresponding month
	 */
	public static Date firstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		int dayOne = c.getActualMinimum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, dayOne);
		return c.getTime();
	}

	/**
	 * @param date
	 * @return true if the date is the last day of the corresponding month
	 */
	public static Date lastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, lastDay);
		return c.getTime();
	}

	public static Date rndDateFrom(Date baseDate, int dias) {
		return addDays(baseDate, rnd(0, dias));
	}

	public static Date rndDateBefore(Date baseDate, int dias) {
		Date d = subDays(baseDate, rnd(1, dias));
		return new Date( d.getTime() - rnd(0, 10000) );
	}

	public static String toString(Date date) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	public static boolean isDateInWindow(Date date, Date from, Date until) {
		return date.compareTo(from) >= 0 && date.compareTo(until) <= 0;
	}

	public static boolean isNotAfter(Date date, Date reference) {
		return date.compareTo(reference) <= 0;
	}

	public static boolean isNotBefore(Date date, Date reference) {
		return date.compareTo(reference) >= 0;
	}

	public static boolean isAfter(Date date, Date reference) {
		return date.compareTo(reference) > 0;
	}

	public static boolean isBefore(Date date, Date reference) {
		return date.compareTo(reference) < 0;
	}

	public static boolean isSameMonth(Date date, Date date2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.setTime(date);
		c2.setTime(date2);

		return c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
	}

	public static boolean isFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH) == 1;
	}

	public static boolean isLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int lastDay = c.getActualMaximum( Calendar.DAY_OF_MONTH );
		return c.get(Calendar.DAY_OF_MONTH) == lastDay;
	}

	public static Date addYears(Date date, int years) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, years);
		return c.getTime();
	}

	public static Date addMonths(Date date, int months) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		return c.getTime();
	}

	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	public static Date addHours(Date date, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hours);
		return c.getTime();
	}

	public static Date addMinutes(Date date, int minutes) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);
		return c.getTime();
	}

	public static Date addSeconds(Date date, int seconds) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, seconds);
		return c.getTime();
	}

	public static Date subYears(Date date, int years) {
		return addYears(date, -years);
	}

	public static Date subMonths(Date date, int months) {
		return addMonths(date, -months);
	}

	public static Date subDays(Date date, int days) {
		return addDays(date, -days);
	}

	public static Date subHours(Date date, int hours) {
		return addHours(date, -hours);
	}

	public static Date subMinutes(Date date, int minutes) {
		return addMinutes(date, -minutes);
	}

	public static Date subSeconds(Date date, int seconds) {
		return addMonths(date, -seconds);
	}

	/**
	 * Truncates a date by setting hh:mm:ss to 00:00:00. 
	 * For example for date "12/02/2012 13:24:34" returns "12/02/2012 00:00:00.000"
	 * It is useful for comparing dates in the same day. 
	 * @param date
	 * @return 
	 */
	public static Date trunc(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static String stringStamp() {
		return new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format( new Date() );
	}

	public static Integer month(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	private static Random rnd = new Random();
	private static int rnd(int min, int max) {
		return rnd.nextInt(max - min) + min;
	}

	public static Date inYearsTime(int years) {
		return addDays(today(), years * 365);
	}

	public static int diffDays(Date until, Date from) {
		long diff = until.getTime() - from.getTime();
		return (int) TimeUnit.DAYS.convert( diff, TimeUnit.MILLISECONDS );
	}

	public static List<Date> generateDates(Date from, Date until, Period period) {
		List<Date> res = new LinkedList<>();
		Date d = from;
		while( until.after( d )) {
			res.add( d );
			d = Dates.addDays(d, period.getDays());
		}
		return res;
	}

	public static List<Date> generateDates(Date from, int repetitions, Period period) {
		List<Date> res = new LinkedList<>();
		Date d = from;
		for(int i = 0; i < repetitions; i++) {
			res.add( d );
			d = Dates.addDays(d, period.getDays());
		}
		return res;
	}

}
