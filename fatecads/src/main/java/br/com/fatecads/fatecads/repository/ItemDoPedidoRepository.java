package br.com.fatecads.fatecads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecads.fatecads.entity.ItemDoPedido;

public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedido, Integer> {

	@EntityGraph(attributePaths = "produto")
	List<ItemDoPedido> findByPedido_IdPedido(Integer idPedido);
}
