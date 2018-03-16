package br.edu.etec.lojainformatica;

import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Hardware;
import model.Hardware;
import persistence.HardwareJdbcDAO;

public class TelaCadHardware extends TelaDeCadastro {
	List list = new List();
	Hardware hardware = new Hardware();

	JLabel lbDescricao = new JLabel("Descrição");
	JTextField txtDescricao = new JTextField();

	JLabel lbQtdAtual = new JLabel("QtdAtual");
	JTextField txtQtdAtual = new JTextField();

	JLabel lbQtdMinima = new JLabel("QtdMinima");
	JTextField txtQtdMinima = new JTextField();

	JLabel lbPrecoUnit = new JLabel("Preco Unitario");
	JTextField txtPrecoUnit = new JTextField();

	public TelaCadHardware() {
		super(4, 2);
		this.painelParaCampos.add(lbDescricao);
		this.painelParaCampos.add(txtDescricao);

		this.painelParaCampos.add(lbPrecoUnit);
		this.painelParaCampos.add(txtPrecoUnit);

		this.painelParaCampos.add(lbQtdAtual);
		this.painelParaCampos.add(txtQtdAtual);

		this.painelParaCampos.add(lbQtdMinima);
		this.painelParaCampos.add(txtQtdMinima);
		System.out.println("terminando de de adicionar os campos, add agora actionlistener...");

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		this.painelListagem.add(list);

		this.btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadHardware.this.limparFormulario();
			}
		});
		this.btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TelaCadHardware.this.salvar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		this.btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadHardware.this.cancelar();
			}
		});

		this.btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TelaCadHardware.this.alterar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		/*this.btnListar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					TelaCadHardware.this.listar();
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
		this.txtDescricao.setText("");
		this.txtPrecoUnit.setText("");
		this.txtQtdAtual.setText("");
		this.txtQtdMinima.setText("");
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
			this.hardware.setDescricao(this.txtDescricao.getText());
			double PrecoUnitario = Double.parseDouble(this.txtPrecoUnit.getText());
			this.hardware.setPrecoUnitario(PrecoUnitario);
			int QtdAtual = Integer.parseInt(this.txtQtdAtual.getText());
			this.hardware.setQtdAtual(QtdAtual);
			int QtdMinima = Integer.parseInt(this.txtQtdMinima.getText());
			this.hardware.setQtdMinima(QtdMinima);
			Connection connection = persistence.JdbcUtil.getConnection();
			persistence.HardwareJdbcDAO hardwareJdbcDAO = new HardwareJdbcDAO(connection);
			if (salvarOuAlterar.equals("salvar")) {
				hardwareJdbcDAO.salvar(this.hardware);
			} else {
				this.hardware.setId(idInt);
				hardwareJdbcDAO.alterar(this.hardware);
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
			HardwareJdbcDAO hardwareJdbcDAO = new HardwareJdbcDAO(conn);
			Hardware cli = hardwareJdbcDAO.findById(idInt);
			if (cli != null) {
				this.txtDescricao.setText(cli.getDescricao());
				this.txtPrecoUnit.setText("" + cli.getPrecoUnitario());
				this.txtQtdAtual.setText("" + cli.getQtdAtual());
				this.txtQtdMinima.setText("" + cli.getQtdMinima());
			} else {
				JOptionPane.showMessageDialog(this, "Nao ha hardwares com esse id");
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
			HardwareJdbcDAO hardwareJdbcDAO = new HardwareJdbcDAO(conn);
			hardwareJdbcDAO.excluir(idInt);
			this.limparFormulario();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*@Override
	void listar() throws SQLException {
		Connection conn;
		try {
			conn = persistence.JdbcUtil.getConnection();
			HardwareJdbcDAO hardwareJdbcDAO = new HardwareJdbcDAO(conn);
			List<Hardware> list = hardwareJdbcDAO.listar();
			String[] strArr = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i).getId_hardware().toString();
				String nome = list.get(i).getNome();
				strArr[i] = id + " - " + nome;
			}
			this.list.setListData(strArr);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
}
