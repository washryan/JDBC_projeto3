package br.com.washryan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.washryan.dao.generic.GenericDAO;
import br.com.washryan.domain.Estoque;
import br.com.washryan.exceptions.DAOException;
import br.com.washryan.exceptions.MaisDeUmRegistroException;
import br.com.washryan.exceptions.TableException;
import br.com.washryan.exceptions.TipoChaveNaoEncontradaException;

public class EstoqueDAO extends GenericDAO<Estoque, String> implements IEstoqueDAO {

	public EstoqueDAO() {
		super();
	}

	@Override
	public Class<Estoque> getTipoClasse() {
		return Estoque.class;
	}

	@Override
	public void atualiarDados(Estoque entity, Estoque entityCadastrado) {
		entityCadastrado.setProdutoCodigo(entity.getProdutoCodigo());
		entityCadastrado.setQuantidade(entity.getQuantidade());
		entityCadastrado.setQuantidadeMinima(entity.getQuantidadeMinima());
	}

	@Override
	public void adicionarEstoque(String produtoCodigo, Integer quantidade) {
		try {
			Estoque estoque = consultar(produtoCodigo);
			if (estoque != null) {
				estoque.adicionarQuantidade(quantidade);
				alterar(estoque);
			} else {
				// Criar novo registro de estoque
				estoque = new Estoque();
				estoque.setProdutoCodigo(produtoCodigo);
				estoque.setQuantidade(quantidade);
				estoque.setQuantidadeMinima(5); // Quantidade mínima padrão
				cadastrar(estoque);
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao adicionar estoque", e);
		}
	}

	@Override
	public void removerEstoque(String produtoCodigo, Integer quantidade) {
		try {
			Estoque estoque = consultar(produtoCodigo);
			if (estoque != null) {
				estoque.removerQuantidade(quantidade);
				alterar(estoque);
			} else {
				throw new RuntimeException("Produto não encontrado no estoque");
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao remover estoque", e);
		}
	}

	@Override
	public boolean verificarDisponibilidade(String produtoCodigo, Integer quantidadeDesejada) {
		try {
			Estoque estoque = consultar(produtoCodigo);
			return estoque != null && estoque.getQuantidade() >= quantidadeDesejada;
		} catch (Exception e) {
			return false;
		}
	}
}
