package br.edu.infnet.cloud.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.cloud.model.domain.Contato;
import br.edu.infnet.cloud.model.domain.Fornecedor;
import br.edu.infnet.cloud.model.service.ContatoService;
import br.edu.infnet.cloud.model.service.CotacaoService;
import br.edu.infnet.cloud.model.service.FornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorService fornecedorService;
	
	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private CotacaoService cotacaoService;

	@GetMapping(value = "/listar")
	public List<Fornecedor> obterLista() {
		
		return fornecedorService.obterLista();
	}

	@PostMapping(value = "/cadastrar")
	public Fornecedor incluir(
			@RequestBody Fornecedor fornecedor, 
			@RequestParam(required = false) Integer contatoId) {
		
		if(contatoId != null) fornecedor.setContato(contatoService.obterContatoById(contatoId));
		
		return fornecedorService.incluir(fornecedor);
	}

	@PutMapping(value = "/{id}/editar")
	public Fornecedor atualizar(
			@PathVariable Integer id, 
			@RequestParam(required = false) Integer cotacaoId,
			@RequestParam(required = false) Integer contatoId, 
			@RequestBody Map<String, String> fornecedor) {
		
		Fornecedor fornecedorOld = fornecedorService.obterFornecedorById(id);
		fornecedorOld.setRazaoSocial(fornecedor.get("razaoSocial"));
		fornecedorOld.setNomeFantasia(fornecedor.get("nomeFantasia"));
		fornecedorOld.setCnpj(fornecedor.get("cnpj"));
		fornecedorOld.setEmail(fornecedor.get("email"));
		fornecedorOld.setTelefone(fornecedor.get("telefone"));
		fornecedorOld.setValorCapitalSocial(Float.parseFloat(fornecedor.get("valorCapitalSocial")));

		Contato contato = contatoService.obterContatoById(contatoId);
		fornecedorOld.setContato(contato);

		if (cotacaoId != null)
			fornecedorOld.setCotacao(cotacaoService.obterCotacaoById(cotacaoId));

		return fornecedorService.incluir(fornecedorOld);
	}

	@DeleteMapping(value = "/{id}/excluir")
	public void excluir(@PathVariable Integer id) {
		fornecedorService.excluir(id);
	}
}