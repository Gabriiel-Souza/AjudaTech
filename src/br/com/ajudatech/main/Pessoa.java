package br.com.ajudatech.main;

public abstract class Pessoa {
	private String nome;
	private String cpf;
	public int idade;
	private String nomeUsuario;
	private String senha;
	private String cidade;

	public Pessoa(String nome, String cpf, int idade, String nomeUsuario, String senha, String cidade) {
		setNome(nome);
		setCidade(cidade);
		setCpf(cpf);
		setIdade(idade);
		setNomeUsuario(nomeUsuario);
		setSenha(senha);
	}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if (cpf.length() == 11) {
			this.cpf = cpf;
		} else {
			throw new IllegalArgumentException("CPF inserido é inválido");
		}
	}

	public int getIdade() {
		return idade;
	}

	public abstract void setIdade(int idade);

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		if (nomeUsuario.length() >= 6 && nomeUsuario.length() <= 15) {
			this.nomeUsuario = nomeUsuario;
		} else {
			throw new IllegalArgumentException("Nome de usuário inválido, deve conter de 6 a 15 caracteres");
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (senha.length() >= 8 && senha.length() <= 20) {
			this.senha = senha;
		} else {
			throw new IllegalArgumentException("Senha inválida, deve conter de 8 a 20 caracteres");
		}
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		if (!cidade.isEmpty()) {
			this.cidade = cidade;
		} else {
			throw new IllegalArgumentException("Cidade inserida é inválida");
		}
	}
}
