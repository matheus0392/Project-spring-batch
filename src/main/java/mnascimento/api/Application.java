package mnascimento.api;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class Application {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public JobExecutionDecider decider() {
		return new DeliveryDecider();
	}

	@Bean
	public Step leaveAtDoorStep() {
		return this.stepBuilderFactory.get("leaveAtDoorStep").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Leaving the package at the door.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step storePackageStep() {
		return this.stepBuilderFactory.get("storePackageStep").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Storing the package.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step givePackeageToCostumerStep() {
		return this.stepBuilderFactory.get("givePackeageToCostumerStep").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Given the packeage to  the Costumer.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step driveToAddressStep() {
		final boolean GOT_LOST = false;
		return this.stepBuilderFactory.get("driveToAddressStep").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				if (GOT_LOST) {
					throw new Exception("Got lost driving to the address.");
				}
				System.out.println("Sucessfully arrived to address.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step packageItemStep() {
		return this.stepBuilderFactory.get("packageItemStep").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("The item has been packaged.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Job deliverPackageJob() {
		return this.jobBuilderFactory
				.get("deliverPackageJob")
				.start(packageItemStep())
				.next(driveToAddressStep())
					.on("FAILED").to(storePackageStep())
				.from(driveToAddressStep())
					.on("*").to(decider())
						.on("PRESENT").to(givePackeageToCostumerStep())
					.from(decider())
						.on("NOT_PRESENT").to(leaveAtDoorStep())
				.end()
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("application started");
	}
}
