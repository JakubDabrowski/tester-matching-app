package pl.applause.jakub.dabrowski.tester.matching.backend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.bug.BugOBService;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.device.DeviceOBService;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.tester.TesterOBService;

@Component
@Profile("init")
@RequiredArgsConstructor
public class DataInitializer implements InitializingBean {
	
	private final TesterOBService testerService;
	private final BugOBService bugService;
	private final DeviceOBService deviceService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//DO NOT CHANGE ORDER OF THE FOLLOWING
		deviceService.writeResourcesDataToDatabase();
		testerService.writeTestersResourcesDataToDatabase();
		testerService.writeTesterDeviceResourcesDataToDatabase();
		bugService.writeResourcesDataToDatabase();
	}
}
