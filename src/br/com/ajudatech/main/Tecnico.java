package br.com.ajudatech.main;

import br.com.ajudatech.utilities.View;

public class Tecnico extends Pessoa implements Usuario, Funcionario {
	private String id;
	private String tipoVeiculo;
	private String modeloVeiculo;
	private String placaVeiculo;

	public Tecnico(String id, String nome, String cpf, int idade, String nomeUsuario, String senha, String cidade,
			String tipoVeiculo, String modeloVeiculo, String placaVeiculo) {
		super(nome, cpf, idade, nomeUsuario, senha, cidade);
		setTipoVeiculo(tipoVeiculo);
		setModeloVeiculo(modeloVeiculo);
		setPlacaVeiculo(placaVeiculo);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		if (id.length() >= 5) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("ID inserido � inv�lido, deve conter no m�nimo 5 caracteres");
		}

	}

	public String getTipoVeiculo() {
		return this.tipoVeiculo;
	}

	public void setTipoVeiculo(String tipoVeiculo) {
		if (tipoVeiculo.equals("Moto") || tipoVeiculo.equals("Carro")) {
			this.tipoVeiculo = tipoVeiculo;
		} else {
			throw new IllegalArgumentException("Tipo de ve�culo � inv�lido");
		}
	}

	public String getModeloVeiculo() {
		return this.modeloVeiculo;
	}

	public void setModeloVeiculo(String modeloVeiculo) {
		if (!modeloVeiculo.isEmpty()) {
			this.modeloVeiculo = modeloVeiculo;
		} else {
			throw new IllegalArgumentException("Modelo de ve�culo inv�lido");
		}
	}

	public String getPlacaVeiculo() {
		return this.placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		if (placaVeiculo.length() == 7) {
			this.placaVeiculo = placaVeiculo;
		} else {
			throw new IllegalArgumentException("Placa do ve�culo inv�lido");
		}
	}

	@Override
	public void excluirConta(App app) {
		app.removeFuncionario(this);
	}

	@Override
	public void solicitarUsuario(App app) {
		boolean sair = false;
		String[] nomeClientes = app.pegaNomeUsuarios(this);
		if (nomeClientes[0] != null) {
			do {
				String nomeCliente = View.selecionaUsuario("Clientes", nomeClientes);
				Cliente cliente = app.pesquisaCliente(nomeCliente);
				if (cliente != null) {
					String[] servicos = { "Chat", "Informa��es", "Voltar" };

					int op = View.exibirMenu("Qual servi�o deseja utilizar", servicos);
					switch (op) {
					case 0:
						String msg = View.solicitarString("Mensagem");
						if (msg != null) {
							View.exibirMensagem("Mensagem enviada para " + nomeCliente, "Chat");
						}
						break;
					case 1:
						View.exibirMensagem(
								"Informa��es" + "\nNome: " + cliente.getNome() + "\nCidade: " + cliente.getCidade(),
								"");
						break;
					case 2:
						sair = true;
					}
				}
			} while (!sair);
		} else {
			View.exibirErro("Voc� n�o atendeu nenhum cliente ainda!", "");
		}

	}

	@Override
	public void verChamados(App app) {
		app.mostrarChamados(this);
	}

	@Override
	public void setIdade(int idade) {
		if (idade >= 18) {
			this.idade = idade;
		} else {
			throw new IllegalArgumentException("Funcion�rio deve ter no m�nimo 18 anos!");
		}
	}

}