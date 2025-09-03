package br.com.washryan.domain;

import anotacao.ColunaTabela;
import anotacao.Tabela;
import anotacao.TipoChave;
import br.com.washryan.dao.Persistente;

@Tabela("TB_ESTOQUE")
public class Estoque implements Persistente {
	
	@ColunaTabela(dbName = "id", setJavaName = "setId")
	private Long id;
	
	@TipoChave("getProdutoCodigo")
	@ColunaTabela(dbName = "produto_codigo", setJavaName = "setProdutoCodigo")
	private String produtoCodigo;
	
	@ColunaTabela(dbName = "quantidade", setJavaName = "setQuantidade")
	private Integer quantidade;
	
	@ColunaTabela(dbName = "quantidade_minima", setJavaName = "setQuantidadeMinima")
	private Integer quantidadeMinima;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(String produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
	
	public void adicionarQuantidade(Integer qtd) {
		this.quantidade += qtd;
	}
	
	public void removerQuantidade(Integer qtd) {
		if (this.quantidade >= qtd) {
			this.quantidade -= qtd;
		} else {
			throw new IllegalArgumentException("Quantidade insuficiente em estoque");
		}
	}
	
	public boolean isEstoqueBaixo() {
		return this.quantidade <= this.quantidadeMinima;
	}
}
