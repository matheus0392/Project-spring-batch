package dev.msnascimento.api;



import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import dev.msnascimento.api.Listeners.StepListener;
import dev.msnascimento.api.Domains.Info;
import dev.msnascimento.api.Listeners.JobNotificationListener;
import dev.msnascimento.api.Processors.InfoItemProcessor;

/**
 * BatchConfig: class to define a reader, a processor, and a writer:
 * @EnableBatchProcessing annotation adds many critical beans that support jobs and save you a lot of leg work.
 *		It also autowires a couple factories needed further below
 */
@Configuration
public class ItemBatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	/**
	 *  creates an instance of the InfoItemProcessor that you defined earlier, meant to convert the data
	 *
	 * @return InfoItemProcessor
	 */
	@Bean
	public InfoItemProcessor infoItemProcessor() {
	  return new InfoItemProcessor();
	}

	@Bean
	public JobExecutionListenerSupport JobListener() {
	  return new JobNotificationListener();
	}

	@Bean
	public StepExecutionListener StepListener() {
		return new StepListener();
	}

	/**
	 * creates an ItemReader
	 *
	 * @return  FlatFileItemReader
	 */
	@Bean
	public FlatFileItemReader<Info> infoItemReader() {
	  return new FlatFileItemReaderBuilder<Info>()
	    .name("infoItemReader")
	    .resource(new ClassPathResource("f1.txt"))
	    .delimited()
	    .names(new String[]{"id", "name", "CPF"})
	    .fieldSetMapper(new BeanWrapperFieldSetMapper<Info>() {{
	      setTargetType(Info.class);
	    }})
	    .build();
	}


	/**
	 * creates an ItemWriter
	 *
	 * @return HibernateItemWriter
	 */
	@Bean
	public JdbcBatchItemWriter<Info> infoItemWriter() {
	  return new JdbcBatchItemWriterBuilder<Info>()
	    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	    .sql("INSERT INTO Info (id, name, CPF) VALUES (:id, :name, :CPF)")
	    .dataSource(dataSource)
	    .build();
	}


	/**
	 * definition of a single step
	 *
	 * @return Step
	 */
	@Bean
	public Step infoItemStep() {
		return stepBuilderFactory.get("infoItemStep")
			 .<Info, Info> chunk(10)
			 .reader(infoItemReader())
			 .processor(infoItemProcessor())
			 .writer(infoItemWriter())
			 .listener(StepListener())
			 .build();
	}

	/**
	 * definition of a job
	 *
	 * @return Job
	 */
	@Bean
	public Job infoJob() {
		return jobBuilderFactory.get("infoJob")
				.listener(JobListener())
				.start(infoItemStep())
				.build();
	}

}