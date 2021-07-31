package br.com.ajudatech.utilities;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import br.com.ajudatech.main.Cliente;
import br.com.ajudatech.main.Tecnico;
import br.com.ajudatech.main.Usuario;
import br.com.ajudatech.main.Admin;
import br.com.ajudatech.main.App;
import br.com.ajudatech.main.Chamado;

public class Utils {

	public static String selecionaTipoVeiculo() {
		String[] tipos = { "Carro", "Moto", "Sair" };
		int op = View.exibirMenu("Qual o seu veículo?", tipos);

		if (op == 0) {
			return "Carro";
		} else if (op == 1) {
			return "Moto";
		} else {
			return null;
		}
	}

	public static void cadastrarPessoa(App app) {
		boolean terminar = false, acessoLiberado = false;
		String[] opcoesUsuario = { "Cliente", "Técnico", "Administrador", "Voltar" };
		int op;
		do {
			try {
				op = View.exibirMenu("Deseja cadastrar qual conta?", opcoesUsuario);
				// Cria o campo Nome
				JTextField campoNome = new JTextField(10);
				// Cria o campo CPF
				JTextField campoCpf = new JTextField(10);
				// Cria o campo Idade
				JTextField campoIdade = new JTextField(10);
				// Cria o campo Nome de Usuário
				JTextField campoNomeUsuario = new JTextField(10);
				// Cria o campo Cidade
				JTextField campoCidade = new JTextField(10);
				// Cria o campo de senha e faz com que os caracteres mostrados sejam
				// substituídos por '•'
				JPasswordField campoSenha = new JPasswordField(10);
				campoSenha.setEchoChar('•');
				// Cria o campo ID
				JTextField campoId = new JTextField(10);
				// Cria o campo Modelo do veículo
				JTextField campoModeloVeiculo = new JTextField(10);
				// Cria o campo Placa do veículo
				JTextField campoPlacaVeiculo = new JTextField(10);
				// Cria a estrutura da janela
				Object[] campo = null;
				Object[] campo2 = null;
				// Cliente
				if (op == 0) {
					Object[] temp = { "Nome", campoNome, "Cidade", campoCidade, "CPF", campoCpf, "Idade", campoIdade,
							"Nome de usuário (6-15 caracteres)", campoNomeUsuario, "Senha (8-15 caracteres)",
							campoSenha };
					campo = temp;
					acessoLiberado = true;
					// Técnico
				} else if (op == 1) {
					Usuario u = fazerLogin(app);
					if (u != null) {
						if (u instanceof Admin) {
							Object[] temp = { "Modelo do veículo", campoModeloVeiculo, "Placa do veículo",
									campoPlacaVeiculo };
							campo = temp;
							Object[] temp2 = { "Nome", campoNome, "Cidade", campoCidade, "ID (5-20 caracteres)",
									campoId, "CPF", campoCpf, "Idade", campoIdade, "Nome de usuário (6-15 caracteres)",
									campoNomeUsuario, "Senha (8-15 caracteres)", campoSenha };
							campo2 = temp2;
							acessoLiberado = true;
						} else {
							View.exibirErro("ACESSO NEGADO\nApenas Administradores podem cadastrar novos funcionários.",
									"");
						}
					} else {
						View.exibirErro("Nome de usuário e/ou senha incorretos", "");
					}

					// Administrador
				} else if (op == 2) {
					Usuario u = fazerLogin(app);
					if (u != null) {
						if (u instanceof Admin) {
							Object[] temp = { "Nome", campoNome, "Cidade", campoCidade, "ID (5-20 caracteres)", campoId,
									"CPF", campoCpf, "Idade", campoIdade, "Nome de usuário (6-15 caracteres)",
									campoNomeUsuario, "Senha (8-15 caracteres)", campoSenha };
							campo = temp;
							acessoLiberado = true;
						} else {
							View.exibirErro("ACESSO NEGADO\nApenas Administradores podem cadastrar novos funcionários.",
									"");
						}
					}
				} else {
					throw new NullPointerException();
				}
				if (acessoLiberado) {
					// Cadastro Padrão
					String tipoVeiculo = null;
					if (op == 1) {
						tipoVeiculo = selecionaTipoVeiculo();
						if (tipoVeiculo == null) {
							throw new NullPointerException();
						}
					}
					int result = JOptionPane.showConfirmDialog(null, campo, "Cadastro", JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						if (!app.existeUsuario(campoNomeUsuario.getText())) {
							// Pega o valor da senha
							String senha = String.valueOf(campoSenha.getPassword());
							// Cadastro cliente
							if (op == 0) {
								Cliente cliente = new Cliente(campoNome.getText(), campoCpf.getText(),
										Integer.parseInt(campoIdade.getText()), campoNomeUsuario.getText(), senha,
										campoCidade.getText());
								app.addCliente(cliente);
								terminar = true;
								// Cadastro técnico
							} else if (op == 1) {
								// Cadastro de Técnico
								int result2 = JOptionPane.showConfirmDialog(null, campo2, "Cadastro",
										JOptionPane.OK_CANCEL_OPTION);
								if (result2 == JOptionPane.OK_OPTION) {
									Tecnico tecnico = new Tecnico(campoId.getText(), campoNome.getText(),
											campoCpf.getText(), Integer.parseInt(campoIdade.getText()),
											campoNomeUsuario.getText(), senha, campoCidade.getText(), tipoVeiculo,
											campoModeloVeiculo.getText(), campoPlacaVeiculo.getText());
									app.addFuncionario(tecnico);
									terminar = true;
								} else {
									throw new NullPointerException();
								}

							} else {
								// Cadastro de Administrador
								Admin admin = new Admin(campoId.getText(), campoNome.getText(), campoCpf.getText(),
										Integer.parseInt(campoIdade.getText()), campoNomeUsuario.getText(), senha,
										campoCidade.getText());
								app.addFuncionario(admin);
								terminar = true;
							}

						} else {
							View.exibirMensagem("Nome de usuário já existente", "");
						}
					} else {
						throw new NullPointerException();
					}
				}
			} catch (NumberFormatException e) {
				View.exibirErro("Idade inserida é inválida", "Erro");
			} catch (NullPointerException e) {
				terminar = true;
			} catch (IllegalArgumentException e) {
				View.exibirErro(e.getMessage(), "Erro");
			}
		} while (!terminar);
	}

	public static Usuario fazerLogin(App app) {
		// Cria o campo Nome de usuário
		JTextField campoNomeUsuario = new JTextField(10);
		// Cria o campo de senha e faz com que os caracteres mostrados sejam
		// substituídos por '•'
		JPasswordField campoSenha = new JPasswordField(10);

		Object[] campo = { "Nome de Usuário", campoNomeUsuario, "Senha", campoSenha };

		int result = JOptionPane.showConfirmDialog(null, campo, "Login", JOptionPane.OK_CANCEL_OPTION, 0,
				new ImageIcon("images/icon.png"));
		if (result == JOptionPane.OK_OPTION) {
			// Pega o valor da senha
			String senha = String.valueOf(campoSenha.getPassword());
			Usuario u = app.encontraUsuario(campoNomeUsuario.getText(), senha);
			return u;
		}
		return null;
	}

	public static void criarChamado(App app, Cliente cliente, Tecnico tecnico, Double valor) {
		Chamado chamado = new Chamado(cliente, tecnico, valor);
		app.addChamado(chamado);
	}

	public static String format(Double numero) {
		return String.format("%.2f", numero);
	}
}
