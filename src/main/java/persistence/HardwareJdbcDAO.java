package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Hardware;

public class HardwareJdbcDAO {
	private Connection conn;

	public HardwareJdbcDAO(Connection conn) {
		this.conn = conn;
	}

	public void salvar(Hardware c) throws SQLException {
		String sql = "insert into tbHardwares values ('" + c.getDescricao() + "','" + c.getPrecoUnitario() + "','" + c.getQtdAtual()
				+ "','" + c.getQtdMinima() + "')";
		System.out.println(sql);
		PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
		prepareStatement.executeUpdate();
		prepareStatement.close();
	}

	public void alterar(Hardware cExample) {
		String sql = "update tbHardwares set descricao='" + cExample.getDescricao() + "',qtdAtual='" + cExample.getQtdAtual()
				+ "',qtdMinima='" + cExample.getQtdMinima() + "',precoUnit='" + cExample.getPrecoUnitario() + "' where pk_idHardware='"
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
		String sql = "delete from tbHardwares where pk_idHardware='" + id + "';";
		System.out.println(sql);
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			prepareStatement.executeUpdate();
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Hardware> listar() {
		String sql = "select * from tbHardwares";
		System.out.println(sql);
		List<Hardware> hardwares = new ArrayList<Hardware>();
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("pk_idHardware");
				String descricao = rs.getString("descricao");
				double precoUnitario = Double.parseDouble(rs.getString("precoUnit"));
				int qtdAtual = Integer.parseInt(rs.getString("qtdAtual"));
				int qtdMinima = Integer.parseInt(rs.getString("qtdMinima"));
				
				Hardware hardware = new Hardware();
				hardware.setId(id);
				hardware.setDescricao(descricao);;
				hardware.setPrecoUnitario(precoUnitario);
				hardware.setQtdAtual(qtdAtual);
				hardware.setQtdMinima(qtdMinima);
				hardwares.add(hardware);
			}
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hardwares;
	}

	public Hardware findById(Integer id) {
		String sql = "select * from tbHardwares where pk_idHardware = " + id;
		System.out.println(sql);
		Hardware hardware = null;
		try {
			PreparedStatement prepareStatement = this.conn.prepareStatement(sql);
			ResultSet rs = prepareStatement.executeQuery();
			while (rs.next()) {
				hardware = new Hardware();
				String descricao = rs.getString("descricao");
				double precoUnitario = Double.parseDouble(rs.getString("precoUnit"));
				int qtdAtual = Integer.parseInt(rs.getString("qtdAtual"));
				int qtdMinima = Integer.parseInt(rs.getString("qtdMinima"));

				hardware.setId(id);
				hardware.setDescricao(descricao);;
				hardware.setPrecoUnitario(precoUnitario);
				hardware.setQtdAtual(qtdAtual);
				hardware.setQtdMinima(qtdMinima);
			}
			prepareStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hardware;
	}
}
