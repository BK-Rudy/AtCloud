package br.edu.infnet.cloud.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.infnet.cloud.model.domain.Cotacao;
import br.edu.infnet.cloud.model.domain.Produto;
import br.edu.infnet.cloud.model.service.CotacaoService;
import br.edu.infnet.cloud.model.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CotacaoService cotacaoService;

	@GetMapping(value = "/listar")
	public List<Produto> obterLista() {
		
		return produtoService.obterLista();
	}

	@GetMapping(value = "/{id}/listar")
	public Produto obterProduto(@PathVariable Integer id) {
		
		return produtoService.obterProdutoById(id);
	}

	@PostMapping(value = "/cadastrar")
	public Produto incluir(@RequestBody Produto produto) {
		
		return produtoService.incluir(produto);
	}

	@PutMapping(value = "/{id}/editar")
	public Produto atualizar(
			@PathVariable Integer id, 
			@RequestParam(required = false) String cotacaoId,
			@RequestBody Map<String, String> produto) {
		
		Produto produtoOld = produtoService.obterProdutoById(id);
		produtoOld.setNome(produto.get("nome"));
		produtoOld.setMaterial(produto.get("material"));
		produtoOld.setPeso(produto.get("peso"));
		produtoOld.setNovoOuUsado(produto.get("novoOuUsado"));
		produtoOld.setQuantidade(Integer.parseInt(produto.get("quantidade")));
		produtoOld.setTipo(produto.get("tipo"));
		produtoOld.setImageUrl(produto.get("imageUrl"));
		
		List<Cotacao> cotacoes = new ArrayList<Cotacao>();
		
		if(cotacaoId != null) {
			
			for (String quote : cotacaoId.split(",")) {
				cotacoes.add(cotacaoService.obterCotacaoById(Integer.parseInt(quote)));
			}
			produtoOld.setCotacoes(cotacoes);
		}
		
		return produtoService.incluir(produtoOld);
	}

	@DeleteMapping(value = "/{id}/excluir")
	public void excluir(@PathVariable Integer id) {
		produtoService.excluir(id);
	}
	
	@PostMapping("/{id}/upload")
    public String produtoIncluirFoto(Model model,@PathVariable Integer id, @RequestParam("file") MultipartFile multipart) throws IOException {
        String fileName = multipart.getOriginalFilename();
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        
        System.out.println("filename: " + fileName);
        String message = "";
        
        try {
            Produto produto = produtoService.obterProdutoById(id);
            S3Util.uploadObjeto(convFile,produto.getId().toString()+produto.getNome()+".png");
            message = "Upload de arquivo realizado com sucesso!";
        } 
        
        catch (Exception ex) {
            message = "Houve um erro ao fazer o upload do arquivo: " + ex.getMessage();
        }

        return message;
    }
    
    @GetMapping(value = "/{id}/foto")
    public String baixarProdutoFoto(@PathVariable Integer id) {
        
    	try{
            Produto produto = produtoService.obterProdutoById(id);
            System.out.println(produto.getNome());
            S3Util.downloadObjeto(produto.getId().toString()+produto.getNome()+".png");
            
            return "Download da foto do produto " + produto.getNome()+ " realizado com sucesso.";
        }
    	
    	catch (Exception e){
            
    		return "Houve um erro ao tentar realizar o download da foto do produto.";
        }
    }
    
    @DeleteMapping(value = "/{id}/foto/excluir")
    public String removerProdutoFoto(@PathVariable Integer id) {
        
    	try{
            Produto produto = produtoService.obterProdutoById(id);
            System.out.println(produto.getNome());
            S3Util.excluirObjeto(produto.getId().toString()+produto.getNome()+".png");
            
            return "Exclusão da foto do produto " + produto.getNome()+ " realizada com sucesso.";
        }
    	
    	catch (Exception e){
    		
            return "Houve um erro ao tentar excluir a foto do produto.";
        }
    }
}