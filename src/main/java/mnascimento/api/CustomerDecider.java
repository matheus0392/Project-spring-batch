package mnascimento.api;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class CustomerDecider implements JobExecutionDecider {

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

		int chose = (int) (Math.random() * 10);
		String result = (chose % 10) > 3 ? "CORRECT" : "NOT_CORRECT";
		System.out.println("Decider result is: " + result + " chose: " + chose);
		return new FlowExecutionStatus(result);
	}
}
