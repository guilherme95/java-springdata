package br.com.springdata.regesc_springadata.service;

import br.com.springdata.regesc_springadata.orm.Professor;
import br.com.springdata.regesc_springadata.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudProfessorService {

    private ProfessorRepository professorRepository; //dependecia da classe CrudProfessorService

    // o Spring automaticamente cria um objeto com a interface 'ProfessorRepository',
    // e o injeta para nós no construtor da classe atual ==> Injeçao de dependencia
    public CrudProfessorService(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
    }

    public void menu(Scanner scanner){
        Boolean isTrue = true;

        while(isTrue){
            System.out.println("\nQual ação você quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar novo Professor");
            System.out.println("2 - Atualizar um Professor");

            int opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    this.cadastrar(scanner);
                    break;

                case 2:
                    this.atualizar(scanner);
                    break;

                default:
                    isTrue = false;
                    break;
            }
        }
        System.out.println();
    }

    private void cadastrar(Scanner scanner){
        System.out.print("Digite o nome do professor: ");
        String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

        System.out.print("Digite o prontuario do professor: ");
        String prontuario = scanner.next(); //le a proxima string até achar um enter ou um espaco

        Professor professor = new Professor(nome, prontuario);
        this.professorRepository.save(professor);
        System.out.println("Professor salvo no banco!!!\n");
    }

    private void atualizar(Scanner scanner){
        System.out.print("Digite o id do Professor a ser atualizado: ");
        Long id = scanner.nextLong();

        Optional<Professor> optional = this.professorRepository.findById(id);

        if(optional.isPresent()){

            System.out.print("Digite o nome do professor: ");
            String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

            System.out.print("Digite o prontuario do professor: ");
            String prontuario = scanner.next(); //le a proxima string até achar um enter ou um espaco

            Professor professor = optional.get();
            professor.setNome(nome);
            professor.setProntuario(prontuario);
            professorRepository.save(professor); // atualiza(persiste) o objeto/registro/tupla no BD
            System.out.println("Professor atualizado com sucesso!\n");

        }else{
            System.out.println("O id do professor informado: "+id+" é inválido!\n");
        }
    }

    private void atualizarSemFindById(Scanner scanner){
        System.out.print("Digite o id do Professor a ser atualizado: ");
        Long id = scanner.nextLong();

        System.out.print("Digite o nome do professor: ");
        String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

        System.out.print("Digite o prontuario do professor: ");
        String prontuario = scanner.next(); //le a proxima string até achar um enter ou um espaco

        Professor professor = new Professor();
        professor.setId(id);
        professor.setNome(nome);
        professor.setProntuario(prontuario);

        professorRepository.save(professor); // atualiza(persiste) o objeto/registro/tupla no BD
        System.out.println("Professor atualizado com sucesso!\n");
    }
}