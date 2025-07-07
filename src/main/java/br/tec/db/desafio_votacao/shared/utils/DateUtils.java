package br.tec.db.desafio_votacao.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	
	private static final DateTimeFormatter REGEX_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime converteStringParaLocalDateTime(String localDateTimeStr) {
		if(StringUtils.isEmpty(localDateTimeStr)) {
			return null;
		}
		
		try {
			return LocalDateTime.parse(localDateTimeStr, REGEX_DATE_TIME);
		} catch (DateTimeParseException e) {
			return null;
		}
	}
	
}
