package de.bistrosoft.palaver.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
 
/**
 * @author Tom
 * 
 */
//public class CalendarWeek {
 
    /**
     * @param args
     */
//    public static void main(final String[] args) {
//        final CalendarWeek calendarWeek = new CalendarWeek(37,Locale.GERMANY);
//        System.out.println(calendarWeek.getStart());
//        System.out.println(calendarWeek.getEnd());
//    }
 
    public class CalendarWeek {
        int weekOfYear;
 
        Date start;
 
        Date end;
        
        public static Date getFirtsDayOfWeek(int week, int year) {
        	CalendarWeek cw = new CalendarWeek(week,year, Locale.GERMANY);
        	return cw.getStart();
        }
        
        public static ArrayList<GregorianCalendar> getDatesOfWeek(int week, int year) {
        	CalendarWeek cw = new CalendarWeek(week,year, Locale.GERMANY);
        	ArrayList<GregorianCalendar> dates = new ArrayList<>();
        	Date tmp = cw.getStart();
        	for (int i = 0;i<7;++i) {
        		GregorianCalendar cal= new GregorianCalendar();
            	cal.setTime(tmp);
        		cal.add(Calendar.DAY_OF_YEAR, i);
        		dates.add(cal);
        	}
        	return dates;
        }
        
        public static ArrayList<GregorianCalendar> getDatesOfWeek(Date date) {
        	Calendar cal = new GregorianCalendar();
        	cal.setTime(date);
        	int week = cal.get(Calendar.WEEK_OF_YEAR);
        	int year = cal.get(Calendar.YEAR);
        	return getDatesOfWeek(week, year);
        }
 
        public CalendarWeek(final int weekOfYear,final int year, final Locale locale) {
            this.weekOfYear = weekOfYear;
 
            final GregorianCalendar calendar = new GregorianCalendar(locale);
//            final int CURRENT_YEAR = calendar.get(Calendar.YEAR);
            calendar.clear();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.WEEK_OF_YEAR, this.weekOfYear);
            
            this.start = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            this.end = calendar.getTime();
        }
 
        public Date getEnd() {
            return end;
        }
 
        public Date getStart() {
            return start;
        }
 
        public int getWeekOfYear() {
            return weekOfYear;
        }

		public static Week getCurrentWeek() {
			Week week= new Week();
			Calendar cal = new GregorianCalendar();
			Date curDate = new Date();
        	cal.setTime(curDate);
        	week.setWeek(cal.get(Calendar.WEEK_OF_YEAR));
        	week.setYear(cal.get(Calendar.YEAR));
        	return week;
		}
    }
//}
