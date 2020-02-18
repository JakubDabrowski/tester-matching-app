package pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;
import pl.applause.jakub.dabrowski.tester.matching.backend.enums.CsvObjectType;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.CsvObject;

@Getter
public class TesterDevice extends CsvObject {
	
	private final Long testerId;
	private final Long deviceId;
	
	@Builder
	public TesterDevice(Long testerId, Long deviceId) {
		Assert.notNull(testerId, "Tester id cannot be null");
		Assert.notNull(deviceId, "Device id cannot be null");
		
		this.testerId = testerId;
		this.deviceId = deviceId;
	}
	
	@Override
	public CsvObjectType getType() {
		return CsvObjectType.TESTER_DEVICE;
	}
}
