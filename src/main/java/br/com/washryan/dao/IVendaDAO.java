package br.com.washryan.dao;

import br.com.washryan.dao.generic.IGenericDAO;
import br.com.washryan.domain.Venda;
import br.com.washryan.exceptions.DAOException;
import br.com.washryan.exceptions.TipoChaveNaoEncontradaException;

public interface IVendaDAO extends IGenericDAO<Venda, String> {

	public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;
	
	public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;
}
