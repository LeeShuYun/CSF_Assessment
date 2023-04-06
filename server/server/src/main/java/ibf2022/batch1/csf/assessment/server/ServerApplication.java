package ibf2022.batch1.csf.assessment.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf2022.batch1.csf.assessment.server.repositories.MovieRepository;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired
	MovieRepository movieRepo;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int count = movieRepo.countComments("godfather");
		System.out.println("cmdLineRunner test count>>" + count);
	}
}
