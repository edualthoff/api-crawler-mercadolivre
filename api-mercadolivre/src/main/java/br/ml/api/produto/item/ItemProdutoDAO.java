package br.ml.api.produto.item;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemProdutoDAO extends JpaRepository<ItemProduto, Long> {

	Iterable<ItemProduto> findAllByIdCategoriaIdIn(Iterable<Long> idCategoria);
	
	Iterable<ItemProduto> findAllByIdMarcaIdIn(Iterable<Long> idMarca);
	
	Optional<ItemProduto> findByModelo(String modelo);
	
	Page<ItemProduto> findByModeloContainingIgnoreCase(String modelo, Pageable pageable);

}
