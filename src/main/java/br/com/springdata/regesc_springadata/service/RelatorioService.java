package br.com.springdata.regesc_springadata.service;

import br.com.springdata.regesc_springadata.orm.Aluno;
import br.com.springdata.regesc_springadata.orm.Professor;
import br.com.springdata.regesc_springadata.repository.AlunoRepository;
import br.com.springdata.regesc_springadata.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {
    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;

    public RelatorioService(AlunoRepository alunoRepository, ProfessorRepository professorRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }

    public void menu(Scanner scanner){
        Boolean isTrue = true;

        while(isTrue){
            System.out.println("\nQual relatorio voce deseja?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Alunos por um dado Nome");
            System.out.println("2 - Alunos por um dado Nome e Idade menor ou igual");
            System.out.println("3 - Alunos por um dado Nome e Idade maior ou igual JPQL");
            System.out.println("4 - Alunos Matriculados com um dado Nome e Idade maior ou igual JPQL");
            System.out.println("5 - Professores atribuídos SQL NATIVO");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    this.alunoPorNome(scanner);
                    break;

                case 2:
                    this.alunoPorNomeIdadeMenorIgual(scanner);
                    break;

                case 3:
                    this.alunoPorNomeIdadeMaiorOuIgual(scanner);
                    break;

                case 4:
                    this.alunoMatricualdoComNomeIdadeMaiorOuIgual(scanner);
                    break;

                case 5:
                    this.professoresAtribuidos(scanner);
                    break;

                default:
                    isTrue = false;
                    break;
            }
        }
        System.out.println();
    }

    private void alunoPorNome(Scanner scanner){
        System.out.print("Nome: ");
        String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

        List<Aluno> alunos = this.alunoRepository.findByNomeContaining(nome);

        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
    }

    private void alunoPorNomeIdadeMenorIgual(Scanner scanner){
        System.out.print("Nome: ");
        String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

        System.out.println("Idade: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findByNomeContainingAndIdadeLessThan(nome, idade);

        alunos.forEach(System.out::println);
    }

    private void alunoPorNomeIdadeMaiorOuIgual(Scanner scanner){
        System.out.print("Nome: ");
        String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

        System.out.println("Idade: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeMaiorOuIgual(nome, idade);

        alunos.forEach(System.out::println);
    }

    private void alunoMatricualdoComNomeIdadeMaiorOuIgual(Scanner scanner){
        System.out.print("Nome: ");
        String nomeAluno = scanner.next(); //le a proxima string até achar um enter ou um espaco

        System.out.println("Idade: ");
        Integer idade = scanner.nextInt();

        System.out.print("Nome da Disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaiorMatriculado(nomeAluno, idade, nomeDisciplina);

        alunos.forEach(System.out::println);
    }

    private void professoresAtribuidos(Scanner scanner){
        System.out.print("Nome Professor: ");
        String nomeProfessor = scanner.next(); //le a proxima string até achar um enter ou um espaco

        System.out.print("Nome da Disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Professor> professores = this.professorRepository.findProfessorAtribuido(nomeProfessor, nomeDisciplina);

        professores.forEach(System.out::println);
    }
}
