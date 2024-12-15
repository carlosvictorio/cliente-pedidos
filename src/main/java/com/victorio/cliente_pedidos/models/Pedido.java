package com.victorio.cliente_pedidos.models;

import com.victorio.cliente_pedidos.enums.PedidoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Integer quantity;
	@Column(nullable = false)
	private Double price;
	@Column(nullable = false)
	private PedidoEnum status;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	public Pedido() {
	}
	
	public Pedido(String name, Integer quantity, Double price, PedidoEnum status) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
	}
	
	public Long getId() {
		return id;
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
	
	

}
