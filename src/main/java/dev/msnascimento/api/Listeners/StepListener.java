package dev.msnascimento.api.Listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepListener implements StepExecutionListener {

	private static final Logger LOG = LoggerFactory.getLogger(StepListener.class);

	@Override
	public void beforeStep(StepExecution stepExecution) {
		if (stepExecution.getStatus() == BatchStatus.STARTING) {
			LOG.info("!!! STEP STARTED " + stepExecution.getStepName());
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}

}
