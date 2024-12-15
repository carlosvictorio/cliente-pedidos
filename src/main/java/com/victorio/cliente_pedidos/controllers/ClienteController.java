package com.victorio.cliente_pedidos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@PostMapping
	public ResponseEntity<Cliente> create(Cliente cliente) {
		Cliente c = service.create(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(c);
	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> getAll() {
		List<Cliente> clientes = service.getAll();
		return ResponseEntity.ok().body(clientes);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getById(@PathVariable Long id) {
		Cliente cliente = service.getById(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update(@PathVariable Long id, Cliente cliente) {
		Cliente clienteAtualizado = service.update(id, cliente);
		return ResponseEntity.ok().body(clienteAtualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}
