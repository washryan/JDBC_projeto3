package br.com.washryan.dao;

import br.com.washryan.domain.Estoque;

public interface IEstoqueDAO extends IGenericDAO<Estoque, String> {
	
	void adicionarEstoque(String produtoCodigo, Integer quantidade);
	
	void removerEstoque(String produtoCodigo, Integer quantidade);
	
	boolean verificarDisponibilidade(String produtoCodigo, Integer quantidadeDesejada);
}
