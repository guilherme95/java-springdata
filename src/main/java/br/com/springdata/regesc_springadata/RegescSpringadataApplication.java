package br.com.springdata.regesc_springadata;

import br.com.springdata.regesc_springadata.orm.Professor;
import br.com.springdata.regesc_springadata.repository.ProfessorRepository;
import br.com.springdata.regesc_springadata.service.CrudProfessorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RegescSpringadataApplication implements CommandLineRunner {
	private CrudProfessorService professorService;

	// os obbjetos passados por parametros sao injetados automaticamente pelo Spring
	// pq suas classes possuem anotacao @Service
	public RegescSpringadataApplication(CrudProfessorService professorService){
		this.professorService = professorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RegescSpringadataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Boolean isTrue = true;
		Scanner scanner = new Scanner(System.in);

		while (isTrue){
			System.out.println("Qual entidade você deseja interagir?");
			System.out.println("0 - Sair");
			System.out.println("1 - Professor");

			int opcao = scanner.nextInt();

			switch (opcao){
				case 1:
					this.professorService.menu(scanner);
					break;

				default:
					isTrue = false;
					break;
			}
		}
	}
}
