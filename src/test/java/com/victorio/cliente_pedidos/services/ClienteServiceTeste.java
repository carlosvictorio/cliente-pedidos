package com.victorio.cliente_pedidos.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
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
import com.victorio.cliente_pedidos.service.exceptions.ResourceNotFoundException;

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
	
	@Test
	void getAllSuccess() {
		List<Cliente> clientesEsperados = new ArrayList<>();
		
		List<Pedido> listaTeste1 = new ArrayList<>();
		List<Pedido> listaTeste2 = new ArrayList<>();
		
		clientesEsperados.add(new Cliente("Victorio", "victorio@gmail.com", "AV. PSM", listaTeste1));
		clientesEsperados.add(new Cliente("Sebastião", "abc@gmail.com", "AV. João Segundo", listaTeste2));
		
		when(repository.findAll()).thenReturn(clientesEsperados);
		
		List<Cliente> clientesRetornados = service.getAll();
		
		Assertions.assertEquals(clientesEsperados, clientesRetornados);	
		
		verify(repository, times(1)).findAll();
	}
	
	@Test
	void getByIdSucess() {

		List<Pedido> listaTeste = new ArrayList<>();
		Cliente cliente = new Cliente("Victorio", "victorio@gmail.com", "AV. PSM", listaTeste);
		
		when(repository.findById(1L)).thenReturn(Optional.of(cliente));
		
		Cliente clienteRetornado = service.getById(1L);
		
		Assertions.assertEquals(cliente, clienteRetornado);
		
		verify(repository, times(1)).findById(1L);		
	}
	
	@Test
	void getByIdFailed() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> service.getById(1L));
	}
	

}
