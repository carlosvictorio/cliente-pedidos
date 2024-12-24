package com.victorio.cliente_pedidos.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.repositories.ClienteRepository;
import com.victorio.cliente_pedidos.service.exceptions.MissingRequiredAttributeException;
import com.victorio.cliente_pedidos.service.exceptions.ResourceNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public void save(Cliente cliente) {
		try {
			repository.save(cliente);
		} catch (DataIntegrityViolationException e) {
			throw new MissingRequiredAttributeException();
		}
	}
	
	public List<Cliente> getAll() {
		List<Cliente> clientes = repository.findAll();
		
		return clientes;
	}

	public Cliente getById(Long id) {
		
		Optional<Cliente> cliente = repository.findById(id);
		if(cliente.isEmpty()) {
			throw new ResourceNotFoundException("Cliente com ID: " + id + " não encontrado!");
		}
		
		return cliente.get();
	}
	
	public Cliente update(Long id, Cliente cliente) {
		
		Optional<Cliente> clienteAntigo = repository.findById(id);
		
		if(clienteAntigo.isEmpty()) {
			throw new ResourceNotFoundException("Cliente com ID:" + id + " não encontrado!");
		}
		
		if(cliente.getNome() == null || cliente.getEmail() == null || cliente.getEndereco() == null) {
			 throw new MissingRequiredAttributeException();
		}
		
		updateData(clienteAntigo.get(), cliente);

		Cliente clienteAtualizado = repository.save(clienteAntigo.get());
		return clienteAtualizado;
	}
	
	public void updateData(Cliente cliente, Cliente obj) {
		cliente.setNome(obj.getNome());
		cliente.setEmail(obj.getEmail());
		cliente.setEndereco(obj.getEndereco());	
	}
	
	public void delete(Long id) {
		
		Optional<Cliente> cliente = repository.findById(id);
		if(cliente.isEmpty()) {
			throw new ResourceNotFoundException("Cliente com ID:" + id + " não encontrado!");
		}
		repository.delete(cliente.get());
	}
}
