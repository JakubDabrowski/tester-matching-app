package pl.applause.jakub.dabrowski.tester.matching.backend.model.device;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.applause.jakub.dabrowski.tester.matching.backend.enums.CsvObjectType;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.CsvObject;
import pl.applause.jakub.dabrowski.tester.matching.backend.model.tester.Tester;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "DEVICES")
public class Device extends CsvObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TESTERS_DEVICES", joinColumns = { @JoinColumn(name = "DEVICE_ID", nullable = false) }, inverseJoinColumns = {
		@JoinColumn(name = "TESTER_ID", nullable = false) })
	private Set<Tester> testers = new HashSet<>();
	
	@Override
	public CsvObjectType getType() {
		return CsvObjectType.DEVICE;
	}
}
