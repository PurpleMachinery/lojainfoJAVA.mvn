package br.edu.etec.view;

import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import br.edu.etec.model.Id;
import br.edu.etec.model.Vendas;
import br.edu.etec.persistence.HardwareJdbcDAO;
import br.edu.etec.persistence.VendasJdbcDAO;

public class TelaCadVendas extends TelaDeCadastro {
	Vendas vendas = new Vendas();

	JLabel lblIdCliente = new JLabel("ID Cliente");
	JTextField txtIdCliente = new JTextField();

	JLabel lblValorTotal = new JLabel("Valor Total");
	JTextField txtValorTotal = new JTextField();

	JLabel lblDesconto = new JLabel("Desconto");
	JTextField txtDesconto = new JTextField();

	JLabel lblData = new JLabel("Data");
	JDateChooser txtData;
	
	static String[] colunas={"id","fk_cliente","valorTotal","Desconto", "Data"};
	public TelaCadVendas() {
		super(4, 2, colunas);
		this.tabela.getColumnModel().getColumn(4).setPreferredWidth(170);
		this.painelParaCampos.add(lblIdCliente);
		this.painelParaCampos.add(txtIdCliente);

		this.painelParaCampos.add(lblValorTotal);
		this.painelParaCampos.add(txtValorTotal);

		this.painelParaCampos.add(lblDesconto);
		this.painelParaCampos.add(txtDesconto);

		this.painelParaCampos.add(lblData);
		txtData = new JDateChooser("dd/MM/yyyy HH:mm:ss", "##/##/#### ##:##:##", '_');
		txtData.setDate(new Date());
		this.painelParaCampos.add(txtData);
		try {
			TelaCadVendas.this.listar();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Construtor TelaCadVendas()");

		this.btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadVendas.this.limparFormulario();
			}
		});
		this.btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TelaCadVendas.this.salvar();
					TelaCadVendas.this.listar();
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
					TelaCadVendas.this.listar();
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
					TelaCadVendas.this.listar();
				} catch (SQLException e1) {
					System.out.println("Excluir nao funfou");
					e1.printStackTrace();
				}
			}
		});

		this.btnProcuraId.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					TelaCadVendas.this.pId();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	void limparFormulario() {
		System.out.println("void limparFormulario()");
		this.txtIdCliente.setText("");
		this.txtData.setDate(new Date());
		this.txtDesconto.setText("");
		this.txtValorTotal.setText("");
	}

	@Override
	void salvar() {

		try {
			this.vendas.setFk_idCliente(Integer.parseInt(this.txtIdCliente.getText()));
			this.vendas.setDesconto(Double.parseDouble(this.txtDesconto.getText()));
			this.vendas.setValorTotal(Double.parseDouble(this.txtValorTotal.getText()));
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.vendas.setData(formatador.format(this.txtData.getDate()));
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
			int idInt = Integer.parseInt((String) this.txtId.getSelectedItem());
			Connection conn = br.edu.etec.persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			Vendas cli = vendasJdbcDAO.findById(idInt);
			if (cli != null) {
				this.vendas.setId(Integer.parseInt((String) this.txtId.getSelectedItem()));
				this.vendas.setFk_idCliente(Integer.parseInt(this.txtIdCliente.getText()));
				this.vendas.setDesconto(Double.parseDouble(this.txtDesconto.getText()));
				this.vendas.setValorTotal(Double.parseDouble(this.txtValorTotal.getText()));
				SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.vendas.setData(formatador.format(this.txtData.getDate()));
				vendasJdbcDAO.alterar(this.vendas);
			} else {
				JOptionPane.showMessageDialog(this, "Nao ha vendas com esse id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	void excluir() throws SQLException {
		String id =""+ this.txtId.getSelectedItem();
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

	void pId() throws SQLException {
		String id =""+ this.txtId.getSelectedItem();
		try {
			int idInt = Integer.parseInt(id);
			Connection conn = br.edu.etec.persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			Vendas cc = vendasJdbcDAO.findById(idInt);
			
			SimpleDateFormat mascareno = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			Date dte = mascareno.parse(cc.getData());
			txtData.setDate(dte);
			
			
			txtDesconto.setText("" + cc.getDesconto());
			txtIdCliente.setText("" + cc.getFk_idCliente());
			txtValorTotal.setText("" + cc.getValorTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void listar() throws SQLException {
		this.modelo.setRowCount(0);
		Connection conn;
		try {
			conn = br.edu.etec.persistence.JdbcUtil.getConnection();
			VendasJdbcDAO vendasJdbcDAO = new VendasJdbcDAO(conn);
			List<Vendas> list = vendasJdbcDAO.listar();
			for (int i = 0; i < list.size(); i++) {
				this.modelo.addRow(new Object[] {list.get(i).getId(),list.get(i).getFk_idCliente(),list.get(i).getValorTotal(),list.get(i).getDesconto(),list.get(i).getData()});			
				/*String id = Integer.toString(list.get(i).getId());
				int fk_idCliente = list.get(i).getFk_idCliente();
				strArr[i] = id + " - " + fk_idCliente;*/
			}
			TelaCadVendas.this.setarIds();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void setarIds() throws SQLException {
		Connection conn;
		try {
			conn = br.edu.etec.persistence.JdbcUtil.getConnection();
			VendasJdbcDAO clienteJdbcDAO = new VendasJdbcDAO(conn);
			List<Id> list = clienteJdbcDAO.listarIds();
			String[] strArr = new String[list.size()];
			this.txtId.setModel(new DefaultComboBoxModel());
			for (int i = 0; i < list.size(); i++) {
				this.txtId.addItem(list.get(i).getId());
				/*
				 * int id = list.get(i).getId(); String nome = list.get(i).getNome(); strArr[i]
				 * = id + " - " + nome;
				 */
			}
			//this.list.setListData(strArr);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}