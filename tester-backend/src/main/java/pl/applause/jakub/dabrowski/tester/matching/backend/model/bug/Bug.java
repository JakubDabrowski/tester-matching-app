package pl.applause.jakub.dabrowski.tester.matching.backend.model.bug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.applause.jakub.dabrowski.tester.matching.backend.enums.CsvObjectType;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.CsvObject;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.device.Device;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.tester.Tester;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "BUGS")
public class Bug extends CsvObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	
	@OneToOne(targetEntity = Device.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "DEVICE_ID")
	private Device device;
	
	@OneToOne(targetEntity = Tester.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "TESTER_ID")
	private Tester tester;
	
	@Override
	public CsvObjectType getType() {
		return CsvObjectType.BUG;
	}
}
