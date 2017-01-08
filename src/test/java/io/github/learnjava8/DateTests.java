package io.github.learnjava8;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class DateTests {

	@Test
	public void testNowDateTime() {
	      LocalDateTime currentDateAndTime = LocalDateTime.now();
	      System.out.println("Current DateTime: " + currentDateAndTime);
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	      System.out.println("  (formatted)   : " + formatter.format(currentDateAndTime));
	}
	
	@Test
	public void testNowDate() {
	      LocalDate currentDate = LocalDate.now();
	      System.out.println("Current Date: " + currentDate);
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	      System.out.println(" (formatted): " + formatter.format(currentDate));
	}
	
	@Test
	public void testSpecificDate() {
	      LocalDate Christmas = LocalDate.of(2014, Month.DECEMBER, 25);
	      System.out.println("Christmas Date: " + Christmas);
	      
	      LocalDate newYearsDay = Christmas.plus(1, ChronoUnit.WEEKS);
	      System.out.println("New Years Eve: " + newYearsDay);
	      
	      assertEquals(DayOfWeek.THURSDAY, newYearsDay.getDayOfWeek());
	      assertEquals(1, newYearsDay.getDayOfMonth());
	      assertEquals(1, newYearsDay.getMonthValue());
	      assertEquals(2015, newYearsDay.getYear());
	}
}
