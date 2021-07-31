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
			throw new IllegalArgumentException("A idade m�nima para se usar o app � de 14 anos");
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
					String nomeTecnico = View.selecionaUsuario("Funcion�rios perto de voc�", tecnicos);
					Tecnico tecnico = app.pesquisaTecnico(nomeTecnico);
					if (tecnico != null) {
						String[] servicos = { "Chamar t�cnico", "Informa��es", "Voltar" };
						int op = View.exibirMenu("Qual servi�o deseja utilizar", servicos);
						switch (op) {
						case 0:
							Random rand = new Random();
							Double valor = 15.00 + (35.00 - 15.00) * rand.nextDouble();
							String valorFormatted = Utils.format(valor);
							int confirmacao = View.solicitarConfirmacao(
									"Valor do chamado: R$" + valorFormatted + "\nDeseja confirmar?");
							if (confirmacao == 1) {
								Utils.criarChamado(app, this, tecnico, valor);
								View.exibirMensagem("Chamado confirmado!\n Por favor aguarde o t�cnico...", "");
								terminar = true;
							} else {
								View.exibirErro("Chamado cancelado", "");
							}

							break;
						case 1:
							View.exibirMensagem("Informa��es" + "\nNome: " + tecnico.getNome() + "\n"
									+ tecnico.getTipoVeiculo() + ": " + tecnico.getModeloVeiculo() + "\n" + "Placa: "
									+ tecnico.getPlacaVeiculo(), "");
							break;
						case 2:
							terminar = true;
						}

					}

				} else {
					View.exibirErro(
							"Infelizmente n�o encontramos nenhum t�cnico na sua regi�o :(\n"
									+ "Estamos trabalhando para que t�cnicos da sua regi�o venham para o AjudaTech!",
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
				String nomeTecnico = View.selecionaUsuario("T�cnicos esperando avalia��o", nomesTecnicos);
				Tecnico tecnico = app.pesquisaTecnico(nomeTecnico);
				Chamado chamado = app.pesquisaChamadoPendente(this.getNome(), tecnico.getNome());
				if (chamado != null) {
					String[] notas = { "0", "1", "2", "3", "4", "5" };
					int nota = View.exibirMenu(
							"O qu�o satisfeito voc� est� com o atendimento de " + tecnico.getNome() + "?", notas);
					chamado.setAvaliacao(nota);
					chamado.setAvaliado();
					View.exibirMensagem("Muito obrigado pela avalia��o!", "");
				} else {
					View.exibirErro("Nenhum chamado encontrado", "");
				}
			} else {
				View.exibirErro("N�o h� t�cnicos a serem avaliados no momento!", "");
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException e) {
			View.exibirErro(e.getMessage(), "");
		}
	}

}
