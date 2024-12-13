package com.victorio.cliente_pedidos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorio.cliente_pedidos.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
