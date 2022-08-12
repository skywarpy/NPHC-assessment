package org.nphc.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.nphc.exceptions.DateFormatException;

/**
 * This is a utility class used to format dates from two different Patterns and return a LocalDate.
 * If the date does not follow any of these two patterns, throws a DateFormatException.
 * Can be modified to accept more patterns.
 */


public class DateFormatterUtil {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(""+"[dd-MMM-yy]"+"[yyyy-MM-dd]");
	
	public LocalDate getFormattedDate(String date) throws DateFormatException {
		try {
			return LocalDate.parse(date, formatter);
		} catch (Exception e){
			throw new DateFormatException("Invalid Date");
		}
		
		
	}
}
