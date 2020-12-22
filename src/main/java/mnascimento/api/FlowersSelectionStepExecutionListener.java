package mnascimento.api;

import java.io.Serializable;
import java.util.Properties;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.Metric;
import javax.batch.runtime.context.StepContext;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * @author Matheus
 *
 */
public class FlowersSelectionStepExecutionListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {

		System.out.println("Executing before step logic");

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("Executing after after logic");

		String flowerType=stepExecution.getJobParameters().getString("type");
		return flowerType != null ?  flowerType.equalsIgnoreCase("roses")? new ExitStatus("TRIM_REQUIRED"):
			 new ExitStatus("NO_TRIM_REQUIRED"): new ExitStatus("NO_TRIM_REQUIRED");
		}

}
