package mnascimento.api.Controllers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mnascimento.api.Domains.Info;
import mnascimento.api.Service.InfoService;

@RestController
public class InfoController {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public JobRepository jobRepository;

	@Autowired
	public InfoService infoService;

	@Autowired
	public JobExecutionListenerSupport JobListener;

	@Autowired
	public Step infoItemStep;

	@GetMapping("/getall")
	public ArrayList<Info> Start() {
		return infoService.GetAll();
	}

	/**
	 * execute a job
	 *
	 * @return console PrintStream as html
	 * @throws JobExecutionAlreadyRunningException
	 * @throws JobRestartException
	 * @throws JobInstanceAlreadyCompleteException
	 */
	@GetMapping("/exec")
	public String Exec()
			throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);

		String job = "importUserJob";

		JobExecution jobExecution = jobRepository.createJobExecution(job,
				new JobParametersBuilder().addDate("data", new Date()).toJobParameters());

		jobBuilderFactory.get(job).listener(JobListener).start(infoItemStep).build().execute(jobExecution);

		// Put things back
		System.out.flush();
		System.setOut(old);
		// Show what happened
		System.out.println(baos.toString());

		return "<p>" + baos.toString().replaceAll("\\n", "</p><p>") + "</p>";
	}

	@GetMapping("/")
	public String Base() {
		return "Nothing to show";
	}

}
