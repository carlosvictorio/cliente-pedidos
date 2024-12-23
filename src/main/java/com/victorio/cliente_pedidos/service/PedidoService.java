package com.victorio.cliente_pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.victorio.cliente_pedidos.enums.PedidoEnum;
import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.models.Pedido;
import com.victorio.cliente_pedidos.repositories.ClienteRepository;
import com.victorio.cliente_pedidos.repositories.PedidoRepository;
import com.victorio.cliente_pedidos.service.exceptions.MissingRequiredAttributeException;
import com.victorio.cliente_pedidos.service.exceptions.ResourceNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente getClienteById(Long id) {
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isEmpty()) {
			throw new ResourceNotFoundException("Cliente com ID: " + id + " não encontrado!");
		}
		
		return cliente.get();
	}
	
	public void save(Pedido pedido) {
		try {
			repository.save(pedido);
		} catch (DataIntegrityViolationException e) {
			throw new MissingRequiredAttributeException();
		}
	}
	
	public List<Pedido> getAll() {
		return repository.findAll();
	}

	public Pedido getById(Long id) {
		Optional<Pedido> pedido = repository.findById(id);
		
		return pedido.orElseThrow(() -> new ResourceNotFoundException("Pedido com o ID:" + id + " não encontrado!"));
	}
	
	public List<Pedido> getByIdCliente(Long clienteId) {
		List<Pedido> pedidos = repository.findByClienteId(clienteId);
	
		if(pedidos.isEmpty()) {
			throw new ResourceNotFoundException("Pedidos não encontrados para o cliente do ID: " + clienteId);
		}
		
		return pedidos; 
	}
	
	public Pedido update(Long id, Pedido obj) {
		Pedido pedido = getById(id);
		try {
			updateData(pedido, obj);
		} catch (DataIntegrityViolationException e) {
			throw new MissingRequiredAttributeException();
		}
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
		try {
		Optional<Pedido> pedido = repository.findById(id);
		repository.delete(pedido.get());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
