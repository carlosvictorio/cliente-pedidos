package com.victorio.cliente_pedidos.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
	void saveClienteSuccess() {
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
	void getByIdSuccess() {

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
	
	@Test
	void updateSuccess() {
		List<Pedido> listaTeste = new ArrayList<>();
		List<Pedido> listaAtualizada = new ArrayList<>();
		Cliente clienteExistente = new Cliente("Joãozinho", "jao@gmail.com", "Marginal Tietê", listaTeste);
		Cliente clienteAtualizado = new Cliente("João", "jao2002@gmail.com", "Centro", listaAtualizada);
		
		when(repository.findById(clienteExistente.getId())).thenReturn(Optional.of(clienteExistente));
		when(repository.save(clienteExistente)).thenReturn(clienteAtualizado);
		
		service.update(clienteExistente.getId(), clienteAtualizado);
		
		Assertions.assertEquals(clienteExistente.getId(), clienteAtualizado.getId());
		Assertions.assertEquals(clienteExistente.getNome(), clienteAtualizado.getNome());
		Assertions.assertEquals(clienteExistente.getEmail(), clienteAtualizado.getEmail());
		Assertions.assertEquals(clienteExistente.getEndereco(), clienteAtualizado.getEndereco());
		Assertions.assertEquals(clienteExistente.getPedidos(), clienteAtualizado.getPedidos());
		
		verify(repository, times(1)).save(clienteExistente);
		
	}
	
	@Test
	void update_ShouldThrowResourceNotFoundException_WhenIdNotFound() {
		List<Pedido> listaAtualizada = new ArrayList<>();
		Cliente clienteAtualizado = new Cliente("João", "jao2002@gmail.com", "Centro", listaAtualizada);
		
		when(repository.findById(10L)).thenReturn(Optional.empty());
		ResourceNotFoundException exceptionThrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(10L, clienteAtualizado));
		
		Assertions.assertEquals("Cliente com ID:10 não encontrado!", exceptionThrown.getMessage());
	}

	@Test
	void update_ShouldThrowMissingRequiredAttributeException_WhenAttributeIsNull() {
		List<Pedido> lista = new ArrayList<>();
		Cliente clienteComNomeNull = new Cliente(null, "jao2002@gmail.com", "Centro", lista);
		when(repository.findById(1L)).thenReturn(Optional.of(clienteComNomeNull));	
		MissingRequiredAttributeException exceptionThrownName = Assertions.assertThrows(MissingRequiredAttributeException.class, () -> service.update(1L, clienteComNomeNull));
		Assertions.assertEquals("Preencha todos campos obrigatórios", exceptionThrownName.getMessage());
		
		Cliente clienteComEmailNull = new Cliente("Victorio", null, "Centro", lista);
		when(repository.findById(1L)).thenReturn(Optional.of(clienteComEmailNull));	
		MissingRequiredAttributeException exceptionThrownEmail = Assertions.assertThrows(MissingRequiredAttributeException.class, () -> service.update(1L, clienteComEmailNull));
		Assertions.assertEquals("Preencha todos campos obrigatórios", exceptionThrownEmail.getMessage());
		
		Cliente clienteComEnderecoNull = new Cliente("Victorio", "jao2002@gmail.com", null, lista);
		when(repository.findById(1L)).thenReturn(Optional.of(clienteComEnderecoNull));
		MissingRequiredAttributeException exceptionThrownEndereco = Assertions.assertThrows(MissingRequiredAttributeException.class, () -> service.update(1L, clienteComEnderecoNull));
		Assertions.assertEquals("Preencha todos campos obrigatórios", exceptionThrownEndereco.getMessage());
	}
	
	@Test
	void deleteSuccess() {
		List<Pedido> lista = new ArrayList<>();
		Cliente cliente = new Cliente("João", "jao2002@gmail.com", "Centro", lista);
		
		when(repository.findById(1L)).thenReturn(Optional.of(cliente));
		
		service.delete(1L);
		
		verify(repository, times(1)).delete(cliente);
				
	}
	
	@Test
	void deleteFailed() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		ResourceNotFoundException exceptionThrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
		
		Assertions.assertEquals(exceptionThrown.getMessage(), "Cliente com ID:1 não encontrado!");
	}
}
