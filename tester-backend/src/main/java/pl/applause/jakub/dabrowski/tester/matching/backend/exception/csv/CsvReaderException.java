package pl.applause.jakub.dabrowski.tester.matching.backend.exception.csv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.beans.ConstructorProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.applause.jakub.dabrowski.tester.matching.backend.exception.ServiceException;

@JsonIgnoreProperties({ "cause", "localizedMessage", "stackTrace", "suppressed" })
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "error")
@EqualsAndHashCode(callSuper = false)
public class CsvReaderException extends ServiceException {
	
	private static final long serialVersionUID = -8369133790428119647L;
	
	@Getter
	private final CsvReaderException.ExceptionType exceptionType;
	
	@ConstructorProperties({ "message", "exceptionType" })
	public CsvReaderException(final String message, final CsvReaderException.ExceptionType exceptionType) {
		super(message, exceptionType.toString());
		this.exceptionType = exceptionType;
	}
	
	public enum ExceptionType {
		FILE_PATH_NOT_FOUND,
		FILE_NOT_FOUND,
		FETCH_ERROR
	}
	
}
