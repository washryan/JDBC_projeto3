package br.com.washryan;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.com.washryan.dao.IProdutoDAO;
import br.com.washryan.dao.ProdutoDAO;
import br.com.washryan.domain.Produto;
import br.com.washryan.exceptions.DAOException;
import br.com.washryan.exceptions.MaisDeUmRegistroException;
import br.com.washryan.exceptions.TableException;
import br.com.washryan.exceptions.TipoChaveNaoEncontradaException;

public class ProdutoDAOTest {
	
	private IProdutoDAO produtoDao;

	public ProdutoDAOTest() {
		produtoDao = new ProdutoDAO();
	}
	
	@After
	public void end() throws DAOException {
		Collection<Produto> list = produtoDao.buscarTodos();
		list.forEach(prod -> {
			try {
				produtoDao.excluir(prod.getCodigo());
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void pesquisarProduto() throws MaisDeUmRegistroException, TableException, TipoChaveNaoEncontradaException, DAOException {
		Produto produto = new Produto();
		produto.setCodigo("A1");
		produto.setNome("Produto Teste");
		produto.setDescricao("Descrição do produto teste");
		produto.setValor(BigDecimal.valueOf(25.50));
		produto.setCategoria("Eletrônicos");
		produtoDao.cadastrar(produto);
		
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		Assert.assertNotNull(produtoConsultado);
		Assert.assertEquals("Eletrônicos", produtoConsultado.getCategoria());
		
		produtoDao.excluir(produto.getCodigo());
	}
	
	@Test
	public void salvarProduto() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
		Produto produto = new Produto();
		produto.setCodigo("B1");
		produto.setNome("Produto Salvar");
		produto.setDescricao("Descrição do produto salvar");
		produto.setValor(BigDecimal.valueOf(15.75));
		produto.setCategoria("Casa e Jardim");
		Boolean retorno = produtoDao.cadastrar(produto);
		Assert.assertTrue(retorno);
		
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		Assert.assertNotNull(produtoConsultado);
		Assert.assertEquals("Casa e Jardim", produtoConsultado.getCategoria());
		
		produtoDao.excluir(produto.getCodigo());
	}
	
	@Test
	public void alterarProduto() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
		Produto produto = new Produto();
		produto.setCodigo("C1");
		produto.setNome("Produto Original");
		produto.setDescricao("Descrição original");
		produto.setValor(BigDecimal.valueOf(30.00));
		produto.setCategoria("Categoria Original");
		Boolean retorno = produtoDao.cadastrar(produto);
		Assert.assertTrue(retorno);
		
		Produto produtoConsultado = produtoDao.consultar(produto.getCodigo());
		Assert.assertNotNull(produtoConsultado);
		
		produtoConsultado.setNome("Produto Alterado");
		produtoConsultado.setCategoria("Categoria Alterada");
		produtoDao.alterar(produtoConsultado);
		
		Produto produtoAlterado = produtoDao.consultar(produtoConsultado.getCodigo());
		Assert.assertNotNull(produtoAlterado);
		Assert.assertEquals("Produto Alterado", produtoAlterado.getNome());
		Assert.assertEquals("Categoria Alterada", produtoAlterado.getCategoria());
		
		produtoDao.excluir(produto.getCodigo());
	}
	
	@Test
	public void buscarTodos() throws TipoChaveNaoEncontradaException, DAOException {
		Produto produto1 = new Produto();
		produto1.setCodigo("D1");
		produto1.setNome("Produto 1");
		produto1.setDescricao("Descrição 1");
		produto1.setValor(BigDecimal.valueOf(10.00));
		produto1.setCategoria("Categoria 1");
		produtoDao.cadastrar(produto1);
		
		Produto produto2 = new Produto();
		produto2.setCodigo("D2");
		produto2.setNome("Produto 2");
		produto2.setDescricao("Descrição 2");
		produto2.setValor(BigDecimal.valueOf(20.00));
		produto2.setCategoria("Categoria 2");
		produtoDao.cadastrar(produto2);
		
		Collection<Produto> list = produtoDao.buscarTodos();
		assertTrue(list != null);
		assertTrue(list.size() == 2);
		
		produtoDao.excluir(produto1.getCodigo());
		produtoDao.excluir(produto2.getCodigo());
	}
}
