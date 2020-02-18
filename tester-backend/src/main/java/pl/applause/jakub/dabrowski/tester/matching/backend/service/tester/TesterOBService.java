package pl.applause.jakub.dabrowski.tester.matching.backend.service.tester;

import com.neovisionaries.i18n.CountryCode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;
import pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.TesterDevice;
import pl.applause.jakub.dabrowski.tester.matching.backend.enums.CsvObjectType;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.CsvObject;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.device.Device;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.tester.Tester;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.bug.BugRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.device.DeviceRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.tester.TesterRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.tester.projection.TesterProjection;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.reader.CsvObjectMapper;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.reader.CsvReaderService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TesterOBService implements TesterService {
	
	private static final String TESTERS_DATA_PATH = "data/testers.csv";
	private static final String TESTER_DEVICE_DATA_PATH = "data/tester_device.csv";
	
	private final TesterRepository testerRepository;
	private final DeviceRepository deviceRepository;
	private final BugRepository bugRepository;
	private final CsvReaderService csvReaderService;
	private final CsvObjectMapper csvObjectMapper;
	
	@Override
	public List<pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester> getAllTesters() {
		// @formatter:off
		return IteratorUtils.toList(testerRepository.findAll().iterator()).stream()
			.map(this::mapTester)
			.sorted(experienceComparator)
			.collect(Collectors.toList());
		// @formatter:on
	}
	
	private pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester mapTester(final Tester tester){
		// @formatter:off
		return pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester.builder()
			.name(tester.getName())
			.experience(bugRepository.getTesterTotalExperience(tester.getId()))
			.build();
		// @formatter:on
	}
	
	@Override
	public List<pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester> getTesters(final List<CountryCode> countries, final List<Long> devices) {
		if(countries.isEmpty() && devices.isEmpty()) {
			return getAllTesters();
		}
		
		final Set<TesterProjection> testers = getTesterProjections(countries, devices);
		
		// @formatter:off
		return testers.stream()
			.map(testerProjection -> mapTester(testerProjection, countries, devices))
			.sorted(experienceComparator)
			.collect(Collectors.toList());
		// @formatter:on
	}
	
	private Set<TesterProjection> getTesterProjections(final List<CountryCode> countries, final List<Long> devices) {
		if(countries.isEmpty()){
			return bugRepository.findTestersByDevices(devices);
			
		} else if(devices.isEmpty()) {
			return bugRepository.findTestersByCountries(countries);
			
		} else {
			return bugRepository.findTestersByCountriesAndDevices(countries, devices);
		}
	}
	
	private pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester mapTester(final TesterProjection testerProj, final List<CountryCode> countries, final List<Long> devices){
		// @formatter:off
		return pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester.builder()
			.name(new StringBuilder().append(testerProj.getFirstName()).append(" ").append(testerProj.getLastName()).toString())
			.experience(getTesterExperience(testerProj.getId(), countries, devices))
			.build();
		// @formatter:on
	}
	
	private Integer getTesterExperience(final Long testerId, final List<CountryCode> countries, final List<Long> devices){
		if(countries.isEmpty() && devices.isEmpty()){
			return bugRepository.getTesterTotalExperience(testerId);
			
		} else if(countries.isEmpty() && !devices.isEmpty()){
			return bugRepository.getTesterExperienceByDevices(testerId, devices);
			
		} else if(!countries.isEmpty() && devices.isEmpty()){
			return bugRepository.getTesterExperienceByCountries(testerId, countries);
			
		} else {
			return bugRepository.getTesterExperienceByCountriesAndDevices(testerId, countries, devices);
		}
	}
	
	private Comparator<pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester> experienceComparator = (tester1, tester2) -> tester2.getExperience().compareTo(tester1.getExperience());
	
	/**
	 * Write testers data from resources(CSV file) to database.
	 * Should be used only to initialize data.
	 * Canceled when repository contains any data.
	 */
	public final void writeTestersResourcesDataToDatabase(){
		if(testerRepository.existsAny()){
			return;
		}
		
		List<List<String>> testersData = csvReaderService.getFileData(TESTERS_DATA_PATH, true);
		List<? extends CsvObject> testers = csvObjectMapper.mapToCsvObjects(testersData, CsvObjectType.TESTER);
		
		testers.forEach(testerData -> {
			if(testerData instanceof Tester){
				testerRepository.save((Tester) testerData);
			}
		});
	}
	
	/**
	 * Write tester device data from resources(CSV file) to database.
	 * Should be used only to initialize data.
	 * Canceled when repository contains any data.
	 */
	public final void writeTesterDeviceResourcesDataToDatabase(){
		List<List<String>> testersDevicesData = csvReaderService.getFileData(TESTER_DEVICE_DATA_PATH, true);
		List<? extends CsvObject> testerDevices = csvObjectMapper.mapToCsvObjects(testersDevicesData, CsvObjectType.TESTER_DEVICE);
		
		testerDevices.forEach(testerDeviceData -> {
			if(testerDeviceData instanceof TesterDevice){
				TesterDevice testerDevice = (TesterDevice) testerDeviceData;
				Optional<Tester> testerOptional = testerRepository.findById(testerDevice.getTesterId());
				Optional<Device> deviceOptional = deviceRepository.findById(testerDevice.getDeviceId());
				
				if(testerOptional.isPresent() && deviceOptional.isPresent()){
					Tester tester = testerOptional.get();
					Device device = deviceOptional.get();
					
					tester.getDevices().add(device);
					device.getTesters().add(tester);
					
					testerRepository.save(tester);
				}
			}
		});
	}
}
