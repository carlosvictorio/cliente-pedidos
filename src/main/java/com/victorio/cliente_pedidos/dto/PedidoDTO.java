package com.victorio.cliente_pedidos.dto;

import org.springframework.beans.BeanUtils;

import com.victorio.cliente_pedidos.enums.PedidoEnum;
import com.victorio.cliente_pedidos.models.Cliente;
import com.victorio.cliente_pedidos.models.Pedido;

public class PedidoDTO {

	private Long id;
	private String name;
	private Integer quantity;
	private Double price;
	private PedidoEnum status;
	private Cliente cliente;
	
	public PedidoDTO() {
	}
	
	public PedidoDTO(Pedido pedido) {
		BeanUtils.copyProperties(pedido, this);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public PedidoEnum getStatus() {
		return status;
	}
	public void setStatus(PedidoEnum status) {
		this.status = status;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
		
}
