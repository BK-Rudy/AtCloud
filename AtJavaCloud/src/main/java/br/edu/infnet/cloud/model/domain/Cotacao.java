package br.edu.infnet.cloud.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Cotacao {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String oferta;
	
	private Float valor;
	
	private String validadeCotacao;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="produtoId")
	private Produto produto;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "fornecedorId")
	private Fornecedor fornecedor;
}
