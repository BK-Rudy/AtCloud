package br.edu.infnet.cloud.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.cloud.model.domain.Cotacao;
import br.edu.infnet.cloud.model.repository.CotacaoRepository;

@Service
public class CotacaoService {
	
	@Autowired
	private CotacaoRepository cotacaoRepository;
	
	public Cotacao incluir(Cotacao cotacao) {
		
		return cotacaoRepository.save(cotacao);
	}
	
	public void excluir(Integer key) {
		cotacaoRepository.deleteById(key);
	}
	
	public Cotacao obterCotacaoById(Integer key) {
		
		return cotacaoRepository.findById(key).get();
	}
	
	public List<Cotacao> obterLista() {
		
		return cotacaoRepository.findAll();
	}
}