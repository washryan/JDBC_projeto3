package br.com.washryan.services;

import br.com.washryan.domain.Cliente;
import br.com.washryan.exceptions.DAOException;
import br.com.washryan.exceptions.TipoChaveNaoEncontradaException;
import br.com.washryan.services.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {

//	Boolean cadastrar(Cliente cliente) throws TipoChaveNaoEncontradaException;
//
	Cliente buscarPorCPF(Long cpf) throws DAOException;
//
//	void excluir(Long cpf);
//
//	void alterar(Cliente cliente) throws TipoChaveNaoEncontradaException;

}
