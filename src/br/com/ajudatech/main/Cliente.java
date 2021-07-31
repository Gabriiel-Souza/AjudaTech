package br.com.ajudatech.main;

import java.util.Random;

import br.com.ajudatech.utilities.Utils;
import br.com.ajudatech.utilities.View;

public class Cliente extends Pessoa implements Usuario {

	public Cliente(String nome, String cpf, int idade, String nomeUsuario, String senha, String cidade) {
		super(nome, cpf, idade, nomeUsuario, senha, cidade);
	}

	@Override
	public void setIdade(int idade) {
		if (idade >= 14) {
			this.idade = idade;
		} else {
			throw new IllegalArgumentException("A idade mínima para se usar o app é de 14 anos");
		}
	}

	@Override
	public void verChamados(App app) {
		app.mostrarChamados(this);
	}

	@Override
	public void solicitarUsuario(App app) {
		boolean terminar = false;
		try {
			do {
				String[] tecnicos = app.pegaNomeUsuarios(this);
				if (tecnicos[0] != null) {
					String nomeTecnico = View.selecionaUsuario("Funcionários perto de você", tecnicos);
					Tecnico tecnico = app.pesquisaTecnico(nomeTecnico);
					if (tecnico != null) {
						String[] servicos = { "Chamar técnico", "Informações", "Voltar" };
						int op = View.exibirMenu("Qual serviço deseja utilizar", servicos);
						switch (op) {
						case 0:
							Random rand = new Random();
							Double valor = 15.00 + (35.00 - 15.00) * rand.nextDouble();
							String valorFormatted = Utils.format(valor);
							int confirmacao = View.solicitarConfirmacao(
									"Valor do chamado: R$" + valorFormatted + "\nDeseja confirmar?");
							if (confirmacao == 1) {
								Utils.criarChamado(app, this, tecnico, valor);
								View.exibirMensagem("Chamado confirmado!\n Por favor aguarde o técnico...", "");
								terminar = true;
							} else {
								View.exibirErro("Chamado cancelado", "");
							}

							break;
						case 1:
							View.exibirMensagem("Informações" + "\nNome: " + tecnico.getNome() + "\n"
									+ tecnico.getTipoVeiculo() + ": " + tecnico.getModeloVeiculo() + "\n" + "Placa: "
									+ tecnico.getPlacaVeiculo(), "");
							break;
						case 2:
							terminar = true;
						}

					}

				} else {
					View.exibirErro(
							"Infelizmente não encontramos nenhum técnico na sua região :(\n"
									+ "Estamos trabalhando para que técnicos da sua região venham para o AjudaTech!",
							"");
					terminar = true;
				}
			} while (!terminar);
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException e) {
			View.exibirErro(e.getMessage(), "");
		}
	}

	@Override
	public void excluirConta(App app) {
		app.removeCliente(this);
	}

	public void avaliarTecnico(App app) {
		try {
			String[] nomesTecnicos = app.pegaTecnicosPendentes(this);
			if (nomesTecnicos[0] != null) {
				String nomeTecnico = View.selecionaUsuario("Técnicos esperando avaliação", nomesTecnicos);
				Tecnico tecnico = app.pesquisaTecnico(nomeTecnico);
				Chamado chamado = app.pesquisaChamadoPendente(this.getNome(), tecnico.getNome());
				if (chamado != null) {
					String[] notas = { "0", "1", "2", "3", "4", "5" };
					int nota = View.exibirMenu(
							"O quão satisfeito você está com o atendimento de " + tecnico.getNome() + "?", notas);
					chamado.setAvaliacao(nota);
					chamado.setAvaliado();
					View.exibirMensagem("Muito obrigado pela avaliação!", "");
				} else {
					View.exibirErro("Nenhum chamado encontrado", "");
				}
			} else {
				View.exibirErro("Não há técnicos a serem avaliados no momento!", "");
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException e) {
			View.exibirErro(e.getMessage(), "");
		}
	}

}
