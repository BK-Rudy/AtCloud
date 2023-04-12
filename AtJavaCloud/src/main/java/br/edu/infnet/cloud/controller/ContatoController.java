package br.edu.infnet.cloud.controller;

import java.util.ArrayList;
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
import br.edu.infnet.cloud.model.service.ContatoService;


@RestController
@RequestMapping("/contatos")
public class ContatoController {

	@Autowired
	private ContatoService contatoService;

	@GetMapping(value = "/listar")
	public List<Contato> obterLista() {
		
		return contatoService.obterLista();
	}

	@GetMapping(value = "/{id}/listar")
	public Contato obterProduto(@PathVariable Integer id) {
		
		return contatoService.obterContatoById(id);
	}

	@PostMapping(value = "/cadastrar")
	public Contato incluir(@RequestBody Contato contato) {
		
		return contatoService.incluir(contato);
	}

	@PutMapping(value = "/{id}/editar")
	    public Contato atualizar(
	    		@PathVariable Integer id,
	    		@RequestBody Map<String, String> contato) {
		
		Contato contatoOld = contatoService.obterContatoById(id);
		contatoOld.setEmail(contato.get("email"));
		contatoOld.setTelefone(contato.get("telefone"));
		
		return contatoService.incluir(contatoOld);
	}

	@DeleteMapping(value = "/{id}/excluir")
	public void excluir(@PathVariable Integer id) {
		contatoService.excluir(id);
	}
}