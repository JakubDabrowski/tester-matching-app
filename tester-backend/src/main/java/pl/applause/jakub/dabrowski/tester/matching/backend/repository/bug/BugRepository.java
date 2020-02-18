package pl.applause.jakub.dabrowski.tester.matching.backend.repository.bug;

import com.neovisionaries.i18n.CountryCode;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.bug.Bug;
import pl.applause.jakub.dabrowski.tester.matching.backend.repository.tester.projection.TesterProjection;

@Transactional(readOnly = true)
public interface BugRepository extends CrudRepository<Bug, Long> {
	
	@Query("SELECT count(b) > 0 FROM Bug b")
	boolean existsAny();
	
	@Query("SELECT DISTINCT t.id as id, t.firstName as firstName, t.lastName as lastName FROM Bug b join b.tester t join b.device d"
		+ " where d.id in :devices and t.country in :countries")
	Set<TesterProjection> findTestersByCountriesAndDevices(@Param("countries") List<CountryCode> countries, @Param("devices") List<Long> devices);
	
	@Query("SELECT DISTINCT t.id as id, t.firstName as firstName, t.lastName as lastName FROM Bug b join b.tester t join b.device d"
		+ " where t.country in :countries")
	Set<TesterProjection> findTestersByCountries(@Param("countries") List<CountryCode> countries);
	
	@Query("SELECT DISTINCT t.id as id, t.firstName as firstName, t.lastName as lastName FROM Bug b join b.tester t join b.device d"
		+ " where d.id in :devices")
	Set<TesterProjection> findTestersByDevices(@Param("devices") List<Long> devices);
	
	@Query("SELECT count(b) FROM Bug b join b.tester t"
		+ " where t.id = :testerId")
	Integer getTesterTotalExperience(@Param("testerId") Long testerId);
	
	@Query("SELECT count(b) FROM Bug b join b.tester t join b.device d"
		+ " where t.id = :testerId and d.id in :devices")
	Integer getTesterExperienceByDevices(@Param("testerId") Long testerId, @Param("devices") List<Long> devices);
	
	@Query("SELECT count(b) FROM Bug b join b.tester t join b.device d"
		+ " where t.id = :testerId and t.country in :countries")
	Integer getTesterExperienceByCountries(@Param("testerId") Long testerId, @Param("countries") List<CountryCode> countries);
	
	@Query("SELECT count(b) FROM Bug b join b.tester t join b.device d"
		+ " where t.id = :testerId and t.country in :countries and d.id in :devices")
	Integer getTesterExperienceByCountriesAndDevices(@Param("testerId") Long testerId, @Param("countries") List<CountryCode> countries, @Param("devices") List<Long> devices);
}
