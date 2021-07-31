package br.com.ajudatech.main;

import java.util.ArrayList;

import br.com.ajudatech.utilities.Utils;
import br.com.ajudatech.utilities.View;

public class App {
	private String nome = "AjudaTech!";
	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
	private ArrayList<Chamado> chamados = new ArrayList<Chamado>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (!nome.isEmpty()) {
			this.nome = nome;
		} else {
			throw new IllegalArgumentException("Nome inserido é inválido");
		}
	}

	public void addCliente(Cliente cliente) {
		if (cliente != null) {
			clientes.add(cliente);
		} else {
			throw new IllegalArgumentException("Cliente é inválido");
		}
	}

	public void removeCliente(Cliente cliente) {
		if (cliente != null) {
			clientes.remove(cliente);
		} else {
			throw new IllegalArgumentException("Cliente é inválido");
		}
	}

	public void addFuncionario(Funcionario funcionario) {
		if (funcionario != null) {
			funcionarios.add(funcionario);
		} else {
			throw new IllegalArgumentException("Funcionário é inválido");
		}
	}

	public void removeFuncionario(Funcionario funcionario) {
		if (funcionario != null) {
			funcionarios.remove(funcionario);
		} else {
			throw new IllegalArgumentException("Funcionário é inválido");
		}
	}

	public void addChamado(Chamado chamado) {
		if (chamado != null) {
			chamados.add(chamado);
		} else {
			throw new IllegalArgumentException("Chamado é inválido");
		}
	}

	public void removeChamado(Chamado chamado) {
		if (chamado != null) {
			chamados.remove(chamado);
		} else {
			throw new IllegalArgumentException("Chamado é inválido");
		}
	}

	public int contaAdmin() {
		int count = 0;
		for (Funcionario funcionario : funcionarios) {
			if (funcionario instanceof Admin) {
				count++;
			}
		}
		return count;
	}

	public boolean existeUsuario(String nomeUsuario) {

		for (Cliente cliente : clientes) {
			if (cliente.getNomeUsuario().equals(nomeUsuario)) {
				return true;
			}
		}
		for (Funcionario funcionario : funcionarios) {
			if (funcionario instanceof Admin) {
				if (((Admin) funcionario).getNomeUsuario().equals(nomeUsuario)) {
					return true;
				}
			} else {
				if (((Tecnico) funcionario).getNomeUsuario().equals(nomeUsuario)) {
					return true;
				}
			}
		}
		return false;
	}

	public void mostrarChamados(Usuario usuario) {
		ArrayList<Chamado> chamadosSelecionados = new ArrayList<Chamado>();
		Chamado chamadoTemp;
		if (usuario instanceof Cliente) {
			for (Chamado chamado : chamados) {
				if (chamado.getCliente().getNome().equals(((Cliente) usuario).getNome())) {
					chamadosSelecionados.add(chamado);
				}
			}
		} else if (usuario instanceof Tecnico) {
			for (Chamado chamado : chamados) {
				if (chamado.getTecnico().getNome().equals(((Tecnico) usuario).getNome())) {
					chamadosSelecionados.add(chamado);
				}
			}
		}

		if (chamadosSelecionados.size() > 0) {
			int cont, op, situacao;
			String[] navegacao;
			for (cont = 0; cont < chamadosSelecionados.size(); cont++) {
				chamadoTemp = chamadosSelecionados.get(cont);
				if (cont == 0 && chamadosSelecionados.size() > 1) {
					String[] nav = { "Próximo", "Sair" };
					navegacao = nav;
					situacao = 0;
				} else if (cont == 0) {
					String[] nav = { "Sair" };
					navegacao = nav;
					situacao = 1;
				} else if (cont < (chamadosSelecionados.size() - 1)) {
					String[] nav = { "Anterior", "Próximo", "Sair" };
					navegacao = nav;
					situacao = 2;
				} else {
					String[] nav = { "Anterior", "Sair" };
					navegacao = nav;
					situacao = 3;
				}

				// Exibe o relatório detalhado
				String valorFormatted = Utils.format(chamadoTemp.getValor());
				op = View.exibirMenu(
						"Chamado " + chamadoTemp.getNumChamado() + "\nTécnico: " + chamadoTemp.getTecnico().getNome()
								+ "\nCliente: " + chamadoTemp.getCliente().getNome() + "\nPlaca do veículo: "
								+ chamadoTemp.getTecnico().getPlacaVeiculo() + "\nValor: " + valorFormatted,
						navegacao);
				switch (op) {
				case 0:
					if (situacao == 1) { // Sair
						throw new NullPointerException();
					} else if (situacao >= 2) { // Anterior
						cont -= 2;
					}
					break;
				case 1:
					if (situacao == 0 || situacao == 3) { // Sair
						throw new NullPointerException();
					}
					break;
				case 2: // Sair
					throw new NullPointerException();
				}
			}
		} else {
			View.exibirMensagem("Nenhum registro encontrado...", "");
		}
	}

	public String[] pegaNomeUsuarios(Cliente cliente) {
		String[] nomeTecnicos = new String[1];
		int i = 0, j;

		for (Funcionario funcionario : funcionarios) {
			if (funcionario instanceof Tecnico) {
				if (cliente.getCidade().equals(((Tecnico) funcionario).getCidade())) {
					nomeTecnicos[i] = ((Tecnico) funcionario).getNome();
					String[] temp = nomeTecnicos;
					nomeTecnicos = new String[nomeTecnicos.length + 1];
					for (j = 0; j < temp.length; j++) {
						nomeTecnicos[j] = temp[j];
					}
					i++;
				}
			}
		}
		if (nomeTecnicos.length > 1) {
			String[] temp = nomeTecnicos;
			nomeTecnicos = new String[nomeTecnicos.length - 1];
			for (i = 0; i < nomeTecnicos.length; i++) {
				nomeTecnicos[i] = temp[i];
			}
		}
		return nomeTecnicos;
	}

	public String[] pegaNomeUsuarios(Tecnico tecnico) {
		String[] nomeClientes = new String[1];
		int i = 0, j;

		for (Chamado chamado : chamados) {
			if (chamado.getTecnico().equals(tecnico)) {
				nomeClientes[i] = chamado.getCliente().getNome();
				String[] temp = nomeClientes;
				nomeClientes = new String[nomeClientes.length + 1];
				for (j = 0; j < temp.length; j++) {
					nomeClientes[j] = temp[j];
				}
				i++;
			}
		}
		if (nomeClientes.length > 1) {
			String[] temp = nomeClientes;
			nomeClientes = new String[nomeClientes.length - 1];
			for (i = 0; i < nomeClientes.length; i++) {
				nomeClientes[i] = temp[i];
			}
		}
		return nomeClientes;
	}

	public String[] pegaNomeUsuarios(Admin admin) {
		String[] nomes = new String[clientes.size() + 2];
		int i = 0, j;

		for (Cliente cliente : clientes) {
			nomes[i] = cliente.getNome();
			i++;
		}
		nomes[i] = "|";
		i++;
		for (Funcionario funcionario : funcionarios) {
			if (funcionario instanceof Tecnico) {
				nomes[i] = ((Tecnico) funcionario).getNome();
				String[] temp = nomes;
				nomes = new String[nomes.length + 1];
				for (j = 0; j < temp.length; j++) {
					nomes[j] = temp[j];
				}
				i++;
			}
		}
		if (nomes.length > 1) {
			String[] temp = nomes;
			nomes = new String[nomes.length - 1];
			for (i = 0; i < nomes.length; i++) {
				nomes[i] = temp[i];
			}
		}
		return nomes;
	}

	public Tecnico pesquisaTecnico(String nomeTecnico) {
		for (Funcionario funcionario : funcionarios) {
			if (funcionario instanceof Tecnico) {
				if (((Tecnico) funcionario).getNome().equals(nomeTecnico)) {
					return ((Tecnico) funcionario);
				}
			}
		}
		return null;
	}

	public String[] pegaTecnicosPendentes(Cliente cliente) {
		String[] nomeTecnicos = new String[1];
		int i = 0;

		for (Chamado chamado : chamados) {
			if (chamado.getCliente().equals(cliente) && !chamado.isAvaliado()) {
				nomeTecnicos[i] = chamado.getTecnico().getNome();
				String[] temp = nomeTecnicos;
				nomeTecnicos = new String[nomeTecnicos.length + 1];
				for (int j = 0; j < temp.length; j++) {
					nomeTecnicos[j] = temp[j];
				}
				i++;
			}
		}
		if (nomeTecnicos.length > 1) {
			String[] temp = nomeTecnicos;
			nomeTecnicos = new String[nomeTecnicos.length - 1];
			for (i = 0; i < nomeTecnicos.length; i++) {
				nomeTecnicos[i] = temp[i];
			}
		}
		return nomeTecnicos;
	}

	public Chamado pesquisaChamadoPendente(String nomeCliente, String nomeTecnico) {
		for (Chamado chamado : chamados) {
			if (chamado.getCliente().getNome().equals(nomeCliente)
					&& chamado.getTecnico().getNome().equals(nomeCliente)) {
				return chamado;
			}
		}
		return null;
	}

	public Usuario encontraUsuario(String nomeUsuario, String senha) {
		for (Cliente cliente : clientes) {
			if (cliente.getNomeUsuario().equals(nomeUsuario)) {
				if (cliente.getSenha().equals(senha)) {
					return cliente;
				}
				return null;
			}
		}
		for (Funcionario funcionario : funcionarios) {
			if (funcionario instanceof Tecnico) {
				if (((Tecnico) funcionario).getNomeUsuario().equals(nomeUsuario)) {
					if (((Tecnico) funcionario).getSenha().equals(senha)) {
						return ((Tecnico) funcionario);
					}
					return null;
				}
			} else {
				if (((Admin) funcionario).getNomeUsuario().equals(nomeUsuario)) {
					if (((Admin) funcionario).getSenha().equals(senha)) {
						return ((Admin) funcionario);
					}
					return null;
				}
			}
		}
		return null;
	}

	public Cliente pesquisaCliente(String nomeCliente) {
		for (Cliente cliente : clientes) {
			if (cliente.getNome().equals(nomeCliente)) {
				return cliente;
			}
		}
		return null;
	}
}