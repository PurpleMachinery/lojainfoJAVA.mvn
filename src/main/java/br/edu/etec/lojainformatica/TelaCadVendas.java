package br.edu.etec.lojainformatica;

import java.awt.Dimension;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Vendas;
import persistence.VendasJdbcDAO;

public class TelaCadVendas extends TelaDeCadastro {
	ArrayList list = new ArrayList();
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
		System.out.println("TelaCadVendas==true");

		//JScrollPane listScroller = new JScrollPane(list);
		//listScroller.setPreferredSize(new Dimension(250, 80));
		//this.painelListagem.add(list);

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

		/*this.btnListar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					TelaCadVendas.this.listar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});*/
	}

	
	@Override
	void limparFormulario() {
		System.out.println("void limparFormulario() {....");
		this.txtIdCliente.setText("");
		this.txtData.setText("");
		this.txtDesconto.setText("");
		this.txtValorPago.setText("");
		this.txtValorTotal.setText("");
	}

	@Override
	void salvar() {
		String salvarOuAlterar = "salvar";

		// o botao salvar vai salvar ou alterar. se tiver id ele altera, se nao ele
		// salva
		String id = this.txtId.getText();
		int idInt = -1;

		try {
			idInt = Integer.parseInt(id);
			salvarOuAlterar = "alterar"; // se deu pra converter num in entao altera
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.vendas.setDesconto(Double.parseDouble(this.txtDesconto.getText()));
			this.vendas.setFk_idCliente(Integer.parseInt(this.txtIdCliente.getText()));
			this.vendas.setValorPago(Double.parseDouble(this.txtValorPago.getText()));
			this.vendas.setValorTotal(Double.parseDouble(this.txtDesconto.getText()));
			Connection connection = persistence.JdbcUtil.getConnection();
			persistence.VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(connection);
			if (salvarOuAlterar.equals("salvar")) {
				vendasJdbcDAO.salvar(this.vendas);
			} else {
				this.vendas.setId(idInt);
				vendasJdbcDAO.alterar(this.vendas);
			}
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
		String id = this.txtId.getText();
		try {
			int idInt = Integer.parseInt(id);
			Connection conn = persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			Vendas cli = vendasJdbcDAO.findById(idInt);
			if (cli != null) {
				this.txtIdCliente.setText(""+cli.getFk_idCliente());
				this.txtDesconto.setText(""+cli.getDesconto());
				this.txtValorPago.setText(""+cli.getValorPago());
				this.txtValorTotal.setText(""+cli.getValorTotal());
			} else {
				JOptionPane.showMessageDialog(this, "Nao ha vendass com esse id");
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
			Connection conn = persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			vendasJdbcDAO.excluir(idInt);
			this.limparFormulario();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*void listar() throws SQLException {
		Connection conn;
		try {
			conn = persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			List<Vendas> list = vendasJdbcDAO.listar();
			String[] strArr = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String id = Integer.toString(list.get(i).getId());
				//String nome = list.get(i).getNome();
				//strArr[i] = id + " - " + nome;
			}
			//this.list.setListData(strArr);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
}