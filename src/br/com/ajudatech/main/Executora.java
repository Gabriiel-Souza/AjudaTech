/*
 * Nome: Gabriel Souza de Araujo
 * Objetivo: Esse sistema auxiliar� no aplicativo "AjudaTech!" criando o sistema de cadastro de clientes, tecnicos e administradores e permitindo
 * que os clientes encontrem e solicitem a visita dos t�cnicos para o conserto de seus equipamentos.
 * Obs.: Todo t�cnico dever� ter um ve�culo.
 * */

package br.com.ajudatech.main;

import br.com.ajudatech.utilities.Teste;
import br.com.ajudatech.utilities.Utils;
import br.com.ajudatech.utilities.View;

public class Executora {

	public static void main(String[] args) {

		App app = new App();
		String[] options = { "Login", "Cadastro", "Sair" };
		int op;
		boolean sair;
		Teste.criaClasses(app);

		do {
			op = View.exibirMenu("MENU", options);
			sair = false;
			switch (op) {
			case 0: // Login
				Usuario usuario = Utils.fazerLogin(app);
				if (usuario != null) {
					if (usuario instanceof Cliente) {
						do {
							String[] optionsCliente = { "Solicitar T�cnico", "Ver Chamados", "Avaliar T�cnico",
									"Excluir Conta", "Sair" };
							int optionCliente = View.exibirMenu("O que deseja fazer?", optionsCliente);
							switch (optionCliente) {
							case 0:
								((Cliente) usuario).solicitarUsuario(app);
								break;
							case 1:
								((Cliente) usuario).verChamados(app);
								break;
							case 2:
								((Cliente) usuario).avaliarTecnico(app);
								break;
							case 3:
								int confirm = View.solicitarConfirmacao(
										"Deseja realmente excluir sua conta?\nVoc� n�o poder� voltar atr�s!");
								if (confirm == 1) {
									((Cliente) usuario).excluirConta(app);
									View.exibirMensagem("Conta exclu�da... Esperamos que voc� volte algum dia :(", "");
									sair = true;
								}
								break;
							case 4:
								sair = true;
							}
						} while (!sair);
					} else if (usuario instanceof Tecnico) {
						do {
							String[] optionsTecnico = { "Solicitar Cliente", "Ver Chamados", "Excluir Conta", "Sair" };
							int optionTecnico = View.exibirMenu("O que deseja fazer?", optionsTecnico);
							switch (optionTecnico) {
							case 0:
								((Tecnico) usuario).solicitarUsuario(app);
								break;
							case 1:
								((Tecnico) usuario).verChamados(app);
								break;
							case 2:
								int confirm = View.solicitarConfirmacao(
										"Deseja realmente excluir sua conta?\nVoc� n�o poder� voltar atr�s!");
								if (confirm == 1) {
									((Tecnico) usuario).excluirConta(app);
									View.exibirMensagem("Conta exclu�da... Esperamos que voc� volte algum dia :(", "");
									sair = true;
								}
								break;
							case 3:
								sair = true;
							}
						} while (!sair);
					} else {
						String[] optionsAdmin = { "Solicitar usu�rio", "Ver chamados", "Excluir Conta", "Sair" };
						do {
							int optionAdmin = View.exibirMenu("O que deseja fazer?", optionsAdmin);
							switch (optionAdmin) {
							// Solicitar usu�rio
							case 0:
								((Admin) usuario).solicitarUsuario(app);
								break;
							case 1:
								((Admin) usuario).verChamados(app);
								break;
							// Excluir conta
							case 2:
								int qtdAdmin = app.contaAdmin();
								if (qtdAdmin > 1) {
									int confirmacao = View.solicitarConfirmacao("Tem certeza que deseja remover "
											+ ((Admin) usuario).getNome() + " do banco de dados?");
									if (confirmacao == 1) {
										((Admin) usuario).excluirConta(app);
										sair = true;
									}
								} else {
									View.exibirErro(
											"S� h� 1 administrador no sistema!\nPor favor adicione mais 1 administrador para que possa excluir essa conta",
											"");
								}

								break;
							// Voltar
							case 3:
								sair = true;
							}
						} while (!sair);
					}
				} else {
					View.exibirErro("Nome de usu�rio e/ou senha incorretos", "Usu�rio n�o encontrado");
				}
				break;
			case 1: // Cadastro
				Utils.cadastrarPessoa(app);
			}

		} while (op != 2);
	}

}
