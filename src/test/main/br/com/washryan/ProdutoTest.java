package br.com.washryan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import br.com.washryan.dao.IProdutoDAO;
import br.com.washryan.dao.ProdutoDAO;
import br.com.washryan.domain.Produto;

public class ProdutoTest {

	@Test
	public void cadastrarTest() throws Exception {
		IProdutoDAO dao = new ProdutoDAO();
		
		Produto produto = new Produto();
		produto.setCodigo("P001");
		produto.setNome("Notebook Dell");
		produto.setDescricao("Notebook Dell Inspiron 15 3000");
		produto.setPreco(new BigDecimal("2500.00"));
		
		Integer qtd = dao.cadastrar(produto);
		assertTrue(qtd == 1);
		
		Produto produtoBD = dao.consultar(produto.getCodigo());
		assertNotNull(produtoBD);
		assertNotNull(produtoBD.getId());
		assertEquals(produto.getCodigo(), produtoBD.getCodigo());
		assertEquals(produto.getNome(), produtoBD.getNome());
		assertEquals(produto.getDescricao(), produtoBD.getDescricao());
		assertEquals(produto.getPreco(), produtoBD.getPreco());
		
		Integer qtdDel = dao.excluir(produtoBD);
		assertNotNull(qtdDel);
	}
	
	@Test
	public void buscarTodosTest() throws Exception {
		IProdutoDAO dao = new ProdutoDAO();
		
		// Cadastrar alguns produtos para teste
		Produto produto1 = new Produto();
		produto1.setCodigo("P002");
		produto1.setNome("Mouse Logitech");
		produto1.setDescricao("Mouse óptico sem fio");
		produto1.setPreco(new BigDecimal("89.90"));
		dao.cadastrar(produto1);
		
		Produto produto2 = new Produto();
		produto2.setCodigo("P003");
		produto2.setNome("Teclado Mecânico");
		produto2.setDescricao("Teclado mecânico RGB");
		produto2.setPreco(new BigDecimal("299.99"));
		dao.cadastrar(produto2);
		
		// Buscar todos
		List<Produto> produtos = dao.buscarTodos();
		assertNotNull(produtos);
		assertTrue(produtos.size() >= 2);
		
		// Limpar dados de teste
		dao.excluir(produto1);
		dao.excluir(produto2);
	}
	
	@Test
	public void atualizarTest() throws Exception {
		IProdutoDAO dao = new ProdutoDAO();
		
		// Cadastrar produto
		Produto produto = new Produto();
		produto.setCodigo("P004");
		produto.setNome("Monitor Samsung");
		produto.setDescricao("Monitor LED 24 polegadas");
		produto.setPreco(new BigDecimal("899.00"));
		dao.cadastrar(produto);
		
		// Atualizar produto
		produto.setNome("Monitor Samsung Curvo");
		produto.setDescricao("Monitor LED Curvo 24 polegadas");
		produto.setPreco(new BigDecimal("1199.00"));
		Integer qtdAtualizada = dao.atualizar(produto);
		assertTrue(qtdAtualizada == 1);
		
		// Verificar se foi atualizado
		Produto produtoAtualizado = dao.consultar(produto.getCodigo());
		assertNotNull(produtoAtualizado);
		assertEquals("Monitor Samsung Curvo", produtoAtualizado.getNome());
		assertEquals("Monitor LED Curvo 24 polegadas", produtoAtualizado.getDescricao());
		assertEquals(new BigDecimal("1199.00"), produtoAtualizado.getPreco());
		
		// Limpar dados de teste
		dao.excluir(produtoAtualizado);
	}
}
