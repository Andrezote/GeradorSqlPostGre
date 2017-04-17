package br.univel.Trabalho1Bim;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import br.univel.anotacoes.AnotaColuna;

public class UtilTela {

	private List<JTextField> textFieldFiel;
	private JTable table_1;
	private int x, y;
	private int i;
	public int contaCol;
	private Object o;

	public JPanel utilTela(Object o) {
		this.o = o;
		JPanel contentPane = new JPanel();

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0,0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		inserirLabel(o, contentPane);
		JButton btnNewButton = new JButton("Inserir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inserir();
				table_1.setModel(Busca());
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.fill = GridBagConstraints.NONE;
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = x;
		gbc_btnNewButton.gridy = y;
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;

		x++;
		contentPane.add(btnNewButton, gbc_btnNewButton);

		JButton btnNewButton_1 = new JButton("Deletar");
		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Deletar();
				table_1.setModel(Busca());
			}
		});

		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = x;
		gbc_btnNewButton_1.gridy = y;
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTH;
		x++;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Buscar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_1.setModel(Busca());
			}

		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = x;
		gbc_btnNewButton_2.gridy = y;
		gbc_btnNewButton_2.anchor = GridBagConstraints.NORTH;
		x++;
		contentPane.add(btnNewButton_2, gbc_btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Atualizar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
				table_1.setModel(Busca());
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_3.gridx = x;
		gbc_btnNewButton_3.gridy = y;
		gbc_btnNewButton_3.anchor = GridBagConstraints.NORTH;
		x++;
		contentPane.add(btnNewButton_3, gbc_btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Drop Table");
		btnNewButton_4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				DroparTable();
			}
		});
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_4.fill = GridBagConstraints.NONE;
		gbc_btnNewButton_4.gridx = x;
		gbc_btnNewButton_4.gridy = y;
		gbc_btnNewButton_4.anchor = GridBagConstraints.NORTH;
		x++;
		contentPane.add(btnNewButton_4, gbc_btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Criar Tabela");
		btnNewButton_5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CriarTable();
			}
		});
		GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
		gbc_btnNewButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_5.fill = GridBagConstraints.NONE;
		gbc_btnNewButton_5.gridx = x;
		gbc_btnNewButton_5.gridy = y;
		gbc_btnNewButton_5.anchor = GridBagConstraints.NORTH;
		x++;
		y++;
		contentPane.add(btnNewButton_5, gbc_btnNewButton_5);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = y + 1;
		gbc_scrollPane.gridheight = 50;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = y;
		gbc_scrollPane.anchor = GridBagConstraints.NORTH;
		contentPane.add(scrollPane, gbc_scrollPane);

		table_1 = new JTable();

		scrollPane.setViewportView(table_1);
		table_1.setMinimumSize(new Dimension(800, 800));
		table_1.getFillsViewportHeight();
		// table_1.setModel(dataModel);

		return contentPane;

	}

	protected void CriarTable() {
		BancoPostGre p = new BancoPostGre();
		String sqlTabela = p.geraTabela(o);
		p.CriarTabela(sqlTabela);
	}

	protected void DroparTable() {
		BancoPostGre p = new BancoPostGre();
		String ps = p.getSqlDropTable(o);
		p.Inserir(ps);
	}

	protected TableModel Busca() {
		int x = 0;
		for (JTextField textF : textFieldFiel) {
			x += 1;
		}
		BancoPostGre p = new BancoPostGre();
		String ps = p.geraSqlBusca(o);
		Meutable modelo = new Meutable(x, BancoPostGre.buscarTodos(ps, o), o);
		return modelo;
	}

	protected void atualizar() {
		List<String> valores = new ArrayList<String>();
		BancoPostGre p = new BancoPostGre();
		String nColuna = checarId();
		for (JTextField textF : textFieldFiel) {
			valores.add(textF.getText());
		}
		String id = textFieldFiel.get(i).getText();
		String ps = p.getAtualizaSql(o, id, nColuna, valores);
		p.Inserir(ps);
	}

	protected void Deletar() {
		List<String> valores = new ArrayList<String>();
		BancoPostGre p = new BancoPostGre();
		String nomeColuna = checarId();
		for (JTextField textF : textFieldFiel) {
			valores.add(textF.getText());
		}
		String id = textFieldFiel.get(0).getText();
		String ps = p.getDeletarSql(o, id, nomeColuna);
		p.Inserir(ps);

	}

	protected void Inserir() {
		List<String> valores = new ArrayList<String>();
		BancoPostGre p = new BancoPostGre();
		for (JTextField textF : textFieldFiel) {
			valores.add(textF.getText());
		}
		String ps = p.getInserirSql(o, valores);
		p.Inserir(ps);
	}

	private void alinha(GridBagConstraints btn) {
		btn.anchor = GridBagConstraints.WEST;
		btn.gridwidth = GridBagConstraints.LINE_END;
		btn.fill = GridBagConstraints.BOTH;
		btn.weightx = 1;
	}

	private String checarId() {
		String nomeColuna = null;
		Field[] f = o.getClass().getDeclaredFields();
		for (Field field : f) {
			AnotaColuna ac = field.getAnnotation(AnotaColuna.class);
			if (field.isAnnotationPresent(AnotaColuna.class)) {
				if (ac.nome().isEmpty() && ac.pk()) {
					nomeColuna = field.getName().toUpperCase();
					break;
				} else {
					nomeColuna = ac.nome();
				}
			} else {
				nomeColuna = field.getName().toUpperCase();
			}
			if (ac.pk()) {
				break;
			}
		}
		return nomeColuna;

	}

	private void inserirLabel(Object o, JPanel contentPane) {
		JLabel label = null;
		Class<?> c1 = o.getClass();
		this.textFieldFiel = new ArrayList<JTextField>();
		for (Field f : c1.getDeclaredFields()) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(AnotaColuna.class)) {
				AnotaColuna a = f.getAnnotation(AnotaColuna.class);
				label = new JLabel(a.nome());
			} else {
				label = new JLabel(f.getName());
			}
			contentPane.add(label, createConstraints(x, y));
			JTextField textField = new JTextField();
			contentPane.add(textField, createConstraints(x, y));
			textField.setColumns(10);
			this.textFieldFiel.add(textField);
		}
		this.x += 1;
	}

	private Object createConstraints(int x, int y) {
		GridBagConstraints texto = new GridBagConstraints();
		texto.gridx = x;
		texto.gridy = y;
		this.y += 1;
		alinha(texto);
		return texto;
	}
}
