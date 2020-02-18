package pl.applause.jakub.dabrowski.tester.matching.backend.service.bug;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.applause.jakub.dabrowski.tester.matching.backend.enums.CsvObjectType;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.CsvObject;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.bug.Bug;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.bug.BugRepository;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.reader.CsvObjectMapper;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.reader.CsvReaderService;

@Service
@RequiredArgsConstructor
public class BugOBService {
	
	private static final String BUGS_DATA_PATH = "data/bugs.csv";
	
	private final BugRepository bugRepository;
	private final CsvReaderService csvReaderService;
	private final CsvObjectMapper csvObjectMapper;
	
	/**
	 * Write bugs data from resources(CSV file) to database.
	 * Should be used only to initialize data.
	 * Canceled when repository contains any data.
	 */
	public final void writeResourcesDataToDatabase(){
		if(bugRepository.existsAny()){
			return;
		}
		
		List<List<String>> bugsData = csvReaderService.getFileData(BUGS_DATA_PATH, true);
		List<? extends CsvObject> bugs = csvObjectMapper.mapToCsvObjects(bugsData, CsvObjectType.BUG);
		
		bugs.forEach(bugData -> {
			if(bugData instanceof Bug){
				bugRepository.save((Bug) bugData);
			}
		});
	}
}
