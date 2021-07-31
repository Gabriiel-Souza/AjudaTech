package br.com.ajudatech.main;

public class Chamado {
	private static int num = 1;
	private int numChamado;
	private Tecnico tecnico;
	private Cliente cliente;
	private Double valor;
	private boolean avaliado = false;
	private int avaliacao;

	public Chamado(Cliente cliente, Tecnico tecnico, Double valor) {
		setNumChamado();
		setTecnico(tecnico);
		setCliente(cliente);
		setValor(valor);
	}

	public int getNumChamado() {
		return numChamado;
	}

	public void setNumChamado() {
		this.numChamado = num;
		num++;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		if (tecnico != null) {
			this.tecnico = tecnico;
		} else {
			throw new IllegalArgumentException("T�cnico inv�lido");
		}

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		if (cliente != null) {
			this.cliente = cliente;
		} else {
			throw new IllegalArgumentException("Cliente inv�lido");
		}
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		if (valor > 0) {
			this.valor = valor;
		} else {
			throw new IllegalArgumentException("Valor inserido � inv�lido!");
		}
	}

	public boolean isAvaliado() {
		return avaliado;
	}

	public void setAvaliado() {
		if (!this.avaliado) {
			this.avaliado = true;
		} else {
			throw new IllegalArgumentException("Chamado j� foi avaliado com " + this.avaliacao + " estrelas!");
		}

	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		if (avaliacao >= 0 && avaliacao <= 5) {
			this.avaliacao = avaliacao;
		} else {
			throw new IllegalArgumentException("A nota deve ir de 1 a 5 estrelas");
		}
	}

}
