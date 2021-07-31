package br.com.ajudatech.utilities;

import br.com.ajudatech.main.Cliente;
import br.com.ajudatech.main.Tecnico;
import br.com.ajudatech.main.Admin;
import br.com.ajudatech.main.App;

public class Teste {

	public static void criaClasses(App app) {
		// Cria cliente
		Cliente cliente = new Cliente("Gabriel", "12345678909", 19, "123456", "12345678", "Ceilândia");
		app.addCliente(cliente);
		cliente = new Cliente("Lucas", "12345678909", 19, "654321", "12345678", "Ceilândia");
		app.addCliente(cliente);
		// Cria técnico
		Tecnico tecnico = new Tecnico("123456789", "Carlos", "09876543212", 22, "carlinhos", "carlos123", "Ceilândia",
				"Carro", "Palio", "ESBR113");
		app.addFuncionario(tecnico);
		// Cria admin
		Admin admin = new Admin("987654321", "Luciana", "12345098761", 44, "lucy0987", "lul12345", "Samambaia");
		app.addFuncionario(admin);

	}

}
