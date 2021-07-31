package br.com.ajudatech.utilities;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class View {

	public static void exibirMensagem(String msg, String titulo) {
		JOptionPane.showMessageDialog(null, msg, titulo, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon("images/icon.png"));
	}

	public static int exibirMenu(String msg, String[] options) {
		int escolha = JOptionPane.showOptionDialog(null, msg, "Menu", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/icon.png"), options, options[0]);
		return escolha;
	}

	public static void exibirErro(String msg, String titulo) {
		JOptionPane.showMessageDialog(null, msg, titulo, JOptionPane.ERROR_MESSAGE);
	}

	public static String solicitarString(String msg) {
		String nome = JOptionPane.showInputDialog(null, msg, null, 0, new ImageIcon("images/icon.png"), null, null)
				.toString();
		return nome;
	}

	public static int solicitarInt(String msg) {

		String nome = JOptionPane.showInputDialog(null, msg, null, 0, new ImageIcon("images/icon.png"), null, null)
				.toString();
		if (nome != null) {
			int numero = Integer.parseInt(nome);
			return numero;
		}
		throw new NullPointerException();

	}

	public static double solicitarDouble(String msg) {
		String nome = JOptionPane.showInputDialog(null, msg, null, 0, new ImageIcon("images/icon.png"), null, null)
				.toString();
		if (nome != null) {
			double numero = Double.parseDouble(nome);
			return numero;
		}
		throw new NullPointerException();

	}

	public static int solicitarConfirmacao(String msg) {
		Object[] options = { "Sim", "Não" };
		int option = JOptionPane.showOptionDialog(null, msg, "Sair", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, new ImageIcon("images/icon.png"), options, options[0]);
		if (option == JOptionPane.YES_OPTION) {
			return 1;
		} else {
			return 0;
		}
	}

	public static String selecionaUsuario(String msg, String[] usuarios) {
		String item = (String) JOptionPane.showInputDialog(null, msg, "Opçao", JOptionPane.INFORMATION_MESSAGE, null,
				usuarios, usuarios[0]);

		if (item == null) {
			throw new NullPointerException();
		}

		return item;
	}

}
