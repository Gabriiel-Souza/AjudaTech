package br.com.ajudatech.main;

public interface Usuario {

	// Fun��o para excluir a conta
	public void excluirConta(App app);

	// Fun��o para chamar outro usu�rio
	public void solicitarUsuario(App app);

	// Fun��o para ver os chamados
	public void verChamados (App app);
}
