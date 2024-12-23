package com.victorio.cliente_pedidos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.victorio.cliente_pedidos.dto.ClienteDTO;
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
	
	public List<ClienteDTO> getAll() {
		List<Cliente> clientes = repository.findAll();
		List<ClienteDTO> clientesDTO = clientes.stream()
				.map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());
		
		return clientesDTO;
	}

	public ClienteDTO getById(Long id) {
		
		Optional<Cliente> cliente = repository.findById(id);
		if(cliente.isEmpty()) {
			throw new ResourceNotFoundException("Cliente com ID: " + id + " não encontrado!");
		}
		
		return new ClienteDTO(cliente.get());
	}
	
	public ClienteDTO update(Long id, Cliente cliente) {
		
		Optional<Cliente> clienteAntigo = repository.findById(id);
		
		if(clienteAntigo.isEmpty()) {
			throw new ResourceNotFoundException("Cliente com ID:" + id + " não encontrado!");
		}
		
		try {
		updateData(clienteAntigo.get(), cliente);
		
		} catch (DataIntegrityViolationException e) {
			throw new MissingRequiredAttributeException();
		}

		Cliente clienteAtualizado = repository.save(clienteAntigo.get());
		return new ClienteDTO(clienteAtualizado);
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
