package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Vendas;

public class VendasJdbcDAO {
	private Connection conn;

	public VendasJdbcDAO(Connection conn) {
		this.conn = conn;
	}

	public void salvar(Vendas c) throws SQLException {
		String sql = "insert into tbVendas values ('" + c.getFk_idCliente() + "','" + c.getData() + "','" + c.getValorTotal()
				+ "','" + c.getDesconto() + "','" + c.getValorPago() + "')";
		System.out.println(sql);
		PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
		prepareStatement.executeUpdate();
		prepareStatement.close();
	}

	public void alterar(Vendas cExample) {
		String sql = "update tbVendas set fk_idCliente='" + cExample.getFk_idCliente() + "',valorTotal='" + cExample.getValorTotal()
				+ "',valorPago='" + cExample.getValorPago() + "',desconto='" + cExample.getDesconto() + "' where pk_idVenda='"
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
		String sql = "delete from tbVendas where id_hardware='" + id + "';";
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
				int id = rs.getInt("id_vendas");
				double desconto = Double.parseDouble(rs.getString("desconto"));
				int fk_cliente = Integer.parseInt(rs.getString("Fk_idCliente"));
				double valorPago = Double.parseDouble(rs.getString("valorPago"));
				Double valorTotal = Double.parseDouble(rs.getString("valorTotal"));
				
				Vendas venda = new Vendas();
				venda.setId(id);
				venda.setDesconto(desconto);
				venda.setFk_idCliente(fk_cliente);
				venda.setValorPago(valorPago);
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
		String sql = "select * from tbVendas where id_hardware = " + id;
		System.out.println(sql);
		Vendas hardware = null;
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				hardware = new Vendas();
				double desconto = Double.parseDouble(rs.getString("desconto"));
				int fk_cliente = Integer.parseInt(rs.getString("Fk_idCliente"));
				double valorPago = Double.parseDouble(rs.getString("valorPago"));
				Double valorTotal = Double.parseDouble(rs.getString("valorTotal"));
				
				Vendas venda = new Vendas();
				venda.setId(id);
				venda.setDesconto(desconto);
				venda.setFk_idCliente(fk_cliente);
				venda.setValorPago(valorPago);
				venda.setValorTotal(valorTotal);
			}
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hardware;
	}
}
