package mnascimento.api.Controllers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mnascimento.api.Domains.Info;
import mnascimento.api.Service.InfoService;

/**
 * @author Matheus
 *
 */

@RestController
public class InfoController {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public JobRepository jobRepository;

	@Autowired
	public InfoService infoService;

	@Autowired
	public JobLauncher jobLauncher;

	@Autowired
	public Step infoItemStep;

	@Autowired
	public Job infoJob;


	@GetMapping("/")
	public String Base() {
		return "API running... nothing to show here";
	}

	/**
	 * get all Infos
	 *
	 * @return  List<Info>
	 */
	@GetMapping("/getall")
	public List<Info> GetAll() {
		return infoService.GetAll();
	}

	/**
	 * save new Info
	 *
	 * @param Info
	 * @return Info
	 */
	@PostMapping("/save")
	public Info Save(@RequestBody  Info info) throws Exception {
		return infoService.save(info);
	}

	/**
	 * get Info with this CPF
	 *
	 * @param String CPF
	 * @return Info
	 */
	@GetMapping("/getbycpf")
	public Info Get(@RequestParam String CPF) {
		return infoService.get(CPF);
	}

	/**
	 * execute a job
	 *
	 * @return console PrintStream as html result
	 * @throws JobExecutionAlreadyRunningException
	 * @throws JobRestartException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws JobParametersInvalidException
	 */
	@GetMapping("/execjob")
	public String ExecJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);


		JobParametersBuilder params = new JobParametersBuilder();
		params.addDate("runTime", new Date());
		this.jobLauncher.run(infoJob, params.toJobParameters());


		// Put things back
		System.out.flush();
		System.setOut(old);
		// Show what happened
		System.out.println(baos.toString());

		return "<p>" + baos.toString().replaceAll("\\n", "</p><p>") + "</p>";
	}

}
