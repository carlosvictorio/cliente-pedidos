package com.victorio.cliente_pedidos.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.models.Pedido;

public class ClienteDTO {

	private Long id;
	private String nome;
	private String email;
	private String endereco;
	private List<Pedido> pedidos;
	
	public ClienteDTO() {	
	}
	
	public ClienteDTO(Cliente cliente) {
		BeanUtils.copyProperties(cliente, this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
}
