package br.com.washryan.services;

import br.com.washryan.dao.IProdutoDAO;
import br.com.washryan.domain.Produto;
import br.com.washryan.services.generic.GenericService;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {

	public ProdutoService(IProdutoDAO dao) {
		super(dao);
	}

}
