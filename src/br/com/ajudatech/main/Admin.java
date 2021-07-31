package br.com.ajudatech.main;

import br.com.ajudatech.utilities.View;

public class Admin extends Pessoa implements Funcionario, Usuario {
	private String id;
	private String senhaMestra;

	public Admin(String id, String nome, String cpf, int idade, String nomeUsuario, String senha, String cidade) {
		super(nome, cpf, idade, nomeUsuario, senha, cidade);
		setId(id);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		if (id.length() >= 5 && id.length() <= 20) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("Id deve conter de 5 a 20 caracteres...");
		}
	}

	@Override
	public void setIdade(int idade) {
		if (idade >= 18) {
			this.idade = idade;
		} else {
			throw new IllegalArgumentException("Administrador deve possuir 18 anos ou mais");
		}
	}

	@Override
	public void verChamados(App app) {
		String[] nomes = app.pegaNomeUsuarios(this);
		String[] nomeClientes = new String[1];
		String[] nomeTecnicos = new String[1];
		String[] temp = null;
		String[] opcoesUsuarios = { "Clientes", "Técnicos", "Voltar" };
		int i = 0, j, k;
		while (!nomes[i].equals("|")) {
			nomeClientes[i] = nomes[i];
			temp = nomeClientes;
			nomeClientes = new String[nomeClientes.length + 1];
			for (j = 0; j < temp.length; j++) {
				nomeClientes[j] = temp[j];
			}
			i++;
		}
		nomeClientes = temp;
		i++;
		j = 0;
		while (i < nomes.length) {
			nomeTecnicos[j] = nomes[i];
			temp = nomeTecnicos;
			nomeTecnicos = new String[nomeTecnicos.length + 1];
			for (k = 0; k < temp.length; k++) {
				nomeTecnicos[k] = temp[k];
			}
			i++;
		}
		nomeTecnicos = temp;

		boolean sair = false;

		try {
			do {
				int opUsuario = View.exibirMenu("Qual usuário deseja verificar?", opcoesUsuarios);
				switch (opUsuario) {
				case 0:
					String nomeCliente = View.selecionaUsuario("Ver chamados de: ", nomeClientes);
					Cliente c = app.pesquisaCliente(nomeCliente);
					app.mostrarChamados(c);
					break;
				case 1:
					String nomeTecnico = View.selecionaUsuario("Ver chamados de: ", nomeTecnicos);
					Tecnico t = app.pesquisaTecnico(nomeTecnico);
					app.mostrarChamados(t);
					break;
				case 2:
					sair = true;
				}

			} while (!sair);
		} catch (NullPointerException e) {
		}
	}

	public String getSenhaMestra() {
		return senhaMestra;
	}

	public void setSenhaMestra(String senhaMestra) {
		if (senhaMestra.length() >= 8 && senhaMestra.length() <= 15) {
			this.senhaMestra = senhaMestra;
		} else {
			throw new IllegalArgumentException("Senha deve conter de 8 a 15 caracteres");
		}
	}

	@Override
	public void excluirConta(App app) {
		app.removeFuncionario(this);
	}

	@Override
	public void solicitarUsuario(App app) {
		boolean terminar = false;
		String[] opcoesUsuarios = { "Clientes", "Técnicos", "Sair" };
		try {
			do {
				int i = 0, j = 0, k, tam;
				String[] nomesClientes = new String[1];
				String[] nomesTecnicos = new String[1];
				// Pega os nomes de todos os clientes e técnicos
				String[] nomes = app.pegaNomeUsuarios(this);
				// Pega os nomes dos clientes
				while (!nomes[i].equals("|") && i < nomes.length) {
					nomesClientes[i] = nomes[i];
					tam = nomesClientes.length;
					String[] temp = nomesClientes;
					nomesClientes = new String[tam + 1];
					for (k = 0; k < temp.length; k++) {
						nomesClientes[k] = temp[k];
					}
					i++;
				}
				if (nomesClientes.length > 1) {
					tam = nomesClientes.length;
					String[] temp = nomesClientes;
					nomesClientes = new String[tam - 1];
					for (k = 0; k < nomesClientes.length; k++) {
						nomesClientes[k] = temp[k];
					}
				}
				i++;
				// Pega os nomes dos Técnicos
				while (i < nomes.length) {
					nomesTecnicos[j] = nomes[i];
					tam = nomesTecnicos.length;
					String[] temp = nomesTecnicos;
					nomesTecnicos = new String[tam + 1];
					for (k = 0; k < temp.length; k++) {
						nomesTecnicos[j] = temp[k];
					}
					i++;
					j++;
				}
				if (nomesTecnicos.length > 1) {
					tam = nomesTecnicos.length;
					String[] temp = nomesTecnicos;
					nomesTecnicos = new String[tam - 1];
					for (k = 0; k < nomesTecnicos.length; k++) {
						nomesTecnicos[k] = temp[k];
					}
				}
				int opUsuario = View.exibirMenu("Qual usuário deseja verificar?", opcoesUsuarios);
				// Clientes
				if (opUsuario == 0) {
					if (nomesClientes[0] != null) {
						String nomeCliente = View.selecionaUsuario("Clientes", nomesClientes);
						Cliente cliente = app.pesquisaCliente(nomeCliente);
						if (cliente != null) {
							String[] servicos = { "Chat", "Informações", "Excluir", "Voltar" };

							int op = View.exibirMenu("Qual serviço deseja utilizar", servicos);
							switch (op) {
							case 0:
								String msg = View.solicitarString("Mensagem");
								if (msg != null) {
									View.exibirMensagem("Mensagem enviada para " + nomeCliente, "Chat");
								}
								break;
							case 1:
								View.exibirMensagem("Informações" + "\nNome: " + cliente.getNome() + "\nCidade: "
										+ cliente.getCidade(), "");
								break;
							case 2:
								int confirmacao = View.solicitarConfirmacao(
										"Tem certeza que deseja remover " + cliente.getNome() + " do banco de dados?");
								if (confirmacao == 1) {
									cliente.excluirConta(app);
								}
							}
						}
					} else {
						View.exibirErro("Nenhum cliente está cadastrado no sistema!", "");
					}
				} else if (opUsuario == 1) {
					// Técnicos
					if (nomesTecnicos[0] != null) {
						String nomeTecnico = View.selecionaUsuario("Técnicos", nomesTecnicos);
						Tecnico tecnico = app.pesquisaTecnico(nomeTecnico);
						if (tecnico != null) {
							String[] servicos = { "Chat", "Informações", "Excluir", "Voltar" };

							int op = View.exibirMenu("Qual serviço deseja utilizar", servicos);
							switch (op) {
							case 0:
								String msg = View.solicitarString("Mensagem");
								if (msg != null) {
									View.exibirMensagem("Mensagem enviada para " + nomeTecnico, "Chat");
								}
								break;
							case 1:
								View.exibirMensagem("Informações" + "\nNome:" + tecnico.getNome() + "\nCidade: "
										+ tecnico.getCidade() + "\n" + tecnico.getTipoVeiculo() + ": "
										+ tecnico.getModeloVeiculo() + "\n" + "Placa: " + tecnico.getPlacaVeiculo(),
										"");
								break;
							case 2:
								int confirmacao = View.solicitarConfirmacao(
										"Tem certeza que deseja remover " + tecnico.getNome() + " do banco de dados?");
								if (confirmacao == 1) {
									tecnico.excluirConta(app);
								}
							}
						}
					} else {
						View.exibirErro("Nenhum técnico está cadastrado no sistema!", "");
					}
				} else {
					terminar = true;
				}

			} while (!terminar);
		} catch (NullPointerException e) {

		} catch (IllegalArgumentException e) {
			View.exibirErro(e.getMessage(), "");
		}
	}
}
