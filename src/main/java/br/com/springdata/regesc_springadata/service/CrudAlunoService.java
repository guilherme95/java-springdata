package br.com.springdata.regesc_springadata.service;

import br.com.springdata.regesc_springadata.orm.Aluno;
import br.com.springdata.regesc_springadata.orm.Disciplina;
import br.com.springdata.regesc_springadata.repository.AlunoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudAlunoService {
    private AlunoRepository alunoRepository; //dependecia da classe CrudAlunoService

    // o Spring automaticamente cria um objeto com a interface 'AlunoRepository',
    // e o injeta para nós no construtor da classe atual ==> Injeçao de dependencia
    public CrudAlunoService(AlunoRepository alunoRepository){
        this.alunoRepository = alunoRepository;
    }

    // colocar notação @Transactional para que não ocorra a exception
    // @Transactional faz uma interação nova de algo que ela não tem no momento, ou seja, faz o novo 'get' necessário
    // @Transactional
    public void menu(Scanner scanner){
        Boolean isTrue = true;

        while(isTrue){
            System.out.println("\nQual ação você quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar novo Aluno");
            System.out.println("2 - Atualizar um Aluno");
            System.out.println("3 - Visualizar todos os Alunos");
            System.out.println("4 - Deletar um Aluno");
            System.out.println("5 - Visualizar um Aluno");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();

            switch (opcao){
                case 1:
                    this.cadastrar(scanner);
                    break;

                case 2:
                    this.atualizar(scanner);
                    break;

                case 3:
                    this.visualizar();
                    break;

                case 4:
                    this.deletar(scanner);
                    break;

                case 5:
                    this.visualizarAluno(scanner);
                    break;

                default:
                    isTrue = false;
                    break;
            }
        }
        System.out.println();
    }

    private void cadastrar(Scanner scanner){
        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

        System.out.print("Digite a idade do aluno: ");
        Integer idade = scanner.nextInt(); //le a proxima string até achar um enter ou um espaco

        Aluno aluno = new Aluno(nome, idade);
        this.alunoRepository.save(aluno);
        System.out.println("Aluno salvo no banco!!!\n");
    }

    private void atualizar(Scanner scanner){
        System.out.print("Digite o id do Aluno a ser atualizado: ");
        Long id = scanner.nextLong();

        Optional<Aluno> optional = this.alunoRepository.findById(id);

        if(optional.isPresent()){
            Aluno aluno = optional.get();

            System.out.print("Digite o nome do aluno: ");
            String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

            System.out.print("Digite a idade do aluno: ");
            Integer idade = scanner.nextInt(); //le a proxima string até achar um enter ou um espaco

            this.alunoRepository.save(aluno);
        }else{
            System.out.println("O id do aluno informado: "+id+" é inválido!\n");
        }
    }

    private void visualizar(){
        Iterable<Aluno> alunos = this.alunoRepository.findAll();
        for(Aluno aluno : alunos){
            System.out.println(aluno);
        }
        System.out.println();
    }

    private void deletar(Scanner scanner){
        System.out.print("Digite o id do Aluno a ser deletado: ");
        Long id = scanner.nextLong();

        try{
            this.alunoRepository.deleteById(id); //se nao encontrar o id passado na tabela, lancara uma excessao
            System.out.println("Aluno deletado!!!");
        }catch(EmptyResultDataAccessException e){
            System.out.println("Aluno nao econtrado com o id: " + id);
        }
    }

    // colocar notação @Transactional para que não ocorra a exception
    // @Transactional faz uma interação nova de algo que ela não tem no momento, ou seja, faz o novo 'get' necessário
    // @Transactional
    private void visualizarAluno(Scanner scanner){
        System.out.print("Digite o ID do Professor: ");
        Long id = scanner.nextLong();

        Optional<Aluno> optional = this.alunoRepository.findById(id);
        if(optional.isPresent()){
            Aluno aluno = optional.get();

            System.out.println("-----Aluno-----");
            System.out.println("ID: " + aluno.getId());
            System.out.println("Nome: " + aluno.getNome());
            System.out.println("Idade: " + aluno.getIdade());

            if(aluno.getDisciplinas() != null){
                System.out.println("-----Suas Disciplinas-----");
                for(Disciplina disciplina : aluno.getDisciplinas()){
                    System.out.println("ID: " + disciplina.getId());
                    System.out.println("Nome: " + disciplina.getNome());
                    System.out.println("Semestre: " + disciplina.getSemestre());
                }
            }
        }else{
            System.out.println("O professor com ID "+id+" não encontrado!!!");
        }
    }
}
