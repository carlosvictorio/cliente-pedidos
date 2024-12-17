package com.victorio.cliente_pedidos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorio.cliente_pedidos.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	public List<Pedido> findByClienteId(Long clienteId);
}
