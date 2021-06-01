package br.com.springdata.regesc_springadata.repository;

import br.com.springdata.regesc_springadata.orm.Aluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {
    List<Aluno> findByNomeContaining(String nome);
    List<Aluno> findByNomeContainingAndIdadeLessThan(String nome, Integer idade);

    // JPQL
    @Query("SELECT a FROM Aluno a WHERE a.nome LIKE :nome% AND a.idade >= :idade")
    List<Aluno> findNomeIdadeMaiorOuIgual(String nome, Integer idade);

    // JPQL
    @Query("SELECT a FROM Aluno a INNER JOIN a.disciplinas d WHERE a.nome LIKE :nomeAluno% AND a.idade >= :idadeAluno AND d.nome LIKE :nomeDisciplina%")
    List<Aluno> findNomeIdadeIgualOuMaiorMatriculado(String nomeAluno, Integer idadeAluno, String nomeDisciplina);
}
