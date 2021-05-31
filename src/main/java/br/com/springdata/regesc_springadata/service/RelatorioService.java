package br.com.springdata.regesc_springadata.service;

import br.com.springdata.regesc_springadata.orm.Aluno;
import br.com.springdata.regesc_springadata.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {
    private AlunoRepository alunoRepository;

    public RelatorioService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public void menu(Scanner scanner){
        Boolean isTrue = true;

        while(isTrue){
            System.out.println("\nQual relatorio voce deseja?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Alunos por um dado Nome");
            System.out.println("1 - Alunos por um dado Nome e Idade");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    this.alunoPorNome(scanner);
                    break;

                case 2:
                    this.alunoPorNomeIdadeMenorIgual(scanner);
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

        System.out.println("Idade");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findByNomeContainingAndIdadeLessThan(nome, idade);

        alunos.forEach(System.out::println);
    }
}
