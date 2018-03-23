package br.edu.etec.view;

import java.awt.Dimension;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import br.edu.etec.model.Vendas;
import br.edu.etec.persistence.VendasJdbcDAO;

public class TelaCadVendas extends TelaDeCadastro {
	JList list = new JList();
	Vendas vendas = new Vendas();

	JLabel lblIdCliente = new JLabel("ID Cliente");
	JTextField txtIdCliente = new JTextField();

	JLabel lblValorTotal = new JLabel("Valor Total");
	JTextField txtValorTotal = new JTextField();

	JLabel lblDesconto = new JLabel("Desconto");
	JTextField txtDesconto = new JTextField();

	JLabel lblValorPago = new JLabel("Valor Pago");
	JTextField txtValorPago = new JTextField();

	JLabel lblData = new JLabel("Data");
	JTextField txtData = new JTextField();

	public TelaCadVendas() {
		super(5, 2);
		this.painelParaCampos.add(lblIdCliente);
		this.painelParaCampos.add(txtIdCliente);

		this.painelParaCampos.add(lblValorTotal);
		this.painelParaCampos.add(txtValorTotal);

		this.painelParaCampos.add(lblDesconto);
		this.painelParaCampos.add(txtDesconto);

		this.painelParaCampos.add(lblValorPago);
		this.painelParaCampos.add(txtValorPago);

		this.painelParaCampos.add(lblData);
		this.painelParaCampos.add(txtData);
		System.out.println("Construtor TelaCadVendas()");

		JScrollPane listScroller = new JScrollPane();
		listScroller.setPreferredSize(new Dimension(250, 80));
		this.painelListagem.add(listScroller);

		this.btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadVendas.this.limparFormulario();
			}
		});
		this.btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TelaCadVendas.this.salvar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		this.btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadVendas.this.cancelar();
			}
		});

		this.btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TelaCadVendas.this.alterar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		this.btnListar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					TelaCadVendas.this.listar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		this.btnExcluir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					TelaCadVendas.this.excluir();
				} catch (SQLException e1) {
					System.out.println("Excluir nao funfou");
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	void limparFormulario() {
		System.out.println("void limparFormulario()");
		this.txtIdCliente.setText("");
		this.txtData.setText("");
		this.txtDesconto.setText("");
		this.txtValorPago.setText("");
		this.txtValorTotal.setText("");
	}

	@Override
	void salvar() {

		try {
			this.vendas.setFk_idCliente(Integer.parseInt(this.txtIdCliente.getText()));
			this.vendas.setDesconto(Double.parseDouble(this.txtDesconto.getText()));
			this.vendas.setValorTotal(Double.parseDouble(this.txtValorTotal.getText()));
			this.vendas.setValorPago(Double.parseDouble(this.txtValorPago.getText()));
			Connection connection = br.edu.etec.persistence.JdbcUtil.getConnection();
			br.edu.etec.persistence.VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(connection);
			vendasJdbcDAO.salvar(this.vendas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	void cancelar() {
		this.setVisible(false);
	}

	@Override
	void alterar() throws SQLException {
		try {
			int idInt = Integer.parseInt(this.txtId.getText());
			Connection conn = br.edu.etec.persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			Vendas cli = vendasJdbcDAO.findById(idInt);
			if (cli != null) {
				this.txtIdCliente.setText("" + cli.getFk_idCliente());
				this.txtDesconto.setText("" + cli.getDesconto());
				this.txtValorPago.setText("" + cli.getValorPago());
				this.txtValorTotal.setText("" + cli.getValorTotal());
			} else {
				JOptionPane.showMessageDialog(this, "Nao ha vendas com esse id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	void excluir() throws SQLException {
		String id = this.txtId.getText();
		try {
			int idInt = Integer.parseInt(id);
			Connection conn = br.edu.etec.persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			vendasJdbcDAO.excluir(idInt);
			this.limparFormulario();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void listar() throws SQLException {
		Connection conn;
		try {
			conn = br.edu.etec.persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			List<Vendas> list = vendasJdbcDAO.listar();
			String[] strArr = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String id = Integer.toString(list.get(i).getId());
				int fk_idCliente = list.get(i).getFk_idCliente();
				strArr[i] = id + " - " + fk_idCliente;
			}
			// this.list.setListData(strArr);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}