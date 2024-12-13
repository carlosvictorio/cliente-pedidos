package com.victorio.cliente_pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente create(Cliente cliente) {
		Cliente clienteSalvo = repository.save(cliente);
		return clienteSalvo;
	}
	
	public List<Cliente> getAll() {
		List<Cliente> clientes = repository.findAll();
		return clientes;
	}

	public Cliente getById(Long id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.get();
	}
	
	public Cliente update(Long id, Cliente cliente) {
		Optional<Cliente> clienteAntigo = repository.findById(id);
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
		repository.delete(cliente.get());
	}
}
