package br.com.fatecads.fatecads.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecads.fatecads.entity.Pedido;

public interface Pedidorepository extends JpaRepository<Pedido, Integer> {
    
}
