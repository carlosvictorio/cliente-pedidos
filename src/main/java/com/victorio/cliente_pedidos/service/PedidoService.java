package com.victorio.cliente_pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victorio.cliente_pedidos.enums.PedidoEnum;
import com.victorio.cliente_pedidos.models.Pedido;
import com.victorio.cliente_pedidos.repositories.PedidoRepository;
import com.victorio.cliente_pedidos.service.exceptions.ResourceNotFoundException;

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
		
		if(pedido.isEmpty()) {
			throw new ResourceNotFoundException("Pedido com o ID:" + id + " não encontrado!");
		}
		
		return pedido.get();
	}
	
	public List<Pedido> getByIdCliente(Long clienteId) {
		List<Pedido> pedidos = repository.findByClienteId(clienteId);
	
		if(pedidos.isEmpty()) {
			throw new ResourceNotFoundException("Pedidos não encontrados para o cliente do ID: " + clienteId);
		}
		
		return pedidos;
	}
	
	public Pedido save(Pedido pedido) {
		Pedido pedidoSaved = repository.save(pedido);
		return pedidoSaved;
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
		
		if(pedido == null) {
			throw new ResourceNotFoundException("Pedido com ID:" + id + " não encontrado!");
		}
		
		pedido.setStatus(status);
		repository.save(pedido);
	}
	
	public void delete(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		repository.delete(pedido.get());
	}
}
