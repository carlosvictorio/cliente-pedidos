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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victorio.cliente_pedidos.dto.PedidoDTO;
import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.models.Pedido;
import com.victorio.cliente_pedidos.service.ClienteService;
import com.victorio.cliente_pedidos.service.PedidoService;

@RestController
@RequestMapping("/cliente/{clienteId}/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService service;

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<Void> createPedido(@PathVariable Long clienteId, @RequestBody Pedido pedido) {
		
		Cliente cliente = service.getClienteById(clienteId);
		pedido.setCliente(cliente);
		service.save(pedido);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping
	public ResponseEntity<List<PedidoDTO>> pedidosByCliente(@PathVariable Long clienteId) {
		List<Pedido> pedidos = service.getByIdCliente(clienteId);
		List<PedidoDTO> pedidosDTO = pedidos.stream().map(pedido -> new PedidoDTO(pedido)).collect(Collectors.toList());
		return ResponseEntity.ok().body(pedidosDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePedido(@PathVariable Long clienteId, @PathVariable Long id) {
		Cliente cliente = service.getClienteById(clienteId);
		Pedido pedido = service.getById(id);
		
		if(cliente.getId().equals(pedido.getCliente().getId())) {
			service.delete(pedido.getId());
			clienteService.save(cliente);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
