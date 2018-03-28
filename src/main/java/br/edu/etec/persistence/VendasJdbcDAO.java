package br.edu.etec.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.etec.model.Vendas;

public class VendasJdbcDAO {
	private Connection conn;

	public VendasJdbcDAO(Connection conn) {
		this.conn = conn;
	}

	public void salvar(Vendas c) throws SQLException {
		System.out.println(c.getData());
		String sql;		
		sql = "insert into tbVendas (fk_idCliente, data, valorTotal, desconto) values ('" + c.getFk_idCliente() + "','" + c.getData() + "','" + c.getValorTotal()
				+ "','" + c.getDesconto() + "')";		
		System.out.println(sql);
		PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
		prepareStatement.executeUpdate();
		prepareStatement.close();
	}

	public void alterar(Vendas cExample) {
		String sql = "update tbVendas set fk_idCliente='" + cExample.getFk_idCliente() + "', data='" + cExample.getData() + "',valorTotal='" + cExample.getValorTotal()
				+ "',desconto='" + cExample.getDesconto() + "' where pk_idVenda='"
				+ cExample.getId() + "';";
		System.out.println(sql);
		PreparedStatement prepareStatement;
		try {
			prepareStatement = this.conn.prepareStatement(sql);
			prepareStatement.executeUpdate();
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void excluir(int id) {
		String sql = "delete from tbVendas where pk_idVenda='" + id + "';";
		System.out.println(sql);
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			prepareStatement.executeUpdate();
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Vendas> listar() {
		String sql = "select * from tbVendas";
		System.out.println(sql);
		List<Vendas> vendas = new ArrayList<Vendas>();
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("pk_IdVenda");
				double desconto = Double.parseDouble(rs.getString("desconto"));
				int fk_cliente = Integer.parseInt(rs.getString("Fk_idCliente"));
				double valorTotal = Double.parseDouble(rs.getString("valorTotal"));
				String data = rs.getString("data");
				
				Vendas venda = new Vendas();
				venda.setId(id);
				venda.setDesconto(desconto);
				venda.setFk_idCliente(fk_cliente);
				venda.setData(data);
				venda.setValorTotal(valorTotal);
				vendas.add(venda);
			}
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vendas;
	}

	public Vendas findById(Integer id) {
		String sql = "select * from tbVendas where pk_idVenda = " + id;
		System.out.println(sql);
		Vendas venda = null;
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				venda = new Vendas();
				double desconto = Double.parseDouble(rs.getString("desconto"));
				int fk_cliente = Integer.parseInt(rs.getString("Fk_idCliente"));
				Double valorTotal = Double.parseDouble(rs.getString("valorTotal"));
				String data = rs.getString("data");

				venda.setId(id);
				venda.setDesconto(desconto);
				venda.setFk_idCliente(fk_cliente);
				venda.setValorTotal(valorTotal);
				venda.setData(data);
			}
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return venda;
	}
}
