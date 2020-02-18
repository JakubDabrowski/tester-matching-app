package pl.applause.jakub.dabrowski.tester.matching.backend.exception.csv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.beans.ConstructorProperties;
import lombok.EqualsAndHashCode;
import pl.applause.jakub.dabrowski.tester.matching.backend.exception.ServiceException;

@JsonIgnoreProperties({ "cause", "localizedMessage", "stackTrace", "suppressed" })
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "error")
@EqualsAndHashCode(callSuper = false)
public class CsvMapperException extends ServiceException {
	
	private static final long serialVersionUID = -6026694244566937085L;
	
	@ConstructorProperties({ "message" })
	public CsvMapperException(final String message) {
		super(message);
	}
	
}
