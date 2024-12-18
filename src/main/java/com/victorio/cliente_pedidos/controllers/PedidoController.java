package com.victorio.cliente_pedidos.controllers;

import java.util.List;

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

import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.models.Pedido;
import com.victorio.cliente_pedidos.service.ClienteService;
import com.victorio.cliente_pedidos.service.PedidoService;

@RestController
@RequestMapping("/cliente/{clienteId}/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<Pedido> createPedido(@PathVariable Long clienteId, @RequestBody Pedido pedido) {
		
		Cliente cliente = clienteService.getById(clienteId);
		pedido.setCliente(cliente);
		Pedido novoPedido = pedidoService.save(pedido);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
	}
	
	@GetMapping
	public ResponseEntity<List<Pedido>> pedidosByCliente(@PathVariable Long clienteId) {
		List<Pedido> pedidos = pedidoService.getByIdCliente(clienteId);
		return ResponseEntity.ok().body(pedidos);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePedido(@PathVariable Long clienteId, @PathVariable Long id) {
		Cliente cliente = clienteService.getById(clienteId);
		Pedido pedido = pedidoService.getById(id);
		
		if(cliente.getId().equals(pedido.getCliente().getId())) {
			pedidoService.delete(pedido.getId());
			clienteService.save(cliente);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
