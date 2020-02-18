package pl.applause.jakub.dabrowski.tester.matching.backend.controller.device;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.applause.jakub.dabrowski.tester.matching.backend.constant.RestConstants;
import pl.applause.jakub.dabrowski.tester.matching.backend.dto.device.Device;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.device.DeviceService;

@RestController
@RequestMapping(RestConstants.DEVICE_API)
@RequiredArgsConstructor
public class DeviceController {
	
	private final DeviceService deviceService;
	
	@GetMapping
	@ResponseBody
	public List<Device> getAllDevices() {
		return deviceService.getAll();
	}
}
