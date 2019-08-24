package io.github.profilr.web;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import lombok.experimental.UtilityClass;

public class DateFormatterExtensions {

	/**
	 * DateTimeFormatter meant for logs and issues, will print everything in UTC
	 */
	private static final DateTimeFormatter UTC_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss z").withZone(ZoneOffset.UTC);
	
	/**
	 * DateTimeFormatter meant for human use, will print everything in US Central Time (America/Chicago)
	 */
	private static final DateTimeFormatter CST_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss z").withZone(ZoneId.of("America/Chicago"));
	
	/**
	 * Format a timestamp meant for logs and issues, will print everything in UTC
	 * @param temporal timestamp to format 
	 * @return the formatted String
	 */
	public static String formatSystem(TemporalAccessor temporal) {
		return UTC_FORMATTER.format(temporal);
	}
	
	/**
	 * Format a date meant for humans to read. Currently prints in US Central Time, this could be changed
	 * @param temporal timestamp to format
	 * @return the formatted String
	 */
	public static String formatHuman(TemporalAccessor temporal) {
		return CST_FORMATTER.format(temporal);
	}
	
}
