package br.univel.Trabalho1Bim;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class TabelaGeradoraPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Voce deve trocar a url no BancoPostGre no Metodo Conectar
					// Aqui voce passa o nome da classe que deseja criar a tela
					// Para as funcoes deletar e atualizar funcionar voce deve
					// atribuir a PK na coluna da sua classe POJO
					TabelaGeradoraPrincipal frame = new TabelaGeradoraPrincipal(new Cachorro());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TabelaGeradoraPrincipal(Object o) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		UtilTela ut = new UtilTela();
		this.setContentPane(ut.utilTela(o));
	}

}
