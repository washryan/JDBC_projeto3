package br.com.washryan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.washryan.dao.ClienteDAO;
import br.com.washryan.dao.IClienteDAO;
import br.com.washryan.domain.Cliente;

public class ClienteTest {

	@Test
	public void cadastrarTest() throws Exception {
		IClienteDAO dao = new ClienteDAO();
		
		Cliente cliente = new Cliente();
		cliente.setCodigo("01");
		cliente.setNome("Rodrigo Pires");
		
		Integer qtd = dao.cadastrar(cliente);
		assertTrue(qtd == 1);
		
		Cliente clienteBD = dao.consultar(cliente.getCodigo());
		assertNotNull(clienteBD);
		assertNotNull(clienteBD.getId());
		assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
		assertEquals(cliente.getNome(), clienteBD.getNome());
		
		Integer qtdDel = dao.excluir(clienteBD);
		assertNotNull(qtdDel);
	}
	
	@Test
	public void buscarTodosTest() throws Exception {
		IClienteDAO dao = new ClienteDAO();
		
		// Cadastrar alguns clientes para teste
		Cliente cliente1 = new Cliente();
		cliente1.setCodigo("02");
		cliente1.setNome("Ana Silva");
		dao.cadastrar(cliente1);
		
		Cliente cliente2 = new Cliente();
		cliente2.setCodigo("03");
		cliente2.setNome("João Santos");
		dao.cadastrar(cliente2);
		
		// Buscar todos
		List<Cliente> clientes = dao.buscarTodos();
		assertNotNull(clientes);
		assertTrue(clientes.size() >= 2);
		
		// Limpar dados de teste
		dao.excluir(cliente1);
		dao.excluir(cliente2);
	}
	
	@Test
	public void atualizarTest() throws Exception {
		IClienteDAO dao = new ClienteDAO();
		
		// Cadastrar cliente
		Cliente cliente = new Cliente();
		cliente.setCodigo("04");
		cliente.setNome("Maria Oliveira");
		dao.cadastrar(cliente);
		
		// Atualizar nome
		cliente.setNome("Maria Oliveira Santos");
		Integer qtdAtualizada = dao.atualizar(cliente);
		assertTrue(qtdAtualizada == 1);
		
		// Verificar se foi atualizado
		Cliente clienteAtualizado = dao.consultar(cliente.getCodigo());
		assertNotNull(clienteAtualizado);
		assertEquals("Maria Oliveira Santos", clienteAtualizado.getNome());
		
		// Limpar dados de teste
		dao.excluir(clienteAtualizado);
	}
}
