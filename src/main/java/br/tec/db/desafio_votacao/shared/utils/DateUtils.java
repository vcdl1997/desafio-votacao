package br.tec.db.desafio_votacao.shared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	
	private static final DateTimeFormatter REGEX_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static LocalDateTime converteStringParaLocalDateTimeSemSegundos(String localDateTimeStr) {
		if(StringUtils.isEmpty(localDateTimeStr)) {
			return null;
		}
		
		try {
			LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeStr, REGEX_DATE_TIME);
			return localDateTime.truncatedTo(ChronoUnit.MINUTES);
		} catch (DateTimeParseException e) {
			return null;
		}
	}
	
	public static String converteLocalDateTimeParaString(LocalDateTime dataHora) {
		if(Objects.isNull(dataHora)) {
			return null;
		}
		
		return dataHora.format(REGEX_DATE_TIME);
	}
	
}
