package pl.applause.jakub.dabrowski.tester.matching.backend.service.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import pl.applause.jakub.dabrowski.tester.matching.backend.exception.csv.CsvReaderException;

@RunWith(MockitoJUnitRunner.class)
public class CsvReaderServiceTest {
	
	@InjectMocks
	private CsvReaderService underTest;
	
	private static final String BUGS_DATA_PATH = "data/bugs.csv";
	private static final String DEVICES_DATA_PATH = "data/devices.csv";
	private static final String TESTER_DEVICES_DATA_PATH = "data/tester_device.csv";
	private static final String TESTERS_DATA_PATH = "data/testers.csv";
	private static final String NOT_EXISTING_PATH = "data/not_existing.csv";
	
	@Test
	public void shouldReadBugsData(){
		List<List<String>> result = underTest.getFileData(BUGS_DATA_PATH, true);
		
		assertThat(result).isNotEmpty();
	}
	
	@Test
	public void shouldReadDevicesData(){
		List<List<String>> result = underTest.getFileData(DEVICES_DATA_PATH, true);
		
		assertThat(result).isNotEmpty();
	}
	
	@Test
	public void shouldReadTesterDevicesData(){
		List<List<String>> result = underTest.getFileData(TESTER_DEVICES_DATA_PATH, true);
		
		assertThat(result).isNotEmpty();
	}
	
	@Test
	public void shouldReadTestersData(){
		List<List<String>> result = underTest.getFileData(TESTERS_DATA_PATH, true);
		
		assertThat(result).isNotEmpty();
	}
	
	@Test(expected = CsvReaderException.class)
	public void shouldNotReadCsvDataWhenResourceNotFound(){
		underTest.getFileData(NOT_EXISTING_PATH, true);
	}
}