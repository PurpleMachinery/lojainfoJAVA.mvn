package br.edu.etec.model;

public class Vendas {
	Integer id;
	int fk_idCliente;
	//data DATE NOT NULL **como colocar?
	double valorTotal;
	double desconto;
	double valorPago;
	String data;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getFk_idCliente() {
		return fk_idCliente;
	}
	public void setFk_idCliente(int fk_idCliente) {
		this.fk_idCliente = fk_idCliente;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public double getDesconto() {
		return desconto;
	}
	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	public double getValorPago() {
		return valorPago;
	}
	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}
}
