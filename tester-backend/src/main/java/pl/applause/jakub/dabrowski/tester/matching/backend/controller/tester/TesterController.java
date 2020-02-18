package pl.applause.jakub.dabrowski.tester.matching.backend.controller.tester;

import com.neovisionaries.i18n.CountryCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.applause.jakub.dabrowski.tester.matching.backend.constant.RestConstants;
import pl.applause.jakub.dabrowski.tester.matching.backend.dto.tester.Tester;
import pl.applause.jakub.dabrowski.tester.matching.backend.service.tester.TesterService;

@RestController
@RequestMapping(RestConstants.TESTER_API)
@RequiredArgsConstructor
public class TesterController {
	
	private final TesterService testerService;
	
	@GetMapping
	@ResponseBody
	public List<Tester> getAllTesters() {
		return testerService.getAllTesters();
	}
	
	@GetMapping("/search")
	@ResponseBody
	public List<Tester> getTesters(@RequestParam(value = "country", required = false, defaultValue = "") List<CountryCode> countries,
									@RequestParam(value = "device", required = false, defaultValue = "") List<Long> devices) {
		return testerService.getTesters(countries, devices);
	}
}
