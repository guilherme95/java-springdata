package br.com.springdata.regesc_springadata;

import br.com.springdata.regesc_springadata.orm.Professor;
import br.com.springdata.regesc_springadata.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegescSpringadataApplication implements CommandLineRunner {
	private ProfessorRepository repository;

	public RegescSpringadataApplication(ProfessorRepository repository){
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RegescSpringadataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Criando objeto");
		Professor professor = new Professor("Guilherme", "xyz");
		System.out.println("Salvando no banco");
		this.repository.save(professor);
	}
}
