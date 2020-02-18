package pl.applause.jakub.dabrowski.tester.matching.backend.repository.device;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.device.Device;

@Transactional(readOnly = true)
public interface DeviceRepository extends CrudRepository<Device, Long> {
	
	@Query("SELECT count(d) > 0 FROM Device d")
	boolean existsAny();
}
