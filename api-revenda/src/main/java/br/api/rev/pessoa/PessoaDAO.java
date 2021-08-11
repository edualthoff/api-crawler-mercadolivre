package br.api.rev.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaDAO extends JpaRepository<Pessoa, Long>{

}
