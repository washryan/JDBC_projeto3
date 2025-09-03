package br.com.washryan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.washryan.dao.EstoqueDAO;
import br.com.washryan.dao.IEstoqueDAO;
import br.com.washryan.dao.IProdutoDAO;
import br.com.washryan.dao.ProdutoDAO;
import br.com.washryan.domain.Estoque;
import br.com.washryan.domain.Produto;
import br.com.washryan.exceptions.DAOException;
import br.com.washryan.exceptions.TipoChaveNaoEncontradaException;

public class EstoqueDAOTest {
	
	private IEstoqueDAO estoqueDao;
	private IProdutoDAO produtoDao;
	private Produto produto;

	public EstoqueDAOTest() {
		estoqueDao = new EstoqueDAO();
		produtoDao = new ProdutoDAO();
	}
	
	@Before
	public void init() throws TipoChaveNaoEncontradaException, DAOException {
		// Criar produto para teste
		produto = new Produto();
		produto.setCodigo("EST001");
		produto.setNome("Produto Estoque Teste");
		produto.setDescricao("Produto para teste de estoque");
		produto.setValor(BigDecimal.valueOf(50.00));
		produto.setCategoria("Teste");
		produtoDao.cadastrar(produto);
	}
	
	@After
	public void end() throws DAOException {
		// Limpar estoque
		Collection<Estoque> estoques = estoqueDao.buscarTodos();
		estoques.forEach(est -> {
			try {
				estoqueDao.excluir(est.getProdutoCodigo());
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
		
		// Limpar produto
		if (produto != null) {
			produtoDao.excluir(produto.getCodigo());
		}
	}
	
	@Test
	public void adicionarEstoqueNovoProduto() {
		// Adicionar estoque para produto novo
		estoqueDao.adicionarEstoque("EST001", 100);
		
		Estoque estoque = estoqueDao.consultar("EST001");
		assertNotNull(estoque);
		assertEquals("EST001", estoque.getProdutoCodigo());
		assertEquals(Integer.valueOf(100), estoque.getQuantidade());
		assertEquals(Integer.valueOf(5), estoque.getQuantidadeMinima()); // Padrão
	}
	
	@Test
	public void adicionarEstoqueProdutoExistente() throws TipoChaveNaoEncontradaException, DAOException {
		// Criar estoque inicial
		Estoque estoque = new Estoque();
		estoque.setProdutoCodigo("EST001");
		estoque.setQuantidade(50);
		estoque.setQuantidadeMinima(10);
		estoqueDao.cadastrar(estoque);
		
		// Adicionar mais estoque
		estoqueDao.adicionarEstoque("EST001", 30);
		
		Estoque estoqueAtualizado = estoqueDao.consultar("EST001");
		assertNotNull(estoqueAtualizado);
		assertEquals(Integer.valueOf(80), estoqueAtualizado.getQuantidade()); // 50 + 30
	}
	
	@Test
	public void removerEstoque() throws TipoChaveNaoEncontradaException, DAOException {
		// Criar estoque inicial
		Estoque estoque = new Estoque();
		estoque.setProdutoCodigo("EST001");
		estoque.setQuantidade(100);
		estoque.setQuantidadeMinima(10);
		estoqueDao.cadastrar(estoque);
		
		// Remover estoque (simulando venda)
		estoqueDao.removerEstoque("EST001", 25);
		
		Estoque estoqueAtualizado = estoqueDao.consultar("EST001");
		assertNotNull(estoqueAtualizado);
		assertEquals(Integer.valueOf(75), estoqueAtualizado.getQuantidade()); // 100 - 25
	}
	
	@Test
	public void verificarDisponibilidade() throws TipoChaveNaoEncontradaException, DAOException {
		// Criar estoque
		Estoque estoque = new Estoque();
		estoque.setProdutoCodigo("EST001");
		estoque.setQuantidade(50);
		estoque.setQuantidadeMinima(10);
		estoqueDao.cadastrar(estoque);
		
		// Verificar disponibilidade
		assertTrue(estoqueDao.verificarDisponibilidade("EST001", 30)); // Tem estoque
		assertTrue(estoqueDao.verificarDisponibilidade("EST001", 50)); // Quantidade exata
		assertFalse(estoqueDao.verificarDisponibilidade("EST001", 60)); // Não tem estoque suficiente
		assertFalse(estoqueDao.verificarDisponibilidade("INEXISTENTE", 10)); // Produto não existe
	}
	
	@Test
	public void verificarEstoqueBaixo() throws TipoChaveNaoEncontradaException, DAOException {
		// Criar estoque com quantidade baixa
		Estoque estoque = new Estoque();
		estoque.setProdutoCodigo("EST001");
		estoque.setQuantidade(5);
		estoque.setQuantidadeMinima(10);
		estoqueDao.cadastrar(estoque);
		
		Estoque estoqueConsultado = estoqueDao.consultar("EST001");
		assertTrue(estoqueConsultado.isEstoqueBaixo());
		
		// Adicionar estoque
		estoqueDao.adicionarEstoque("EST001", 10);
		
		Estoque estoqueAtualizado = estoqueDao.consultar("EST001");
		assertFalse(estoqueAtualizado.isEstoqueBaixo()); // Agora tem 15, acima do mínimo
	}
	
	@Test(expected = RuntimeException.class)
	public void tentarRemoverEstoqueInsuficiente() throws TipoChaveNaoEncontradaException, DAOException {
		// Criar estoque com pouca quantidade
		Estoque estoque = new Estoque();
		estoque.setProdutoCodigo("EST001");
		estoque.setQuantidade(10);
		estoque.setQuantidadeMinima(5);
		estoqueDao.cadastrar(estoque);
		
		// Tentar remover mais do que tem
		estoqueDao.removerEstoque("EST001", 15); // Deve lançar exceção
	}
}
