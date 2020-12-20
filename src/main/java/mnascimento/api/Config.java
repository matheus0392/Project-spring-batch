package mnascimento.api;

import org.hibernate.SessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hibernate-config")
public class Config {

	@Bean
	public SessionFactory setup() {
		System.out.println("hibernateConfig");
		org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory factory = config.buildSessionFactory();

		return factory;
	}
}