package pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@EqualsAndHashCode
public class Tester implements Serializable {
	
	private static final long serialVersionUID = 1364543404964601594L;
	
	private final String name;
	private final Integer experience;
	
	@Builder
	@ConstructorProperties({"name", "experience"})
	public Tester(final String name, final Integer experience) {
		
		Assert.notNull(name, "Name cannot be null");
		Assert.notNull(experience, "Experience cannot be null");
		Assert.isTrue(experience >= 0, "Experience must greater or equal 0");
		
		this.name = name;
		this.experience = experience;
	}
}
