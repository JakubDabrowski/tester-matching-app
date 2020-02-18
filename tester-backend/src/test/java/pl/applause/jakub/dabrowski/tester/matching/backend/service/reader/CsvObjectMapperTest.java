package pl.applause.jakub.dabrowski.tester.matching.backend.service.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.TesterDevice;
import pl.applause.jakub.dabrowski.tester.matching.backend.enums.CsvObjectType;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.CsvObject;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.bug.Bug;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.device.Device;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.tester.Tester;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.device.DeviceRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.tester.TesterRepository;

@RunWith(MockitoJUnitRunner.class)
public class CsvObjectMapperTest {
	
	@InjectMocks
	private CsvObjectMapper underTest;
	
	@Mock
	private DeviceRepository deviceRepository;
	
	@Mock
	private TesterRepository testerRepository;
	
	@Test
	public void shouldMapBugsData(){
		List<List<String>> bugsData = createBugsData();
		
		given(deviceRepository.findById(any(Long.class))).willReturn(Optional.of(Device.builder().build()));
		given(testerRepository.findById(any(Long.class))).willReturn(Optional.of(Tester.builder().build()));
		
		List<? extends CsvObject> result = underTest.mapToCsvObjects(bugsData, CsvObjectType.BUG);
		
		assertThat(result).isNotEmpty();
		result.forEach(bug -> assertEquals(bug instanceof Bug, true));
	}
	
	private List<List<String>> createBugsData(){
		List<List<String>> result = new LinkedList<>();
		
		result.add(createData("1", "1", "1"));
		result.add(createData("2", "3", "5"));
		result.add(createData("3", "5", "2"));
		
		return result;
	}
	
	@Test
	public void shouldMapDevicesData(){
		List<List<String>> devicesData = createDevicesData();
		
		List<? extends CsvObject> result = underTest.mapToCsvObjects(devicesData, CsvObjectType.DEVICE);
		
		assertThat(result).isNotEmpty();
		result.forEach(device -> assertEquals(device instanceof Device, true));
	}
	
	private List<List<String>> createDevicesData(){
		List<List<String>> result = new LinkedList<>();
		
		result.add(createData("1", "iPhone 4"));
		result.add(createData("2", "iPhone 4S"));
		
		return result;
	}
	
	@Test
	public void shouldMapTestersData() {
		List<List<String>> testersData = createTestersData();
		List<? extends CsvObject> result = underTest.mapToCsvObjects(testersData, CsvObjectType.TESTER);
		
		assertThat(result).isNotEmpty();
		result.forEach(csvObject -> {
			assertEquals(true, csvObject instanceof Tester);
			Tester tester = (Tester) csvObject;
			assertNotNull(tester.getId());
			assertNotNull(tester.getFirstName());
			assertNotNull(tester.getLastName());
			assertNotNull(tester.getCountry());
			assertNotNull(tester.getLastLogin());
		});
	}
	
	private List<List<String>> createTestersData(){
		List<List<String>> result = new LinkedList<>();

		result.add(createData("1", "Miguel", "Beutista", "US", "2013-08-04 23:57:38"));
		result.add(createData("2", "Michael", "Lubavin", "US", "2013-07-12 13:27:18"));
		result.add(createData("3", "Darshini", "Thiagarajan", "GB", "2013-08-05 15:00:38"));
		
		return result;
	}
	
	@Test
	public void shouldMapTesterDevicesData(){
		List<List<String>> testerDevicesData = createTesterDevicesData();
		
		List<? extends CsvObject> result = underTest.mapToCsvObjects(testerDevicesData, CsvObjectType.TESTER_DEVICE);
		
		assertThat(result).isNotEmpty();
		result.forEach(device -> assertEquals(device instanceof TesterDevice, true));
	}
	
	private List<List<String>> createTesterDevicesData(){
		List<List<String>> result = new LinkedList<>();
		
		result.add(createData("1", "1"));
		result.add(createData("1", "2"));
		result.add(createData("1", "6"));
		result.add(createData("2", "8"));
		
		return result;
	}
	
	private List<String> createData(String... objectData){
		List<String> result = new LinkedList<>();
		
		Collections.addAll(result, objectData);
		
		return result;
	}
}