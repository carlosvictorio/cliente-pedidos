package com.victorio.cliente_pedidos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/{id}")
	public ResponseEntity<Pedido> createPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
		
		Cliente cliente = clienteService.getById(id);
		pedido.setCliente(cliente);
		Pedido novoPedido = pedidoService.save(pedido);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
		
		
	}

}
