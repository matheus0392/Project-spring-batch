package mnascimento.api.Controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mnascimento.api.Domains.Info;

/**
 * @author Matheus
 *
 */
@RestController
public class InfoController {

	@GetMapping("/start")
	public Info Start() {
		return new Info((long) 1, "Jhon", "12345678901");
	}

	@GetMapping("/exec")
	public void Exec() {

	}

	@GetMapping("/")
	public String Base() {
		return "Nothing to show";
	}

}
