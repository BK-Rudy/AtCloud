package br.edu.infnet.cloud.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.cloud.model.domain.Produto;
import br.edu.infnet.cloud.model.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto incluir(Produto produto) {
		
		return produtoRepository.save(produto);
	}
	
	public void excluir(Integer key) {
		produtoRepository.deleteById(key);
	}
	
	public Produto obterProdutoById(Integer key) {
		
		return produtoRepository.findById(key).get();
	}
	
	public List<Produto> obterLista() {
		
		return produtoRepository.findAll();
	}
}
