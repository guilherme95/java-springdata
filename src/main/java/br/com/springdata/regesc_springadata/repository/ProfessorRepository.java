package br.com.springdata.regesc_springadata.repository;

import br.com.springdata.regesc_springadata.orm.Professor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {
//    SQLnativa
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM professor p INNER JOIN disciplina d ON p.id = d.professor_id WHERE p.nome LIKE :nomeProfessor% AND d.nome LIKE :nomeDisciplina%")
            List<Professor> findProfessorAtribuido(String nomeProfessor, String nomeDisciplina);
}
