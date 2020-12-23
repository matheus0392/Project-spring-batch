package mnascimento.api.Listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobNotificationListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(JobNotificationListener.class);

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOG.info("!!! JOB FINISHED! " + jobExecution.getJobConfigurationName());

			/*
			 * jdbcTemplate.query("SELECT first_name, last_name FROM people", (rs, row) ->
			 * new Person( rs.getString(1), rs.getString(2)) ).forEach(person ->
			 * log.info("Found <" + person + "> in the database."));
			 */
		}

	}
}