package br.edu.infnet.cloud.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.cloud.model.domain.Fornecedor;
import br.edu.infnet.cloud.model.repository.FornecedorRepository;

@Service
public class FornecedorService {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	public Fornecedor incluir(Fornecedor fornecedor) {
		
		return fornecedorRepository.save(fornecedor);
	}
	
	public void excluir(Integer key) {
		fornecedorRepository.deleteById(key);
	}
	
	public Fornecedor obterFornecedorById(Integer key) {
		
		return fornecedorRepository.findById(key).get();
	}
	
	public List<Fornecedor> obterLista() {
		
		return fornecedorRepository.findAll();
	}
}