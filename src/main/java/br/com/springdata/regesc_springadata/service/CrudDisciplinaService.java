package br.com.springdata.regesc_springadata.service;

import br.com.springdata.regesc_springadata.orm.Aluno;
import br.com.springdata.regesc_springadata.orm.Disciplina;
import br.com.springdata.regesc_springadata.orm.Professor;
import br.com.springdata.regesc_springadata.repository.AlunoRepository;
import br.com.springdata.regesc_springadata.repository.DisciplinaRepository;
import br.com.springdata.regesc_springadata.repository.ProfessorRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CrudDisciplinaService {

    private DisciplinaRepository disciplinaRepository;
    private ProfessorRepository professorRepository; //dependecia da classe CrudProfessorService
    private AlunoRepository alunoRepository;
    // o Spring automaticamente cria um objeto com a interface 'ProfessorRepository',
    // e o injeta para nós no construtor da classe atual ==> Injeçao de dependencia
    public CrudDisciplinaService(DisciplinaRepository disciplinaRepository,
                                 ProfessorRepository professorRepository,
                                 AlunoRepository alunoRepository){
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
    }

    public void menu(Scanner scanner){
        Boolean isTrue = true;

        while(isTrue){
            System.out.println("\nQual ação você quer executar?");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1 - Cadastrar novo Disciplina");
            System.out.println("2 - Atualizar um Disciplina");
            System.out.println("3 - Visualizar todos os Disciplina");
            System.out.println("4 - Deletar um Disciplina");
            System.out.println("5 - Matricular Alunos");
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
                    this.matricularAlunos(scanner);
                    break;

                default:
                    isTrue = false;
                    break;
            }
        }
        System.out.println();
    }

    private void cadastrar(Scanner scanner){
        System.out.print("Digite o nome da disciplina: ");
        String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

        System.out.print("Semestre: ");
        Integer semestre = scanner.nextInt();

        System.out.print("Professor ID: ");
        Long professorID = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(professorID);
        if(optional.isPresent()){
            Professor professor = optional.get();
            Disciplina disciplina = new Disciplina(nome, semestre, professor);
            disciplinaRepository.save(disciplina);
            System.out.println("Disciplina cadastrada com sucesso!!!");
        }else{
            System.out.println("Professor com ID "+professorID+" nao localizado");
        }
    }

    private void atualizar(Scanner scanner){
        System.out.print("Digite o id da Disciplina a ser atualizada: ");
        Long id = scanner.nextLong();

        Optional<Disciplina> optionalDisciplina = this.disciplinaRepository.findById(id);

        if(optionalDisciplina.isPresent()) {
            Disciplina disciplina = optionalDisciplina.get();

            System.out.print("Digite o nome da Disciplina: ");
            String nome = scanner.next(); //le a proxima string até achar um enter ou um espaco

            System.out.print("Semestre: ");
            Integer semestre = scanner.nextInt();

            System.out.print("Professor ID: ");
            Long professorID = scanner.nextLong();

            Optional<Professor> optionalProfessor = this.professorRepository.findById(professorID);
            if (optionalProfessor.isPresent()) {
                Professor professor = optionalProfessor.get();

                disciplina.setNome(nome);
                disciplina.setSemestre(semestre);
                disciplina.setProfessor(professor);

                disciplinaRepository.save(disciplina);
                System.out.println("Disciplina atualizada com sucesso!!!\n");
            } else {
                System.out.println("O id do professor informado: " + id + " é inválido!\n");
            }
        }
    }

    private void visualizar() {
        Iterable<Disciplina> disciplinas = this.disciplinaRepository.findAll();
        for(Disciplina disciplina : disciplinas){
            System.out.println(disciplina);
        }
        System.out.println();
    }

    private void deletar(Scanner scanner){
        System.out.print("Digite o id da Disciplina a ser deletada: ");
        Long id = scanner.nextLong();

        try{
            this.disciplinaRepository.deleteById(id); //se nao encontrar o id passado na tabela, lancara uma excessao
            System.out.println("Disciplina deletada!!!");
        }catch(EmptyResultDataAccessException e){
            System.out.println("Professor nao econtrado com o id: " + id);
        }
    }

    private Set<Aluno> matricular(Scanner scanner){
        Boolean isTrue = true;
        Set<Aluno> alunos = new HashSet<Aluno>();

        while(isTrue){
            System.out.println("Id do Aluno a ser matriculado (digite 0 para sair): ");
            Long alunoId = scanner.nextLong();

            if(alunoId > 0){
                System.out.println("alunoId: "+ alunoId);
                Optional<Aluno> optional = this.alunoRepository.findById(alunoId);
                if(optional.isPresent()){
                    alunos.add(optional.get());
                }else{
                    System.out.println("Nenhum aluno possui id "+alunoId+"!!!");
                }
            }else{
                isTrue = false;
            }
        }
        return alunos;
    }

    private void matricularAlunos(Scanner scanner){
        System.out.println("Digite o ID da Disciplina para matricular alunos: ");
        Long id = scanner.nextLong();

        Optional<Disciplina> optionalDisciplina = this.disciplinaRepository.findById(id);

        if(optionalDisciplina.isPresent()){
            Disciplina disciplina = optionalDisciplina.get();
            Set<Aluno> novosAlunos = this.matricular(scanner);
            disciplina.getAlunos().addAll(novosAlunos);
            this.disciplinaRepository.save(disciplina);
        }else{
            System.out.println("O id da disciplina informada: "+id+" é inválido\n");
        }
    }
}
