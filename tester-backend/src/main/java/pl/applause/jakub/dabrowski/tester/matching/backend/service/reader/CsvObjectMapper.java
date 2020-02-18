package pl.applause.jakub.dabrowski.tester.matching.backend.service.reader;

import com.neovisionaries.i18n.CountryCode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.TesterDevice;
import pl.applause.jakub.dabrowski.tester.matching.backend.enums.CsvObjectType;
import pl.applause.jakub.dabrowski.tester.matching.backend.exception.csv.CsvMapperException;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.CsvObject;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.bug.Bug;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.device.Device;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.tester.Tester;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.device.DeviceRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.tester.TesterRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class CsvObjectMapper {
	
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final Integer BUG_NUMBER_OF_FIELDS = 3;
	private static final Integer DEVICE_NUMBER_OF_FIELDS = 2;
	private static final Integer TESTER_NUMBER_OF_FIELDS = 5;
	private static final Integer TESTER_DEVICE_NUMBER_OF_FIELDS = 2;
	
	private final DeviceRepository deviceRepository;
	private final TesterRepository testerRepository;
	
	public List<? extends CsvObject> mapToCsvObjects(final List<List<String>> data, final CsvObjectType type){
		switch (type){
			case BUG:
				return mapToBug(data);
			
			case DEVICE:
				return mapToDevice(data);
			
			case TESTER:
				return mapToTester(data);
			
			case TESTER_DEVICE:
				return mapToTesterDevice(data);
				
			default:
				throw new CsvMapperException("Csv object type " + type + " not handled.");
		}
	}
	
	private List<Bug> mapToBug(final List<List<String>> data){
		List<Bug> bugs = new LinkedList<>();
		
		if(data == null) {
			return bugs;
		}
		
		data.forEach(bugData -> {
			if(bugData.size() == BUG_NUMBER_OF_FIELDS) {
				Optional<Device> device = deviceRepository.findById(Long.parseLong(bugData.get(1)));
				Optional<Tester> tester = testerRepository.findById(Long.parseLong(bugData.get(2)));
				
				if(device.isPresent() && tester.isPresent()){
					// @formatter:off
					bugs.add(Bug.builder()
						.id(Long.parseLong(bugData.get(0)))
						.device(device.get())
						.tester(tester.get())
						.build());
					// @formatter:on
				}
			}
		});
		
		return bugs;
	}
	
	private List<Device> mapToDevice(final List<List<String>> data){
		List<Device> devices = new LinkedList<>();
		
		if(data == null) {
			return devices;
		}
		
		data.forEach(deviceData -> {
			if(deviceData.size() == DEVICE_NUMBER_OF_FIELDS) {
				// @formatter:off
				devices.add(Device.builder()
					.id(Long.parseLong(deviceData.get(0)))
					.description(deviceData.get(1))
					.build());
				// @formatter:on
			}
		});
		
		return devices;
	}
	
	private List<Tester> mapToTester(final List<List<String>> data){
		List<Tester> testers = new LinkedList<>();
		
		if(data == null) {
			return testers;
		}
		
		data.forEach(testerData -> {
			if(testerData.size() == TESTER_NUMBER_OF_FIELDS) {
				// @formatter:off
				testers.add(Tester.builder()
					.id(Long.parseLong(testerData.get(0)))
					.firstName(testerData.get(1))
					.lastName(testerData.get(2))
					.country(CountryCode.valueOf(testerData.get(3)))
					.lastLogin(LocalDateTime.parse(testerData.get(4), DATE_TIME_FORMATTER))
					.build());
				// @formatter:on
			}
		});
		
		return testers;
	}
	
	private List<TesterDevice> mapToTesterDevice(final List<List<String>> data){
		List<TesterDevice> testerDevices = new LinkedList<>();
		
		if(data == null) {
			return testerDevices;
		}
		
		data.forEach(testerDeviceData -> {
			if(testerDeviceData.size() == TESTER_DEVICE_NUMBER_OF_FIELDS) {
				// @formatter:off
				testerDevices.add(TesterDevice.builder()
					.testerId(Long.parseLong(testerDeviceData.get(0)))
					.deviceId(Long.parseLong(testerDeviceData.get(1)))
					.build());
				// @formatter:on
			}
		});
		
		return testerDevices;
	}
}
