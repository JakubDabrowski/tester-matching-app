package pl.applause.jakub.dabrowski.tester.matching.backend.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@JsonIgnoreProperties({ "cause", "localizedMessage", "stackTrace", "suppressed" })
@JsonTypeInfo(use = Id.CLASS, property = "error")
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
	
	private final String exceptionKey;
	
	public static final String ERROR_FIELD_NAME = "error";
	
	public static final String MESSAGE_FIELD_NAME = "message";
	
	public static final String SERVICE_ERROR_HEADER = "X-Service-Error";
	
	private static final long serialVersionUID = 5224737703601070296L;
	
	public ServiceException(final String message) {
		super(message);
		this.exceptionKey = this.getClass().getName();
	}
	
	public ServiceException(final String message, final String subtype) {
		super(message);
		this.exceptionKey = (this.getClass().getName() + ".") + subtype;
	}
	
	@Override
	@JsonProperty(MESSAGE_FIELD_NAME)
	public String getMessage() {
		return super.getMessage();
	}
}
