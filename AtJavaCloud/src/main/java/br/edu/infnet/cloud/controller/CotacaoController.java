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

import br.edu.infnet.cloud.model.domain.Cotacao;
import br.edu.infnet.cloud.model.service.CotacaoService;
import br.edu.infnet.cloud.model.service.FornecedorService;
import br.edu.infnet.cloud.model.service.ProdutoService;

@RestController
@RequestMapping("/cotacoes")
public class CotacaoController {

	@Autowired
	private CotacaoService cotacaoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FornecedorService fornecedorService;

	@GetMapping(value = "/listar")
	public List<Cotacao> obterLista() {
		
		return cotacaoService.obterLista();
	}

	@PostMapping(value = "/cadastrar")
	public Cotacao incluir(
			@RequestBody Cotacao cotacao,
			@RequestParam(required = false) Integer fornecedorId, 
			@RequestParam(required = false) Integer produtoId) {
		
		if(fornecedorId != null) cotacao.setFornecedor(fornecedorService.obterFornecedorById(fornecedorId));
		
		if(produtoId != null) cotacao.setProduto(produtoService.obterProdutoById(produtoId));
		
		return cotacaoService.incluir(cotacao);
	}

	@PutMapping(value = "/{id}/editar")
	public Cotacao atualizar(
			@PathVariable Integer id, 
			@RequestParam(required = false) Integer fornecedorId,
			@RequestParam(required = false) Integer produtoId, 
			@RequestBody Map<String, String> cotacao) {
		
		Cotacao cotacaoOld = cotacaoService.obterCotacaoById(id);

		cotacaoOld.setOferta(cotacao.get("oferta"));
		cotacaoOld.setValor(Float.parseFloat(cotacao.get("valor")));
		cotacaoOld.setValidadeCotacao(cotacao.get("validadeCotacao"));

		if (fornecedorId != null)
			cotacaoOld.setFornecedor(fornecedorService.obterFornecedorById(fornecedorId));

		if (produtoId != null)
			cotacaoOld.setProduto(produtoService.obterProdutoById(produtoId));

		return cotacaoService.incluir(cotacaoOld);
	}

	@DeleteMapping(value = "/{id}/excluir")
	public void excluir(@PathVariable Integer id) {
		cotacaoService.excluir(id);
	}
}