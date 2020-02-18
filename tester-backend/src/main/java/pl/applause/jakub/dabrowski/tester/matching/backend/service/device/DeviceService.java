package pl.applause.jakub.dabrowski.tester.matching.backend.service.device;

import java.util.List;
import pl.applause.jakub.dabrowski.tester.matching.backend.dto.device.Device;

public interface DeviceService {
	
	List<Device> getAll();
}
