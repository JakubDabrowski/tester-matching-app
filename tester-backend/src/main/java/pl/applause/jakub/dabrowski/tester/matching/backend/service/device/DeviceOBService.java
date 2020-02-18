package pl.applause.jakub.dabrowski.tester.matching.backend.service.device;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;
import pl.applause.jakub.dabrowski.tester.matching.backend.enums.CsvObjectType;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.CsvObject;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.device.Device;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.device.DeviceRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.reader.CsvObjectMapper;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.reader.CsvReaderService;

@Service
@RequiredArgsConstructor
public class DeviceOBService implements DeviceService {
	
	private static final String DEVICES_DATA_PATH = "data/devices.csv";
	
	private final DeviceRepository deviceRepository;
	private final CsvReaderService csvReaderService;
	private final CsvObjectMapper csvObjectMapper;
	
	@Override
	public List<pl.applause.jakub.dabrowski.tester.matching.backend.dto.device.Device> getAll() {
		//@formatter:off
		return IteratorUtils.toList(deviceRepository.findAll().iterator()).stream()
			.map(this::mapDevice)
			.collect(Collectors.toList());
		//@formatter:on
	}
	
	private pl.applause.jakub.dabrowski.tester.matching.backend.dto.device.Device mapDevice(Device device) {
		//@formatter:off
		return pl.applause.jakub.dabrowski.tester.matching.backend.dto.device.Device.builder()
			.id(device.getId())
			.description(device.getDescription())
			.build();
		//@formatter:on
	}
	
	/**
	 * Write devices data from resources(CSV file) to database.
	 * Should be used only to initialize data.
	 * Canceled when repository contains any data.
	 */
	public final void writeResourcesDataToDatabase(){
		if(deviceRepository.existsAny()){
			return;
		}
		
		List<List<String>> devicesData = csvReaderService.getFileData(DEVICES_DATA_PATH, true);
		List<? extends CsvObject> devices = csvObjectMapper.mapToCsvObjects(devicesData, CsvObjectType.DEVICE);
		
		devices.forEach(deviceData -> {
			if(deviceData instanceof Device){
				deviceRepository.save((Device) deviceData);
			}
		});
	}
}
