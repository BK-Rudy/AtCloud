package br.edu.infnet.cloud.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.cloud.model.domain.Contato;
import br.edu.infnet.cloud.model.repository.ContatoRepository;

@Service
public class ContatoService {
	
	@Autowired
	private ContatoRepository contatoRepository;
	
	public Contato incluir(Contato contato) {
		
		return contatoRepository.save(contato);
	}
	
	public void excluir(Integer key) {
		contatoRepository.deleteById(key);
	}
	
	public Contato obterContatoById(Integer key) {
		
		return contatoRepository.findById(key).get();
	}
	
	public List<Contato> obterLista() {
		
		return contatoRepository.findAll();
	}
}