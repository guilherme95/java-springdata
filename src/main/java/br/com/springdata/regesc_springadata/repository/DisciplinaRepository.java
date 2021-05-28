package br.com.springdata.regesc_springadata.repository;

import br.com.springdata.regesc_springadata.orm.Disciplina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long> {
}
