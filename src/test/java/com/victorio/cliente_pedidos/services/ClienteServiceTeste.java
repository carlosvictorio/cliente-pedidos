package com.victorio.cliente_pedidos.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.models.Pedido;
import com.victorio.cliente_pedidos.repositories.ClienteRepository;
import com.victorio.cliente_pedidos.service.ClienteService;
import com.victorio.cliente_pedidos.service.exceptions.MissingRequiredAttributeException;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTeste {
	
	@Mock
	private ClienteRepository repository;
	
	@InjectMocks
	private ClienteService service;
	
	@Test
	void saveClienteSucess() {
		List<Pedido> listaTeste = new ArrayList<>();
		Cliente cliente = new Cliente("Carlos Victorio", "victorio@gmail.com", "AV. PSM", listaTeste);
		
		when(repository.save(cliente)).thenReturn(cliente);
		
		service.save(cliente);
		
		verify(repository, times(1)).save(cliente);
	}
	
	@Test
	void saveClienteError() {
		List<Pedido> listaTeste = new ArrayList<>();
		
		Cliente cliente1 = new Cliente(null, "victorio@gmail.com", "AV. PSM", listaTeste);
		assertThrows(MissingRequiredAttributeException.class, () -> service.save(cliente1));
		
		Cliente cliente2 = new Cliente("Carlos Victorio", null, "AV. PSM", listaTeste);
		assertThrows(MissingRequiredAttributeException.class, () -> service.save(cliente2));
		
		Cliente cliente3 = new Cliente("Carlos Victorio", "victorio@gmail.com", null, listaTeste);
		assertThrows(MissingRequiredAttributeException.class, () -> service.save(cliente3));
		
		
	}

}
