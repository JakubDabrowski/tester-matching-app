package pl.applause.jakub.dabrowski.tester.matching.backend.service.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.applause.jakub.dabrowski.tester.matching.backend.exception.csv.CsvReaderException;
import pl.applause.jakub.dabrowski.tester.matching.backend.exception.csv.CsvReaderException.ExceptionType;

@Service
@Slf4j
public class CsvReaderService {
	
	public List<List<String>> getFileData(final String resourceName, final Boolean skipFirstLine) {
		List<List<String>> records = new LinkedList<>();
		final String filePath = getFilePath(resourceName);
		if(filePath == null){
			throw new CsvReaderException("Could not find filepath for resourceName: " + resourceName, ExceptionType.FILE_PATH_NOT_FOUND);
		}

		try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
			addRecordsData(skipFirstLine, records, csvReader);
		} catch (FileNotFoundException e) {
			log.info("File not found: ", e);
			throw new CsvReaderException("Could not find file for path: " + filePath, ExceptionType.FILE_NOT_FOUND);
			
		} catch (IOException | CsvValidationException e) {
			log.info("Cannot fetch testers data due to ", e);
			throw new CsvReaderException("Could fetch testers data for file in path: " + filePath, ExceptionType.FETCH_ERROR);
		}
		
		return records;
	}
	
	private String getFilePath(final String resourceName){
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourceUrl = classLoader.getResource(resourceName);
		if(resourceUrl == null){
			return null;
		}
		
		return new File(resourceUrl.getFile()).getAbsolutePath();
	}
	
	private void addRecordsData(final Boolean skipFirstLine, final List<List<String>> records, final CSVReader csvReader) throws IOException, CsvValidationException {
		String[] values;
		if(skipFirstLine) {
			csvReader.readNext();
		}
		
		while ((values = csvReader.readNext()) != null) {
			if(values.length == 1 || values[0].isEmpty()){
				continue;
			}
			
			records.add(Arrays.asList(values));
		}
	}
}
