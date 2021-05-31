package br.com.springdata.regesc_springadata.repository;

import br.com.springdata.regesc_springadata.orm.Aluno;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlunoRepository extends CrudRepository<Aluno, Long> {
    List<Aluno> findByNomeContaining(String nome);
    List<Aluno> findByNomeContainingAndIdadeLessThan(String nome, Integer idade);
}
