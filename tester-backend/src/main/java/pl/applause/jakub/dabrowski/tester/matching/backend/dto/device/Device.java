package pl.applause.jakub.dabrowski.tester.matching.backend.dto.device;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@EqualsAndHashCode
public class Device implements Serializable {
	
	private static final long serialVersionUID = 2079210786280319274L;
	
	private final Long id;
	private final String description;
	
	@Builder
	@ConstructorProperties({"id", "description"})
	public Device(final Long id, final String description) {
		
		Assert.notNull(id, "Id cannot be null");
		Assert.notNull(description, "Description cannot be null");
		
		this.id = id;
		this.description = description;
	}
}
