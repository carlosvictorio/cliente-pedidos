package com.victorio.cliente_pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victorio.cliente_pedidos.enums.PedidoEnum;
import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.models.Pedido;
import com.victorio.cliente_pedidos.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	public Pedido create(Pedido pedido) {
		return repository.save(pedido);
	}
	
	public List<Pedido> getAll() {
		return repository.findAll();
	}
	
	public Pedido getById(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		return pedido.get();
	}
	
	public Pedido update(Long id, Pedido obj) {
		Pedido pedido = getById(id);
		updateData(pedido, obj);
		return repository.save(pedido);
	}
	
	public void updateData(Pedido pedido, Pedido obj) {
		pedido.setName(obj.getName());
		pedido.setPrice(obj.getPrice());
		pedido.setQuantity(obj.getQuantity());
		pedido.setStatus(obj.getStatus());
	}
	
	public void updateStatus(Long id, PedidoEnum status) {
		Pedido pedido = getById(id);
		pedido.setStatus(status);
		repository.save(pedido);
	}
	
	public void delete(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		repository.delete(pedido.get());
	}
}
