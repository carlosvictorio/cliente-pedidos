package com.victorio.cliente_pedidos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorio.cliente_pedidos.dto.ClienteDTO;
import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteService service;
	
	@PostMapping
	public ResponseEntity<String> create(@RequestBody Cliente cliente) {
		service.save(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body("Cliente criado com sucesso!");
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> getAll() {
		List<Cliente> clientes = service.getAll();
		List<ClienteDTO> clientesDTO = clientes.stream()
				.map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(clientesDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> getById(@PathVariable Long id) {
		Cliente cliente = service.getById(id);
		ClienteDTO clienteDTO = new ClienteDTO(cliente);
		return ResponseEntity.ok().body(clienteDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente clienteAtualizado = service.update(id, cliente);
		ClienteDTO clienteDTO = new ClienteDTO(clienteAtualizado);
		
		return ResponseEntity.ok().body(clienteDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}
