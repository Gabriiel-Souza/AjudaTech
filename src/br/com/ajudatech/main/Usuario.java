package br.com.ajudatech.main;

public interface Usuario {

	// Função para excluir a conta
	public void excluirConta(App app);

	// Função para chamar outro usuário
	public void solicitarUsuario(App app);

	// Função para ver os chamados
	public void verChamados (App app);
}
