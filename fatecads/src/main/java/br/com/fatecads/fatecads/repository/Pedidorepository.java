package br.com.fatecads.fatecads.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecads.fatecads.entity.Pedido;

public interface Pedidorepository extends JpaRepository<Pedido, Integer> {

	@EntityGraph(attributePaths = "usuario")
	Optional<Pedido> findWithUsuarioByIdPedido(Integer idPedido);
}
