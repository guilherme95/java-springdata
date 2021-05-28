package br.com.springdata.regesc_springadata;

import br.com.springdata.regesc_springadata.orm.Professor;
import br.com.springdata.regesc_springadata.repository.ProfessorRepository;
import br.com.springdata.regesc_springadata.service.CrudDisciplinaService;
import br.com.springdata.regesc_springadata.service.CrudProfessorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RegescSpringadataApplication implements CommandLineRunner {
	private CrudProfessorService professorService;
	private CrudDisciplinaService disciplinaService;

	// os obbjetos passados por parametros sao injetados automaticamente pelo Spring
	// pq suas classes possuem anotacao @Service
	public RegescSpringadataApplication(CrudProfessorService professorService, CrudDisciplinaService disciplinaService){
		this.professorService = professorService;
		this.disciplinaService = disciplinaService;
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
			System.out.println("2 - Disciplina");
			System.out.print("Opção: ");

			int opcao = scanner.nextInt();

			switch (opcao){
				case 1:
					this.professorService.menu(scanner);
					break;

				case 2:
					this.disciplinaService.menu(scanner);
					break;

				default:
					isTrue = false;
					break;
			}
		}
	}
}
