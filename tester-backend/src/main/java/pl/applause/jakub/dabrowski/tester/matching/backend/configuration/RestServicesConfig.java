package pl.applause.jakub.dabrowski.tester.matching.backend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.bug.BugRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.device.DeviceRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.tester.TesterRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.device.DeviceOBService;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.device.DeviceService;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.reader.CsvObjectMapper;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.reader.CsvReaderService;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.tester.TesterOBService;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.tester.TesterService;

@Configuration
@RequiredArgsConstructor
public class RestServicesConfig {
	
	private final DeviceRepository deviceRepository;
	private final TesterRepository testerRepository;
	private final BugRepository bugRepository;
	private final CsvReaderService csvReaderService;
	private final CsvObjectMapper csvObjectMapper;
	
	@Bean
	public DeviceService deviceService(){
		return new DeviceOBService(deviceRepository, csvReaderService, csvObjectMapper);
	}
	
	@Bean
	public TesterService testerService(){
		return new TesterOBService(testerRepository, deviceRepository, bugRepository, csvReaderService, csvObjectMapper);
	}
}
