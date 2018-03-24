package br.edu.etec.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public abstract class TelaDeCadastro extends JPanel {
	JPanel painelDeBotoes = new JPanel();
	JPanel painelParaCampos = new JPanel();
	JPanel painelListagem = new JPanel();
	JButton btnSalvar = new JButton("Salvar");
	JButton btnAlterar = new JButton("Alterar");
	JButton btnListar = new JButton("Listar");
	JButton btnExcluir = new JButton("Excluir");
	JButton btnLimpar = new JButton("Limpar");
	JButton btnCancelar = new JButton("Cancelar");
	JTextField txtId = new JTextField("Digite Id Para Alterar");

	DefaultTableModel modelo = new DefaultTableModel();
	JTable tabela = new JTable();

	public TelaDeCadastro(int nLinhas, int nColunas, String[] camposListagem) {
		// https://docs.oracle.com/javase/tutorial/uiswing/layout/layoutlist.html
		// https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);

		// https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
		GridLayout layoutParaCampos = new GridLayout(nLinhas, nColunas);
		painelParaCampos.setLayout(layoutParaCampos);
		this.add(painelParaCampos, BorderLayout.NORTH);

		this.painelDeBotoes = new JPanel();
		this.btnSalvar = new JButton("Salvar");
		this.btnLimpar = new JButton("Limpar");
		this.btnCancelar = new JButton("Cancelar");
		this.painelDeBotoes.add(btnSalvar);
		this.painelDeBotoes.add(btnAlterar);
		this.painelDeBotoes.add(btnListar);
		this.painelDeBotoes.add(btnLimpar);
		this.painelDeBotoes.add(btnExcluir);
		this.painelDeBotoes.add(btnCancelar);
		this.painelDeBotoes.add(txtId);
		this.add(painelDeBotoes, BorderLayout.CENTER);
		
		for (int i = 0; i < camposListagem.length; i++) {
			modelo.addColumn(camposListagem[i]);
		}
		tabela.setModel(modelo);
		tabela.setVisible(true);
		painelListagem.add(new JScrollPane(tabela), BorderLayout.CENTER);
		this.add(painelListagem, BorderLayout.SOUTH);

	}

	abstract void limparFormulario() throws SQLException;

	abstract void salvar() throws SQLException;

	abstract void cancelar() throws SQLException;

	abstract void alterar() throws SQLException;

	abstract void excluir() throws SQLException;

}