package br.com.springdata.regesc_springadata.repository;

import br.com.springdata.regesc_springadata.orm.Professor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long> {

}
