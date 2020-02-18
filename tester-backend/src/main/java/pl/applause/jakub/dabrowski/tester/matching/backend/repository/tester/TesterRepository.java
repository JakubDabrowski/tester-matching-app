package pl.applause.jakub.dabrowski.tester.matching.backend.repository.tester;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.tester.Tester;

@Transactional(readOnly = true)
public interface TesterRepository extends CrudRepository<Tester, Long> {
	
	@Query("SELECT count(t) > 0 FROM Tester t")
	boolean existsAny();
}
