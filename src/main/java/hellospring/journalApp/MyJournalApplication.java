package hellospring.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"abcd","hellospring.journalApp"})
@EnableTransactionManagement
public class MyJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyJournalApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager fallana(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
}
