package pl.applause.jakub.dabrowski.tester.matching.backend.service.tester;

import com.neovisionaries.i18n.CountryCode;
import java.util.List;
import pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester;

public interface TesterService {
	
	List<Tester> getAllTesters();
	
	List<Tester> getTesters(List<CountryCode> countries, List<Long> devices);
}
