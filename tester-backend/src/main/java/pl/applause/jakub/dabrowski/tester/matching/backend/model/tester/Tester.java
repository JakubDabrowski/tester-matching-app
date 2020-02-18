package pl.applause.jakub.dabrowski.tester.matching.backend.model.tester;

import com.neovisionaries.i18n.CountryCode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import pl.applause.jakub.dabrowski.tester.matching.backend.model.device.Device;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false, exclude = { "devices" })
@Entity
@Table(name = "TESTERS")
public class Tester extends CsvObject {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	
	@NotNull
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@NotNull
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "COUNTRY")
	private CountryCode country;
	
	@NotNull
	@Column(name = "LAST_LOGIN")
	private LocalDateTime lastLogin;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "TESTERS_DEVICES", joinColumns = { @JoinColumn(name = "TESTER_ID", nullable = false) }, inverseJoinColumns = {
		@JoinColumn(name = "DEVICE_ID", nullable = false) })
	private Set<Device> devices = new HashSet<>();
	
	public String getName(){
		return new StringBuilder().append(firstName).append(" ").append(lastName).toString();
	}
	
	@Override
	public CsvObjectType getType() {
		return CsvObjectType.TESTER;
	}
}
