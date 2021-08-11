package br.ml.api.produto.marca;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaDAO extends JpaRepository<Marca, Long>{

	Optional<Marca> findByNome(String nome);
	Optional<Marca> findById(Long idMarca);
}
