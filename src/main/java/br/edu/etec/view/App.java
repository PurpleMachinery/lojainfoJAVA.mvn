package br.edu.etec.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class App extends JFrame {
	TelaCadClientes tlaCadCli = new TelaCadClientes();
	TelaCadVendas tlaCadVdas = new TelaCadVendas();
	TelaCadHardware tlaCadHard = new TelaCadHardware();
	private JMenuBar menuBar;

	public App() throws ParseException {
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(800, 600);
		// https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html#border
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		configuraMenu();
		this.pack();
	}

	private void configuraMenu() throws ParseException {
		this.menuBar = new JMenuBar();
		JMenu menuCadastros = new JMenu("CADASTROS");
		JMenuItem menuItemClientes = new JMenuItem("Clientes");
		menuItemClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("menuClieActionPerformed...");
				App.this.tlaCadCli.txtId.setPreferredSize(new Dimension(50, 27));
				App.this.tlaCadHard.setVisible(false);
				App.this.tlaCadVdas.setVisible(false);
				App.this.tlaCadCli.setVisible(true);
				App.this.getContentPane().add(App.this.tlaCadCli, BorderLayout.CENTER);
				App.this.pack();
				System.out.println("tlaCadCli_visibility='true'");
			}
		});
		menuCadastros.add(menuItemClientes);

		JMenuItem menuItemHardware = new JMenuItem("Hardware");
		menuItemHardware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("menuHardActionPerformed...");
				App.this.tlaCadHard.txtId.setPreferredSize(new Dimension(170, 50));
				App.this.tlaCadCli.setVisible(false);
				App.this.tlaCadVdas.setVisible(false);
				App.this.tlaCadHard.setVisible(true);
				App.this.getContentPane().add(App.this.tlaCadHard, BorderLayout.CENTER);
				App.this.pack();
				System.out.println("tlaCadHard_visibility='true'");
			}
		});

		menuCadastros.add(menuItemHardware);

		JMenuItem menuItemVenda = new JMenuItem("Vendas");
		menuItemVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.this.tlaCadVdas.txtId.setPreferredSize(new Dimension(170, 50));;
				System.out.println("menuVendasActionPerformed...");
				App.this.tlaCadCli.setVisible(false);
				App.this.tlaCadHard.setVisible(false);
				App.this.tlaCadVdas.setVisible(true);
				App.this.getContentPane().add(App.this.tlaCadVdas, BorderLayout.CENTER);
				App.this.pack();
				System.out.println("tlaCadVdas_visibility='true'");
			}
		});

		menuCadastros.add(menuItemVenda);

		this.menuBar.add(menuCadastros);
		this.getContentPane().add(menuBar, BorderLayout.NORTH);
	}

	public static void main(String[] args) throws ParseException {
		App app = new App();
	}
}
